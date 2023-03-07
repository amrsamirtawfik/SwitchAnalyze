package SwitchAnalyzer.Commands;

public class MachineCommand {
    private int MachineId;
    private float rates;
    private String mode;
    private long packets;
    public MachineCommand(int MachineId, float rates, String mode, long packets) {
        this.MachineId = MachineId;
        this.rates = rates;
        this.mode = mode;
        this.packets = packets;
    }
    public int getMachineID() {
        return MachineId;
    }

    public void setMachineId(int MachineId) {
        this.MachineId = MachineId;
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
        MachineCommand command = (MachineCommand ) o;
        return rates == command.rates &&
                packets == command.packets &&
                MachineId==command.MachineId &&
                mode.equals(command.mode);
    }



    @Override
    public String toString() {
        return "Command{" +
                "machineID='" + MachineId + '\'' +
                ", rates=" + rates +
                ", mode='" + mode + '\'' +
                ", packets=" + packets +
                '}';
    }
}
