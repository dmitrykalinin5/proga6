package Console;

import Collections.CollectionManager;
import Collections.Ticket;
import Commands.CommandProcessor;
import Network.Request;
import Network.Response;

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
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());) {

                    commandProcessor = new CommandProcessor(collectionManager, historyDeque, out, in, "server");
                    commandProcessor.CommandPut();

                    System.out.println("Клиент подключен");

                    String responseText;
                    while (true) {
                        Object obj = in.readObject(); // 🟡 Блокирующий вызов: ждет объект от клиента
                        if (obj instanceof Request request) {
                            String command = request.commandName();
                            Ticket argument = request.argument();

                            if (argument != null) {
                                collectionManager.getQueue().add(argument);
                                System.out.println("Элемент добавлен в коллекцию");
                                responseText = "Все гуд";
                            } else {
                                responseText = commandProcessor.executeCommand(command);
                                System.out.println("Команда без аргумента выполнена");
                            }

//                            System.out.println("Получена команда: " + command);

                            // Тут логика обработки
//                            String responseText = "Команда принята: " + command;

                            out.writeObject(new Response(responseText));
                            out.flush();
                        }

                        System.out.println("Команда выполнена, ответ отправлен клиенту");


                    }
                } catch (ClassNotFoundException | IOException e) {
                    System.out.println("Клиент отключился");
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeCommand(String command) {
        commandProcessor.CommandPut();
//        return commandProcessor.executeCommand(command);
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
