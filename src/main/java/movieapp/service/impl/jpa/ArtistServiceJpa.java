package movieapp.service.impl.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import movieapp.dto.ArtistSimple;
import movieapp.persistence.entity.Artist;
import movieapp.persistence.repository.ArtistRepository;
import movieapp.service.IArtistService;

@Service
@Transactional
public class ArtistServiceJpa implements IArtistService{

	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ArtistSimple> getAll() {
		// TODO
		return List.of();
	}
	
	@Override
	public Optional<ArtistSimple> getById(int id) {
		// TODO
		return Optional.empty();
	}
	
	@Override
	public List<ArtistSimple> getByName(String name) {
		// TODO
		return List.of();
	}

	@Override
	public ArtistSimple add(ArtistSimple artist) {
		// TODO
		return null;
	}

	@Override
	public Optional<ArtistSimple> update(ArtistSimple artist) {
		// TODO
		return Optional.empty();
	}

	@Override
	public Optional<ArtistSimple> delete(int id) {
		// TODO
		return Optional.empty();
	}


}

