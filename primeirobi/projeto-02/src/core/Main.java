package core;

public class Main {
    public static void main(String[] args) {
        AppFactory factory = new AppFactory();
        factory.createConsoleController().start();
    }
}