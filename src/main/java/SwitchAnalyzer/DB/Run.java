package SwitchAnalyzer.DB;

import java.sql.Timestamp;

public class Run implements Schema {

    private long runNo;
    private Timestamp startTimeStamp;
    private Timestamp endTimeStamp;
    private float packetLoss;
    private float latency;
    private float throughput;
    private String switchName;
    private float successfulFramesPercentage;
    private float framesWithErrorsPercentage;

    public void setRunNo(long runNo) {
        this.runNo = runNo;
    }

    public void setStartTimeStamp(Timestamp startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public void setEndTimeStamp(Timestamp endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public void setPacketLoss(float packetLoss) {
        this.packetLoss = packetLoss;
    }

    public void setLatency(float latency) {
        this.latency = latency;
    }

    public void setThroughput(float throughput) {
        this.throughput = throughput;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public void setSuccessfulFramesPercentage(float successfulFramesPercentage) {
        this.successfulFramesPercentage = successfulFramesPercentage;
    }

    public void setFramesWithErrorsPercentage(float framesWithErrorsPercentage) {
        this.framesWithErrorsPercentage = framesWithErrorsPercentage;
    }

    public long getRunNo() {
        return runNo;
    }

    public Timestamp getStartTimeStamp() {
        return startTimeStamp;
    }

    public Timestamp getEndTimeStamp() {
        return endTimeStamp;
    }

    public float getPacketLoss() {
        return packetLoss;
    }

    public float getLatency() {
        return latency;
    }

    public float getThroughput() {
        return throughput;
    }

    public String getSwitchName() {
        return switchName;
    }

    public float getSuccessfulFramesPercentage() {
        return successfulFramesPercentage;
    }

    public float getFramesWithErrorsPercentage() {
        return framesWithErrorsPercentage;
    }
}
