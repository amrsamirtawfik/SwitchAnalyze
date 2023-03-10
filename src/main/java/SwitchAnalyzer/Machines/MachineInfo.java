package SwitchAnalyzer.Machines;

import java.util.HashMap;

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
