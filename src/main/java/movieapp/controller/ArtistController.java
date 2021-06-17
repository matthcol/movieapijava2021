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

import movieapp.dto.ArtistSimple;
import movieapp.service.IArtistService;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
	
	@Autowired
	IArtistService artistService;
	
	/**
	 * /api/artists
	 * @return
	 */
	@GetMapping()
	@ResponseBody
	List<ArtistSimple> getAll(){
		return artistService.getAll();
	}
	
	/**
	 * /api/artists/1
	 */
	@GetMapping("/{id}")
	@ResponseBody
	Optional<ArtistSimple> getById(@PathVariable("id") int id){
		return artistService.getById(id);
	}
	
	/**
	 * GET /api/artists?n=Eastwood 
	 * @param name
	 * @return
	 */
	@GetMapping("/byName")
	@ResponseBody
	List<ArtistSimple> getById(@RequestParam("n") String name){
		return artistService.getByName(name);
	}
	
	/**
	 * POST /api/artists
	 * @param artistSimple
	 * @return
	 */
	@PostMapping
	@ResponseBody
	ArtistSimple add(@RequestBody ArtistSimple artistSimple) {
		return artistService.add(artistSimple);
	}
	
}
