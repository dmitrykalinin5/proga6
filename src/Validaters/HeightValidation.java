package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации роста.
 * Проверяет, является ли введенное значение роста валидным (больше 0).
 */
public class HeightValidation implements Validation {
    private Long Height;
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса.
     *
     * @param message Сообщение, которое будет выведено пользователю для ввода роста.
     * @param commandProcessor Компонент для обработки команд (например, для выполнения скриптов).
     */
    public HeightValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации введенного роста.
     * Запрашивает ввод роста у пользователя, проверяет его корректность (рост должен быть больше 0).
     *
     * @return Корректное значение роста, если оно валидно.
     */
    public Long validation() {
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
                this.Height = Long.parseLong(input);
                // Проверяем, что рост больше 0
                if (!validate()) {
                    System.out.println("Рост должен быть больше 0");
                    continue;
                }
                return this.Height;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод");
            }
        }
    }

    /**
     * Метод для получения значения роста.
     *
     * @return Введенный рост.
     */
    public Long getHeight() { return Height; }

    /**
     * Метод для проверки валидности введенного роста.
     *
     * @return true, если рост валиден (больше 0), иначе false.
     */
    @Override
    public boolean validate() {
        return Height != null && Height > 0;
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректного роста.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в росте";
    }
}
