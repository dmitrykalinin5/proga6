package Commands;

import Collections.*;
import Console.Client;
import Validaters.*;

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

    /**
     * Конструктор для создания объекта UpdateIdCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     * @param historyDeque Очередь команд, предназначенная для хранения истории выполнения.
     * @param commandProcessor Обработчик команд, используемый для выполнения команд в скрипте.
     */
    public UpdateIdCommand(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
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
            System.out.println("Какой элемент вы хотите обновить? (имя, координаты, цена, тип билета, дата рождения, рост, вес, локация): ");

            // Цикл для получения корректного ввода
            while (true) {
                String input;
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim(); // Ввод из скрипта
                    System.out.println(input);
                } else {
                    Scanner scanner = new Scanner(System.in);
                    input = scanner.nextLine().trim(); // Ввод с клавиатуры
                }
                boolean isUpdated = update(id, input); // Попытка обновить элемент
                if (isUpdated) {
                    break; // Если обновление прошло успешно, выходим из цикла
                } else {
                    System.out.print("Некорректный ввод, попробуйте еще раз: "); // Если ввод некорректный, просим повторить
                }
            }
            System.out.println("Данные обновлены");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный ввод"); // Ошибка, если id не передан
        }
    }

    public boolean update(int id, String element) {
        queue = collectionManager.getQueue();
        try {
            for (Ticket ticket : queue) {
                if (ticket.getId() == id) {
                    CollectionManager c = new CollectionManager();
                    Deque<String> h = new ArrayDeque<>();
                    String message;
                    switch (element.toLowerCase()) {
                        case "имя":
                            message = "Введите обновленное имя: ";
                            NameValidation nameValidation = new NameValidation(commandProcessor);
                            String name = nameValidation.getName();
                            ticket.setName(name);
                            return true;
                        case "координаты":
                            System.out.println("Ввод новых координат:");
                            message = "Введите обновленную координату x: ";
                            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(message, commandProcessor);
                            int x = xCoordinateValidation.getX();
                            message = "Введите обновленную координату y: ";
                            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(message, commandProcessor);
                            double y = yCoordinateValidation.getY();
                            Coordinates coords = new Coordinates(x, y);
                            ticket.setCoordinates(coords);
                            return true;
                        case "цена":
                            message = "Введите обновленную цену: ";
                            PriceValidation priceValidation = new PriceValidation(commandProcessor);
                            Long price = priceValidation.getPrice();
                            ticket.setPrice(price);
                            return true;
                        case "тип билета":
                            message = "Введите обновленный тип билета (VIP, USUAL, CHEAP): ";
                            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(message, commandProcessor);
                            TicketType ticketType = ticketTypeValidation.getTicketType();
                            ticket.setType(ticketType);
                            return true;
                        case "дата рождения":
                            message = "Введите обновленную дату рождения в формате DD.MM.YYYY: ";
                            BirthdayValidation birthdayValidation = new BirthdayValidation(message, commandProcessor);
                            String birthdayInput = birthdayValidation.getBirthday();
                            // парсинг даты из формата DD.MM.YYYY в ZonedDateTime
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            LocalDate localdate = LocalDate.parse(birthdayInput, formatter);
                            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());
                            ticket.setBirthday(birthday);
                            return true;
                        case "рост":
                            message = "Введите обновленный рост: ";
                            HeightValidation heightValidation = new HeightValidation(message, commandProcessor);
                            Long height = heightValidation.getHeight();
                            ticket.setHeight(height);
                            return true;
                        case "вес":
                            message = "Введите обновленный вес: ";
                            WeightValidation weightValidation = new WeightValidation(message, commandProcessor);
                            int weight = weightValidation.getweight();
                            ticket.setWeight(weight);
                            return true;
                        case "локация":
                            message = "Введите обновленные значения координат через пробел (x y z): ";
                            LocationValidation locationValidation = new LocationValidation(message, commandProcessor);
                            Location loc = locationValidation.getLocation();
                            ticket.setLocation(loc);
                            return true;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Такого элемента не существует");
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