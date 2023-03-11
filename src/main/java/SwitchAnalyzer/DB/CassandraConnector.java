package SwitchAnalyzer.DB;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.util.ArrayList;

public class CassandraConnector {
    private Cluster cluster;
    private Session session;
    public void connect(ArrayList<String>IPS,Integer port) {
        /*add nodes to connect*/
        Cluster.Builder b =Cluster.builder();
        for(int i=0;i< IPS.size();++i){
            b =  b.addContactPoint(IPS.get(i));
        }
        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();
        session = cluster.connect();
    }
    public Session getSession() {
        return this.session;
    }
    public void close() {
        session.close();
        cluster.close();
    }
}
