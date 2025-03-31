package Commands;

import Collections.CollectionManager;
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

    /**
     * Конструктор класса CommandProcessor, инициализирует необходимые компоненты.
     *
     * @param collectionManager Объект для работы с коллекцией
     * @param historyDeque История команд
     */
    public CommandProcessor(CollectionManager collectionManager, Deque<String> historyDeque) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
    }

    public CommandProcessor getCommandProcessor() {
        return this;
    }

    /**
     * Метод, который добавляет команды в список доступных команд.
     */
    public void CommandPut() {
        // Список команд
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("remove_first", new RemoveFirstCommand(collectionManager));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("history", new HistoryCommand(this));
        commands.put("min_by_id", new MinByIdCommand(collectionManager));
        commands.put("group_counting_by_person", new GroupCountingByPersonCommand(collectionManager));
        commands.put("exit", new ExitCommand(this));

        // Команды с аргументами
        commands.put("add", new AddCommand(collectionManager, this));
        commands.put("update", new UpdateIdCommand(collectionManager, historyDeque, this));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("remove_all_by_price", new RemoveAllByPriceCommand(collectionManager));
    }

    public void ServerCommandPut() {
        commands.put("save", new SaveCommand(collectionManager));
    }

    /**
     * Метод для выполнения скрипта команд.
     * Проверяет, не вызывает ли скрипт сам себя.
     */
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

    /**
     * Устанавливает стек команд.
     *
     * @param commandStack Стек команд
     */
    public void setCommandStack(Deque<String> commandStack) {
        this.commandStack = commandStack;
    }

    /**
     * Удаляет и возвращает первую команду из стека.
     *
     * @return Первая команда в стеке
     */
    public String removeFirstCommandStack() {
        return commandStack.removeFirst();
    }

    /**
     * Удаляет и возвращает последнюю команду из стека.
     *
     * @return Последняя команда в стеке
     */
    public String removeLastCommandStack() {
        return commandStack.removeLast();
    }

    /**
     * Получает текущий стек команд.
     *
     * @return Стек команд
     */
    public Deque<String> getCommandStack() {
        return commandStack;
    }

    /**
     * Добавляет команду в начало стека команд.
     *
     * @param command Команда для добавления
     */
    public void addFirstCommandtoStack(String command) {
        commandStack.addFirst(command);
    }

    /**
     * Добавляет команду в конец стека команд.
     *
     * @param command Команда для добавления
     */
    public void addLastCommandtoStack(String command) {
        commandStack.addLast(command);
    }

    /**
     * Получает следующую команду из стека и удаляет её.
     *
     * @return Следующая команда из стека
     */
    public String getNextCommand() {
        return this.commandStack.removeFirst().trim();
    }

    /**
     * Устанавливает флаг скрипта, который указывает на выполнение скрипта.
     *
     * @param flag Значение флага
     */
    public void setScriptFlag(boolean flag) {
        this.scriptFlag = flag;
    }

    /**
     * Получает флаг скрипта.
     *
     * @return Флаг скрипта
     */
    public boolean getScriptFlag() {
        return scriptFlag;
    }

    /**
     * Выполняет команду, переданную через входные данные.
     *
     * @param input Входные данные с командой
     */
    public void executeCommand(String input) {
        String[] parts = input.split(" ");
        String commandName = parts[0];
        Command command = commands.get(commandName);
        try {
            command.execute(parts);
            saveCommand(parts[0]);
        } catch (NullPointerException exception) {
            System.out.println("Такой команды не существует");
        }
    }

    /**
     * Сохраняет команду в историю, если её размер не превышает максимума.
     *
     * @param command Команда для сохранения
     */
    public void saveCommand(String command) {
        int maxSize = 13;
        if (this.historyDeque.size() >= maxSize) {
            this.historyDeque.removeFirst();
        }
        this.historyDeque.addLast(command);
    }

    /**
     * Получает очередь команд из истории.
     *
     * @return История команд
     */
    public Deque<String> getDeque() {
        return this.historyDeque;
    }

    /**
     * Получает список запрещённых файлов.
     *
     * @return Список запрещённых файлов
     */
    public List<String> getBannedFiles() {
        return bannedFiles;
    }

    /**
     * Устанавливает список запрещённых файлов.
     *
     * @param bannedFiles Новый список запрещённых файлов
     */
    public void setBannedFiles(List<String> bannedFiles) {
        this.bannedFiles = bannedFiles;
    }
}
