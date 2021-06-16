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
	List<MovieSimple> getByTitleYear(String title, int year);
	List<MovieSimple> getByYearRange(int minYear, int maxYear);
	List<MovieSimple> getByYearLess(int maxYear);
	List<MovieSimple> getByYearGreater(int minYear);
	MovieStatistics getStatistics();
	// UPDATE
	Optional<MovieDetail> update(MovieDetail movie);
	// DELETE
	Optional<MovieDetail> deleteMovieById(int id);
}
