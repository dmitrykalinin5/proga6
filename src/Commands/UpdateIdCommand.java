package Commands;

import Collections.*;
import Console.Client;
import Validaters.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private PriorityQueue<Ticket> queue;
    private BufferedReader in;
    private PrintWriter out;
    private String result;

    /**
     * Конструктор для создания объекта UpdateIdCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     * @param historyDeque Очередь команд, предназначенная для хранения истории выполнения.
     * @param commandProcessor Обработчик команд, используемый для выполнения команд в скрипте.
     */
    public UpdateIdCommand(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor, BufferedReader in, PrintWriter out) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
        this.in = in;
        this.out = out;
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

            boolean flag = false;
            queue = collectionManager.getQueue();
            for (Ticket ticket : queue) {
                if (ticket.getId() == id) {
                    flag = true;
                }
            }

            // Цикл для получения корректного ввода
            if (flag) {
                while (true) {
                    String input;
                    if (commandProcessor.getScriptFlag()) {
                        input = commandProcessor.getNextCommand().trim(); // Ввод из скрипта
                        System.out.println(input);
                    } else {
                        out.println("Какой элемент вы хотите обновить? (имя, координаты, цена, тип билета, дата рождения, рост, вес, локация): \u00A0");
                        input = in.readLine().trim(); // Ввод с клавиатуры
                    }
                    boolean isUpdated = update(id, input); // Попытка обновить элемент
                    if (isUpdated) {
                        break; // Если обновление прошло успешно, выходим из цикла
                    } else {
                        out.println("Некорректный ввод"); // Если ввод некорректный, просим повторить
                    }
                }
                response("Данные обновлены");
            } else {response("Неверный айди");}
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            response("Некорректный ввод");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(int id, String element) {
        queue = collectionManager.getQueue();
        try {
            for (Ticket ticket : queue) {
                if (ticket.getId() == id) {
                    CollectionManager c = new CollectionManager();
                    Deque<String> h = new ArrayDeque<>();
                    String userInput;
                    switch (element.toLowerCase()) {
                        case "имя":
                            out.println("Введите обновленное имя: \u00A0");
                            userInput = in.readLine();
                            NameValidation nameValidation = new NameValidation(commandProcessor, userInput, in, out);
                            String name = nameValidation.getName();
                            ticket.setName(name);
                            return true;
                        case "координаты":
                            out.println("--Ввод новых координат--");
                            out.println("Введите обновленную координату x: \u00A0");
                            userInput = in.readLine();
                            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(commandProcessor, userInput, in, out);
                            int x = xCoordinateValidation.getX();
                            out.println("Введите обновленную координату y: \u00A0");
                            userInput = in.readLine();
                            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(commandProcessor, userInput, in, out);
                            double y = yCoordinateValidation.getY();
                            Coordinates coords = new Coordinates(x, y);
                            ticket.setCoordinates(coords);
                            return true;
                        case "цена":
                            out.println("Введите обновленную цену: \u00A0");
                            userInput = in.readLine();
                            PriceValidation priceValidation = new PriceValidation(commandProcessor, userInput, in, out);
                            Long price = priceValidation.getPrice();
                            ticket.setPrice(price);
                            return true;
                        case "тип билета":
                            out.println("Введите обновленный тип билета (VIP, USUAL, CHEAP): \u00A0");
                            userInput = in.readLine();
                            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(commandProcessor, userInput, in, out);
                            TicketType ticketType = ticketTypeValidation.getTicketType();
                            ticket.setType(ticketType);
                            return true;
                        case "дата рождения":
                            out.println("Введите обновленную дату рождения в формате DD.MM.YYYY: \u00A0");
                            userInput = in.readLine();
                            BirthdayValidation birthdayValidation = new BirthdayValidation(commandProcessor, userInput, in, out);
                            String birthdayInput = birthdayValidation.getBirthday();
                            // парсинг даты из формата DD.MM.YYYY в ZonedDateTime
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            LocalDate localdate = LocalDate.parse(birthdayInput, formatter);
                            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());
                            ticket.setBirthday(birthday);
                            return true;
                        case "рост":
                            out.println("Введите обновленный рост: \u00A0");
                            userInput = in.readLine();
                            HeightValidation heightValidation = new HeightValidation(commandProcessor, userInput, in, out);
                            Long height = heightValidation.getHeight();
                            ticket.setHeight(height);
                            return true;
                        case "вес":
                            out.println("Введите обновленный вес: \u00A0");
                            userInput = in.readLine();
                            WeightValidation weightValidation = new WeightValidation(commandProcessor, userInput, in, out);
                            int weight = weightValidation.getWeight();
                            ticket.setWeight(weight);
                            return true;
                        case "локация":
                            out.println("Введите обновленные значения координат через пробел (x y z): \u00A0");
                            userInput = in.readLine();
                            LocationValidation locationValidation = new LocationValidation(commandProcessor, userInput, in, out);
                            Location loc = locationValidation.getLocation();
                            ticket.setLocation(loc);
                            return true;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            out.println("Такого элемента не существует");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
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
     * @return Описание команды, которая обновляет данные элемента по его id.
     */
    @Override
    public String description() {
        return "Обновить элемент по id";
    }
}