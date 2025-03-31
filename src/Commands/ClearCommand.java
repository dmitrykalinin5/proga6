package Commands;

import Collections.CollectionManager;

/**
 * Команда для очистки коллекции.
 * Этот класс реализует команду, которая удаляет все элементы из коллекции.
 */
public class ClearCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор для создания команды очистки коллекции.
     *
     * @param collectionManager Менеджер коллекции, которая будет очищена
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду очистки коллекции.
     * Удаляет все элементы из коллекции и выводит сообщение о завершении операции.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        collectionManager.getQueue().clear();
        System.out.println("Коллекция очищена");
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая очищает коллекцию
     */
    @Override
    public String description() {
        return "Clears collection";
    }
}
