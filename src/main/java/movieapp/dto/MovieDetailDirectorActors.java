package movieapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MovieDetailDirectorActors extends MovieDetail {
	@Getter @Setter private ArtistSimple director;
	@Getter @Setter private List<ArtistSimple> actors;
	
	//@Builder
	public MovieDetailDirectorActors(Integer id, String  title, 
			Integer year, String posterUri, Integer duration, String synopsis,
			ArtistSimple director, List<ArtistSimple> actors)
	{
	    super(id, title, year, posterUri, duration, synopsis);
	    this.director = director;
	    this.actors = actors;
	} 
}
