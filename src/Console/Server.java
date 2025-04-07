package Console;

import Collections.CollectionManager;
import Commands.CommandProcessor;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Однопоточный сервер, принимающий команды по TCP
 */
public class Server {
    private static CollectionManager collectionManager = new CollectionManager();
    private static Deque<String> historyDeque = new ArrayDeque<>();
    private static CommandProcessor commandProcessor;

    public Server(CollectionManager collectionManager, Deque<String> historyDeque) {
        Server.collectionManager = collectionManager;
        Server.historyDeque = historyDeque;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Сервер запущен");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    commandProcessor = new CommandProcessor(collectionManager, historyDeque, in, out);

                    System.out.println("Клиент подключен");

                    String command;
                    while ((command = in.readLine()) != null) {
                        String response = executeCommand(command);
                        out.println(response + "\u202F");
                    }

                    System.out.println("Клиент отключился");
                } catch (IOException e) {
                    System.out.println("Клиент отключился");
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

    public static void main(String[] args) {
        collectionManager.loadFromFile();

        new Thread(() -> {
            Server server = new Server(collectionManager, historyDeque);
            server.run();
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
