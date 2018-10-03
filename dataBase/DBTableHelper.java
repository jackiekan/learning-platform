

package dataBase;

import java.sql.Connection;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the abstract class used for the database table helpers
 * Each table helper will contain the database connection and the table name
 */
abstract public class DBTableHelper {

	protected Connection connection;
	protected String tableName;
	
	/**
	 * Constructs a table helper with the provided connection and table name
	 * @param c the database connection
	 * @param table the name of the table
	 */
	public DBTableHelper(Connection c, String table) {
		connection = c;
		tableName = table;
	}
}
