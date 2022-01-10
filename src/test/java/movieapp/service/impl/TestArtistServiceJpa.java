package movieapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.any;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import movieapp.dto.ArtistSimple;
import movieapp.persistence.entity.Artist;
import movieapp.persistence.repository.ArtistRepository;
import movieapp.service.IArtistService;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TestArtistServiceJpa {

	// layer to mock
	// @Mock : pure mockito
	@MockBean // mock with spring IOC
	ArtistRepository artistRepository;
	
	// layer to test using layer mocked
	// @InjectMocks : pure mockito
	@Autowired
	IArtistService artistService;
	
	@Test
	void testGetByIdPresent() {
		// 1. given
		int id = 1;
		String name = "Will Smith";
		LocalDate birthdate = LocalDate.of(1968, 9, 25);
		// perfect answer from mock
		Artist artistEntity = new Artist(name, birthdate);
		artistEntity.setId(id);
		given(artistRepository.findById(id))
			.willReturn(Optional.of(artistEntity));
		// 2. when
		Optional<ArtistSimple> optArtistSimpleDto = artistService.getById(id);
		// 3. then
		// check mock has been called
		then(artistRepository)
			.should()
			.findById(eq(id));
		// check answer
		assertTrue(optArtistSimpleDto.isPresent());
		optArtistSimpleDto.ifPresent(
				artistSimpleDto -> assertAll(
						() -> assertEquals(id, artistSimpleDto.getId()),
						() -> assertEquals(name, artistSimpleDto.getName()),
						() -> assertEquals(birthdate, artistSimpleDto.getBirthdate())));
	}

	@Test
	void testGetByIdAbsent() {
		// 1. given : id with no corresponding data in repository
		int id = 0;
		// perfect answer from mock
		given(artistRepository.findById(id))
			.willReturn(Optional.empty());
		// 2. when
		Optional<ArtistSimple> optArtistSimpleDto = artistService.getById(id);
		// 3. then
		// check mock has been called
		then(artistRepository)
			.should()
			.findById(eq(id));
		// check answer
		assertTrue(optArtistSimpleDto.isEmpty());
	}
	
	@Test
	void testAdd() {
		// 1. given
		// DTO to add
		String name = "Will Smith";
		LocalDate birthdate = LocalDate.of(1968, 9, 25);
		ArtistSimple artistSimpleDtoIn = new ArtistSimple(null, name, birthdate);
		// Entity response from mock repository
		int id = 1;
		Artist artistEntity = new Artist(name,birthdate);
		artistEntity.setId(id);
		given(artistRepository.save(any()))
			.willReturn(artistEntity);
		// 2. when
		ArtistSimple artistSimpleDtoOut = artistService.add(artistSimpleDtoIn);
		// 3. then
		then(artistRepository)
			.should()
			.save(any());
		assertNotNull(artistSimpleDtoOut.getId());
		assertEquals(id, artistSimpleDtoOut.getId()); // from repo response
		assertEquals(name, artistSimpleDtoOut.getName()); 
		assertEquals(birthdate, artistSimpleDtoOut.getBirthdate());
	}
}








