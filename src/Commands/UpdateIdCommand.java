package Commands;

import Collections.*;
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
 */
public class UpdateIdCommand implements Command {
    private int id;
    private final CollectionManager collectionManager;
    private final Deque<String> historyDeque;
    private final CommandProcessor commandProcessor;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private String result;

    public UpdateIdCommand(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
        this.reader = commandProcessor.getReader();
        this.writer = commandProcessor.getWriter();
    }

    @Override
    public void execute(String[] args) {
        try {
            id = Integer.parseInt(args[1]);
            writer.println("Что вы хотите обновить? (имя, координаты, цена, тип билета, дата рождения, рост, вес, локация):");
            writer.print("> ");
            writer.flush();

            while (true) {
                String input;
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                    writer.println(input);
                } else {
                    input = reader.readLine().trim();
                }

                boolean isUpdated = update(id, input);
                if (isUpdated) {
                    break;
                } else {
                    writer.println("Некорректный ввод, попробуйте снова.");
                    writer.print("> ");
                    writer.flush();
                }
            }

            writer.println("Данные обновлены");
            writer.flush();
        } catch (ArrayIndexOutOfBoundsException e) {
            writer.println("ID не передан в команду.");
            writer.flush();
        } catch (IOException e) {
            writer.println("Ошибка чтения: " + e.getMessage());
            writer.flush();
        }
    }

    public boolean update(int id, String element) throws IOException {
        PriorityQueue<Ticket> queue = collectionManager.getQueue();
        for (Ticket ticket : queue) {
            if (ticket.getId() == id) {
                switch (element.toLowerCase()) {
                    case "имя":
                        writer.print("Введите новое имя: ");
                        writer.flush();
                        String nameInput = reader.readLine();
                        NameValidation nameValidation = new NameValidation(commandProcessor, nameInput);
                        ticket.setName(nameValidation.getName());
                        return true;

                    case "координаты":
                        writer.print("Введите x: ");
                        writer.flush();
                        String xInput = reader.readLine();
                        XCoordinateValidation xVal = new XCoordinateValidation(commandProcessor, xInput);

                        writer.print("Введите y: ");
                        writer.flush();
                        String yInput = reader.readLine();
                        YCoordinateValidation yVal = new YCoordinateValidation(commandProcessor, yInput);

                        ticket.setCoordinates(new Coordinates(xVal.getX(), yVal.getY()));
                        return true;

                    case "цена":
                        writer.print("Введите цену: ");
                        writer.flush();
                        String priceInput = reader.readLine();
                        PriceValidation priceVal = new PriceValidation(commandProcessor, priceInput);
                        ticket.setPrice(priceVal.getPrice());
                        return true;

                    case "тип билета":
                        writer.print("Введите тип билета (VIP, USUAL, CHEAP): ");
                        writer.flush();
                        String typeInput = reader.readLine();
                        TicketTypeValidation typeVal = new TicketTypeValidation(commandProcessor, typeInput);
                        ticket.setType(typeVal.getTicketType());
                        return true;

                    case "дата рождения":
                        writer.print("Введите дату (DD.MM.YYYY): ");
                        writer.flush();
                        String birthInput = reader.readLine();
                        BirthdayValidation birthVal = new BirthdayValidation(commandProcessor, birthInput);
                        LocalDate localDate = LocalDate.parse(birthVal.getBirthday(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        ticket.setBirthday(localDate.atStartOfDay(ZoneId.systemDefault()));
                        return true;

                    case "рост":
                        writer.print("Введите рост: ");
                        writer.flush();
                        String heightInput = reader.readLine();
                        HeightValidation heightVal = new HeightValidation(commandProcessor, heightInput);
                        ticket.setHeight(heightVal.getHeight());
                        return true;

                    case "вес":
                        writer.print("Введите вес: ");
                        writer.flush();
                        String weightInput = reader.readLine();
                        WeightValidation weightVal = new WeightValidation(commandProcessor, weightInput);
                        ticket.setWeight(weightVal.getWeight());
                        return true;

                    case "локация":
                        writer.print("Введите координаты (x y z): ");
                        writer.flush();
                        String locationInput = reader.readLine();
                        LocationValidation locVal = new LocationValidation(commandProcessor, locationInput);
                        ticket.setLocation(locVal.getLocation());
                        return true;
                }
            }
        }

        writer.println("Элемент с таким ID не найден.");
        writer.flush();
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

    @Override
    public String description() {
        return "Обновить элемент по id";
    }
}
