package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

public class PriceValidation implements Validation {
    private Long price;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса PriceValidation.
     *
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     * @param userInput Ввод пользователя, полученный извне.
     */
    public PriceValidation(CommandProcessor commandProcessor, String userInput) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
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
                    System.out.println("Цена должна быть больше 0");
                    continue;
                }
                return this.price;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод");
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
