package SwitchAnalyzer.DB;

public class DBCreate {
    public static void createRunsTable()
    {

    }
    public static void createFrames_RunTable(long runNO)
    {


    }
    public static void  createFramesTable() {
        //StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(").append("id uuid PRIMARY KEY, ").append("title text,").append("author text,").append("subject text);");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS frames(id bigint,payload list<tinyint>,PRIMARY KEY(id));");
        final String query = sb.toString();
        DBConnect.getSession().execute(query);
    }
}
