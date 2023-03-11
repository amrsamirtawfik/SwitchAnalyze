package SwitchAnalyzer.DB;

import java.sql.Timestamp;

/**
 * this DB.Frame class represents the actualy frame stored in kafka along with its ID
 * it implements JSON interface in order to be converted to and from JSON format.
 * @author ziad Fahmy
 * @since 2022-12-07
 */

public class Frame implements Schema {
    private long id ;
    private byte[] bytes;
    private long runNo;
    private Timestamp timeStamp;
    private int sendingPort;
    private int recievingPort;
    private String networkHeader;
    private String transportHeader;
    private boolean errorInRouting;
    private boolean crcChecker;
    private final static Object obj = new Object();
    /**
     * there is no unsigned long in java so in case you want it to be viewed as unsigned use this function:
     * Long.toUnsignedString(i)
     * note that this variable is shared so it is critical region
     */
     static private  long  counter = 0;
    /**
     * could be adjusted later in case we want a bigger range than long
     */



    public  Frame()
    {
        synchronized (obj)
        {
            counter++;
            id = counter;
        }
    }
    public  Frame(byte[] bytes) {
        this.bytes = bytes;

        synchronized (obj)
        {
            counter++;

            //id = counter;
        }

    }
    public  Frame(long id , byte[] bytes) {
        this.bytes = bytes;
        this.id=id;
    }

    public byte[] getBytes() {
        return bytes;
    }
    public long getID() {
        return id;
    }

    public long getRunNo() {
        return runNo;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public int getSendingPort() {
        return sendingPort;
    }

    public int getRecievingPort() {
        return recievingPort;
    }

    public String getNetworkHeader() {
        return networkHeader;
    }

    public String getTransportHeader() {
        return transportHeader;
    }

    public boolean isErrorInRouting() {
        return errorInRouting;
    }

    public boolean isCrcChecker() {
        return crcChecker;
    }

    public static long getCounter() {
        return counter;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder("id: ");
        s.append(id);
        s.append(" bytes: ");
        int i = 0;
        for ( ; i < bytes.length-1;i++)
        {
            s.append(bytes[i]);
            s.append(",");

        }
        s.append(bytes[i]);
        return s.toString();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRunNo(long runNo) {
        this.runNo = runNo;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setSendingPort(int sendingPort) {
        this.sendingPort = sendingPort;
    }

    public void setRecievingPort(int recievingPort) {
        this.recievingPort = recievingPort;
    }

    public void setNetworkHeader(String networkHeader) {
        this.networkHeader = networkHeader;
    }

    public void setTransportHeader(String transportHeader) {
        this.transportHeader = transportHeader;
    }

    public void setErrorInRouting(boolean errorInRouting) {
        this.errorInRouting = errorInRouting;
    }

    public void setCrcChecker(boolean crcChecker) {
        this.crcChecker = crcChecker;
    }

    public String byteArrToString()
    {
        StringBuilder s = new StringBuilder("[ ");
        int i = 0;
        for ( ; i < bytes.length-1;i++)
        {
            s.append(bytes[i]);
            s.append(",");

        }
        s.append(bytes[i]);
        s.append("]");
        return s.toString();
    }
}
