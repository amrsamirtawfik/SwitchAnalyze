package SwitchAnalyzer.Machines;

import java.util.HashMap;

public class HPC_INFO {
    public int HPCID;
    public HashMap<String,String> map;
    public HPC_INFO(int id )
    {
        this.HPCID = id;
        map =new HashMap<String, String>();
    }
}
