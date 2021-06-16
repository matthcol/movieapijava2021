package movieapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString
public class MovieSimple {
	@Getter @Setter private Integer id;
	@NonNull @Getter @Setter private String title;
	@NonNull @Getter @Setter private Integer year;
	@Getter @Setter private String posterUri;
}
