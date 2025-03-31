package Console;

import Collections.CollectionManager;
import Commands.CommandProcessor;

import java.util.Deque;

/**
 * Класс, представляющий сессию работы программы.
 * Этот класс управляет процессом работы программы, создавая объект клиента,
 * который обрабатывает ввод пользователя и выполняет соответствующие команды.
 */
public class Session {
    private final CollectionManager collectionManager;
    private final Deque<String> historyDeque;
    private final CommandProcessor commandProcessor;

    /**
     * Конструктор для создания объекта Session.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     * @param historyDeque Очередь команд, предназначенная для хранения истории выполнения.
     * @param commandProcessor Обработчик команд, управляющий процессом выполнения команд.
     */
    public Session(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Метод для запуска сессии программы.
     * Создает объект Client и инициирует взаимодействие с пользователем.
     */
    public void run() {
        Client client = new Client(collectionManager, historyDeque, commandProcessor); // Создаем объект клиента
        client.interact(); // Запускаем взаимодействие с пользователем
    }
}
