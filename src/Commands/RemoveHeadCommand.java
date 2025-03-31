package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.PriorityQueue;

/**
 * Команда для вывода и удаления первого элемента из коллекции.
 * Этот класс реализует команду, которая выводит первый элемент коллекции и удаляет его.
 */
public class RemoveHeadCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор для создания объекта RemoveHeadCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     */
    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду вывода и удаления первого элемента из коллекции.
     * Если коллекция не пуста, первый элемент будет выведен на экран и удален из коллекции.
     * Если коллекция пуста, будет выведено сообщение, что нечего выводить.
     *
     * @param args Аргументы команды (не используются в данном случае).
     */
    @Override
    public void execute(String[] args) {
        PriorityQueue<Ticket> queue = collectionManager.getQueue();
        boolean flag = false;
        if (!queue.isEmpty()) {
            System.out.println(queue.peek());
            queue.remove(queue.peek());
            flag = true;
        }
        if (flag) {
            System.out.println("Элемент успешно выведен и удален");
        } else {
            System.out.println("Нечего выводить! Коллекция пуста");
        }
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая выводит и удаляет первый элемент из коллекции.
     */
    @Override
    public String description() {
        return "Displays and removes the first element from collection";
    }
}
