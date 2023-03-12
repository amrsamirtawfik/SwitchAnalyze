package SwitchAnalyzer.DB;

import java.sql.Timestamp;

public class DBCreate {
    public static void createRunsTable()
    {

    }
    public static void createFrames_RunTable(long runNO)
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT" +
                " EXISTS frames_run");
        sb.append(runNO)
                .append("(")
                .append("id timeuuid")  //overflow
                .append("fullPacket list<tinyint>")   //make sure list is suitable for us
                .append("timeStamp timestamp")
                .append("sendingPort int")
                .append("recievingPort int")
                .append("networkHeader text")
                .append("transportHeader text")
                .append("errorInRouting boolean")
                .append("crcChecker boolean")
                .append("PRIMARY KEY(id)" ) //opinion
                .append(");");
        final String query = sb.toString();
        DBConnect.getSession().execute(query);
    }
    public static void  createFramesTable() {
        //StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(").append("id uuid PRIMARY KEY, ").append("title text,").append("author text,").append("subject text);");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS frames(id bigint,payload list<tinyint>,PRIMARY KEY(id));");
        final String query = sb.toString();
        DBConnect.getSession().execute(query);
    }
}
