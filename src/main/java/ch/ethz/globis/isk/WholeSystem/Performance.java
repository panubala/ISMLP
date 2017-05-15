package ch.ethz.globis.isk.WholeSystem;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Filters;

import ch.ethz.globis.isk.Zoo.PanelErrorMessage;
import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.zoodb.ZooConference;
import ch.ethz.globis.isk.domain.zoodb.ZooConferenceEdition;
import ch.ethz.globis.isk.domain.zoodb.ZooInProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;
import ch.ethz.globis.isk.domain.zoodb.ZooPublisher;
import ch.ethz.globis.isk.domain.zoodb.ZooSeries;
import ch.ethz.globis.isk.mongodb.Database;
import ch.ethz.globis.isk.mongodb.Table;
import ch.ethz.globis.isk.util.Pair;
import ch.ethz.globis.isk.xmldb.api.BaseXClient.Query;
import ch.ethz.globis.isk.domain.mongodb.*;
import ch.ethz.globis.isk.xmldb.*;

public class Performance {

	private static ch.ethz.globis.isk.mongodb.Database db2;
	
	private static ch.ethz.globis.isk.xmldb.Database db3;

	private static String filter = "";

	public static void main(String[] args) throws IOException {
		
		db2 = new ch.ethz.globis.isk.mongodb.Database("MongoDb"); 
		
		db3 = new ch.ethz.globis.isk.xmldb.Database();
		
//		zPublications("3");
//		mPublications("3");
		
//		mProceedings("3");

	}

	private static void utils(ArrayList<Long> ztimeList, ArrayList<Long> zmemoryList, String name) throws IOException {

		long tsum = 0;

		Writer out = new BufferedWriter(new FileWriter("/Users/panuyabalasuntharam/Desktop/InfSys/" + name + ".txt"));
		out.append("Time:" + "\n");
		for (int i = 0; i < ztimeList.size(); i++) {
			tsum = ztimeList.get(i) + tsum;
			out.append(Long.toString(ztimeList.get(i)) + "ms \n");
		}

		tsum = tsum / 30;

		long msum = 0;
		out.append("Memory:" + "\n");
		for (int i = 0; i < zmemoryList.size(); i++) {
			msum = zmemoryList.get(i) + msum;
			out.append(Long.toString(zmemoryList.get(i)) + "MB \n");
		}

		msum = msum / 30;

		out.append(
				"Average time: " + Long.toString(tsum) + "ms    " + "Average time: " + Long.toString(msum) + "MB \n");

		out.close();
		;

		System.out.println("Number of time elements: " + Integer.toString(ztimeList.size()));
		System.out.println("Number of memory elements: " + Integer.toString(zmemoryList.size()));
		System.out.println(Long.toString(tsum) + "    " + Long.toString(msum));
	}

	// Publications

	private static void zPublications(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			ch.ethz.globis.isk.Zoo.Database db = new ch.ethz.globis.isk.Zoo.Database("database", false);

			db.open();
			Collection<ZooPublication> collection = db.getWithFilter(ZooPublication.class, "");
			getObjectsAndTitle(collection, filter);
			db.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mPublications(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			
			db2.open();

			new ch.ethz.globis.isk.mongodb.Table(db2.publications, db2.publications.find().iterator(), "Publications",
					new String[] { "ID", "Title" }, new String[] { "_id", "title" }, true);
			
			db2.close();
			
			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

	
		utils(ztimeList, zmemoryList, "mPublications" + num);

	}

	private static void xPublications(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			db3.open();
			new ch.ethz.globis.isk.xmldb.Table(db3, "publications.xml", null, "Publications",
					new String[] { "ID", "Title" }, new String[] { "id", "title" }, true);
			db3.close();
			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Proceedings

	private static void zProceedings(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zProceedings" + num);

	}

	private static void mProceedings(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			db2.open();
			new ch.ethz.globis.isk.mongodb.Table(db2.publications,
					db2.publications.find(Filters.exists("publications")).iterator(), "Proceedings",
					new String[] { "ID", "Title", "Year", "Publisher", "ISBN", "Editors", "Series", "Conference Edition",
							"Publications" },
					new String[] { "_id", "title", "year", "publisher", "isbn", "editors", "series", "conferenceEdition",
							"publications" },
					true);
			db2.close();
			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "mProceedings" + num);

	}

	private static void xProceedings(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		
		utils(ztimeList, zmemoryList, "xProceedings" + num);

	}

	// InProceedings

	private static void zInProceedings(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "zInProceedings" + num);

	}

