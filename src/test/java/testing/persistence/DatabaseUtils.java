package testing.persistence;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Util class to write data in database in the given part of test
 * before testing reading method
 */
public class DatabaseUtils {
	public static void insertData(
			TestEntityManager entityManager, Object entity) 
	{
		entityManager.persist(entity);
	}
	
	public static void insertData(
			TestEntityManager entityManager, Collection<?> entities) 
	{
		insertData(entityManager, entities.stream());
	}
	
	public static void insertData(
			TestEntityManager entityManager, Stream<?> entities) 
	{
		entities.forEach(entityManager::persist);
	}
	
	public static void insertDataFlushAndClearCache(
			TestEntityManager entityManager, Object entity) 
	{
		insertData(entityManager, entity);
		flushAndClearCache(entityManager);
	}
	
	public static void insertDataFlushAndClearCache(
			TestEntityManager entityManager, Collection<?> entities) 
	{
		insertData(entityManager, entities);
		flushAndClearCache(entityManager);
	}
	
	public static void insertDataFlushAndClearCache(
			TestEntityManager entityManager, Stream<?> entities) 
	{
		insertData(entityManager, entities);
		flushAndClearCache(entityManager);
	}

	private static void flushAndClearCache(TestEntityManager entityManager) {
		entityManager.flush();
		entityManager.clear();
	}
}
