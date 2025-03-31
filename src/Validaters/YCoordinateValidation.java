package Validaters;

import Commands.CommandProcessor;
import Console.Client;
import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации координаты Y.
 * Запрашивает у пользователя ввод координаты Y и проверяет, что ввод является числом с плавающей запятой.
 */
public class YCoordinateValidation implements Validation {
    private double y;
    private String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса YCoordinateValidation.
     *
     * @param message Сообщение, которое будет выведено при запросе ввода координаты Y.
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     */
    public YCoordinateValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации координаты Y.
     * Запрашивает у пользователя ввод и проверяет, что это число с плавающей запятой.
     *
     * @return Валидированное значение координаты Y.
     */
    public double validation() {
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
                this.y = Double.parseDouble(input);
                return this.y;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод");
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
