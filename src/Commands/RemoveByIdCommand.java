package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Команда для удаления элемента из коллекции по его идентификатору.
 * Этот класс реализует команду, которая удаляет элемент из коллекции, используя его уникальный идентификатор.
 */
public class RemoveByIdCommand implements Command {
    private CollectionManager collectionManager;
    private PriorityQueue<Ticket> queue;

    /**
     * Конструктор для создания объекта RemoveByIdCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду удаления элемента из коллекции по его id.
     * Если элемент с указанным id существует и успешно удалён, выводится сообщение об успешном удалении.
     * В случае ошибки (например, если id не существует или неправильно указан), выводится соответствующее сообщение.
     *
     * @param args Аргументы команды, где args[1] — это id элемента, который нужно удалить.
     */
    @Override
    public void execute(String[] args) {
        this.queue = collectionManager.getQueue();
        boolean isRemoved = false;
        Iterator<Ticket> iterator = queue.iterator();
        int id = Integer.parseInt(args[1]);
        try {
            while (iterator.hasNext()) {
                Ticket ticket = iterator.next();
                if (ticket.getId() == id) {
                    iterator.remove();
                    isRemoved = true;
                }
            }
            if (isRemoved) {
                System.out.println("Removed " + id + " from the collection");
            } else {
                System.out.println("Could not remove " + id + " from the collection");
            }
        } catch (NumberFormatException e) {
            System.out.println("id должно быть числом");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный ввод");
        }
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая удаляет элемент по его id из коллекции.
     */
    @Override
    public String description() {
        return "Removes an element by its id";
    }
}
