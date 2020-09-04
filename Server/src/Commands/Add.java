package Commands;

import ServerDataClasses.Product;
import ServerManager.CollectionManager;

public class Add extends AbsCommand {
    public Add(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод для генерации id и добавления элемента в коллекцию
     *
     * @param product
     * @return
     */
    @Override
    public String execute(Product product) {
        Integer id = ++manager.id;
        product.setId(id);
        manager.getCollection().add(product);
        return "Элемент коллекции успешно добавлен";
    }
}
