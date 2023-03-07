package SwitchAnalyzer.Machines;

public class HPCinfo {
    private int HPCID;
    private int noOfMachines;
    private float CurrentOverallRate;
/*    private int maxHealth;
    private int currentHealth;*/

    // we have three status stopped, sending and receiving
    //note: we can create more than one status(one for sending and the other for receiving
    private String status;
    public HPCinfo(int HPCID, int noOfMachines,int health) {
        this.HPCID = HPCID;
        this.noOfMachines = noOfMachines;
//        this.maxHealth=health;
    }

/*    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }*/

    public int getNoOfMachines() {
        return noOfMachines;
    }

    public void setNoOfMachines(int noOfMachines) {
        this.noOfMachines = noOfMachines;
    }

    public HPCinfo(int HPCID) {
        this.HPCID = HPCID;
    }

    public void setCurrentOverallRate(float currentOverallRate) {
        CurrentOverallRate = currentOverallRate;
    }

    public int getHPCID() {
        return HPCID;
    }

    public void setHPCID(int HPCID) {
        this.HPCID = HPCID;
    }

    public float getCurrentOverallRate() {
        return CurrentOverallRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}