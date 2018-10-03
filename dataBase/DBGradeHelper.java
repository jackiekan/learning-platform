//Armin Ghezelbashan & Jacyln Kan

package dataBase;

import java.sql.Connection;

public class DBGradeHelper extends DBTableHelper {

	public DBGradeHelper(Connection c) {
		super(c, "Grades");
	}

}
