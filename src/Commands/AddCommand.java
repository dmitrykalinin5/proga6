package Commands;

import Collections.*;
import Console.Client;
import Validaters.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    private String result;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Конструктор для создания команды добавления элемента.
     *
     * @param collectionManager Менеджер коллекции, в которую будет добавлен элемент
     * @param commandProcessor Обработчик команд
     */
    public AddCommand(CollectionManager collectionManager, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.commandProcessor = commandProcessor;
        this.reader = commandProcessor.getReader();
        this.writer = commandProcessor.getWriter();
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

        try {
            writer.print("Введите ваше имя: ");
            writer.flush();
            String userInput = reader.readLine();
            NameValidation nameValidation = new NameValidation(commandProcessor, userInput);
            String name = nameValidation.getName();

            writer.println("Ввод координат:");
            writer.flush();

            writer.println("Введите координату x: ");
            writer.flush();
            String xInput = reader.readLine();
            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(commandProcessor, xInput);
            int x = xCoordinateValidation.getX();

            writer.print("Введите координату y: ");
            writer.flush();
            String yInput = reader.readLine();
            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(commandProcessor, yInput);
            double y = yCoordinateValidation.getY();
            Coordinates coordinates = new Coordinates(x, y);

            LocalDateTime date = LocalDateTime.now();

            writer.print("Введите цену: ");
            writer.flush();
            String priceInput = reader.readLine();
            PriceValidation priceValidation = new PriceValidation(commandProcessor, priceInput);
            Long price = priceValidation.getPrice();

            writer.print("Введите тип билета (VIP, USUAL, CHEAP): ");
            writer.flush();
            String typeInput = reader.readLine();
            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(commandProcessor, typeInput);
            TicketType ticketType = ticketTypeValidation.getTicketType();

            writer.print("Введите дату рождения в формате DD.MM.YYYY: ");
            writer.flush();
            String birthdayInput = reader.readLine();
            BirthdayValidation birthdayValidation = new BirthdayValidation(commandProcessor, birthdayInput);
            String birthdayString = birthdayValidation.getBirthday();
            LocalDate localdate = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());

            writer.print("Введите ваш рост: ");
            writer.flush();
            String heightInput = reader.readLine();
            HeightValidation heightValidation = new HeightValidation(commandProcessor, heightInput);
            Long height = heightValidation.getHeight();

            writer.print("Введите ваш вес: ");
            writer.flush();
            String weightInput = reader.readLine();
            WeightValidation weightValidation = new WeightValidation(commandProcessor, weightInput);
            int weight = weightValidation.getWeight();

            writer.print("Введите координаты вашей локации через пробел (x y z): ");
            writer.flush();
            String locationInput = reader.readLine();
            LocationValidation locationValidation = new LocationValidation(commandProcessor, locationInput);
            Location location = locationValidation.getLocation();

            Person person = new Person(birthday, height, weight, location);
            Ticket ticket = new Ticket(newId, name, coordinates, date, price, ticketType, person);
            this.collectionManager.getQueue().add(ticket);

            response("Элемент добавлен");
            // writer.println("Элемент добавлен");
            // writer.flush();
        } catch (IOException e) {
            response("Ошибка ввода/вывода: " + e.getMessage());
        }
    }

    @Override
    public void response(String result) {
        this.result = result;
    }

    @Override
    public String getResponse() {
        return this.result;
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая добавляет элемент в коллекцию
     */
    @Override
    public String description() { return "Adds element to collection"; }
}
