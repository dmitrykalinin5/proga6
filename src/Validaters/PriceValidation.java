package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class PriceValidation implements Validation {
    private Long price;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public PriceValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    /**
     * Метод для валидации цены.
     * Проверяет, что цена больше 0.
     *
     * @param userInput строка, введенная пользователем
     * @return Валидированное значение цены.
     */
    public Long validation(String userInput) {
        while (true) {
            try {
                String input;
                // Если скрипт выполняется, получаем команду из скрипта, иначе используем переданный userInput
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                } else {
                    input = userInput.trim();
                }
                this.price = Long.parseLong(input);
                if (!validate()) {
                    out.println("Цена должна быть больше 0");
                    continue;
                }
                return this.price;
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод");
                return null; // чтобы не зациклиться при ошибке в режиме обычного ввода
            }
        }
    }

    /**
     * Метод для получения валидированного значения цены.
     *
     * @return Цена, введенная пользователем.
     */
    public Long getPrice() {
        return price;
    }

    /**
     * Метод для валидации цены.
     * Проверяет, что цена больше 0.
     *
     * @return true, если цена больше 0, иначе false.
     */
    @Override
    public boolean validate() {
        return price > 0;
    }

    /**
     * Метод для получения сообщения об ошибке при неверной цене.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в цене";
    }
}
