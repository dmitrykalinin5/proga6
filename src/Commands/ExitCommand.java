package Commands;

import Commands.CommandProcessor;

/**
 * Команда для завершения работы программы.
 * Этот класс реализует команду, которая завершает выполнение программы.
 */
public class ExitCommand implements Command {
    private final CommandProcessor commandProcessor;

    public ExitCommand(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    /**
     * Выполняет команду завершения программы.
     * Этот метод вызывает системное завершение программы с кодом 0.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        this.commandProcessor.ServerCommandPut();
        this.commandProcessor.executeCommand("save");
        System.exit(0);
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая завершает выполнение программы
     */
    @Override
    public String description() {
        return "Exits the program";
    }
}
