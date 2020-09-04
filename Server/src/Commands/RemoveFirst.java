package Commands;

import ServerManager.CollectionManager;

public class RemoveFirst extends AbsCommand {
    public RemoveFirst(CollectionManager manager) {
        super(manager);
    }


    /**
     * Метод удаляет первый элемент коллекции
     *
     * @return
     */
    @Override
    public String execute() {
        if (!(manager.getCollection().size() == 0)) {
            manager.getCollection().remove();
            return "Первый элемент коллекции удален";
        } else {
            return "Коллекция пуста";
        }
    }
}
