package movieapp.persistence.entity;

import java.time.LocalDate;
//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "stars")
public class Artist {
	private Integer id;
	private String name; // required 

	// Date, Calendar, LocalDate (J8)
	private LocalDate birthdate;
	private LocalDate deathdate;
	
	public Artist() {
		super();
	}
	
	public Artist(String name) {
		this(name, null, null);
	}
	
	public Artist(String name, LocalDate birthdate) {
		this(name, birthdate, null);
	}

	public Artist(String name, LocalDate birthdate, LocalDate deathdate) {
		super();
		this.name = name;
		this.birthdate = birthdate;
		this.deathdate = deathdate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable=false, length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable=true)
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append(name)
			.append("(")
			.append(birthdate)
			.append(", ")
			.append(deathdate)
			.append(")#")
			.append(id)
			.toString();
	}

}
