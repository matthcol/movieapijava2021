package movieapp.controller;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import movieapp.dto.MovieDetail;
import movieapp.dto.MovieSimple;
import movieapp.service.IMovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private IMovieService movieService;
	
	// GET requests
	
	/**
	 * path /api/movies
	 * @return list of all movies
	 */
	@GetMapping 
	@ResponseBody
	public List<MovieSimple> movies() {
		return movieService.getAll();
	}
	
	/**
	 * path /api/movies/1
	 * @param id id of the movie to find
	 * @return movie with this id or Optional empty if not found
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<MovieDetail> movie(@PathVariable("id") int id) {
		return movieService.getById(id);
	}
	
	/**
	 * path /api/movies/byTitle?t=Spectre
	 * @param title
	 * @return
	 */
	@GetMapping("/byTitle")
	public List<MovieSimple> moviesByTitle(@RequestParam("t") String title) {
		return movieService.getByTitle(title);
	}
	
	// POST request
	
	/**
	 * path /api/movies
	 * @param movie movie to add
	 * @return movie added and completed (id, default values)
	 */
	@PostMapping
	@ResponseBody
	public MovieDetail addMovie(@RequestBody MovieDetail movie) {
		return movieService.add(movie); 
	}

	
}
