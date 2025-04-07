package Console;

import Collections.CollectionManager;
import Commands.CommandProcessor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import Network.*;
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

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Введите команду: ");
                String input = userInput();

                if (input.equals("exit")) {
                    System.out.println("Завершение клиента...");
                    System.exit(0);
                }

                // Парсишь команду, можешь использовать CommandProcessor, если хочешь
                // Здесь формируем объект Request

                Request request;
                if (commandProcessor.isClientCommand(input)) {
                    Object argument = null;
                    if (input.equals("add")) {
                        AddCommand addCommand = (AddCommand) commandProcessor.getCommand("add");
                        argument = addCommand.getResult(); // получаем ticket
                    }
                    System.out.println("выполнено");
                    request = new Request(input, argument);
                } else {
                    request = new Request(input, null); // Пока без аргумента
                }


                // Отправка запроса на сервер
                out.writeObject(request);
                out.flush();

                // Получение ответа
                Response response = (Response) in.readObject();
                System.out.println("Ответ сервера: " + response.message());
            }

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
