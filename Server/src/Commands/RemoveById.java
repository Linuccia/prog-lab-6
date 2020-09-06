package Commands;

import ProgramManager.CollectionManager;

public class RemoveById extends AbsCommand {
    public RemoveById(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод удаляет из коллекции элемент по заданному id
     *
     * @param args
     * @return
     */
    @Override
    public String execute(Integer args) {
        if (!(manager.getCollection().size() == 0)) {
                if (manager.getCollection().removeIf(collection -> collection.getId() == args)) {
                    return "Элемент удален из коллекции";
                } else return "Элемента с таким id не существует";
        } else {
                return "Коллекция пуста";
        }
    }
}
