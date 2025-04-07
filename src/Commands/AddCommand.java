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
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Конструктор для создания команды добавления элемента.
     *
     * @param collectionManager Менеджер коллекции, в которую будет добавлен элемент
     * @param commandProcessor Обработчик команд
     */
    public AddCommand(CollectionManager collectionManager, CommandProcessor commandProcessor, BufferedReader in, PrintWriter out) {
        this.collectionManager = collectionManager;
        this.commandProcessor = commandProcessor;
        this.in = in;
        this.out = out;
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
            String userInput;
            if (commandProcessor.getScriptFlag()) {
                out.println("Введите ваше имя: ");
                userInput = commandProcessor.getNextCommand().trim();
                out.println(userInput);
            } else {
                out.println("Введите ваше имя:\u00A0");
                userInput = in.readLine();
            }

            System.out.println("name: " + userInput);
            NameValidation nameValidation = new NameValidation(commandProcessor, userInput, in, out);
            String name = nameValidation.getName();

            out.println("--Ввод координат--");
            String xInput;
            if (commandProcessor.getScriptFlag()) {
                out.println("Введите координату x: ");
                xInput = commandProcessor.getNextCommand().trim();
                out.println(xInput);
            } else {
                out.println("Введите координату x:\u00A0 ");
                xInput = in.readLine();
            }
            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(commandProcessor, xInput, in, out);
            int x = xCoordinateValidation.getX();

            String yInput;
            if (commandProcessor.getScriptFlag()) {
                out.println("Введите координату y: ");
                yInput = commandProcessor.getNextCommand().trim();
                out.println(xInput);
            } else {
                out.println("Введите координату y:\u00A0 ");
                yInput = in.readLine();
            }
            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(commandProcessor, yInput, in, out);
            double y = yCoordinateValidation.getY();
            Coordinates coordinates = new Coordinates(x, y);

            LocalDateTime date = LocalDateTime.now();

            out.println("Введите цену:\u00A0 ");
            String priceInput = in.readLine();
            PriceValidation priceValidation = new PriceValidation(commandProcessor, priceInput, in, out);
            Long price = priceValidation.getPrice();

            out.println("Введите тип билета (VIP, USUAL, CHEAP):\u00A0 ");
            String typeInput = in.readLine();
            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(commandProcessor, typeInput, in, out);
            TicketType ticketType = ticketTypeValidation.getTicketType();

            out.println("Введите дату рождения в формате DD.MM.YYYY:\u00A0 ");
            String birthdayInput = in.readLine();
            BirthdayValidation birthdayValidation = new BirthdayValidation(commandProcessor, birthdayInput, in, out);
            String birthdayString = birthdayValidation.getBirthday();
            LocalDate localdate = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());

            out.println("Введите ваш рост:\u00A0 ");
            String heightInput = in.readLine();
            HeightValidation heightValidation = new HeightValidation(commandProcessor, heightInput, in, out);
            Long height = heightValidation.getHeight();

            out.println("Введите ваш вес:\u00A0 ");
            String weightInput = in.readLine();
            WeightValidation weightValidation = new WeightValidation(commandProcessor, weightInput, in, out);
            int weight = weightValidation.getWeight();

            out.println("Введите координаты вашей локации через пробел (x y z):\u00A0 ");
            String locationInput = in.readLine();
            LocationValidation locationValidation = new LocationValidation(commandProcessor, locationInput, in, out);
            Location location = locationValidation.getLocation();

            Person person = new Person(birthday, height, weight, location);
            Ticket ticket = new Ticket(newId, name, coordinates, date, price, ticketType, person);
            this.collectionManager.getQueue().add(ticket);

            response("Элемент добавлен.");
            // writer.println("Элемент добавлен");
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
