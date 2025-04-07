package Commands;

import Collections.CollectionManager;

import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.nio.Buffer;
import java.util.*;

/**
 * Класс для обработки команд, выполнения их и управления историей команд.
 */
public class CommandProcessor {

    private final Map<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private Deque<String> historyDeque;
    private Queue<String> inputQueue = new LinkedList<>();
    private boolean scriptFlag = false;
    private List<String> bannedFiles = new ArrayList<>();
    private Deque<String> commandStack = new ArrayDeque<>();
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String role;

    public CommandProcessor(CollectionManager collectionManager, Deque<String> historyDeque, ObjectOutputStream out, ObjectInputStream in, String role) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.out = out;
        this.in = in;
        this.role = role;
    }

    public CommandProcessor getCommandProcessor() {
        return this;
    }

    public void CommandPut() {
        if (role.equals("client")) {
            // клиентские команды
            commands.put("add", new AddCommand(collectionManager, this));
            commands.put("update", new UpdateIdCommand(collectionManager, historyDeque, this));
            commands.put("exit", new ExitCommand(this));
        } else if (role.equals("server")) {
            // Список команд
            commands.put("help", new HelpCommand());
            commands.put("info", new InfoCommand(collectionManager));
            commands.put("show", new ShowCommand(collectionManager));
            commands.put("clear", new ClearCommand(collectionManager));
            commands.put("remove_first", new RemoveFirstCommand(collectionManager));
            commands.put("remove_head", new RemoveHeadCommand(collectionManager, this));
            commands.put("history", new HistoryCommand(this));
            commands.put("min_by_id", new MinByIdCommand(collectionManager));
            commands.put("group_counting_by_person", new GroupCountingByPersonCommand(collectionManager));
            commands.put("save", new SaveCommand(collectionManager));
            commands.put("exit", new ExitCommand(this));

            // Команды с аргументами
            commands.put("add", new AddCommand(collectionManager, this));
            commands.put("update", new UpdateIdCommand(collectionManager, historyDeque, this));
            commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
            commands.put("execute_script", new ExecuteScriptCommand(this));
            commands.put("remove_all_by_price", new RemoveAllByPriceCommand(collectionManager));
        }
    }

    public void executeScript() {
        String currentCommand = getNextCommand();
        String[] args = currentCommand.split(" ");
        System.out.println("Текущая команда: " + currentCommand);
        if (args[0].equals("execute_script") && bannedFiles.contains(args[1])) {
            System.out.println("Скрипт не может вызывать сам себя");
        } else {
            Command command = commands.get(args[0]);
            command.execute(args);
            saveCommand(args[0]);
        }
    }

    public void executeCommand(String input) {
        String[] parts = input.split(" ");
        String commandName = parts[0];
        Command command = commands.get(commandName);
        try {
            command.execute(parts);
            saveCommand(parts[0]);
        } catch (NullPointerException exception) {
            System.out.println("Некорректный ввод" + exception.getMessage());
        }
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    public void saveCommand(String command) {
        int maxSize = 13;
        if (this.historyDeque.size() >= maxSize) {
            this.historyDeque.removeFirst();
        }
        this.historyDeque.addLast(command);
    }

//    public void SetIOStreams(BufferedReader reader, PrintWriter writer) {
//        this.reader = reader;
//        this.writer = writer;
//    }

    public BufferedInputStream getInputStream() {
        return new BufferedInputStream(in);
    }

    public boolean isClientCommand(String commandName) {
        return commands.containsKey(commandName);
    }

    public void setCommandStack(Deque<String> commandStack) {
        this.commandStack = commandStack;
    }

    public String removeFirstCommandStack() {
        return commandStack.removeFirst();
    }

    public String removeLastCommandStack() {
        return commandStack.removeLast();
    }

    public Deque<String> getCommandStack() {
        return commandStack;
    }

    public void addFirstCommandtoStack(String command) {
        commandStack.addFirst(command);
    }

    public void addLastCommandtoStack(String command) {
        commandStack.addLast(command);
    }

    public String getNextCommand() {
        return this.commandStack.removeFirst().trim();
    }

    public void setScriptFlag(boolean flag) {
        this.scriptFlag = flag;
    }

    public boolean getScriptFlag() {
        return scriptFlag;
    }

    public Deque<String> getDeque() {
        return this.historyDeque;
    }

    public List<String> getBannedFiles() {
        return bannedFiles;
    }

    public void setBannedFiles(List<String> bannedFiles) {
        this.bannedFiles = bannedFiles;
    }
}
