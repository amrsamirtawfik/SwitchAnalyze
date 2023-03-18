package SwitchAnalyzer.Sockets;

import SwitchAnalyzer.Commands.ICommand;
import SwitchAnalyzer.miscellaneous.JSONConverter;
import SwitchAnalyzer.miscellaneous.SystemMaps;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Queue;

public class UserRequestHandler
{
    public static void readCommands(WebSocketServer webSocketServer,int portNumber, int maxMessageLength, Queue<ICommand> cmdQueue)
    {
        while (true)
        {
            byte[] command = webSocketServer.readFromSocket(maxMessageLength);
            if (command == null)
            {
                webSocketServer.HandShake();
                continue;
            }
            try { parseCommand(command, cmdQueue); }
            catch (JsonProcessingException e) { throw new RuntimeException(e);}
        }
    }
    public static void parseCommand(byte[] command, Queue<ICommand> cmdQueue) throws JsonProcessingException
    {
        String jsonStr = new String(command);
        int commandTypeIndex = Character.getNumericValue(jsonStr.charAt(0));
        jsonStr = jsonStr.replaceFirst("[0-9]*","");
        System.out.println("UserRequestHandler: "+ jsonStr);
        ICommand c= JSONConverter.fromJSON(jsonStr, SystemMaps.commandClasses.get(commandTypeIndex));
        cmdQueue.add(c);
    }

    public static void writeToUser (WebSocketServer server , byte[] writtenData) {
        server.writeToSocket(writtenData);
    }

}
