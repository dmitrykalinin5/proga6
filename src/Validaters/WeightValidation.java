package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;
import Console.Client;

import java.util.Scanner;

/**
 * Класс для валидации веса.
 * Проверяет, что введенный вес является положительным числом.
 */
public class WeightValidation implements Validation {
    private int weight;
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса WeightValidation.
     *
     * @param message Сообщение, которое будет выведено при запросе ввода веса.
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     */
    public WeightValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации веса.
     * Запрашивает у пользователя ввод и проверяет, что вес больше 0.
     *
     * @return Валидированное значение веса.
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
                this.weight = Integer.parseInt(input);
                if (!validate()) {
                    System.out.println("Вес должен быть больше 0");
                    continue;
                }
                return this.weight;
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Некорректный ввод: " + e.getMessage());
            }
        }
    }

    /**
     * Метод для получения валидированного значения веса.
     *
     * @return Введенный вес.
     */
    public int getweight() {
        return weight;
    }

    /**
     * Метод для валидации веса.
     * Проверяет, что вес больше 0.
     *
     * @return true, если вес больше 0, иначе false.
     */
    @Override
    public boolean validate() {
        return weight > 0;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода веса.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в росте";  // Возможно, это сообщение должно быть более специфичным для веса, например, "Ошибка в весе"
    }
}
