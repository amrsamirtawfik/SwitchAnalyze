package SwitchAnalyzer.Cluster;

import java.util.ArrayList;

public class ClusterConfiguartions {

    String cluster_name;

    String cluster_Id;

    public ArrayList<MachineConfigurations> machines;

    public String getCluster_name() {
        return cluster_name;
    }

    public int getCluster_Id() {
        return Integer.parseInt(cluster_Id);
    }
}