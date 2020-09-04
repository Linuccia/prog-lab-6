package Commands;

import ServerManager.CollectionManager;

public class RemoveGreater extends AbsCommand {
    public RemoveGreater(CollectionManager manager) {
        super(manager);
    }


    /**
     * Метод удаляет элементы коллекции, чей price выше заданного
     *
     * @param args
     * @return
     */
    @Override
    public String execute(Integer args) {
        if (!(manager.getCollection().size() == 0)) {
            int oldSize = manager.getCollection().size();
            if (manager.getCollection().removeIf(collection -> collection.getPrice() > args)) {
                int newSize = oldSize - manager.getCollection().size();
                if (newSize == 1) {
                    return "Был удален один элемент коллекции";
                } else {
                    return "Было удалено " + newSize + " элемент(а/ов) коллекции";
                }
            } else {
                return "Коллекция не изменена, так как price всех элементов меньше введенного";
            }
        } else {
            return "Коллекция пуста";
        }

    }
}
