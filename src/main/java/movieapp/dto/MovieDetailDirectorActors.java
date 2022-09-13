package movieapp.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MovieDetailDirectorActors extends MovieDetail {
	@Getter @Setter private ArtistSimple director;
	@Singular
	@Getter @Setter private List<ArtistSimple> actors;
	
	@Builder(builderMethodName="builderDA")
	public MovieDetailDirectorActors(Integer id, String  title, 
			Integer year, String posterUri, Integer duration, String synopsis,
			ArtistSimple director, List<ArtistSimple> actors)
	{
	    super(id, title, year, posterUri, duration, synopsis);
	    this.director = director;
	    this.actors = actors;
	} 
}
