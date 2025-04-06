package Console;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {

    public void interact() {
        try (Socket socket = new Socket("localhost", 5000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Введите команду: ");
                //ввод
                String userInput = scanner.nextLine();
                writer.println(userInput);
                // Ответ от сервера
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                    if (serverMessage.trim().isEmpty()) {
                        break;
                    }
                    if (serverMessage.trim().equalsIgnoreCase("завершение программы..")) {
                        System.exit(0);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу" + e.getMessage());
        }
    }

    public String userInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.trim();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.interact();
    }
}
