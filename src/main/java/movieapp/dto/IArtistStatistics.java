package movieapp.dto;



public interface IArtistStatistics {
	Integer getArtistId();
	String getArtistName();
	Long getCount();
	Integer getMinYear();
	Integer getMaxYear();
}
