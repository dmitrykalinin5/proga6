package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;
import Console.Client;

import java.util.Scanner;

/**
 * Класс для валидации цены.
 * Проверяет, что цена больше 0.
 */
public class PriceValidation implements Validation {
    private Long price;
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса PriceValidation.
     *
     * @param message Сообщение, которое будет выведено при запросе цены.
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     */
    public PriceValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации цены.
     * Запрашивает у пользователя ввод и проверяет, что цена больше 0.
     *
     * @return Валидированное значение цены.
     */
    public Long validation() {
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
                this.price = Long.parseLong(input);
                if (!validate()) {
                    System.out.println("Цена должна быть больше 0");
                    continue;
                }
                return this.price;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод");
            }
        }
    }

    /**
     * Метод для получения валидированного значения цены.
     *
     * @return Цена, введенная пользователем.
     */
    public Long getPrice() { return price; }

    /**
     * Метод для валидации цены.
     * Проверяет, что цена больше 0.
     *
     * @return true, если цена больше 0, иначе false.
     */
    @Override
    public boolean validate() { return price > 0; }

    /**
     * Метод для получения сообщения об ошибке при неверной цене.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() { return "Ошибка в цене"; }
}
