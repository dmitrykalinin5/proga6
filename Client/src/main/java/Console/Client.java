package Console;

import Collections.CollectionManager;
import Collections.Ticket;
import Commands.CommandProcessor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

import Commands.AddCommand;


public class Client {
    private static final Deque<String> historyDeque = new ArrayDeque<>();
    private static final CommandProcessor commandProcessor = new CommandProcessor(null, historyDeque, null, null, "client");

    public static String userInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.trim();
    }

    public void execute(String[] args) {

    }

    public static void main(String[] args) {
        commandProcessor.CommandPut();

        try (Socket socket = new Socket("localhost", 6133);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                System.out.print("Введите команду: ");
                String input = userInput();
                Request request;

                if (input.equals("exit")) {
                    request = new Request("exit", null);
                    out.writeObject(request);
                    out.flush();
                    Response response = (Response) in.readObject();
                    System.out.println(response.message());
                    System.exit(0);
                }

                // Парсишь команду, можешь использовать CommandProcessor, если хочешь
                // Здесь формируем объект Request


                Ticket argument = null;
                if (commandProcessor.isClientCommand(input)) {
                    argument = (Ticket) commandProcessor.executeArgumentCommand(input);
                }
                request = new Request(input, argument);

                // Отправка запроса на сервер
                out.writeObject(request);
                out.flush();

                // Получение ответа
                Response response = (Response) in.readObject();
                System.out.println(response.message());
            }

        } catch (IOException e) {
            System.err.println("Некорректный ввод");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
