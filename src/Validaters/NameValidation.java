package Validaters;

import Commands.CommandProcessor;
import Console.Client;
import Tools.Validation;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class NameValidation implements Validation {
    private String name;
    private String message;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public NameValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    public String validation(String userInput) {
        while (true) {
            try {
                this.name = userInput.trim();
                // Проверяем, что имя не пустое
                if (!validate()) {
                    assert out != null;
                    out.println("Неверный формат ввода. \u00A0");
                    assert in != null;
                    userInput = in.readLine();
                    continue;
                }
                return this.name;
            } catch (Exception e) {
                assert out != null;
                out.println("Некорректный ввод: " + e.getMessage());
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
