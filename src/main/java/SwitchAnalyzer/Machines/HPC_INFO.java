package SwitchAnalyzer.Machines;

import java.util.HashMap;

/**
 * this is the information thats going to be produced into kafka
 * do not add anything here other than the information needed to be put into kafka
 */

public class HPC_INFO {
    public int HPCID;
    public HashMap<String,String> map;
    public HPC_INFO(int id )
    {
        this.HPCID = id;
        map =new HashMap<String, String>();
    }
}
