package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

public class XCoordinateValidation implements Validation {
    private int x;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса XCoordinateValidation.
     *
     * @param commandProcessor Объект для обработки команд, включая работу с флагом скрипта.
     * @param userInput Ввод пользователя, полученный извне.
     */
    public XCoordinateValidation(CommandProcessor commandProcessor, String userInput) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
    }

    /**
     * Метод для валидации координаты X.
     * Запрашивает у пользователя ввод и проверяет, что это целое число.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение координаты X.
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
                this.x = Integer.parseInt(input);
                return this.x;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод: " + e.toString());
            }
        }
    }

    /**
     * Метод для получения валидированного значения координаты X.
     *
     * @return Введенная координата X.
     */
    public int getX() {
        return x;
    }

    /**
     * Метод для валидации координаты X.
     * Для координаты X всегда возвращается true, так как нет дополнительных условий для валидации.
     *
     * @return true, так как нет специфических проверок для координаты X.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода координаты X.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в координате X";
    }
}
