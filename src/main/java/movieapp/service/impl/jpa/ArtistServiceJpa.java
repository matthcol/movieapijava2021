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
		return artistRepository.findAll().stream()
				.map(ae-> modelMapper.map(ae, ArtistSimple.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<ArtistSimple> getById(int id) {
		return artistRepository.findById(id) // fetch opt entity artist
			.map(artistEntity -> modelMapper.map(artistEntity, ArtistSimple.class)); // convert entity->dto
	}
	
	@Override
	public List<ArtistSimple> getByName(String name) {
		return artistRepository.findByNameEndingWithIgnoreCase(name)
				.map(ae-> modelMapper.map(ae, ArtistSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public ArtistSimple add(ArtistSimple artist) {
		Artist artistEntityFromRepo = artistRepository.save(
				// convert dto param to entity
				modelMapper.map(artist, Artist.class));
		// convert dto param to entity
		return modelMapper.map(artistEntityFromRepo, ArtistSimple.class); // convert entity to dto result
	}

	@Override
	public Optional<ArtistSimple> update(ArtistSimple artist) {
		return artistRepository.findById(artist.getId())
				.map(ae -> {
					modelMapper.map(artist, ae);
					return modelMapper.map(ae, ArtistSimple.class);
				});
	}

	@Override
	public Optional<ArtistSimple> delete(int id) {
		return artistRepository.findById(id)
				.map(ae -> {
					artistRepository.deleteById(id);
					return modelMapper.map(ae, ArtistSimple.class);
				});
	}


}

