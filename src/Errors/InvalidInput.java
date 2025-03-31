package Errors;

public class InvalidInput extends RuntimeException {
    public InvalidInput(String message) {
        super(message);
    }

    public static void Message(String message) {
        System.out.println(message);
    }
}
