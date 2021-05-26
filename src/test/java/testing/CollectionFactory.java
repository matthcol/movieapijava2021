package testing;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.function.Function;

public class CollectionFactory {
	public static <T extends Comparable<? super T>>
	NavigableSet<T> of(Collection<? extends T> coll) {
		return new TreeSet<>(coll);
	}
	
	public static <T>
	NavigableSet<T> of(Collection<? extends T> coll, Comparator<? super T> comparator) 
	{
		NavigableSet<T> res =  new TreeSet<>(comparator);
		res.addAll(coll);
		return res;
	}
	
	public static <T, U extends Comparable<? super U>>
	NavigableSet<T> of(
			Collection<? extends T> coll, 
			Function<? super T, ? extends U> keyExtractor) 
	{
		Comparator<T> comparator = Comparator.comparing(keyExtractor);
		return of(coll, comparator);
	}
	
	public static <T, U extends Comparable<? super U>, V extends Comparable<? super V>>
	NavigableSet<T> of(
			Collection<? extends T> coll, 
			Function<? super T, ? extends U> keyExtractor1,
			Function<? super T, ? extends V> keyExtractor2) 
	{
//		var comparator = Comparator
//				.comparing(keyExtractor1)
//				.thenComparing(keyExtractor1);
		return null; //of(coll, comparator);
	}
}
