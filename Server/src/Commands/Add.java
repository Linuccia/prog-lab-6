package Commands;

import DataClasses.Product;
import ProgramManager.CollectionManager;

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
        try{
            Integer id = ++manager.id;
            product.setId(id);
            manager.getCollection().add(product);
            return "Элемент коллекции успешно добавлен";
        } catch (NullPointerException e){
            return "Ошибка в агрументах для команды add";
        }

    }
}
