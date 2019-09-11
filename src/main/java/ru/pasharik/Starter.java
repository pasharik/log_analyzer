package ru.pasharik;

public class Starter {
    public static void main(String[] args) {
        IoCFactory ioCFactory = new IoCFactory();
        LogAnalyzer app = ioCFactory.createApp();
        app.analyze(args);
    }
}
