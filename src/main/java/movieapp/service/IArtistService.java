package movieapp.service;

import java.util.List;
import java.util.Optional;

import movieapp.dto.ArtistSimple;

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
}
