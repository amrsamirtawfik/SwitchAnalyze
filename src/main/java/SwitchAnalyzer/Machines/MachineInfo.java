package SwitchAnalyzer.Machines;

import java.util.HashMap;
/**
 * this is the information thats going to be produced into kafka
 * do not add anything here other than the information needed to be put into kafka
 */

public class MachineInfo
{
    public int machineID;
    public HashMap<String,String> map;
    public MachineInfo(int id )
    {
        this.machineID = id;
        map =new HashMap<String, String>();
    }
}
