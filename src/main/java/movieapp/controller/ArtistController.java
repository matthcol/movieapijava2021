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
	
	@GetMapping()
	@ResponseBody
	List<ArtistSimple> getAll(){
		return artistService.getAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	Optional<ArtistSimple> getById(@PathVariable("id") int id){
		return artistService.getById(id);
	}
	
	@GetMapping("/byName")
	@ResponseBody
	List<ArtistSimple> getById(@RequestParam("n") String name){
		return artistService.getByName(name);
	}
	
	@PostMapping
	@ResponseBody
	ArtistSimple add(@RequestBody ArtistSimple artistSimple) {
		return artistService.add(artistSimple);
	}
	

}
