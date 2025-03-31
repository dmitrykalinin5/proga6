package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Команда для удаления всех элементов из коллекции, у которых поле 'price' равно заданному значению.
 * Этот класс реализует команду, которая удаляет все элементы из коллекции, у которых цена равна указанной.
 */
public class RemoveAllByPriceCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор для создания объекта RemoveAllByPriceCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией
     */
    public RemoveAllByPriceCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду удаления всех элементов из коллекции, у которых поле 'price' равно заданной цене.
     * Если элементы с такой ценой найдены, они удаляются, иначе выводится сообщение о том, что таких элементов нет.
     * В случае неверного формата цены, выводится сообщение об ошибке.
     *
     * @param args Аргументы команды, где args[1] — это цена, по которой нужно выполнить удаление.
     */
    @Override
    public void execute(String[] args) {
        try {
            int price = Integer.parseInt(args[1]);
            PriorityQueue<Ticket> queue = collectionManager.getQueue();
            boolean flag = false;
            Iterator<Ticket> iterator = queue.iterator();
            while (iterator.hasNext()) {
                Ticket ticket = iterator.next();
                if (ticket.getPrice() == price) {
                    iterator.remove();
                    flag = true;
                }
            }
            if (flag) {
                System.out.println("Элементы успешно удалены");
            } else {
                System.out.println("Элементов с такой ценой нет");
            }
        } catch (NumberFormatException e) {
            System.out.println("Цена должна быть числом");
        }
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая удаляет все элементы с заданной ценой из коллекции.
     */
    @Override
    public String description() {
        return "Removes all elements from the collection where the 'price' field is equal to the specified value";
    }
}
