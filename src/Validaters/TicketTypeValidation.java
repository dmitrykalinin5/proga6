package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;
import Collections.TicketType;
import Console.Client;

import java.util.Scanner;

/**
 * Класс для валидации типа билета.
 * Проверяет, что введенный тип билета является допустимым значением из перечисления TicketType.
 */
public class TicketTypeValidation implements Validation {
    private TicketType ticketType;
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса TicketTypeValidation.
     *
     * @param message Сообщение, которое будет выведено при запросе типа билета.
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     */
    public TicketTypeValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации типа билета.
     * Запрашивает у пользователя ввод и проверяет, что введенный тип является допустимым значением из перечисления TicketType.
     *
     * @return Валидированное значение типа билета.
     */
    public TicketType validation() {
        while (true) {
            try {
                System.out.print(message);
                String input;
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                    System.out.println(input);
                } else {
                    Scanner scanner = new Scanner(System.in);
                    input = scanner.nextLine().trim();
                }
                this.ticketType = TicketType.valueOf(input.toUpperCase());
                return ticketType;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат ввода");
            }
        }
    }

    /**
     * Метод для получения валидированного значения типа билета.
     *
     * @return Тип билета, введенный пользователем.
     */
    public TicketType getTicketType() {
        return ticketType;
    }

    /**
     * Метод для валидации типа билета.
     * Этот метод всегда возвращает true, так как тип билета либо валиден, либо выбрасывается исключение.
     *
     * @return true, так как тип билета всегда валиден после валидации.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Пустая строка, так как ошибка уже обрабатывается в validation().
     */
    @Override
    public String getErrorMessage() {
        return " ";
    }
}
