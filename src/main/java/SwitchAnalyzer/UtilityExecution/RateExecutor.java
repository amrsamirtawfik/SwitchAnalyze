package SwitchAnalyzer.UtilityExecution;

import SwitchAnalyzer.NamingConventions;
import SwitchAnalyzer.Network.Observer;


public class RateExecutor implements IExecutor
{
    public void execute() { UtilityExecutor.result.put(NamingConventions.rates, Float.toString(Observer.getRate())); }
}
