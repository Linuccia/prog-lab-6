package Commands;

import ProgramManager.CollectionManager;

public class Clear extends AbsCommand {
    public Clear(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод для очистки коллекции
     *
     * @return
     */
    @Override
    public String execute() {
        manager.getCollection().clear();
        return "Коллекция очищена";
    }
}
