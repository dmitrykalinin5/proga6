package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * Команда для отображения всех элементов коллекции.
 * Этот класс реализует команду, которая выводит все элементы текущей коллекции.
 */
public class ShowCommand implements Command {
    private final CollectionManager collectionManager;
    private PriorityQueue<Ticket> queue;

    /**
     * Конструктор для создания объекта ShowCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     */
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду отображения всех элементов коллекции.
     * Выводит элементы коллекции, вызывая метод getAllElements из CollectionManager.
     *
     * @param args Аргументы команды (не используются в данном случае).
     */
    @Override
    public void execute(String[] args) {
        queue = collectionManager.getQueue();
        System.out.println();
        if (this.queue.isEmpty()) {
            System.out.println("Коллекция пуста\n");
        } else {
            StringBuilder result = new StringBuilder();
            this.queue.stream()
                    .sorted(Comparator.comparing(Ticket::getId))
                    .forEach(ticket -> result.append(ticket.toString()).append("\n"));
            System.out.print(result.toString());
        }
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая отображает все элементы коллекции.
     */
    @Override
    public String description() {
        return "Displays all elements from collection";
    }
}
