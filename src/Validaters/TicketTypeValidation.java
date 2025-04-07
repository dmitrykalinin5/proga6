package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;
import Collections.TicketType;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class TicketTypeValidation implements Validation {
    private TicketType ticketType;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public TicketTypeValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    /**
     * Метод для валидации типа билета.
     * Запрашивает у пользователя ввод и проверяет, что введенный тип является допустимым значением из перечисления TicketType.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение типа билета.
     */
    public TicketType validation(String userInput) {
        while (true) {
            try {
                String input;
                // Если скрипт выполняется, получаем команду из скрипта, иначе используем переданный userInput
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                } else {
                    input = userInput.trim();
                }
                this.ticketType = TicketType.valueOf(input.toUpperCase());
                return ticketType;
            } catch (IllegalArgumentException e) {
                assert out != null;
                out.println("Неправильный формат ввода");
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
