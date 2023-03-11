package SwitchAnalyzer.DB;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class KeySpace {
    private static Session session = DBConnect.getSession();
    private static String replicationStrategy = "NetworkTopologyStrategy";
    private static int numberOfReplicas = 1;

    public void setReplicationStrategy(String replicationStrategy) {
        this.replicationStrategy = replicationStrategy;
    }

    public void setNumberOfReplicas(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    private static void createKeyspace (String keySpaceName , String replicationStrategy , int numberOfReplicas )
    {
        DBConnect.getKeySpaceRepo().createKeyspace(keySpaceName, replicationStrategy, numberOfReplicas);
    }
    public static void useKeyspace(String keySpaceName){
        if(!isKeyspaceExists(keySpaceName)){
            createKeyspace(keySpaceName , replicationStrategy , numberOfReplicas);
        }
        DBConnect.getKeySpaceRepo().useKeyspace(keySpaceName);
    }
    static void deleteKeyspace(String keySpaceName){
        DBConnect.getKeySpaceRepo().deleteKeyspace(keySpaceName);
    }
    private static boolean isKeyspaceExists(String keySpaceName)
    {
        StringBuilder sb = new StringBuilder("SELECT keyspace_name FROM system_schema.keyspaces WHERE keyspace_name = '").append(keySpaceName).append("';");
        ResultSet rs = session.execute(sb.toString());
        Row row = rs.one();
        if(row != null)
        {
            return true;
        }
        return false;
    }

}

