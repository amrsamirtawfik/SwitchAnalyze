package SwitchAnalyzer.DB;

import com.datastax.driver.core.*;

import java.sql.Timestamp;

public class DBSelect {
    //session is used in order to execute the query
    private static Session session = DBConnect.getSession();
    //selectedAttributes is string builder containing the attributes passed next to the word SELECT
    private static StringBuilder selectedAttributes;
    //private static List<String> attributesToBeShown;
    //whereCondition is string builder containing the conditions passed next to WHERE
    private static StringBuilder whereCondition;
    //fromTableName is string builder containing FROM table name
    private static StringBuilder fromTableName;
    //This is a boolean that will be set in order to tell me if the select has specific attributes or it is select all
    private static boolean selectAll;
    //wholeSelectQuery is the whole cql statement that will be executed
    private static StringBuilder wholeSelectQuery;
    private static String TableName;
    private static boolean JSON;

    public static boolean isSelectAll() {
        return selectAll;
    }

    public static void setSelectAll(boolean selectAll) {
        DBSelect.selectAll = selectAll;
    }

    public static void setJSON(boolean JSON) {
        DBSelect.JSON = JSON;
    }

    /*
                    Input : condition string
                    Output : void
                    Description :
                        This function will begin the select query with a condition all or specific
                        all means we need to execute select all
                        specific means we need to execute select certain attributes
                */
    public static void beginSelectRuns() {
        fromTableName = new StringBuilder("FROM Runs");
        TableName = "Runs";
        if (selectAll) {
            if (JSON) {
                selectedAttributes = new StringBuilder("SELECT JSON * ");
            } else {
                selectedAttributes = new StringBuilder("SELECT * ");
            }
        } else {
            if (JSON) {
                selectedAttributes = new StringBuilder("SELECT JSON ");
            } else {
                selectedAttributes = new StringBuilder("SELECT ");
            }

        }
        whereCondition = new StringBuilder("WHERE ");
    }

    public static void beginSelectFrames(long runNo) {
        fromTableName = new StringBuilder("FROM Frames_Run").append(runNo);
        TableName = "Frames_Run" + String.valueOf(runNo);
        if (selectAll) {
            if (JSON) {
                selectedAttributes = new StringBuilder("SELECT JSON * ");
            } else {
                selectedAttributes = new StringBuilder("SELECT * ");
            }
        } else {
            if (JSON) {
                selectedAttributes = new StringBuilder("SELECT JSON ");
            } else {
                selectedAttributes = new StringBuilder("SELECT ");
            }

        }
        whereCondition = new StringBuilder("WHERE ");
    }

    /*********************************************************************************************************************************/
    public static void conditionRunNo(long runNo) {
        whereCondition.append("RunNo = ").append(String.valueOf(runNo));
    }

    public static void viewRunNo() {
        selectedAttributes.append("RunNo ");
        //attributesToBeShown.add("RunNo");
    }

    public static void conditionStartTimeStamp(Timestamp startTimeStamp) {
        whereCondition.append("StartTimeStamp = ").append(startTimeStamp.toString());


    }

    public static void viewStartTimeStamp() {
        selectedAttributes.append("StartTimeStamp ");
        //attributesToBeShown.add("StartTimeStamp");
    }

    public static void conditionEndTimeStamp(Timestamp endTimeStamp) {
        whereCondition.append("EndTimeStamp = ").append(endTimeStamp.toString());

    }

    public static void viewEndTimeStamp() {
        selectedAttributes.append("EndTimeStamp ");
        //attributesToBeShown.add("EndTimeStamp");
    }

    public static void conditionSwitchName(String switchName) {
        whereCondition.append("SwitchName = ").append(switchName);

    }

    public static void viewSwitchName() {
        selectedAttributes.append("SwitchName ");
        //attributesToBeShown.add("SwitchName");
    }

    public static void conditionPacketLoss(float packetLoss) {
        whereCondition.append("PacketLoss = ").append(String.valueOf(packetLoss));

    }

    public static void viewPacketLoss() {
        selectedAttributes.append("PacketLoss ");
        //attributesToBeShown.add("PacketLoss");
    }

    public static void conditionLatency(float latency) {
        whereCondition.append("Latency = ").append(String.valueOf(latency));

    }

    public static void viewLatency() {
        selectedAttributes.append("Latency ");
        //attributesToBeShown.add("Latency");
    }

    public static void conditionThroughput(float throughput) {
        whereCondition.append("Throughput = ").append(String.valueOf(throughput));

    }

    public static void viewThroughput() {
        selectedAttributes.append("Throughput ");
        //attributesToBeShown.add("Throughput");
    }

    public static void conditionSuccessfulFramesPercentage(float successfulFramesPercentage) {
        whereCondition.append("SuccessfulFramesPercentage = ").append(String.valueOf(successfulFramesPercentage));

    }

    public static void viewSuccessfulFramesPercentage() {
        selectedAttributes.append("SuccessfulFramesPercentage ");
        //attributesToBeShown.add("SuccessfulFramesPercentage");
    }

    public static void conditionFramesWithErrorsPercentage(float framesWithErrorsPercentage) {
        whereCondition.append("FramesWithErrorsPercentage = ").append(String.valueOf(framesWithErrorsPercentage));

    }

    public static void viewFramesWithErrorsPercentage() {
        selectedAttributes.append("FramesWithErrorsPercentage ");
        //attributesToBeShown.add("FramesWithErrorsPercentage");
    }


    /****************************************************************************************************************************/
    public static void conditionID(long id) {
        whereCondition.append("ID = ").append(String.valueOf(id));

    }

