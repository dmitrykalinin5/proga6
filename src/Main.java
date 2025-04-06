import Collections.CollectionManager;
import Commands.CommandProcessor;
import Console.Client;
import Console.Server;
import java.util.Deque;
import java.util.ArrayDeque;

public class Main {

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
            server.run();
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
