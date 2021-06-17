package movieapp.service;

import java.util.List;
import java.util.Optional;

import movieapp.dto.IMovieStatistics;
import movieapp.dto.IMovieYearCount;
import movieapp.dto.MovieDetail;
import movieapp.dto.MovieDetailDirectorActors;
import movieapp.dto.MovieSimple;
import movieapp.dto.MovieStatistics;

public interface IMovieService {
	// CREATE
	MovieDetail add(MovieDetail movie);

	// READ
	Optional<MovieDetailDirectorActors> getById(int id);
	List<MovieSimple> getAll();
	List<MovieSimple> getByTitle(String title);
	List<MovieSimple> getByTitleYear(String title, int year);
	List<MovieSimple> getByYearRange(int minYear, int maxYear);
	List<MovieSimple> getByYearLess(int maxYear);
	List<MovieSimple> getByYearGreater(int minYear);
	IMovieStatistics getStatistics();
	List<IMovieYearCount> getCountMovieByYear(int yearMin, int CountMin);

	// UPDATE
	Optional<MovieDetail> update(MovieDetail movie);
	Optional<MovieDetailDirectorActors> setDirector(int idMovie, int idDirector);
	Optional<MovieDetailDirectorActors> setActors(int idMovie, List<Integer> idActors);

	// DELETE
	Optional<MovieDetail> deleteMovieById(int id);
}