	private static void mInProceedings(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			
			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "mInProceedings" + num);

	}

	private static void xInProceedings(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Conferences

	private static void zConference(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mConference(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xConference(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Conference Edtions

	private static void zConferenceEdition(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mConferenceEdition(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xConferenceEdition(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Authors

	private static void zAuthors(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mAuthors(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xAuthors(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Publishers

	private static void zPublishers(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mPublishers(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xPublishers(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Series

	private static void zSeries(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mSeries(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xSeries(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Query 1

	private static void zQuery1(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery1(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xQuery1(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Query 2

	private static void zQuery2(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery2(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xQuery2(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Query 3

	private static void zQuery3(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery3(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xQuery3(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Query 4

	private static void zQuery4(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery4(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xQuery4(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	// Query 5
	private static void zQuery5(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery5(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xQuery5(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 6

	private static void zQuery6(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery6(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery6(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 7

	private static void zQuery7(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery7(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery7(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 8

	private static void zQuery8(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery8(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery8(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 9

	private static void zQuery9(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery9(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery9(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 10

	private static void zQuery10(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery10(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery10(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 11

	private static void zQuery11(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery11(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery11(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 12

	private static void zQuery12(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery12(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery12(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 13

	private static void zQuery13(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery13(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery13(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	// Query 14

	private static void zQuery14(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void mQuery14(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			db2.open();
			//TODO
			db2.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static void xQuery14(String num) throws IOException {
		
		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			//TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		//TODO
		utils(ztimeList, zmemoryList, "zPublications" +num);

	}

	private static Pair<Object[][], String[]> getObjectsAndTitle(Collection<?> collection, String filter) {
		if (collection.isEmpty())
			return null;

		Object[][] objects = new Object[collection.size()][];
		int i = 0;
		for (Object object : collection) {
			if (object instanceof ZooPublication) {
				ZooPublication publication = (ZooPublication) object;
				String authors = "";
				for (Person author : publication.getAuthors()) {
					authors += author.getName() + ", ";
				}
				if (authors.length() > 2)
					authors = authors.substring(0, authors.length() - 2);

				if ((publication.getTitle() != null && publication.getTitle().contains(filter))
						|| (Integer.toString(publication.getYear()).contains(filter)) || (authors.contains(filter)))
					objects[i++] = new Object[] { publication.getTitle(), publication.getYear(), authors };
			} else if (object instanceof ZooConference) {
				ZooConference conference = (ZooConference) object;
				String editions = "";
				for (ConferenceEdition edition : conference.getEditions()) {
					editions += edition.getYear() + ", ";
				}
				if (editions.length() > 2)
					editions = editions.substring(0, editions.length() - 2);

				if ((conference.getName() != null && conference.getName().contains(filter))
						|| (editions.contains(filter)))
					objects[i++] = new Object[] { conference.getName(), editions };
			} else if (object instanceof ZooConferenceEdition) {
				ZooConferenceEdition conferenceEdition = (ZooConferenceEdition) object;
				if ((conferenceEdition.getConference() != null && conferenceEdition.getConference().getName() != null
						&& conferenceEdition.getConference().getName().contains(filter))
						|| (Integer.toString(conferenceEdition.getYear()).contains(filter)))
					objects[i++] = new Object[] { conferenceEdition.getConference().getName(),
							conferenceEdition.getYear() };
			} else if (object instanceof ZooPerson) {
				ZooPerson person = (ZooPerson) object;
				String publications = "";
				for (Publication publication : person.getAuthoredPublications()) {
					publications += publication.getTitle() + ", ";
				}
				for (Publication publication : person.getEditedPublications()) {
					publications += publication.getTitle() + ", ";
				}
				if (publications.length() > 2)
					publications = publications.substring(0, publications.length() - 2);

				if ((person.getName() != null && person.getName().contains(filter)) || (publications.contains(filter)))
					objects[i++] = new Object[] { person.getName(), publications };
			} else if (object instanceof ZooPublisher) {
				ZooPublisher publisher = (ZooPublisher) object;
				String publications = "";
				for (Publication publication : publisher.getPublications()) {
					publications += publication.getTitle() + ", ";
				}
				if (publications.length() > 2)
					publications = publications.substring(0, publications.length() - 2);

				if ((publisher.getName() != null && publisher.getName().contains(filter))
						|| (publications.contains(filter)))
					objects[i++] = new Object[] { publisher.getName(), publications };
			} else if (object instanceof ZooSeries) {
				ZooSeries series = (ZooSeries) object;
				String publications = "";
				for (Publication publication : series.getPublications()) {
					publications += publication.getTitle() + ", ";
				}
				if (publications.length() > 2)
					publications = publications.substring(0, publications.length() - 2);

				if ((series.getName() != null && series.getName().contains(filter)) || (publications.contains(filter)))
					objects[i++] = new Object[] { series.getName(), publications };
			}
		}

		String[] title = null;
		if (collection.iterator().next() instanceof ZooPublication)
			title = new String[] { "Title", "Year", "Authors" };
		else if (collection.iterator().next() instanceof ZooConference)
			title = new String[] { "Name", "Editions" };
		else if (collection.iterator().next() instanceof ZooConferenceEdition)
			title = new String[] { "Conference", "Edition" };
		else if (collection.iterator().next() instanceof ZooPerson)
			title = new String[] { "Name", "Publications" };
		else if (collection.iterator().next() instanceof ZooPublisher)
			title = new String[] { "Name", "Publications" };
		else if (collection.iterator().next() instanceof ZooSeries)
			title = new String[] { "Name", "Publications" };

		return new Pair<Object[][], String[]>(objects, title);
	}

	private static void openNewTable(DefaultTableModel model) {
		////////////// Table/////////
		JFrame frame = new JFrame("Result");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setBackground(new java.awt.Color(97, 212, 195));
		panel.setForeground(new java.awt.Color(255, 255, 255));
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		JTable table = new JTable();
		table.setModel(model);
		table.setFont(new java.awt.Font("Century Gothic", 0, 12));
		table.setGridColor(new java.awt.Color(97, 212, 195));
		// table.setBackground(new java.awt.Color(97, 212, 195));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new java.awt.Color(97, 212, 195));
		table.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
		table.getTableHeader().setBorder(null);
		table.getTableHeader().setFont(new java.awt.Font("Century Gothic", 0, 12));

		Container container = new Container();

		container.setLayout(new BorderLayout());
		container.setBackground(new java.awt.Color(97, 212, 195));
		container.add(table.getTableHeader(), BorderLayout.PAGE_START);
		container.add(table, BorderLayout.CENTER);
		frame.add(new JScrollPane(table));
		frame.pack();
		// frame.add(panel);
		frame.setVisible(true);
	}

}
