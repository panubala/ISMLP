package ch.ethz.globis.isk.WholeSystem;

import ch.ethz.globis.isk.xmldb.*;
import ch.ethz.globis.isk.xmldb.api.BaseXClient.Query;

public class WTable extends Table{

	public WTable(Database db, String xml, Query query, String title, String[] columnNames, String[] tagNames,
			boolean allowModifications) {
		super(db, xml, query, title, columnNames, tagNames, allowModifications);
		// TODO Auto-generated constructor stub
	}

}
