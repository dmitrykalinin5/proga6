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
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса WeightValidation.
     *
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     * @param userInput Ввод пользователя, полученный извне.
     */
    public WeightValidation(CommandProcessor commandProcessor, String userInput) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
    }

    /**
     * Метод для валидации веса.
     * Запрашивает у пользователя ввод и проверяет, что вес больше 0.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение веса.
     */
    public int validation(String userInput) {
        while (true) {
            try {
                String input;
                // Если скрипт выполняется, получаем команду из скрипта, иначе используем переданный userInput
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                } else {
                    input = userInput.trim();
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
    public int getWeight() {
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
        return "Ошибка в весе";  // Теперь это сообщение соответствует весу, а не росту
    }
}
