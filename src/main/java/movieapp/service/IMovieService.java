package movieapp.service;

import java.util.List;
import java.util.Optional;

import movieapp.dto.MovieDetail;
import movieapp.dto.MovieSimple;
import movieapp.dto.MovieStatistics;

public interface IMovieService {
	// CREATE
	MovieDetail add(MovieDetail movie);
	// READ
	Optional<MovieDetail> getById(int id);
	List<MovieSimple> getAll();
	List<MovieSimple> getByTitle(String title);
	MovieStatistics getStatistics();
}
