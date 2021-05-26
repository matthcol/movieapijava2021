package movieapp.service.impl.jpa;

// java
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// converter
import org.modelmapper.ModelMapper;

// spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service interface
import movieapp.service.IMovieService;

// Dto
import movieapp.dto.MovieDetail;
import movieapp.dto.MovieSimple;
import movieapp.dto.MovieStatistics;

// Entity + repository
import movieapp.persistence.entity.Movie;
import movieapp.persistence.repository.MovieRepository;


@Service
@Transactional
public class MovieServiceJpa implements IMovieService {
	@Autowired
	MovieRepository movieRepository; 
	
	@Autowired
	ModelMapper modelMapper;

	// CREATE method
	
	@Override
	public MovieDetail add(MovieDetail movie) {
		// convert DTO to entity
		Movie movieEntityIn = modelMapper.map(movie, Movie.class);
		// persist entity
		Movie movieEntityOut = movieRepository.save(movieEntityIn);
		// convert entity back to DTO
		MovieDetail movieDtoRes = modelMapper.map(movieEntityOut, MovieDetail.class);
		// return DTO completed (id, default values, ...)
		return movieDtoRes;
	}
	
	
	// READ methods
	
	@Override
	public List<MovieSimple> getAll() {
		// find entities
		List<Movie> moviesEntity = movieRepository.findAll();
		// convert entities to DTOs
		List<MovieSimple> moviesDto = moviesEntity.stream()
			.map(me -> modelMapper.map(me, MovieSimple.class))
			.collect(Collectors.toList());
		// return DTOs
		return moviesDto;
		// same thing without intermediate variables
//		return movieRepository.findAll()
//				.stream()
//				.map(me -> modelMapper.map(me, MovieSimple.class))
//				.collect(Collectors.toList());
	}

	@Override
	public Optional<MovieDetail> getById(int id) {
		return movieRepository.findById(id)
				.map(me -> modelMapper.map(me, MovieDetail.class));
	}


	@Override
	public List<MovieSimple> getByTitle(String title) {
		return movieRepository.findByTitle(title)
				.map(me->modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}


	@Override
	public MovieStatistics getStatistics() {
		return movieRepository.statisticsDto();
	}


}
