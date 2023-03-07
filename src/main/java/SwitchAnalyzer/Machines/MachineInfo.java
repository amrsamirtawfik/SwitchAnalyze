package SwitchAnalyzer.Machines;
public class MachineInfo {
    private int MachineID;
    private float rate;


    public MachineInfo(int machineID, float rate) {
        MachineID = machineID;
        this.rate = rate;
    }

    public int getMachineID() {
        return MachineID;
    }

    public void setMachineID(int machineID) {
        MachineID = machineID;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }



}
//
//public class MachineInfo {
//    private int ID;
//    private float rate;
//    private String mode;
//    // we have three status stopped, sending and receiving
//    private String status;
//
//    private int maxHealth;
//    private int currentHealth;
//
//    //can each machine know it's health? or shall i compute it in the master
//    public MachineInfo(int ID, float rate, String mode, int maxHealth) {
//        this.ID = ID;
//        this.rate = rate;
//        this.mode = mode;
//        this.maxHealth=maxHealth;
//    }
//
//    public int getMaxHealth() {
//        return maxHealth;
//    }
//
//    public void setMaxHealth(int maxHealth) {
//        this.maxHealth = maxHealth;
//    }
//
//    public int getCurrentHealth() {
//        return currentHealth;
//    }
//
//    public void setCurrentHealth(int currentHealth) {
//        this.currentHealth = currentHealth;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public int getID() {
//        return ID;
//    }
//
//    public float getRate() {
//        return rate;
//    }
//
//    public String getMode() {
//        return mode;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }
//
//    public void setRate(float rate) {
//        this.rate = rate;
//    }
//
//    public void setMode(String mode) {
//        this.mode = mode;
//    }
//}