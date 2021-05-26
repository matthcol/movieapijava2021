package movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// DTO: Data Transfert Object

@AllArgsConstructor
@ToString
public class MovieStatistics {
	@Getter private long count;
	@Getter private Integer minYear;
	@Getter private Integer maxYear;
	@Getter private long totalDuration;
	@Getter private Double averageDuration;
	@Getter private Integer minDuration;
	@Getter private Integer maxDuration;
	@Getter private Integer minTitleLength;
	@Getter private Integer maxTitleLength;
}
