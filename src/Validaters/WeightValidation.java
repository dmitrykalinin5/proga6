package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;
import Console.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Класс для валидации веса.
 * Проверяет, что введенный вес является положительным числом.
 */
public class WeightValidation implements Validation {
    private int weight;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public WeightValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    /**
     * Метод для валидации веса.
     * Запрашивает у пользователя ввод и проверяет, что вес больше 0.
     *
     * @param userInput строка, введенная пользователем.
     * @return Валидированное значение веса.
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
                this.weight = Integer.parseInt(input);
                if (!validate()) {
                    assert out != null;
                    out.println("Вес должен быть больше 0, попробуйте еще раз: \u00A0");
                    assert in != null;
                    userInput = in.readLine();
                    continue;
                }
                return this.weight;
            } catch (NumberFormatException | NullPointerException e) {
                assert out != null;
                out.println("Некорректный ввод: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод для получения валидированного значения веса.
     *
     * @return Введенный вес.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Метод для валидации веса.
     * Проверяет, что вес больше 0.
     *
     * @return true, если вес больше 0, иначе false.
     */
    @Override
    public boolean validate() {
        return weight > 0;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода веса.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в весе";  // Теперь это сообщение соответствует весу, а не росту
    }
}
