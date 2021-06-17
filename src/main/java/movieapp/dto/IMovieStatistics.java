package movieapp.dto;

public interface IMovieStatistics {
	long getCount();
	Integer getMinYear();
	Integer getMaxYear();
	int getTotalDuration();
	Double getAverageDuration();
	Integer getMinDuration();
	Integer getMaxDuration();
	Integer getMinTitleLength();
	Integer getMaxTitleLength();
}
