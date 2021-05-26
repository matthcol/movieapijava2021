package movieapp.persistence.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.data.util.Pair;

import movieapp.persistence.entity.Movie;

/**
 * Test util class to provide Movie entities
 *
 */
public class MovieProvider {
	
	public static List<Movie> moviesGoodOnesOneBad(
			List<String> goodTitles, List<Integer> goodYears, 
			String otherTitle, int otherYear) 
	{
	
		var res = IntStream.range(0, goodTitles.size())
			.mapToObj(i -> new Movie(goodTitles.get(i), goodYears.get(i), null))
			.collect(Collectors.toCollection(ArrayList::new));
		res.add(new Movie(otherTitle, otherYear, null));
		return res;
	}
	

}
