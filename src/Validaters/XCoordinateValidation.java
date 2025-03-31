package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;
import Console.Client;

import java.util.Scanner;

/**
 * Класс для валидации координаты X.
 * Запрашивает у пользователя ввод координаты X и проверяет, что ввод является числом.
 */
public class XCoordinateValidation implements Validation {
    private int x;
    private String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса XCoordinateValidation.
     *
     * @param message Сообщение, которое будет выведено при запросе ввода координаты X.
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     */
    public XCoordinateValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации координаты X.
     * Запрашивает у пользователя ввод и проверяет, что это целое число.
     *
     * @return Валидированное значение координаты X.
     */
    public int validation() {
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
                this.x = Integer.parseInt(input);
                return this.x;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод: " + e.toString());
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
