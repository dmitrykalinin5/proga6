package Commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Команда для выполнения скрипта.
 * Этот класс реализует команду, которая читает файл скрипта, выполняет команды в нем,
 * избегая рекурсивных вызовов и предотвращая зацикливание.
 */
public class ExecuteScriptCommand implements Command {
    private final CommandProcessor commandProcessor;

    /**
     * Конструктор для создания команды выполнения скрипта.
     *
     * @param commandProcessor Обработчик команд, который будет использоваться для выполнения команд
     */
    public ExecuteScriptCommand(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    /**
     * Метод для чтения содержимого файла и разделения его на строки.
     *
     * @param fileName Имя файла, который нужно прочитать
     * @return Массив строк, содержащий команды из файла
     */
    private String[] readFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName)).split("\n");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
    }

    /**
     * Выполняет команду скрипта.
     * Эта команда читает файл, добавляет команды из него в очередь на выполнение,
     * предотвращает рекурсивные вызовы скриптов и выполняет все команды по очереди.
     *
     * @param args Аргументы команды, где args[1] - это имя файла с командой
     */
    @Override
    public void execute(String[] args) {
        commandProcessor.setScriptFlag(true);
        Set<String> bannedFiles = new HashSet<>(commandProcessor.getBannedFiles());
        String fileName = args[1];

        // Проверка на рекурсивный вызов
        if (bannedFiles.contains(fileName)) {
            System.out.println("Предотвращено рекурсивное выполнение: " + fileName);
            return;
        }

        // Чтение и обработка файла
        String[] fileInput = readFile(fileName);
        if (fileInput == null) return;

        // Баним файл
        bannedFiles.add(fileName);
        commandProcessor.setBannedFiles(new ArrayList<>(bannedFiles));

        Deque<String> newCommands = new ArrayDeque<>();

        // Заполняем дек командами из файла, избегая рекурсивных вызовов
        for (String line : fileInput) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length > 1 && "execute_script".equals(parts[0])) {
                String nextFile = parts[1];
                if (!bannedFiles.contains(nextFile)) {
                    newCommands.addLast(line);
                } else {
                    System.out.println("Игнорируем рекурсивный вызов: " + nextFile);
                }
            } else {
                newCommands.addLast(line);
            }
        }

        // Мерджим стек с командами
        Deque<String> mergeStack = new ArrayDeque<>(newCommands);
        mergeStack.addAll(commandProcessor.getCommandStack());
        commandProcessor.setCommandStack(mergeStack);

        // Выполнение команд из стека
        commandProcessor.CommandPut();
        try {
            while (!commandProcessor.getCommandStack().isEmpty()) {
                commandProcessor.executeScript();
                try {
                    Thread.sleep(1000); // Задержка между командами
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка, элементы кончились. Проверьте правильность введенных вами команд, скорее всего проблема в них.");
        }
        commandProcessor.setScriptFlag(false);
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая выполняет скрипт
     */
    @Override
    public String description() {
        return "Executes script";
    }
}
