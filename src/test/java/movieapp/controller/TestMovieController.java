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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import movieapp.dto.MovieSimple;
import movieapp.dto.MovieDetail;
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
		var movieDetailDto = MovieDetail.builder()
				.id(id)
				.title(title)
				.year(year)
				.build();
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
			.andExpect(jsonPath("$.year").value(year)); 
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
				new MovieSimple(1, title, 1934),
				new MovieSimple(2, title, 1956));
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
			.update(any());
	}
}
