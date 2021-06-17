package movieapp.persistence.repository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import movieapp.persistence.entity.Artist;

// NB: queries with projection
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
public interface ArtistRepository extends JpaRepository<Artist, Integer>{
	
	Set<Artist> findByNameIgnoreCase(String name);
	Stream<Artist> findByNameEndingWithIgnoreCase(String name);
	
	@Query("select a from Artist a where extract(year from a.birthdate) = :year")
	Stream<Artist> findByBirthdateYear(int year);
	
	Stream<Artist> findByBirthdate(LocalDate birthdate);
		
}
