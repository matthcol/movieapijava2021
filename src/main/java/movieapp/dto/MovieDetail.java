package movieapp.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString 
public class MovieDetail extends MovieSimple {
	@Getter @Setter private Integer duration;
	@Getter @Setter private String synopsis;
	
	@Builder
	public MovieDetail(Integer id, String  title, Integer year, String posterUri, Integer duration, String synopsis){
	    super(id, title, year, posterUri);
	    this.duration = duration;
	    this.synopsis = synopsis;
	  } 

}
