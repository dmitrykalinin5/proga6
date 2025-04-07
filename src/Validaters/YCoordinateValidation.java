package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class YCoordinateValidation implements Validation {
    private double y;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public YCoordinateValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    /**
     * Метод для валидации координаты Y.
     * Запрашивает у пользователя ввод и проверяет, что это число с плавающей запятой.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение координаты Y.
     */
    public double validation(String userInput) {
        while (true) {
            try {
                String input;
                input = userInput.trim();
                this.y = Double.parseDouble(input);
                return this.y;
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод: " + e.toString());
            }
        }
    }

    /**
     * Метод для получения валидированного значения координаты Y.
     *
     * @return Введенная координата Y.
     */
    public double getY() {
        return y;
    }

    /**
     * Метод для валидации координаты Y.
     * Для координаты Y всегда возвращается true, так как нет дополнительных условий для валидации.
     *
     * @return true, так как нет специфических проверок для координаты Y.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода координаты Y.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в координате Y";
    }
}
