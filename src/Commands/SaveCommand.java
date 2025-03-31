package Commands;

import Collections.CollectionManager;

/**
 * Команда для сохранения коллекции в файл.
 * Этот класс реализует команду, которая сохраняет текущую коллекцию в файл.
 */
public class SaveCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор для создания объекта SaveCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     */
    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду сохранения коллекции в файл.
     * Выводит сообщение о начале сохранения и вызывает метод сохранения из CollectionManager.
     *
     * @param args Аргументы команды (не используются в данном случае).
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Сохранение коллекции...");
        collectionManager.saveToFile();
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая сохраняет коллекцию в файл.
     */
    @Override
    public String description() {
        return "Save the collection to a file";
    }
}
