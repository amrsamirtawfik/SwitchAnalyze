package SwitchAnalyzer.DB;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class DBInsert
{
    private static Session session= DBConnect.getSession();
    public static void beginInsert(Run run){
        if(!IsTableExists("Runs"))
        {
            DBCreate.createRunsTable();
        }
        else
        {
            //opinion check if this run row already exists
        }
    }
    public static void beginInsert(Frame frame){
        if(!IsTableExists("Frames_Run"+String.valueOf(frame.getRunNo())))
        {
            DBCreate.createFrames_RunTable(frame.getRunNo());
        }
        else
        {

        }
    }
    private static boolean IsTableExists(String tableName){
        StringBuilder sb = new StringBuilder("SELECT table_name FROM system_schema.tables WHERE table_name = ").append(tableName);
        ResultSet rs = session.execute(sb.toString());
        Row row = rs.one();
        if (row != null) {
            return true;
        }
        return false;
    }
    /**
     * Input : DB.Frame
     * Output : void
     * Description :
     *          The function insert a frame in the Frames table.
     *
     * @param frame
     */
    /*
    * if(frame.sendingPort!=-1) append fel string builder
    *
    * */
    public static void  insert(Frame frame) {
        String bytesStrings = frame.byteArrToString();
        StringBuilder sb = new StringBuilder("INSERT INTO ").append("Frames").append("(id,payload) ").append("VALUES (").append(String.valueOf(frame.getID())).append(", ").append(bytesStrings).append(");");
        //insertInto("Frames").value("id", frame.getID()).value("payload",frame.getBytes());
        final String query = sb.toString();
        session.execute(query);
    }
    public static void insert(Run run){

    }

}
