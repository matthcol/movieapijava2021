package movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString 
public class MovieDetail extends MovieSimple {
	@Getter @Setter private Integer duration;
	private String synopsis;
	private String posterUri;
}
