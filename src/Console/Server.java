package Console;

import Collections.CollectionManager;
import Collections.Ticket;
import Commands.CommandProcessor;
import Network.Request;
import Network.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Однопоточный сервер, принимающий команды по TCP
 */
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static CollectionManager collectionManager = new CollectionManager();
    private static Deque<String> historyDeque = new ArrayDeque<>();
    private static CommandProcessor commandProcessor;

    public Server(CollectionManager collectionManager, Deque<String> historyDeque) {
        Server.collectionManager = collectionManager;
        Server.historyDeque = historyDeque;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            logger.info("Сервер запущен");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());) {

                    commandProcessor = new CommandProcessor(collectionManager, historyDeque, out, in, "server");
                    commandProcessor.CommandPut();

                    logger.info("Клиент подключен");

                    String responseText;
                    while (true) {
                        Object obj = in.readObject(); // 🟡 Блокирующий вызов: ждет объект от клиента
                        if (obj instanceof Request request) {
                            String command = request.commandName();
                            Ticket argument = request.argument();

                            if (argument != null) {
                                collectionManager.getQueue().add(argument);
                                responseText = ("Элемент добавлен в коллекцию");
                            } else {
                                responseText = commandProcessor.executeCommand(command);
                                logger.info("Команда без аргумента выполнена");
                            }

                            out.writeObject(new Response(responseText));
                            out.flush();

                            if ("exit".equals(command)) {
                                logger.info("Клиент завершил сессию командой exit");
                                break;
                            }
                        }

                        logger.info("Команда выполнена, ответ отправлен клиенту");


                    }
                } catch (EOFException | SocketException e) {
                    logger.error("Клиент отключился");
                } catch (ClassNotFoundException | IOException e) {
                    logger.error("Произошла ошибка при обработке клиента");
                    e.printStackTrace(); // логируем ошибку
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
