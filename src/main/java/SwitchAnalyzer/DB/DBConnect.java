package SwitchAnalyzer.DB;

import com.datastax.driver.core.Session;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.Arrays;

/*
*these IPS for DataBase Nodes
* 192.168.1.60
* 192.168.1.70
*
*
*
* */
public class DBConnect {
    //hard coded
    public static ArrayList<String> IPS=new ArrayList<String>(Arrays.asList("192.168.1.60","192.168.1.70"));
    private static CassandraConnector connector;
    private static  Session session;
    private static KeyspaceRepository keySpaceRepo;

    public static Session getSession() {
        return session;
    }

    public static KeyspaceRepository getKeySpaceRepo() {
        return keySpaceRepo;
    }

    static public void connectToDB(String switchName){
        DBConnect.connect();
        KeySpace.useKeyspace(switchName);
    }
    public static void connect() {
        try {
            BasicConfigurator.configure();
            connector = new CassandraConnector();
            /* choose the node or nodes to connect with */
            connector.connect(IPS,null);
            session = connector.getSession();
            keySpaceRepo = new KeyspaceRepository(session);
        } catch (Exception e) {
            System.out.println("Cant connect to DB server ");
            e.printStackTrace();
        }
    }
    public static void closeConnectionToDB() {
        connector.close();
    }
}