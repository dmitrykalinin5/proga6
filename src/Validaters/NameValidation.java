package Validaters;

import Commands.CommandProcessor;
import Console.Client;
import Tools.Validation;
import java.util.Scanner;

public class NameValidation implements Validation {
    private String name;
    private String message;
    private CommandProcessor commandProcessor;

    public NameValidation(CommandProcessor commandProcessor, String userInput) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
    }

    public String validation(String userInput) {
        while (true) {
            try {
                String input;
                // Если скрипт выполняется, получаем команду из скрипта, иначе ожидаем ввод с клавиатуры
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                    System.out.println(input);
                } else {
                    input = userInput.trim();
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
