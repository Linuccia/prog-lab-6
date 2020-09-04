package Commands;

import ServerManager.CollectionManager;


public class Info extends AbsCommand {
    public Info(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод выводит информацию о коллекции
     *
     * @return
     */
    @Override
    public String execute() {
        return "Тип коллекции: PriorityQueue\n" +
        "Размер коллекции: " + manager.getCollection().size() + "\n" +
        "Дата инициализации: " + manager.initDate;
    }
}
