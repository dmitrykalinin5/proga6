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
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса.
     *
     * @param message Сообщение, которое будет выведено пользователю для ввода локации.
     * @param commandProcessor Компонент для обработки команд (например, для выполнения скриптов).
     */
    public LocationValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации введенной локации.
     * Запрашивает ввод локации у пользователя и проверяет корректность ввода.
     * Локация состоит из трех чисел: x (long), y (double), и z (float).
     *
     * @return Корректное значение локации, если оно валидно.
     */
    public Location validation() {
        while (true) {
            try {
                System.out.print(message);
                String input;
                // Если скрипт выполняется, получаем команду из скрипта, иначе ожидаем ввод с клавиатуры
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                    System.out.println(input);
                } else {
                    Scanner scanner = new Scanner(System.in);
                    input = scanner.nextLine().trim();
                }
                String[] elements = input.split(" ");
                // Преобразуем введенные строки в соответствующие типы данных
                long x = Long.parseLong(elements[0]);
                double y = Double.parseDouble(elements[1]);
                Float z = Float.parseFloat(elements[2]);
                // Создаем объект Location с введенными значениями
                this.location = new Location(x, y, z);
                return this.location;
            } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Некорректный ввод");
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
        return "Ошибка в Coordinates";
    }
}
