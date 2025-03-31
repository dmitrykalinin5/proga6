package Validaters;

import Commands.CommandProcessor;
import Console.Client;
import Tools.Validation;
import java.util.Scanner;

/**
 * Класс для валидации имени.
 * Проверяет, является ли введенное имя валидным (не пустое и не null).
 */
public class NameValidation implements Validation {
    private String name;
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса.
     *
     * @param message Сообщение, которое будет выведено пользователю для ввода имени.
     * @param commandProcessor Компонент для обработки команд (например, для выполнения скриптов).
     */
    public NameValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации введенного имени.
     * Запрашивает ввод имени у пользователя и проверяет корректность ввода.
     *
     * @return Валидное имя, если оно не пустое и не null.
     */
    public String validation() {
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
                this.name = input;
                // Проверяем, что имя не пустое
                if (!validate()) {
                    System.out.println("Неверный формат ввода.");
                    continue;
                }
                return this.name;
            } catch (Exception e) {
                System.out.println("Некорректный ввод: " + e.getMessage());
            }
        }
    }

    /**
     * Метод для получения имени.
     *
     * @return Введенное имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для проверки валидности введенного имени.
     *
     * @return true, если имя не пустое и не null, иначе false.
     */
    @Override
    public boolean validate() {
        return name != null && !name.isEmpty();
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректного ввода имени.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в ID";
    }
}
