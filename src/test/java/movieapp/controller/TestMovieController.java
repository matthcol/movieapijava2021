package movieapp.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import movieapp.dto.MovieSimple;
import movieapp.dto.ArtistSimple;
import movieapp.dto.MovieDetail;
import movieapp.dto.MovieDetailDirectorActors;
import movieapp.service.IMovieService;

@WebMvcTest(MovieController.class) // controller to test with MockMvc client
class TestMovieController {
	private final static String BASE_URI = "/api/movies";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	IMovieService movieService; // service layer mocked

	@Test
	void testGetIdAbsent() throws Exception {
		// 1. given
		int id = 0;
		given(movieService.getById(id))
			.willReturn(Optional.empty());
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/" + id)
					.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").doesNotExist());
		// check mock service has been called
				then(movieService)
					.should()
					.getById(eq(id));
	}
	
	@Test
	void testGetIdPresent() throws Exception {
		// 1. given
		int id = 1;
		String title = "Nobody";
		Integer year = 2021;
		var movieDetailDto = new MovieDetailDirectorActors(
				id, title, year, null, null, null, 
				ArtistSimple.of("Ilya Naishuller"), 
				List.of(
						ArtistSimple.of("Bob Odenkirk"),
						ArtistSimple.of("Christopher Lloyd")));
		given(movieService.getById(id))
			.willReturn(Optional.of(movieDetailDto));
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/" + id)	// build GET HTTP request
					.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.title").value(title))
			.andExpect(jsonPath("$.year").value(year))
			.andExpect(jsonPath("$.director").exists())
			.andExpect(jsonPath("$.actors").exists());
		// check mock service has been called
		then(movieService)
			.should()
			.getById(eq(id));
	}
	
	@Test
	void testGetByTitle() throws Exception {
		// 1. given
		int nbMovies = 2;
		var title = "The Man Who Knew Too Much";
		var moviesFromService = List.of(
				new MovieSimple(1, title, 1934, null),
				new MovieSimple(2, title, 1956, null));
		given(movieService.getByTitle(eq(title)))
			.willReturn(moviesFromService);
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/byTitle")	// build GET HTTP request
					.queryParam("t", title)
					.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", Matchers.hasSize(nbMovies)))
			.andExpect(
					jsonPath("$[*].title", 
							Matchers.everyItem(
									Matchers.is(title)
									)
					));
		then(movieService)
			.should()
			.getByTitle(eq(title));
	}
	
	@Test
	void testGetByTitleYear() throws Exception {
		// 1. given
		int nbMovies = 1;
		var title = "The Man Who Knew Too Much";
		Integer year = 1956;
		var moviesFromService = List.of(
				new MovieSimple(2, title, year, null));
		given(movieService.getByTitleYear(eq(title), eq(year)))
			.willReturn(moviesFromService);
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/byTitleYear")	// build GET HTTP request
					.queryParam("t", title)
					.queryParam("y", ""+year)
					.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", Matchers.hasSize(nbMovies)))
			.andExpect(
					jsonPath("$[*].title", 
							Matchers.everyItem(
									Matchers.is(title)
									)
					))
			.andExpect(
				jsonPath("$[*].year", 
						Matchers.everyItem(
								Matchers.is(year)
								)
				));
		then(movieService)
			.should()
			.getByTitleYear(eq(title), eq(year));
	}

	@Test
	void testGetByYearRange() throws Exception {
		// 1. given
		int nbMovies = 3;
		var title1 = "Spider-Man: Far From Home";
		Integer year1 = 2019;
		var title2 = "Wonder Woman 1984";
		Integer year2 = 2020;
		var title3 = "Nobody";
		Integer year3 = 2021;
		
		var moviesFromService = List.of(
				new MovieSimple(1, title1, year1, null),
				new MovieSimple(2, title2, year2, null),
				new MovieSimple(3, title3, year3, null));
		given(movieService.getByYearRange(eq(year1), eq(year3)))
			.willReturn(moviesFromService);
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/byYearRange")	// build GET HTTP request
					.queryParam("mi", ""+year1)
					.queryParam("ma", ""+year3)
					.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", Matchers.hasSize(nbMovies)))
			.andExpect(
					jsonPath("$[*].year", 
							Matchers.everyItem(
									Matchers.allOf(
											Matchers.greaterThanOrEqualTo(year1),
											Matchers.lessThanOrEqualTo(year3))
									)
					));
		then(movieService)
			.should()
			.getByYearRange(eq(year1),  eq(year3));
	}
	
