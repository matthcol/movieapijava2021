package movieapp.service;

import java.util.List;
import java.util.Optional;

import movieapp.dto.ArtistSimple;
import movieapp.dto.IArtistStatistics;
import org.springframework.web.bind.annotation.PathVariable;

public interface IArtistService {
	// READ
	List<ArtistSimple> getAll();
	Optional<ArtistSimple> getById(int id);
	List<ArtistSimple> getByName(String name);
	// CREATE
	ArtistSimple add(ArtistSimple artist);
	// UPDATE
	Optional<ArtistSimple> update(ArtistSimple artist);
	// DELETE
	Optional<ArtistSimple> delete(int id);
	IArtistStatistics statsArtists( int id);
}
