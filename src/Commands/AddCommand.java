package Commands;

import Collections.*;
import Console.Client;
import Validaters.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.*;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Выполняет все необходимые шаги для ввода данных пользователя,
 * валидации этих данных и создания нового объекта Ticket, который затем добавляется в коллекцию.
 */
public class AddCommand implements Command {
    private final CollectionManager collectionManager;
    private final CommandProcessor commandProcessor;

    /**
     * Конструктор для создания команды добавления элемента.
     *
     * @param collectionManager Менеджер коллекции, в которую будет добавлен элемент
     * @param commandProcessor Обработчик команд
     */
    public AddCommand(CollectionManager collectionManager, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Выполняет команду добавления нового билета в коллекцию.
     * Запрашивает у пользователя данные для создания объекта Ticket, валидирует их
     * и добавляет в коллекцию.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        int newId = collectionManager.getNextId();

        String message = "Введите ваше имя: ";
        NameValidation nameValidation = new NameValidation(message, commandProcessor);
        String name = nameValidation.getName();

        System.out.println("Ввод координат:");
        message = "Введите координату x: ";
        XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(message, commandProcessor);
        int x = xCoordinateValidation.getX();
        message = "Введите координату y: ";
        YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(message,commandProcessor);
        double y = yCoordinateValidation.getY();
        Coordinates coordinates = new Coordinates(x, y); // коорды

        LocalDateTime date = LocalDateTime.now(); // дата

        message = "Введите цену: ";
        PriceValidation priceValidation = new PriceValidation(message, commandProcessor);
        Long price = priceValidation.getPrice();

        message = "Введите тип вашего билета(VIP, USUAL, CHEAP): ";
        TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(message, commandProcessor);
        TicketType ticketType = ticketTypeValidation.getTicketType();

        message = "Введите дату рождения в формате DD.MM.YYYY: ";
        BirthdayValidation birthdayValidation = new BirthdayValidation(message, commandProcessor);
        String birthdayInput = birthdayValidation.getBirthday();
        // парсинг даты из формата DD.MM.YYYY в ZonedDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localdate = LocalDate.parse(birthdayInput, formatter);
        ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());

        message = "Введите ваш рост: ";
        HeightValidation heightValidation = new HeightValidation(message, commandProcessor);
        Long height = heightValidation.getHeight();

        message = "Введите ваш вес: ";
        WeightValidation weightValidation = new WeightValidation(message, commandProcessor);
        int weight = weightValidation.getweight();

        message = "Введите координаты вашей локации через пробел (x y z): ";
        LocationValidation locationValidation = new LocationValidation(message, commandProcessor);
        Location location = locationValidation.getLocation();

        Person person = new Person(birthday, height, weight, location);
        Ticket ticket = new Ticket(newId, name, coordinates, date,
                price, ticketType, person);

        this.collectionManager.getQueue().add(ticket);
        System.out.println("Элемент добавлен");
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая добавляет элемент в коллекцию
     */
    @Override
    public String description() { return "Adds element to collection"; }
}
