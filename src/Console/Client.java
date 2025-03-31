package Console;

import java.util.Deque;
import java.util.Scanner;
import Commands.CommandProcessor;
import Collections.CollectionManager;

/**
 * Класс для взаимодействия с пользователем в консоли.
 * Этот класс обрабатывает ввод команд от пользователя и выполняет их с использованием
 * команды-обработчика и менеджера коллекции.
 */
public class Client {
    private final CollectionManager collectionManager;
    private Deque<String> historyDeque;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор для создания объекта Client.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     * @param historyDeque Очередь команд, предназначенная для хранения истории выполнения.
     * @param commandProcessor Обработчик команд, который управляет процессом выполнения команд.
     */
    public Client(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Метод для начала взаимодействия с пользователем.
     * Запрашивает ввод команды и передает управление на выполнение команды.
     */
    public void interact() {
        System.out.print("Enter command: ");
        executeCommand(); // Вызов метода для выполнения команды
    }

    /**
     * Метод для получения пользовательского ввода.
     * Ожидает ввод строки с клавиатуры и возвращает ее без ведущих и завершающих пробелов.
     *
     * @return Введенная пользователем строка.
     */
    public String userInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.trim();
    }

    /**
     * Метод для выполнения введенной пользователем команды.
     * Получает команду от пользователя и передает ее обработчику команд для исполнения.
     */
    public void executeCommand() {
        String command = userInput(); // Получаем команду от пользователя
        commandProcessor.CommandPut(); // Добавляем команду в стек обработчика
        commandProcessor.executeCommand(command); // Исполняем команду
    }
}
