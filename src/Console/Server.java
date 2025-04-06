package Console;

import Collections.CollectionManager;
import Commands.CommandProcessor;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Deque;

/**
 * Однопоточный сервер, принимающий команды по TCP
 */
public class Server {
    private final CollectionManager collectionManager;
    private final Deque<String> historyDeque;
    private final CommandProcessor commandProcessor;

    public Server(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Сервер запущен");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Клиент подключен");

                    commandProcessor.SetIOStreams(reader, writer);

                    String command;
                    while ((command = reader.readLine()) != null) {
                        String response = executeCommand(command);
                        writer.println(response);
                    }

                    System.out.println("Клиент отключился");
                } catch (IOException e) {
                    System.out.println("Клиент отключился");
//                    System.err.println("Ошибка при обработке клиента " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера " + e.getMessage());
        }
    }

    public String executeCommand(String command) {
        commandProcessor.CommandPut();
        return commandProcessor.executeCommand(command);
    }
}
