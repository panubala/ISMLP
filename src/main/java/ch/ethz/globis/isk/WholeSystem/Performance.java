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
import java.util.logging.Level;
import java.util.logging.Logger;

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
import ch.ethz.globis.isk.Zoo.ZooQueryExecutor;
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
import ch.ethz.globis.isk.mongodb.MongoQueryExecutor;
import ch.ethz.globis.isk.util.Triple;

public class Performance {

	private static ch.ethz.globis.isk.Zoo.Database dbZ;
	private static ch.ethz.globis.isk.mongodb.Database dbM;
	private static ch.ethz.globis.isk.xmldb.Database dbX;

	private static String filter = "";

	private static ZooQueryExecutor zooQE;
	private static MongoQueryExecutor mongoQE;
	private static BasexQueryExecutor basexQE;

	public static int ITERATIONS = 100;

	// TODO: Implement memory usage measurement
	public static void main(String[] args) throws IOException {

		// Create new database objects for all three databases.
		dbZ = new ch.ethz.globis.isk.Zoo.Database("database", false);
		dbM = new ch.ethz.globis.isk.mongodb.Database("database");
		dbX = new ch.ethz.globis.isk.xmldb.Database();

		// Suppress unimportaint mongo logging
		Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

		// Open databases
		dbZ.open();
		dbM.open();
		dbX.open();

		// Create MongoQueryExecutor class.
		zooQE = new ZooQueryExecutor(dbZ);
		mongoQE = new MongoQueryExecutor(dbM);
		basexQE = new BasexQueryExecutor(dbX);

		System.out.println("\n\t\t-----Testing query 1:-----");
		test1(30);

		System.out.println("\n\t\t-----Testing query 2:-----");
		test2(30);

		System.out.println("\n\t\t-----Testing query 3:-----");
		test3(30);

		System.out.println("\n\t\t-----Testing query 4:-----");
		test4(30);

		System.out.println("\n\t\t-----Testing query 5:-----");
		test5(30);

		System.out.println("\n\t\t-----Testing query 6:-----");
		test6(30);

		System.out.println("\n\t\t-----Testing query 7:-----");
		test7(30);

		System.out.println("\n\t\t-----Testing query 8:-----");
		test8(30);

		System.out.println("\n\t\t-----Testing query 9:-----");
		test9(30);

		System.out.println("\n\t\t-----Testing query 10:-----");
		test10(30);

		System.out.println("\n\t\t-----Testing query 11:-----");
		test11(30);

		System.out.println("\n\t\t-----Testing query 12:-----");
		test12(30);

		System.out.println("\n\t\t-----Testing query 13:-----");
		test13(30);

		System.out.println("\n\t\t-----Testing query 14:-----");
		test14(30);

		dbZ.close();
		dbM.close();
		dbX.close();

		System.out.println("\t\t-----Testing finished.-----");
	}

	// -----SIMPLER QUERIES------------------------

