package testing;

import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions {
	public static
	void assertSizeEquals(Collection<?> expected, Collection<?> actual, String message) 
	{
		assertEquals(expected.size(), actual.size(), message);
	}
	
	public static
	void assertSizeEquals(Collection<?> expected, Collection<?> actual) {
		assertSizeEquals(expected, actual, "size");
	}
	
	public static <T>
	void assertAllEquals(T expected, Collection<? extends T> actual, String message) {
		assertAll(actual.stream().map(
				t -> ()->assertEquals(expected, t, message)));
	}
	
	public static<T>
	void assertAllTrue(
			Collection<? extends T> coll, 
			Predicate<? super T> predicate,
			Function<? super T, String> messageSupplier)
	{
		assertAll(coll.stream().map(
			t -> ()->assertTrue(predicate.test(t), messageSupplier.apply(t))));
	}
	
	public static <T extends Comparable<? super T>>
	void assertCollectionUniqueElementEquals(
			Collection<? extends T> expected, Collection<? extends T> actual,
			String message) 
	{
		var sortedExpected = new TreeSet<>(expected);
		var sortedActual = new TreeSet<>(actual);
		assertIterableEquals(sortedExpected, sortedActual, message);
	}


}
