package Console;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public void interact() {
        try (Socket socket = new Socket("localhost", 5000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                System.out.print("Введите команду: ");
                //ввод
                String command = userInput.readLine();
                writer.println(command);
                // Ответ от сервера
                String serverMessage = "\u202F";
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                    if (serverMessage.trim().contains("Завершение программы..")) {
                        System.exit(0);
                    }
                    if (serverMessage.contains("\u00A0")) {
                        String com = userInput.readLine();
                        writer.println(com);
                    }
                    if (serverMessage.contains("\u202F")) {
                        System.out.print("Введите команду: ");
                        String com = userInput.readLine();
                        writer.println(com);
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