	private static Triple test1(int iters) {

		String id1 = "conf/icail/Berman89"; // Cutting legal loops
		String id2 = "conf/hmi/Waxman87"; // Planting the seeds
		String id3 = "conf/hmi/Lindberg87"; // In praise of computing

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");

		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query1(id1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query1(id2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query1(id3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query1(id1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query1(id2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query1(id3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query1(id1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query1(id2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query1(id3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);

	}

	private static Triple test2(int iters) {

		String title1 = "IEEE";
		String title2 = "CSCW";
		String title3 = "International";

		int beginOffset1 = 0;
		int beginOffset2 = 0;
		int beginOffset3 = 0;

		int endOffset1 = 10;
		int endOffset2 = 10;
		int endOffset3 = 10;

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query2(title1, beginOffset1, endOffset1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query2(title2, beginOffset2, endOffset2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query2(title3, beginOffset3, endOffset3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query2(title1, beginOffset1, endOffset1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query2(title2, beginOffset2, endOffset2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query2(title3, beginOffset3, endOffset3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query2(title1, beginOffset1, endOffset1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query2(title2, beginOffset2, endOffset2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query2(title3, beginOffset3, endOffset3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test3(int iters) {

		String title1 = "IEEE International Conference on Acoustics";
		String title2 = "CSCW";
		String title3 = "IEEE";

		int beginOffset1 = 0;
		int beginOffset2 = 0;
		int beginOffset3 = 0;

		int endOffset1 = 10;
		int endOffset2 = 10;
		int endOffset3 = 10;

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query3(title1, beginOffset1, endOffset1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query3(title2, beginOffset2, endOffset2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query3(title3, beginOffset3, endOffset3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query3(title1, beginOffset1, endOffset1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query3(title2, beginOffset2, endOffset2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query3(title3, beginOffset3, endOffset3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query3(title1, beginOffset1, endOffset1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query3(title2, beginOffset2, endOffset2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query3(title3, beginOffset3, endOffset3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test4(int iters) {

		String title1 = "R. D. Purdy";
		String title2 = "Alan Tyree";
		String title3 = "G. Grdy";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query4(title1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query4(title2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query4(title3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query4(title1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query4(title2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query4(title3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query4(title1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query4(title2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query4(title3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test5(int iters) {

		String author1L = "R. D. Purdy";
		String author2L = "R. D. Purdy";
		String author3L = "G. Grdy";

		String author1R = "G. Grdy";
		String author2R = "Alan Tyree";
		String author3R = "Alan Tyree";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query5(author1L, author1R);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query5(author2L, author2R);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query5(author3L, author3R);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query5(author1L, author1R);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query5(author2L, author2R);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query5(author3L, author3R);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query5(author1L, author1R);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query5(author2L, author2R);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query5(author3L, author3R);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test6(int iters) {

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query6();
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query6();
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query6();
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query6();
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query6();
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query6();
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query6();
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query6();
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query6();
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test7(int iters) {

		int yearFrom1 = 1987;
		int yearFrom2 = 1990;
		int yearFrom3 = 1987;

		int yearTo1 = 1990;
		int yearTo2 = 1995;
		int yearTo3 = 1988;

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query7(yearFrom1, yearTo1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query7(yearFrom2, yearTo2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query7(yearFrom3, yearTo3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query7(yearFrom1, yearTo1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query7(yearFrom2, yearTo2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query7(yearFrom3, yearTo3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query7(yearFrom1, yearTo1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query7(yearFrom2, yearTo2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query7(yearFrom3, yearTo3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test8(int iters) {

		String confName1 = "Concurrency 88: International Conference on Concurrency";
		String confName2 = "Proceedings of the 1990 International Conference on Parallel Processing, Volume 1";
		String confName3 = "Logic Colloquium";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query8(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query8(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query8(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query8(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query8(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query8(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query8(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query8(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query8(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	private static Triple test9(int iters) {

		String confName1 = "ICPP";
		String confName2 = "EACL";
		String confName3 = "ECHT";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query9(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query9(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query9(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query9(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query9(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query9(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query9(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query9(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query9(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	
	private static Triple test10(int iters) {

		String confName1 = "ICPP";
		String confName2 = "EACL";
		String confName3 = "ECHT";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query10(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query10(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query10(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query10(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query10(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query10(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query10(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query10(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query10(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	
	private static Triple test11(int iters) {

		String confName1 = "ICPP";
		String confName2 = "EACL";
		String confName3 = "ECHT";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query11(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query11(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query11(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query11(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query11(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query11(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query11(confName1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query11(confName2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query11(confName3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	// TODO: Check what to do with the Mongo Implementation
	private static Triple test12(int iters) {

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < iters; i++) {
			zooQE.query12();
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query12();
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query12();
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;
		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// TODO: Uncomment after fixing query.
		// // Iteration 1
		// for(int i = 0; i < iters; i++){
		// mongoQE.query12();
		// }
		//
		// // Iteration 2
		// for(int i = 0; i < iters; i++){
		// mongoQE.query12();
		// }
		//
		// // Iteration 3
		// for(int i = 0; i < iters; i++){
		// mongoQE.query12();
		// }

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;
		elapsedMongo = -1;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query12();
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query12();
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query12();
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	
	private static Triple test13(int iters) {

		String name1 = "R. D. Purdy";
		String name2 = "Alan Tyree";
		String name3 = "G. Grdy";

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query13(name1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query13(name2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query13(name3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;
		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query13(name1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query13(name2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query13(name3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query13(name1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query13(name2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query13(name3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	// TODO: Add values
	private static Triple test14(int iters) {

		int yearFrom1 = 1987;
		int yearFrom2 = 1998;
		int yearFrom3 = 1987;

		int yearTo1 = 1988;
		int yearTo2 = 2017;
		int yearTo3 = 2017;

		long startTime, stopTime;
		long elapsedZoo, elapsedMongo, elapsedBasex;
		Runtime runtime = Runtime.getRuntime();

		// ZooDB measurement
		System.out.println("Measuring ZooDB...");
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			zooQE.query14(yearFrom1, yearTo1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			zooQE.query14(yearFrom2, yearTo2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			zooQE.query14(yearFrom3, yearTo3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedZoo = (stopTime - startTime) / 3;
		System.out.println("\tMeasured average time: " + elapsedZoo + " ms");

		// MongoDB
		// measurement------------------------------------------------------------------
		System.out.println("Measuring MongoDB...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			mongoQE.query14(yearFrom1, yearTo1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			mongoQE.query14(yearFrom2, yearTo2);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			mongoQE.query14(yearFrom3, yearTo3);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedMongo = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedMongo + " ms");

		// BaseX
		// measurement-------------------------------------------------------------------
		System.out.println("Measuring BaseX...");

		// startTime = System.nanoTime();
		startTime = System.currentTimeMillis();

		// Iteration 1
		for (int i = 0; i < iters; i++) {
			basexQE.query14(yearFrom1, yearTo1);
		}

		// Iteration 2
		for (int i = 0; i < iters; i++) {
			basexQE.query14(yearFrom1, yearTo1);
		}

		// Iteration 3
		for (int i = 0; i < iters; i++) {
			basexQE.query14(yearFrom1, yearTo1);
		}

		// stopTime = System.nanoTime();
		stopTime = System.currentTimeMillis();

		elapsedBasex = (stopTime - startTime) / 3;

		System.out.println("\tMeasured average time: " + elapsedBasex + " ms");

		return new Triple(elapsedZoo, elapsedMongo, elapsedBasex);
	}

	// --------------------------------------------

	public static void main_Old(String[] args) throws IOException {

		// Create new MongoDB database object.
		dbM = new ch.ethz.globis.isk.mongodb.Database("database");

		// Open database
		dbM.open();

		// Create MongoQueryExecutor class.
		mongoQE = new MongoQueryExecutor(dbM);

		dbX = new ch.ethz.globis.isk.xmldb.Database();

		// zPublications("3");
		// mPublications("3");

		// mProceedings("3");

		// mInProceedings("3");

		// mQuery1("3", "conf/cscw/1986");

		// mQuery1("1", "conf/hmi/1987");
		// mQuery1("2", "conf/cscw/1990");
		// mQuery1("3", "conf/cscw/1986");
		//
		// mQuery2("1", "IEEE", 20, 30);
		// mQuery2("2", "CSCW", 20, 30);
		// mQuery2("3", "International", 20, 30);
		//
		//
		// mQuery3("1", "IEEE International Conference on Acoustics", 10, 20);
		// mQuery3("2", "CSCW", 76, 85);
		// mQuery3("3", "IEEE", 11, 14);
		//
		// mQuery4("1", "R. D. Purdy");
		// mQuery4("2", "Alan Tyree");
		// mQuery4("3", "G. Grdy");

		// xQuery4("1", "R. D. Purdy");
		// xQuery4("2", "Alan Tyree");
		// xQuery4("3", "G. Grdy");

		mQuery5("1", "R. D. Purdy", "Alan Tyree");
		mQuery5("2", "R. D. Purdy", "G. Grdy");
		mQuery5("3", "G. Grdy", "Alan Tyree");

		// xQuery5("1", "R. D. Purdy", "Alan Tyree");
		// xQuery5("2", "R. D. Purdy", "G. Grdy");
		// xQuery5("3", "G. Grdy", "Alan Tyree");

		mQuery6("1");
		mQuery6("2");
		mQuery6("3");

		mQuery7("1", 1987, 1990);
		mQuery7("2", 1990, 1995);
		mQuery7("3", 1987, 1988);

		// xQuery7("1", 1987, 1990);
		// xQuery7("2", 1990, 1995);
		// xQuery7("3", 1987, 1988);

		mQuery8("1", "Concurrency 88: International Conference on Concurrency");
		mQuery8("2", "Proceedings of the 1990 International Conference on Parallel Processing, Volume 1");
		mQuery8("3", "Logic Colloquium");

		// mQuery9("1", "ICPP");
		// mQuery9("2", "EACL");
		// mQuery9("3", "ECHT");

		// mQuery10("1", "ICPP");
		// mQuery10("2", "EACL");
		// mQuery10("3", "ECHT");

		// mQuery11("1", "ICPP");
		// mQuery11("2", "EACL");
		// mQuery11("3", "ECHT");

		// mQuery12("1");
		// mQuery12("2");
		// mQuery12("3");

		// mQuery13("1", "R. D. Purdy");
		// mQuery13("2", "Alan Tyree");
		// mQuery13("3", "G. Grdy");

		// mQuery14("1", 1987, 1988);
		// mQuery14("2", 1998, 2017);
		// mQuery14("3", 1987, 2017);

		// Close MongoDB database
		dbM.close();

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

			// TODO: Change method and method call (change name, delete
			// parameter);
			mongoQE.publications();

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
			dbX.open();
			new ch.ethz.globis.isk.xmldb.Table(dbX, "publications.xml", null, "Publications",
					new String[] { "ID", "Title" }, new String[] { "id", "title" }, true);
			dbX.close();
			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "xPublications" + num);

	}

	// Proceedings

	private static void zProceedings(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zProceedings" + num);

	}

	private static void mProceedings(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			dbM.open();
			new ch.ethz.globis.isk.mongodb.Table(dbM.publications,
					dbM.publications.find(Filters.exists("publications")).iterator(), "Proceedings",
					new String[] { "ID", "Title", "Year", "Publisher", "ISBN", "Editors", "Series",
							"Conference Edition", "Publications" },
					new String[] { "_id", "title", "year", "publisher", "isbn", "editors", "series",
							"conferenceEdition", "publications" },
					true);
			dbM.close();
			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mProceedings" + num);

	}

	private static void xProceedings(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

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

			// TODO

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

			dbM.open();
			new ch.ethz.globis.isk.mongodb.Table(dbM.publications,
					dbM.publications.find(Filters.exists("proceedings")).iterator(), "Inproceedings",
					new String[] { "ID", "Title", "Authors", "Proceedings" },
					new String[] { "_id", "title", "authors", "proceedings" }, true);

			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mInProceedings" + num);

	}

	private static void xInProceedings(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
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

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mConference(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			// TODO
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xConference(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xConference" + num);

	}

	// Conference Edtions

	private static void zConferenceEdition(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mConferenceEdition(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			// TODO
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xConferenceEdition(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xConferenceEdition" + num);

	}

	// Authors

	private static void zAuthors(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zAuthors" + num);

	}

	private static void mAuthors(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			// TODO
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xAuthors(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xAuthors" + num);

	}

	// Publishers

	private static void zPublishers(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mPublishers(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			// TODO
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xPublishers(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xPublishers" + num);

	}

	// Series

	private static void zSeries(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mSeries(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			// TODO
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void xSeries(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			// TODO
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xSeries" + num);

	}

	// Query 1

	private static void zQuery1(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery1(String num, String id) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			Iterator<Document> iterator = dbM.publications.find(Filters.eq("_id", id)).iterator();
			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator, "Publication by ID",
					new String[] { "ID", "Title" }, new String[] { "_id", "title" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery1" + num);

	}

	private static void xQuery1(String num, String id) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $publications := doc('publications.xml')/root//* " + "return <root>{ "
					+ "for $p in $publications " + "return " + "if (contains($p/id, '" + id + "')) " + "then $p "
					+ "else () " + "}</root> ";
			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Publications by id", new String[] { "ID", "Title" },
					new String[] { "id", "title" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery1" + num);

	}

	// Query 2

	private static void zQuery2(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery2(String num, String title, int beginOffset, int endOffset) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			Iterator<Document> iterator = dbM.publications.aggregate(Arrays.asList(
					new Document("$match", new Document("title", new Document("$regex", ".*" + title + ".*i"))),
					new Document("$skip", beginOffset), new Document("$limit", endOffset - beginOffset))).iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator,
					"Publications by title, begin-offset, end-offset", new String[] { "ID", "Title" },
					new String[] { "_id", "title" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery2" + num);

	}

	private static void xQuery2(String num, String id, int beginOffset, int endOffset) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $publications := doc('publications.xml')/root//*[contains(title, '" + id + "')] "
					+ "return <root>{ " + "subsequence($publications, " + beginOffset + ", " + (endOffset - beginOffset)
					+ ") " + "}</root> ";
			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Publications by id limited",
					new String[] { "ID", "Title" }, new String[] { "id", "title" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery2" + num);

	}

	// Query 3

	private static void zQuery3(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zQuery3" + num);

	}

	private static void mQuery3(String num, String title, int beginOffset, int endOffset) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.publications.aggregate(Arrays.asList(
					new Document("$match", new Document("title", new Document("$regex", ".*" + title + ".*i"))),
					new Document("$skip", beginOffset), new Document("$limit", endOffset - beginOffset),
					new Document("$sort", new Document("title", 1)))).iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator,
					"Publications by title, begin-offset, end-offset ordered by title", new String[] { "ID", "Title" },
					new String[] { "_id", "title" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mQuery3" + num);

	}

	private static void xQuery3(String num, String id, int beginOffset, int endOffset) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $publications := doc('publications.xml')/root//*[contains(title, '" + id + "')] "
					+ "return <root>{ " + "let $sorted := for $publication in $publications "
					+ "order by $publication/title/text() " + "return $publication " + "return subsequence($sorted, "
					+ beginOffset + ", " + (endOffset - beginOffset) + ") " + "}</root> ";
			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Publications by id sorted limited",
					new String[] { "ID", "Title" }, new String[] { "id", "title" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery3" + num);

	}

	// Query 4

	private static void zQuery4(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "zQuery4" + num);

	}

	private static void mQuery4(String num, String name) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.publications
					.aggregate(Arrays.asList(new Document("$match", new Document("authors", name)),
							new Document("$project",
									new Document("author", name).append("coAuthors", new Document("$filter",
											new Document("input", "$authors").append("as", "author").append("cond",
													new Document("$ne", Arrays.asList("$$author", name)))))),
							new Document("$unwind", "$coAuthors"), new Document("$group", new Document("_id", "$author")
									.append("coAuthors", new Document("$addToSet", "$coAuthors")))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator, "Co-Authors",
					new String[] { "Author", "Co-Authors" }, new String[] { "_id", "coAuthors" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mQuery4" + num);

	}

	private static void xQuery4(String num, String authorId) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $author := doc('persons.xml')/root//*[id = '" + authorId + "'] " + "return <root>{ "
					+ "<author>{ " + "$author/id, "
					+ "for $i in doc('inproceedings.xml')/root//*[id = $author//iid/text()] " + "return "
					+ "for $coAuthor in $i//author " + "return " + "if ($coAuthor/text() = $author/id/text()) "
					+ "then () " + "else <coAuthor>{$coAuthor/text()}</coAuthor> " + "}</author> " + "}</root> ";
			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Co-Authors", new String[] { "Author", "Co-Authors" },
					new String[] { "id", "coAuthor" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery4" + num);

	}

	// Query 5
	private static void zQuery5(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery5(String num, String author1, String author2) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.publications.aggregate(Arrays.asList(
					new Document("$match", new Document("$or",
							Arrays.asList(new Document("authors", author1), new Document("authors", author2)))),
					/*
					 * new Document("$lookup", new Document("from", "persons")
					 * .append("localField", "authors") .append("foreignField",
					 * "name") .append("as", "author") ),
					 */
					new Document("$unwind", "$authors"),

					new Document("$graphLookup",
							new Document("from", "$$ROOT").append("startWith", "$authors")
									.append("connectFromField", "authors").append("connectToField", "authors")
									.append("as", "connectedAuthors"))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator, "Co-Authors",
					new String[] { "Author", "connectedAuthors", "authors", "author" },
					new String[] { "_id", "connectedAuthors", "authors", "author" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mQuery5" + num);

	}

	private static void xQuery5(String num, String author1Id, String author2Id) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();
			dbX.open();

			System.out.println("Geopned");

			String input = "declare function local:shortestPath($authors, $target, $depth) { " + "if ($depth > 20) "
					+ "then 'The authors do not have anything in common' " + "else "
					+ "if (some $author in $authors satisfies $author/id/text() = $target/id/text()) " + "then $depth "
					+ "else local:shortestPath(doc('coAuthors.xml')/root//*[id/text() = $authors//coAuthor/text()], $target, $depth + 1) "
					+ "}; " + "let $coAuthors := doc('coAuthors.xml')/root//*, " + "$author := $coAuthors[id = '"
					+ author1Id + "'], " + "$target := $coAuthors[id = '" + author2Id + "'] " + "return <root>{ "
					+ "<item>{ " + "<shortestPath>{ " + "local:shortestPath($author, $target, 0) " + "}</shortestPath> "
					+ "}</item> " + "}</root> ";

			System.out.println("here");
			Query query = dbX.execute(input);
			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Shortest Path", new String[] { "Shortest Path" },
					new String[] { "shortestPath" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery5" + num);

	}

	// Query 6

	private static void zQuery6(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery6(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			AggregateIterable<Document> query = dbM.publications
					.aggregate(Arrays.asList(
							new Document("$project",
									new Document("_id", 1).append("title", 1).append("authors", 1).append("total",
											new Document("$size",
													new Document("$ifNull",
															Arrays.asList("$authors", Arrays.asList()))))),
							new Document("$group",
									new Document("_id", "average").append("count", new Document("$avg", "$total")))));

			Document firstRes = query.first();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, query.iterator(), "Global avg",
					new String[] { "ID", "Count" }, new String[] { "_id", "count" }, true);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery6" + num);

	}

	private static void xQuery6(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			Query query = dbX.executeFile("query6.xq");

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Global average number of authors per publication",
					new String[] { "Avg number of authors" }, new String[] { "avg" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery6" + num);

	}

	// Query 7

	private static void zQuery7(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery7(String num, int yearFrom, int yearTo) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			if (yearFrom > yearTo) {
				int temp = yearTo;
				yearTo = yearFrom;
				yearFrom = temp;
			}

			/*
			 * String expr = "(this['year'] != undefined && this['year'] >= " +
			 * yearFrom + " && this['year'] <= " + yearTo + ")";
			 * 
			 * long result = db.publications.count(Filters.where(expr));
			 * resultLabel.setText(Long.toString(result));
			 * 
			 * new Table(db.publications,
			 * db.publications.find(Filters.where(expr)).iterator(),
			 * "Publications between " + yearFrom + " and " + yearTo, new
			 * String[] {"Title", "Year"}, new String[] {"title", "year"},
			 * false);
			 */

			Iterator<Document> iterator = dbM.publications.aggregate(Arrays.asList(
					new Document("$project",
							new Document("_id", "$_id").append("year", "$year")
									.append("gte", new Document("$gte", Arrays.asList("$year", yearFrom))).append("lte",
											new Document("$lte", Arrays.asList("$year", yearTo)))),
					new Document("$match",
							new Document("$and", Arrays.asList(new Document("gte", true), new Document("lte", true)))),
					new Document("$group",
							new Document("_id", "$_id").append("count", new Document("$sum", 1)).append("year",
									new Document("$first", "$year"))),
					new Document("$group",
							new Document("_id", "$year").append("count", new Document("$sum", "$count"))),
					new Document("$sort", new Document("_id", 1)))).iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator,
					"Publications between " + yearFrom + " and " + yearTo,
					new String[] { "Year", "Number of Publications" }, new String[] { "_id", "count" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mQuery7" + num);

	}

	private static void xQuery7(String num, int beginYear, int endYear) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			if (beginYear > endYear) {
				int temp = beginYear;
				beginYear = endYear;
				endYear = temp;
			}

			String beginStr = Integer.toString(beginYear);
			String endStr = Integer.toString(endYear);

			String input = "let $inproceedings := doc('publications.xml')/root/inproceedings[(ceid >= " + beginYear
					+ ") and (ceid <= " + endYear + ")] " + "return <root>{ "
					+ "for $year in distinct-values($inproceedings/ceid) " + "order by $year " + "return " + "<result> "
					+ "<year>{ " + "$year " + "}</year> " + "<num>{ " + "count($inproceedings[ceid = $year]) "
					+ "}</num> " + "</result> " + "}</root>";
			System.out.println("Here");

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Number of publications per year between " + beginStr + " and " + endStr + ".",
					new String[] { "Year", "Number" }, new String[] { "year", "num" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery7" + num);

	}

	// Query 8

	private static void zQuery8(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery8(String num, String conferenceName) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.conferences
					.aggregate(Arrays.asList(new Document("$match", new Document("name", conferenceName)),
							new Document("$lookup",
									new Document("from", "conferenceEditions").append("localField", "editions")
											.append("foreignField", "_id").append("as", "editions")),
							new Document("$unwind", "$editions"),

							new Document("$lookup",
									new Document("from", "publications").append("localField", "editions.proceedings")
											.append("foreignField", "_id").append("as", "proceedings")),
							new Document("$unwind", "$proceedings"),

							new Document("$group",
									new Document("_id", null).append("name", new Document("$first", "$name")).append(
											"count", new Document("$sum",
													new Document("$size", "$proceedings.publications"))))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.conferences, iterator,
					"Number of Publications of conference " + conferenceName,
					new String[] { "Name", "Number of publications" }, new String[] { "name", "count" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery8" + num);

	}

	private static void xQuery8(String num, String confID) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $publications := doc('publications.xml')/root " + "return <root>{ " + "<item>{ "
					+ "<num>{ " + "for $p in $publications/proceedings[cid = '" + confID + "'] " + "return ( "
					+ "count( " + "for $ip in  $publications/inproceedings " + "where $ip/pid = $p/id " + "return $ip "
					+ ") " + ") " + "}</num> " + "}</item> " + "}</root>";

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Number of publications for conference " + confID + ". ", new String[] { "Total" },
					new String[] { "num" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery8" + num);

	}

	// Query 9

	private static void zQuery9(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery9(String num, String conferenceName) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.conferences
					.aggregate(Arrays.asList(new Document("$match", new Document("name", conferenceName)),
							new Document("$lookup",
									new Document("from", "conferenceEditions").append("localField", "editions")
											.append("foreignField", "_id").append("as", "editions")),
							new Document("$unwind", "$editions"),

							new Document("$lookup",
									new Document("from", "publications").append("localField", "editions.proceedings")
											.append("foreignField", "_id").append("as", "proceedings")),
							new Document("$unwind", "$proceedings"),

							new Document("$lookup",
									new Document("from", "publications")
											.append("localField", "proceedings.publications")
											.append("foreignField", "_id").append("as", "inProceedings")),
							new Document("$unwind", "$inProceedings"),

							new Document("$group",
									new Document("_id", null).append("name", new Document("$first", "$name"))
											.append("editors", new Document("$addToSet", "$proceedings.editors"))
											.append("authors", new Document("$addToSet", "$inProceedings.authors"))),

							new Document("$project", new Document("name", "$name").append("authors",
									new Document("$setUnion", Arrays.asList("$authors", "$editors")))),

							new Document("$group",
									new Document("_id", null).append("name", new Document("$first", "$name"))
											.append("count", new Document("$sum", new Document("$size", "$authors"))))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.conferences, iterator,
					"Number of Authors/Editors of conference " + conferenceName,
					new String[] { "Name", "Number of authors/editors" }, new String[] { "name", "count" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery9" + num);

	}

	private static void xQuery9(String num, String confID) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $proceedings := doc('proceedings.xml')/root//*[cid/text() = '" + confID + "'], "
					+ "$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()] "
					+ "return <root><item><count>{count(distinct-values($proceedings/editor/text() | $inproceedings/author/text()))}</count></item></root> ";

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Count of authors and editors of conference " + confID + ".", new String[] { "Count" },
					new String[] { "count" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery9" + num);

	}

	// Query 10

	private static void zQuery10(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery10(String num, String conferenceName) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.conferences
					.aggregate(Arrays.asList(new Document("$match", new Document("name", conferenceName)),
							new Document("$lookup",
									new Document("from", "conferenceEditions").append("localField", "editions")
											.append("foreignField", "_id").append("as", "editions")),
							new Document("$unwind", "$editions"),

							new Document("$lookup",
									new Document("from", "publications").append("localField", "editions.proceedings")
											.append("foreignField", "_id").append("as", "proceedings")),
							new Document("$unwind", "$proceedings"),

							new Document("$lookup",
									new Document("from", "publications")
											.append("localField", "proceedings.publications")
											.append("foreignField", "_id").append("as", "inProceedings")),
							new Document("$unwind", "$inProceedings"),

							new Document("$group",
									new Document("_id", null).append("name", new Document("$first", "$name"))
											.append("authors", new Document("$addToSet", "$proceedings.editors"))
											.append("authors", new Document("$addToSet", "$inProceedings.authors"))),
							new Document("$unwind", "$authors"), new Document("$unwind", "$authors"),

							new Document("$group",
									new Document("_id", null).append("name", new Document("$first", "$name"))
											.append("authors", new Document("$addToSet", "$authors")))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.conferences, iterator,
					"Authors/Editors of conference " + conferenceName, new String[] { "Name", "Authors/Editors" },
					new String[] { "name", "authors" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mQuery10" + num);

	}

	private static void xQuery10(String num, String confID) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			/*
			 * String input =
			 * "let $publications := doc('publications.xml')/root " +
			 * "return <root>{ " + "for $p in $publications/proceedings " +
			 * "where $p/cid = '" + confID +"' " + "return( " +
			 * "for $e in $p/editor " +
			 * "return <name><author>{$e/text()}</author></name> " + ") " + "} "
			 * + "{ " + "for $p in $publications/proceedings " +
			 * "where $p/cid = '" + confID +"' " + "return ( " +
			 * "for $ip in $publications/inproceedings " +
			 * "where $ip/pid = $p/id " + "return " + "for $a in $ip/author " +
			 * "return <name>{$a}</name> " + ") " + "}</root>";
			 */

			String input = "let $proceedings := doc('proceedings.xml')/root//*[cid/text() = '" + confID + "'], "
					+ "$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()] "
					+ "return " + "<root>{ "
					+ "for $author in distinct-values($proceedings/editor/text() | $inproceedings/author/text()) "
					+ "return " + "<author>{ " + "<id>{$author}</id> " + "}</author> " + "}</root> ";

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Names of authors and editors of conference " + confID + ".", new String[] { "Name" },
					new String[] { "id" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery10" + num);

	}

	// Query 11

	private static void zQuery11(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery11(String num, String conferenceName) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.conferences
					.aggregate(Arrays.asList(new Document("$match", new Document("name", conferenceName)),
							new Document("$lookup",
									new Document("from", "conferenceEditions").append("localField", "editions")
											.append("foreignField", "_id").append("as", "editions")),
							new Document("$unwind", "$editions"),

							new Document("$lookup",
									new Document("from", "publications").append("localField", "editions.proceedings")
											.append("foreignField", "_id").append("as", "proceedings")),
							new Document("$unwind", "$proceedings"),

							new Document("$lookup",
									new Document("from", "publications")
											.append("localField", "proceedings.publications")
											.append("foreignField", "_id").append("as", "inProceedings")),
							new Document("$unwind", "$inProceedings"),

							new Document("$group",
									new Document("_id", null).append("name", new Document("$first", "$name"))
											.append("inProceedings", new Document("$addToSet", "$inProceedings._id"))
											.append("proceedings", new Document("$addToSet", "$proceedings._id"))),

							new Document("$project", new Document("name", "$name").append("publications",
									new Document("$setUnion", Arrays.asList("$inProceedings", "$proceedings"))))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.conferences, iterator,
					"Publications of conference " + conferenceName, new String[] { "Name", "Publications" },
					new String[] { "name", "publications" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery11" + num);

	}

	private static void xQuery11(String num, String confID) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $proceedings := doc('proceedings.xml')/root//*[cid/text() = '" + confID + "'], "
					+ "$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()] "
					+ "return <root>{$inproceedings}</root> ";

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query, "Inproceedings of conference " + confID + ".",
					new String[] { "ID", "Title", "Proceedings", "Authors" },
					new String[] { "id", "title", "pid", "author" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery11" + num);

	}

	// Query 12

	private static void zQuery12(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery12(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery12" + num);

	}

	private static void xQuery12(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();
			Query query = dbX.executeFile("query12.xq");

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Persons that are author in InProceedings and editor in appropriate Proceedings",
					new String[] { "Name" }, new String[] { "id" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery12" + num);

	}

	// Query 13

	private static void zQuery13(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery13(String num, String name) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			// Exact matching could be used alternatively
			// String expr = "this.authors != undefined &&
			// this.authors[this.authors.length - 1] === '" + name + "'";
			String expr = "this.authors != undefined && this.authors[this.authors.length - 1].includes('" + name + "')";

			Iterator<Document> iterator = dbM.publications.find(Filters.where(expr)).iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publications, iterator,
					"Publications containing " + name + " as last author",
					new String[] { "ID", "Title", "Authors", "Pages", "Year" },
					new String[] { "_id", "title", "authors", "pages", "year" }, true);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "mQuery13" + num);

	}

	private static void xQuery13(String num, String authorID) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			String input = "let $inproceedings :=doc('inproceedings.xml')/root/* " + "return <root>{ "
					+ "for $ip in $inproceedings "
					+ "where some $author in $ip/author[last()] satisfies $author/text() = '" + authorID + "' "
					+ "return $ip " + "}</root> ";

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Publications where author " + authorID + " appears as last author.",
					new String[] { "ID", "Title", "Proceedings", "Authors" },
					new String[] { "id", "title", "pid", "author" }, false);

			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery13" + num);

	}

	// Query 14

	private static void zQuery14(String num) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			// TODO

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "zPublications" + num);

	}

	private static void mQuery14(String num, int yearFrom, int yearTo) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbM.open();

			Iterator<Document> iterator = dbM.publications
					.aggregate(
							Arrays.asList(
									new Document("$project", new Document("_id", "$_id")
											.append("publisher", "$publisher").append("authors", "$authors")
											.append("editors", "$editors").append("year", "$year")
											.append("gte", new Document("$gte", Arrays.asList("$year", yearFrom)))
											.append("lte", new Document("$lte", Arrays.asList("$year", yearTo)))),
									new Document("$match", new Document("$and",
											Arrays.asList(new Document("gte", true), new Document("lte", true)))),

									new Document("$lookup",
											new Document("from", "$$ROOT").append("localField", "authors")
													.append("foreignField", "editors").append("as", "proceedings")),

									new Document("$group", new Document("_id", "$publisher"))))
					.iterator();

			new ch.ethz.globis.isk.mongodb.Table(dbM.publishers, iterator,
					"Publishers of proceedings whose authors appear in inproceedings in range of years " + yearFrom
							+ " to " + yearTo,
					new String[] { "Publisher" }, new String[] { "_id" }, false);
			dbM.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		utils(ztimeList, zmemoryList, "mQuery14" + num);

	}

	private static void xQuery14(String num, int beginYear, int endYear) throws IOException {

		ArrayList<Long> ztimeList = new ArrayList<>();
		ArrayList<Long> zmemoryList = new ArrayList<>();

		long memory = 0;

		for (int i = 0; i < 30; i++) {
			Runtime.getRuntime().freeMemory();
			long startTime = System.currentTimeMillis();

			dbX.open();

			if (beginYear > endYear) {
				int temp = beginYear;
				beginYear = endYear;
				endYear = temp;
			}

			String input = "let $proceedings := doc('proceedings.xml')/root/*[1982 <= ceid and ceid <= 1986], "
					+ "$inproceedings := doc('inproceedings.xml')/root/*, " + "$publishers := "
					+ "for $p in $proceedings "
					+ "where every $e in $p/editor satisfies $e/id/text() = $inproceedings/author/id/text() "
					+ "return $p/publisher " + "return <root>{ " + "for $publisher in distinct-values($publishers) "
					+ "return <publisher><id>{$publisher}</id></publisher> " + "}</root> ";

			Query query = dbX.execute(input);

			new ch.ethz.globis.isk.xmldb.Table(dbX, null, query,
					"Publishers of Proceedings whose authors appear in any InProceedings in range of years",
					new String[] { "Publisher" }, new String[] { "id" }, false);
			dbX.close();

			long stopTime = System.currentTimeMillis();
			ztimeList.add(stopTime - startTime);

			Runtime runtime = Runtime.getRuntime();

			runtime.gc();

			memory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
			zmemoryList.add(memory);

			System.out.println(Long.toString(stopTime - startTime) + "    " + Long.toString(memory));
		}

		// TODO
		utils(ztimeList, zmemoryList, "xQuery14" + num);

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
