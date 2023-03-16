package SwitchAnalyzer.Network;

public abstract class ErrorDetectingAlgorithms extends Header {
   String AlgorithmName;

    public ErrorDetectingAlgorithms(String AlgorithmName) {
        this.AlgorithmName=AlgorithmName;
    }

    public abstract boolean isAlgorithmCorrect(byte [] packet);
}
