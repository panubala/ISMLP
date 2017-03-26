package ch.ethz.globis.isk.domain.zoodb;

import java.util.ArrayList;
import java.util.List;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;

public class ZooPublication extends ZooPC implements Publication {
	
	String id;
	String title;
	List<Person> authors;
	int year;
	String electronicEdition;
	
<<<<<<< HEAD
	public ZooPublication(String id, String title, List<Person> authors, int year, String electronicEdition){
=======
	public ZooPublication() {
		zooActivateWrite();
		authors = new ArrayList<>();
	}
	
	public ZooPublication(String id, String title, List<Person> authors, int year, String electronicEdition) {
		zooActivateWrite();
>>>>>>> origin/master
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.year = year;
		this.electronicEdition = electronicEdition;
	}
	
<<<<<<< HEAD
	

=======
>>>>>>> origin/master
	public String getId() {
		zooActivateRead();
		return id;
	}

	public void setId(String id) {
		zooActivateWrite();
		this.id = id;
<<<<<<< HEAD

=======
>>>>>>> origin/master
	}

	public String getTitle() {
		zooActivateRead();
		return title;
	}

	public void setTitle(String title) {
		zooActivateWrite();
		this.title = title;
<<<<<<< HEAD

=======
>>>>>>> origin/master
	}

	public List<Person> getAuthors() {
		zooActivateRead();
		return authors;
	}

	public void setAuthors(List<Person> authors) {
		zooActivateWrite();
		this.authors = authors;
<<<<<<< HEAD

=======
>>>>>>> origin/master
	}

	public int getYear() {
		zooActivateRead();
		return year;
	}

	public void setYear(int year) {
		zooActivateWrite();
		this.year = year;
<<<<<<< HEAD

=======
>>>>>>> origin/master
	}

	public String getElectronicEdition() {
		zooActivateRead();
		return electronicEdition;
	}

	public void setElectronicEdition(String electronicEdition) {
		zooActivateWrite();
		this.electronicEdition = electronicEdition;
<<<<<<< HEAD

=======
>>>>>>> origin/master
	}

}
