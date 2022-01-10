package movieapp.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Builder
@Data // @ToString + @Getter/@Setter + @EqualsAndHashCode
public class ArtistSimple {
	private Integer id;
	@NonNull private String name;
	private LocalDate birthdate;
}
