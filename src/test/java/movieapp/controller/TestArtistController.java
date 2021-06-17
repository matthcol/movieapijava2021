package movieapp.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import movieapp.dto.ArtistSimple;
import movieapp.service.IArtistService;

@WebMvcTest(ArtistController.class) // controller to test with MockMvc client
class TestArtistController {
	private final static String BASE_URI = "/api/artists";
	
	@Autowired
	MockMvc mockMvc; // client to perform http request to controller
	
	@MockBean
	IArtistService artistService; // service layer mocked

	@Test
	void testGetIdAbsent() throws Exception {
		// 1. given
		int id = 0;
		given(artistService.getById(id))
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
				then(artistService)
					.should()
					.getById(eq(id));
	}
	
	@Test
	void testGetIdPresent() throws Exception {
		// 1. given
		int id = 1;
		String name = "Will Smith";
		LocalDate birthdate = LocalDate.of(1968, 9, 25);
		ArtistSimple artistSimpleDto = new ArtistSimple(id, name, birthdate);
		given(artistService.getById(id))
			.willReturn(Optional.of(artistSimpleDto));
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/" + id)	// build GET HTTP request
					.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value(name))
			.andExpect(jsonPath("$.birthdate").value(birthdate.toString())); // ISO Format
		// check mock service has been called
		then(artistService)
			.should()
			.getById(eq(id));
	}
	
	@Test
	void testGetByName() throws Exception {
		// 1. given
		int nbArtist = 3;
		String name = "McQueen";
		List<ArtistSimple> artistsFromService = List.of(
				new ArtistSimple(1, "Steve McQueen", LocalDate.of(1930, 3, 24)),
				new ArtistSimple(2, "Steve McQueen", LocalDate.of(1969, 10, 9)),
				new ArtistSimple(3, "Steven R. McQueen", null));
		given(artistService.getByName(eq(name)))
			.willReturn(artistsFromService);
		// 2. when/then
		mockMvc
			.perform(get(BASE_URI + "/byName")	// build GET HTTP request
					.queryParam("n", name)
					.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", Matchers.hasSize(3)))
			.andExpect(
					jsonPath("$[*].name", 
							Matchers.everyItem(
									//Matchers.is("Steve McQueen")
									Matchers.endsWithIgnoringCase(name)
									)
					//jsonPath("$[0].name", Matchers.is("Steve McQueen")
					));
		then(artistService)
			.should()
			.getByName(eq(name));
	}
	
	@Test
	void testAdd() throws Exception {
		// 1. given
		// properties for json
		String name = "Will Smith";
		LocalDate birthdate = LocalDate.of(1968, 9, 25);
		String artistJsonIn = JsonProvider.artistJson(name, birthdate);
		// perfect response from mock service
		int id = 1;
		given(artistService.add(any()))
				.willReturn(new ArtistSimple(id, name, birthdate));
		// 2. when/then
		mockMvc
			.perform(post(BASE_URI)	// build POST HTTP request
				.contentType(MediaType.APPLICATION_JSON)
				.content(artistJsonIn)
				.accept(MediaType.APPLICATION_JSON)) // + header request
			.andDo(print())	// intercept request to print
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value(name))
			.andExpect(jsonPath("$.birthdate").value(birthdate.toString())); // ISO Format
		// check mock service has been called
		then(artistService)
			.should()
			.add(any());
	}


}
