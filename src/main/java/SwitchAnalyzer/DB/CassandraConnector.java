package SwitchAnalyzer.DB;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.util.ArrayList;

public class CassandraConnector {
    private Cluster cluster;
    private Session session;
    public void connect(ArrayList<String>IPS,Integer port) {
        /*add nodes to connect*/
        Cluster.Builder b ;
        for(int i=0;i< IPS.size();++i){
            try{
                b=Cluster.builder().addContactPoint(IPS.get(i));
                // we want to check what will happen if more than an IP is correct
                if (port != null) {
                    b.withPort(port);
                }
                cluster = b.build();
                session = cluster.connect();
            }
            catch (Exception ex) {
                System.out.println("can't connect to node with this IP " + IPS.get(i));
            }
        }
    }
    public Session getSession() {
        return this.session;
    }
    public void close() {
        session.close();
        cluster.close();
    }
}
