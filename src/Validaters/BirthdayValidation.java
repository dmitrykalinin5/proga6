package Validaters;

import Commands.CommandProcessor;
import Tools.Validation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс для валидации даты рождения пользователя.
 * Проверяет, соответствует ли введенная дата корректному формату и является ли она реальной (например, проверка
 * на количество дней в месяце, високосные года и т.д.).
 */
public class BirthdayValidation implements Validation {
    private String birthday;
    private final String message;
    private CommandProcessor commandProcessor;

    /**
     * Конструктор класса.
     *
     * @param message Сообщение, которое будет выведено пользователю для ввода даты рождения.
     * @param commandProcessor Компонент для обработки команд (например, для выполнения скриптов).
     */
    public BirthdayValidation(String message, CommandProcessor commandProcessor) {
        this.message = message;
        this.commandProcessor = commandProcessor;
        validation();
    }

    /**
     * Метод для валидации введенной даты рождения.
     * Запрашивает ввод даты у пользователя, проверяет ее корректность и возвращает правильную дату.
     *
     * @return Корректно введенная дата рождения в формате "dd.mm.yyyy".
     */
    public String validation() {
        int[] months31 = {1, 3, 5, 7, 8, 10, 12};
        int[] months30 = {4, 6, 9, 11};
        while (true) {
            try {
                System.out.print(message);
                String input;
                if (commandProcessor.getScriptFlag()) {
                    input = commandProcessor.getNextCommand().trim();
                } else {
                    Scanner scanner = new Scanner(System.in);
                    input = scanner.nextLine().trim();
                }
                this.birthday = input;
                String[] inputSplit = this.birthday.split("\\.");
                if (inputSplit[0].length() != 2 || inputSplit[1].length() != 2 || inputSplit[2].length() != 4) {
                    System.out.println("Некорректный ввод");
                    continue;
                }
                int day = Integer.parseInt(inputSplit[0]);
                int month = Integer.parseInt(inputSplit[1]);
                int year = Integer.parseInt(inputSplit[2]);
                // Проверка корректности введенной даты (учет количества дней в месяце и високосных годов)
                if (((day >= 1 && day <= 31 && Arrays.stream(months31).anyMatch(n -> n == month))
                        || (day >= 1 && day <= 30 && Arrays.stream(months30).anyMatch(n -> n == month))
                        || (day >= 1 && day <= 29 && month == 2 && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)))
                        || (day >= 1 && day <= 28 && month == 2 && !(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)))) && year > 0) {
                    return this.birthday;
                } else {
                    System.out.println("Некорректный ввод");
                }
            } catch (NullPointerException e) {
                System.out.println("Дата не может быть null");
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Некорректный ввод");
            }
        }
    }

    /**
     * Метод для получения даты рождения.
     *
     * @return Дата рождения в формате "dd.mm.yyyy".
     */
    public String getBirthday() {
        return this.birthday;
    }

    /**
     * Метод для проверки валидности введенной даты рождения.
     *
     * @return true, если дата валидна, иначе false.
     */
    @Override
    public boolean validate() {
        return birthday != null;
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректной даты рождения.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в дне рождения";
    }
}
