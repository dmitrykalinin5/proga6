package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

public class YCoordinateValidation implements Validation {
    private double y;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса YCoordinateValidation.
     *
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     * @param userInput Ввод пользователя, полученный извне.
     */
    public YCoordinateValidation(CommandProcessor commandProcessor, String userInput) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
    }

    /**
     * Метод для валидации координаты Y.
     * Запрашивает у пользователя ввод и проверяет, что это число с плавающей запятой.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение координаты Y.
     */
    public double validation(String userInput) {
        while (true) {
            try {
                String input;
                // Если скрипт выполняется, получаем команду из скрипта, иначе используем переданный userInput
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                } else {
                    input = userInput.trim();
                }
                this.y = Double.parseDouble(input);
                return this.y;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод: " + e.toString());
            }
        }
    }

    /**
     * Метод для получения валидированного значения координаты Y.
     *
     * @return Введенная координата Y.
     */
    public double getY() {
        return y;
    }

    /**
     * Метод для валидации координаты Y.
     * Для координаты Y всегда возвращается true, так как нет дополнительных условий для валидации.
     *
     * @return true, так как нет специфических проверок для координаты Y.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода координаты Y.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в координате Y";
    }
}
