package SwitchAnalyzer.Commands;

public abstract class ICommandMaster implements ICommand
{
    public int portID;
    public abstract void GenCmd(int machineID);
}
