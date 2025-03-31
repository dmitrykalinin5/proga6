import Collections.CollectionManager;
import Commands.CommandProcessor;
import Console.Client;
import Console.Server;
import java.util.Deque;
import java.util.ArrayDeque;


/**
 * Главный класс программы, отвечающий за инициализацию и запуск сессий.
 */
public class Main {

    /**
     * Точка входа в программу, где создаются все необходимые компоненты и запускаются сессии.
     *
     * @param args Аргументы командной строки (не используются в данной реализации)
     */
    public static void main(String[] args) {
        // Создание и загрузка коллекции
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.loadFromFile();

        // Инициализация истории команд и процессора команд
        Deque<String> historyDeque = new ArrayDeque<>();
        CommandProcessor commandProcessor = new CommandProcessor(collectionManager, historyDeque);
        collectionManager.setCommandProcessor(commandProcessor);

        new Thread(() -> {
            Server server = new Server(collectionManager, historyDeque, commandProcessor);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client client = new Client(collectionManager, historyDeque, commandProcessor);
        client.interact();

        // Запуск бесконечного цикла для создания новых сессий
//        while (true) {
//            Server server = new Server(collectionManager, historyDeque, commandProcessor);
//            server.run();
//        }
    }
}
