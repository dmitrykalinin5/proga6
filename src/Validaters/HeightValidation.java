package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HeightValidation implements Validation {
    private Long height;
    private CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    public HeightValidation(CommandProcessor commandProcessor, String userInput, BufferedReader in, PrintWriter out) {
        this.commandProcessor = commandProcessor;
        validation(userInput);
        this.in = in;
        this.out = out;
    }

    /**
     * Метод для валидации введенного роста.
     *
     * @param userInput строка, введенная пользователем
     * @return Корректное значение роста, если оно валидно.
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
                this.height = Long.parseLong(input);
                // Проверяем, что рост больше 0
                if (!validate()) {
                    assert out != null;
                    out.println("Рост должен быть больше 0 \u00A0");
                    assert in != null;
                    userInput = in.readLine();
                    continue;
                }
                return this.height;
            } catch (NumberFormatException e) {
                assert out != null;
                out.println("Некорректный ввод");
                return null; // чтобы не зациклиться при ошибке в режиме обычного ввода
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод для получения значения роста.
     *
     * @return Введенный рост.
     */
    public Long getHeight() {
        return height;
    }

    /**
     * Метод для проверки валидности введенного роста.
     *
     * @return true, если рост валиден (больше 0), иначе false.
     */
    @Override
    public boolean validate() {
        return height != null && height > 0;
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
