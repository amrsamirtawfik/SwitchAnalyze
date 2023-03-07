package SwitchAnalyzer.Commands;

public class Command {
    private int HPCID;
    private float rates;
    private String mode;
    private long packets;
    public Command(int HPCID, int rates, String mode, int packets) {
        this.HPCID = HPCID;
        this.rates = rates;
        this.mode = mode;
        this.packets = packets;
    }
    public int getHPCID() {
        return HPCID;
    }

    public void setHPCID(int HPCID) {
        this.HPCID = HPCID;
    }

    public float getRates() {
        return rates;
    }

    public void setRates(int rates) {
        this.rates = rates;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public long getPackets() {
        return packets;
    }

    public void setPackets(int packets) {
        this.packets = packets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return rates == command.rates &&
                packets == command.packets &&
                HPCID==command.HPCID &&
                mode.equals(command.mode);
    }



    @Override
    public String toString() {
        return "Command{" +
                "machineID='" + HPCID + '\'' +
                ", rates=" + rates +
                ", mode='" + mode + '\'' +
                ", packets=" + packets +
                '}';
    }
}
