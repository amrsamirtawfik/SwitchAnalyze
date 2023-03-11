package SwitchAnalyzer.DB;

import com.datastax.driver.core.Session;

public class AlterTables {
    static Session session= DBConnect.getSession();

    public static void alterTableFrames(String tableName,String columnName, String columnType) {
        StringBuilder sb = new StringBuilder("ALTER TABLE ").append(tableName).append(" ADD ").append(columnName).append(" ").append(columnType).append(";");
        String query = sb.toString();
        session.execute(query);
    }
}
