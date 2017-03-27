package ch.ethz.globis.isk.domain.zoodb;

import java.util.ArrayList;
import java.util.Collections;
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
	

	public ZooPublication() {
		zooActivateWrite();
		authors = new ArrayList<>();
	}
	
	public ZooPublication(String id, String title, List<Person> authors, int year, String electronicEdition) {
		zooActivateWrite();
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.year = year;
		this.electronicEdition = electronicEdition;
	}
	
	@Override
	public String getId() {
		zooActivateRead();
		return id;
	}

	@Override
	public void setId(String id) {
		zooActivateWrite();
		this.id = id;

	}

	@Override
	public String getTitle() {
		zooActivateRead();
		return title;
	}

	@Override
	public void setTitle(String title) {
		zooActivateWrite();
		this.title = title;

	}

	@Override
	public List<Person> getAuthors() {
		zooActivateRead();
		return Collections.unmodifiableList(authors);
	}

	@Override
	public void addAuthor(Person author) {
		zooActivateRead();
		authors.add(author);
	}

	@Override
	public void setAuthors(List<Person> authors) {
		zooActivateWrite();
		this.authors = authors;
	}

	@Override
	public int getYear() {
		zooActivateRead();
		return year;
	}

	@Override
	public void setYear(int year) {
		zooActivateWrite();
		this.year = year;
	}

	@Override
	public String getElectronicEdition() {
		zooActivateRead();
		return electronicEdition;
	}

	@Override
	public void setElectronicEdition(String electronicEdition) {
		zooActivateWrite();
		this.electronicEdition = electronicEdition;
	}

}
