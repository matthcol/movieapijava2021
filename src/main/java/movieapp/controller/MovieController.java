package movieapp.controller;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import movieapp.dto.MovieDetail;
import movieapp.dto.MovieDetailDirectorActors;
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
	public Optional<MovieDetailDirectorActors> movie(@PathVariable("id") int id) {
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
	
	/**
	 * path /api/movies/byTitleYear?t=Spectre&y=2015
	 * @param title
	 * @param year
	 * @return
	 */
	@GetMapping("/byTitleYear")
	public List<MovieSimple> moviesByTitleYear(
			@RequestParam("t") String title, 
			// @RequestParam(value="y", defaultValue = "2020") int year)
			@RequestParam(value="y", required=false) Integer year)
	{
		if (Objects.isNull(year)) {
			return movieService.getByTitle(title);
		} else {
			return movieService.getByTitleYear(title, year);
		}
	}
	
	/**
	 * paths 
	 * 	/api/movies/byYearRange?mi=1950&ma=1980
	 *  /api/movies/byYearRange?mi=1950
	 *  /api/movies/byYearRange?ma=1980
	 *  @return list of movies within range, empty list if unbounded range
	 */
	@GetMapping("/byYearRange")
	@ResponseBody
	public List<MovieSimple> moviesByYearRange(
			@RequestParam(value="mi", required=false) Integer minYear,
			@RequestParam(value="ma", required=false) Integer maxYear)
	{
		if (Objects.nonNull(minYear)) {
			if (Objects.nonNull(maxYear)) {
				return movieService.getByYearRange(minYear, maxYear);
			} else {
				return movieService.getByYearGreater(minYear);
			}
		} else if (Objects.nonNull(maxYear)) {
			return movieService.getByYearLess(maxYear);
		} else {
			return List.of();
		}
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
	
	// PUT request
	
	/**
	 * path /api/movies
	 * @param movie
	 * @return
	 */
	@PutMapping
	public Optional<MovieDetail> updateMovie(@RequestBody MovieDetail movie) {
		return movieService.update(movie);
	}
	
	/**
	 * path /api/movies/director?mid=1&did=3
	 * @param idMovie
	 * @param idDirector
	 * @return
	 */
	@PutMapping("/director")
	public Optional<MovieDetailDirectorActors> setDirector(
			@RequestParam("mid") int idMovie, 
			@RequestParam("did") int idDirector) 
	{
		return movieService.setDirector(idMovie, idDirector);	
	}

	/**
	 * path /api/movies/actors?mid=1
	 * body json : [33, 4, 42]
	 * @param idMovie
	 * @param idDirector
	 * @return
	 */
	@PutMapping("/actors")
	public Optional<MovieDetailDirectorActors> setActors(
			@RequestParam("mid") int idMovie, 
			@RequestBody List<Integer> idActors) 
	{
		return movieService.setActors(idMovie, idActors);	
	}



	// DELETE request 
	
	/**
	 * url /api/movies/1
	 * @param id id of movie to delete
	 * @return movie deleted
	 */
	@DeleteMapping("/{id}")
	public Optional<MovieDetail> deleteMovieById(@PathVariable("id") int id) {
		return movieService.deleteMovieById(id);
	}
}
