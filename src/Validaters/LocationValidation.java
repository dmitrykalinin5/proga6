package Validaters;

import Collections.Location;
import Commands.CommandProcessor;
import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации локации.
 * Проверяет, является ли введенная локация валидной (состоящей из трех числовых значений).
 */
public class LocationValidation implements Validation {
    private Location location;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса.
     *
     * @param commandProcessor Компонент для обработки команд (например, для выполнения скриптов).
     * @param userInput Ввод пользователя, полученный извне.
     */
    public LocationValidation(CommandProcessor commandProcessor, String userInput) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
    }

    /**
     * Метод для валидации введенной локации.
     * Запрашивает ввод локации у пользователя и проверяет корректность ввода.
     * Локация состоит из трех чисел: x (long), y (double), и z (float).
     *
     * @param userInput строка, введенная пользователем.
     * @return Корректное значение локации, если оно валидно.
     */
    public Location validation(String userInput) {
        while (true) {
            try {
                String input = userInput.trim();
                String[] elements = input.split(" ");
                // Проверяем, что введены 3 элемента
                if (elements.length != 3) {
                    System.out.println("Неверное количество элементов. Введите 3 значения.");
                    continue;
                }

                // Преобразуем введенные строки в соответствующие типы данных
                long x = Long.parseLong(elements[0]);
                double y = Double.parseDouble(elements[1]);
                Float z = Float.parseFloat(elements[2]);

                // Создаем объект Location с введенными значениями
                this.location = new Location(x, y, z);
                return this.location;
            } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Некорректный ввод. Убедитесь, что все три значения числовые.");
            }
        }
    }

    /**
     * Метод для получения значения локации.
     *
     * @return Введенная локация.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Метод для проверки валидности введенной локации.
     *
     * @return true, если локация валидна, иначе false.
     */
    @Override
    public boolean validate() {
        return location != null;
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректного ввода локации.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в координатах локации";
    }
}
