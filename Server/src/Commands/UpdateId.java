package Commands;

import DataClasses.Product;
import ProgramManager.CollectionManager;

public class UpdateId extends AbsCommand {
    public UpdateId(CollectionManager manager) {
        super(manager);
    }


    /**
     * Метод выполняет скрипт из execute скрипта
     *
     * @param args
     * @param product
     * @return
     */
    @Override
    public String execute(Integer args, Product product) {
        if (!(manager.getCollection().size() == 0)) {
            if (manager.getCollection().removeIf(collection -> collection.getId().equals(args))) {
                product.setId(args);
                manager.getCollection().add(product);
                return "Элемент с данным id обновлен";
            } else {
                return "Элемента с данным id не существует";
            }
        } else {
            return "Коллекция пуста";
        }
    }
}
