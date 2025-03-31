package Commands;

import Collections.CollectionManager;
import Console.Client;

import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

/**
 * Команда для обновления элемента коллекции по его id.
 * Этот класс реализует команду, которая позволяет пользователю обновить
 * данные элемента коллекции по заданному id.
 */
public class UpdateIdCommand implements Command {
    private int id;
    private final CollectionManager collectionManager;
    private final Deque<String> historyDeque;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор для создания объекта UpdateIdCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     * @param historyDeque Очередь команд, предназначенная для хранения истории выполнения.
     * @param commandProcessor Обработчик команд, используемый для выполнения команд в скрипте.
     */
    public UpdateIdCommand(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Выполняет команду обновления элемента коллекции по его id.
     * Запрашивает у пользователя информацию для обновления элемента (имя, координаты, цену и другие параметры)
     * и обновляет указанный элемент в коллекции.
     *
     * @param args Аргументы команды. Первым аргументом должен быть id элемента для обновления.
     */
    @Override
    public void execute(String[] args) {
        try {
            id = Integer.parseInt(args[1]); // Парсим id элемента
            System.out.println("Какой элемент вы хотите обновить? (имя, координаты, цена, тип билета, дата рождения, рост, вес, локация): ");

            // Цикл для получения корректного ввода
            while (true) {
                String input;
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim(); // Ввод из скрипта
                    System.out.println(input);
                } else {
                    Scanner scanner = new Scanner(System.in);
                    input = scanner.nextLine().trim(); // Ввод с клавиатуры
                }
                boolean isUpdated = collectionManager.update(id, input); // Попытка обновить элемент
                if (isUpdated) {
                    break; // Если обновление прошло успешно, выходим из цикла
                } else {
                    System.out.print("Некорректный ввод, попробуйте еще раз: "); // Если ввод некорректный, просим повторить
                }
            }
            System.out.println("Данные обновлены");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный ввод"); // Ошибка, если id не передан
        }
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая обновляет данные элемента по его id.
     */
    @Override
    public String description() {
        return "Обновить элемент по id";
    }
}
