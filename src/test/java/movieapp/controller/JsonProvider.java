package movieapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JsonProvider {
	
	public static String artistJson(String name, LocalDate birthdate) {
		// TODO: improve this solution
		return "{\"name\": \"" + name + "\", \"birthdate\": \"" + birthdate + "\"}";
	}
	
	public static String movieJson(String title, Integer year) {
		// TODO: improve this solution
		return "{\"title\": \"" + title + "\", \"year\": " + year + "}";
	}
	
	public static String movieJson(Integer id, String title, Integer year, Integer duration, String synopsis) {
		// TODO: improve this solution
		return "{\"id\": " + id 
				+ ",\"title\": \"" + title + "\", \"year\": " + year
				+ ", \"duration\": " + duration
				+ ", \"synopsis\": \"" + synopsis
				+ "\"}";
	}

	public static String idsson(List<Integer> ids) {
		return "["
				+ ids.stream().map(id -> ""+id)
						.collect(Collectors.joining(","))
				+ "]";
	}

}