    public static void viewID() {
        selectedAttributes.append("ID ");
        //attributesToBeShown.add("ID");
    }

    public static void conditionBytes(byte[] bytes) {
        whereCondition.append("Bytes = ").append(byteArrToString(bytes));
    }

    public static void viewBytes() {
        selectedAttributes.append("Bytes ");
        //attributesToBeShown.add("Bytes");
    }

    public static void conditionTimestamp(Timestamp timestamp) {
        whereCondition.append("Timestamp = ").append(timestamp.toString());

    }

    public static void viewTimestamp() {
        selectedAttributes.append("Timestamp ");
        //attributesToBeShown.add("Timestamp");
    }

    public static void conditionSendingPort(int sendingPort) {
        whereCondition.append("SendingPort = ").append(String.valueOf(sendingPort));

    }

    public static void viewSendingPort() {
        selectedAttributes.append("SendingPort ");
        //attributesToBeShown.add("SendingPort");
    }

    public static void conditionRecievingPort(int recievingPort) {
        whereCondition.append("RecievingPort = ").append(String.valueOf(recievingPort));
    }

    public static void viewRecievingPort() {
        selectedAttributes.append("RecievingPort ");
        //attributesToBeShown.add("RecievingPort");
    }

    public static void conditionNetworkHeader(String networkHeader) {
        whereCondition.append("NetworkHeader = ").append(networkHeader);

    }

    public static void viewNetworkHeader() {
        selectedAttributes.append("NetworkHeader ");
        //attributesToBeShown.add("NetworkHeader");
    }

    public static void conditionTransportHeader(String transportHeader) {
        whereCondition.append("TransportHeader = ").append(transportHeader);
    }

    public static void viewTransportHeader() {
        selectedAttributes.append("TransportHeader ");
        //attributesToBeShown.add("TransportHeader");
    }

    public static void conditionErrorInRouting(boolean errorInRouting) {
        whereCondition.append("ErrorInRouting = ").append(String.valueOf(errorInRouting));
    }

    public static void viewErrorInRouting() {
        selectedAttributes.append("ErrorInRouting ");
        //attributesToBeShown.add("ErrorInRouting");
    }

    public static void conditionCrcChecker(boolean crcChecker) {
        whereCondition.append("CrcChecker = ").append(String.valueOf(crcChecker));
    }

    public static void viewCrcChecker() {
        selectedAttributes.append("CrcChecker ");
        //attributesToBeShown.add("CrcChecker");
    }

    /**
     * Input : void
     * Output : void
     * Description :
     * If select is select all the function will add the word AND in the condition
     * If select is selected specific the function will add the condition and the attribute
     *
     * @return
     */
    public static void otherCondition() {
        whereCondition.append(" AND ");
    }

    /**
     * Input : void
     * Output : void
     * Description :
     * If select is select all the function will add the word AND in the condition
     * If select is selected specific the function will add the condition and the attribute
     *
     * @return
     */
    public static void otherAttribute() {
        selectedAttributes.append(", ");
    }

    public static ResultSet executeSelect() {
        wholeSelectQuery = new StringBuilder();
        wholeSelectQuery.append(selectedAttributes).append(fromTableName).append(whereCondition).append(";");
        final String query = wholeSelectQuery.toString();
        SimpleStatement x = new SimpleStatement(query);
        x.setConsistencyLevel(ConsistencyLevel.ONE);
        ResultSet rs = session.execute(x);

//        List<DB.Frame> frames = new ArrayList<DB.Frame>();
//        for (Row r : rs) {
//            //add id for each frame and put it in the constructor
//            int id =r.get("id", Integer.class);
//            List<Byte> tinyintList = r.getList("payload", Byte.class);
//            byte[] byteArray = new byte[tinyintList.size()];
//            for (int i = 0; i < tinyintList.size(); i++) {
//                byteArray[i] = tinyintList.get(i);
//            }
//            DB.Frame frame = new DB.Frame(id,byteArray);
//            frames.add(frame);
//        }
        return rs;
    }

//    public static List<DB.Run> executeSelectRuns()
//    {
//        wholeSelectQuery = new StringBuilder();
//        wholeSelectQuery.append(selectedAttributes).append(fromTableName).append(whereCondition).append(";");
//        final String query = wholeSelectQuery.toString();
//        SimpleStatement x= new SimpleStatement(query);
//        x.setConsistencyLevel(ConsistencyLevel.ONE);
//        ResultSet rs = session.execute(x);
//        ColumnDefinitions columnDefinitions = rs.getColumnDefinitions();
//
//        // Retrieve the column names
//        for (ColumnDefinitions.Definition definition : columnDefinitions) {
//            String columnName = definition.getName();
//            System.out.print(columnName + "\t");
//        }
//
//        List<DB.Run> runs = new ArrayList<DB.Run>();
////        for (Row r : rs) {
////            //add id for each frame and put it in the constructor
////            int id =r.get("id", Integer.class);
////            List<Byte> tinyintList = r.getList("payload", Byte.class);
////            byte[] byteArray = new byte[tinyintList.size()];
////            for (int i = 0; i < tinyintList.size(); i++) {
////                byteArray[i] = tinyintList.get(i);
////            }
////            DB.Frame frame = new DB.Frame(id,byteArray);
////            frames.add(frame);
////        }
//        return runs;
//    }

    private static String byteArrToString(byte[] bytes) {
        StringBuilder s = new StringBuilder("[ ");
        int i = 0;
        for (; i < bytes.length - 1; i++) {
            s.append(bytes[i]);
            s.append(",");

        }
        s.append(bytes[i]);
        s.append("]");
        return s.toString();
    }
}