	@Test
	void testAdd() throws Exception {
		// 1. given
		// properties for json in
		var title = "Nobody";
		Integer year = 2021;
		Integer duration = null;
		String synopsis = null;
		
		String movieJsonIn = JsonProvider.movieJson(title, year);
		// perfect response from mock service
		int id = 1;
		var movieFromService = MovieDetail.builder()
				.id(id)
				.title(title)
				.year(year)
				.build();	 
		given(movieService.add(any()))
				.willReturn(movieFromService);
		// 2. when/then
		mockMvc
			.perform(post(BASE_URI)	// build POST HTTP request
				.contentType(MediaType.APPLICATION_JSON)
				.content(movieJsonIn)
				.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.title").value(title))
			.andExpect(jsonPath("$.year").value(year))
			.andExpect(jsonPath("$.duration").value(duration))
			.andExpect(jsonPath("$.synopsis").value(synopsis));
		// check mock service has been called
		then(movieService)
			.should()
			.add(any());
	}
	
	@Test
	void testUpdate() throws Exception {
		// 1. given
		// properties for json in
		Integer id = 1;
		var title = "Nobody";
		Integer year = 2021;
		Integer duration = 120;
		String synopsis = "Un homme, un bus, ...";
				
		String movieJsonIn = JsonProvider.movieJson(id, title, year, duration, synopsis);
		// perfect response from mock service
		var movieFromService = MovieDetail.builder()
				.id(id)
				.title(title)
				.year(year)
				.duration(duration)
				.synopsis(synopsis)
				.build();	 
		given(movieService.update(any()))
				.willReturn(Optional.of(movieFromService));
		// 2. when/then
		mockMvc
			.perform(put(BASE_URI)	// build PUT HTTP request
				.contentType(MediaType.APPLICATION_JSON)
				.content(movieJsonIn)
				.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.title").value(title))
			.andExpect(jsonPath("$.year").value(year))
			.andExpect(jsonPath("$.duration").value(duration))
			.andExpect(jsonPath("$.synopsis").value(synopsis));
		// check mock service has been called
		then(movieService)
			.should()
			.update(any());
	}
	
	@Test
	void testDelete() throws Exception {
		// 1. given
		// properties for json 
		Integer id = 1;
		var title = "Nobody";
		Integer year = 2021;
		Integer duration = 120;
		String synopsis = "Un homme, un bus, ...";
				
		// perfect response from mock service
		var movieFromService = MovieDetail.builder()
				.id(id)
				.title(title)
				.year(year)
				.duration(duration)
				.synopsis(synopsis)
				.build();	 
		given(movieService.deleteMovieById(eq(id)))
				.willReturn(Optional.of(movieFromService));
		// 2. when/then
		mockMvc
			.perform(delete(BASE_URI + "/" + id)	// build DELETE HTTP request
				.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.title").value(title))
			.andExpect(jsonPath("$.year").value(year))
			.andExpect(jsonPath("$.duration").value(duration))
			.andExpect(jsonPath("$.synopsis").value(synopsis));
		// check mock service has been called
		then(movieService)
			.should()
			.deleteMovieById(eq(id));
	}

	@Test
	void testSetDirector() throws Exception {
		// 1. given
		// properties for json / dto
		String title = "Nobody";
		Integer year = 2021;
		Integer duration = null;
		String synopsis = null;
		String name = "Ilya Naishuller";
		LocalDate birthdate = LocalDate.of(1983,11,19);

		// perfect response from mock service
		int idMovie = 1;
		int idDirector = 2;
		var directorFromService = ArtistSimple.builder()
				.id(idDirector)
				.name(name)
				.birthdate(birthdate)
				.build();
		var movieFromService = MovieDetailDirectorActors.builderDA()
				.id(idMovie)
				.title(title)
				.year(year)
				.director(directorFromService)
				.build();
		var optMovieFromService = Optional.of(movieFromService);
		given(movieService.setDirector(eq(idMovie), eq(idDirector)))
				.willReturn(optMovieFromService);
		// 2. when/then
		mockMvc
				.perform(patch(BASE_URI + "/director")	// build PATCH HTTP request
						.queryParam("mid", ""+idMovie)
						.queryParam("did", ""+idDirector)
						.accept(MediaType.APPLICATION_JSON)) // + header request
				.andDo(print())	// intercept request to print
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(idMovie))
				.andExpect(jsonPath("$.title").value(title))
				.andExpect(jsonPath("$.year").value(year))
				.andExpect(jsonPath("$.director.id").exists())
				.andExpect(jsonPath("$.director.id").value(idDirector))
				.andExpect(jsonPath("$.director.name").value(name))
				.andExpect(jsonPath("$.director.birthdate").value(birthdate.toString()));
		// check mock service has been called
		then(movieService)
				.should()
				.setDirector(eq(idMovie), eq(idDirector));
	}

	@Test
	void testSetActors() throws Exception {
		// 1. given
		// properties for json / dto
		// 1.1. movie
		int idMovie = 1;
		String title = "Nobody";
		Integer year = 2021;
		Integer duration = null;
		String synopsis = null;
		// 1.2. actors
		List<Integer> idActors = List.of(5, 7, 13);
		List<String> nameActors = List.of("Bod Odenkirk", "Christopher Lloyd", "Connie Nielsen");
		List<LocalDate> birthdateActors = List.of(
			 	LocalDate.of(1962,10,22),
			 	LocalDate.of(1938,10,22),
				LocalDate.of(1965,7,3));

		// JSON body to send
		String idActorsJsonIn = JsonProvider.idsson(idActors);

		// perfect response from mock service
		var actorsFromService = IntStream.range(0,3)
				.mapToObj(index -> ArtistSimple.builder()
					.id(idActors.get(index))
					.name(nameActors.get(index))
					.birthdate(birthdateActors.get(index))
					.build())
				.collect(Collectors.toList());
		var movieFromService = MovieDetailDirectorActors.builderDA()
				.id(idMovie)
				.title(title)
				.year(year)
				.actors(actorsFromService)
				.build();
		var optMovieFromService = Optional.of(movieFromService);
		given(movieService.setActors(eq(idMovie), eq(idActors)))
				.willReturn(optMovieFromService);
		// 2. when/then
		mockMvc
				.perform(patch(BASE_URI + "/actors")	// build PATCH HTTP request
						.queryParam("mid", ""+idMovie)
						.contentType(MediaType.APPLICATION_JSON)
						.content(idActorsJsonIn)
						.accept(MediaType.APPLICATION_JSON)) // + header request
				.andDo(print())	// intercept request to print
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(idMovie))
				.andExpect(jsonPath("$.title").value(title))
				.andExpect(jsonPath("$.year").value(year))

				.andExpect(jsonPath("$.actors").isArray())
				.andExpect(jsonPath("$.actors", Matchers.hasSize(actorsFromService.size())))
				.andExpect(jsonPath("$.actors[*].id",
						Matchers.contains(idActors.toArray())))
				.andExpect(jsonPath("$.actors[*].name",
						Matchers.contains(nameActors.toArray())))
				.andExpect(jsonPath("$.actors[*].birthdate",
						Matchers.contains(birthdateActors.stream()
							.map(Object::toString)
							.collect(Collectors.toList())
							.toArray())));

		// check mock service has been called
		then(movieService)
				.should()
				.setActors(eq(idMovie), eq(idActors));
	}
}
