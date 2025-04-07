package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class XCoordinateValidation implements Validation {
    private int x;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public XCoordinateValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) throws IOException {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    /**
     * Метод для валидации координаты X.
     * Запрашивает у пользователя ввод и проверяет, что это целое число.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение координаты X.
     */
    public int validation(String userInput) throws IOException {
        while (true) {
            try {
                String input;
                input = userInput.trim();
                this.x = Integer.parseInt(input);
                return this.x;
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод: ");
            }
        }
    }

    /**
     * Метод для получения валидированного значения координаты X.
     *
     * @return Введенная координата X.
     */
    public int getX() {
        return x;
    }

    /**
     * Метод для валидации координаты X.
     * Для координаты X всегда возвращается true, так как нет дополнительных условий для валидации.
     *
     * @return true, так как нет специфических проверок для координаты X.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода координаты X.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в координате X";
    }
}
