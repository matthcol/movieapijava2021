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
		return movieRepository.findAll()
				.stream()
				.map(me -> modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
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
	public List<MovieSimple> getByTitleYear(String title, int year) {
		return movieRepository.findByTitleAndYearOrderByYear(title, year)
				.map(me->modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MovieSimple> getByYearRange(int minYear, int maxYear) {
		return movieRepository.findByYearBetweenOrderByYear(minYear, maxYear)
				.map(me->modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());	
	}

	@Override
	public List<MovieSimple> getByYearLess(int maxYear) {
		return movieRepository.findByYearLessThanEqual(maxYear)
				.map(me -> modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MovieSimple> getByYearGreater(int minYear) {
		return movieRepository.findByYearGreaterThanEqual(minYear)
				.map(me -> modelMapper.map(me, MovieSimple.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public MovieStatistics getStatistics() {
		return movieRepository.statisticsDto();
	}

	// UPDATE METHODS
	
	@Override
	public Optional<MovieDetail> update(MovieDetail movie) {
		return movieRepository.findById(movie.getId())
				.map(me-> {
					// update entity with DTO
					modelMapper.map(movie, me);
					// convert back entity to DTO
					return modelMapper.map(me, MovieDetail.class);
				});
	}

	// DELETE METHODS
	
	@Override
	public Optional<MovieDetail> deleteMovieById(int id) {
		return movieRepository.findById(id)
				.map(me->{
					movieRepository.delete(me);
					return modelMapper.map(me, MovieDetail.class);
				});
	}

}
