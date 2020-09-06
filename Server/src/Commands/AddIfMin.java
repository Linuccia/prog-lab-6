package Commands;

import DataClasses.Product;
import ProgramManager.CollectionManager;

import java.util.Comparator;
import java.util.stream.Stream;

public class AddIfMin extends AbsCommand {
    public AddIfMin(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод для добавления элемента в коллекцию, если его price меньше минимального
     *
     * @param product
     * @return
     */
    @Override
    public String execute(Product product) {
        if (!(manager.getCollection().size() == 0)) {
            Stream<Product> stream = manager.getCollection().stream();
            Integer minPrice = stream.filter(collection -> collection.getPrice() != null).min(Comparator.comparingInt(p -> p.getPrice())).get().getPrice();
            if (product.getPrice()>=minPrice) {
                return "Цена элемента выше, чем минимальная цена элементов коллекции. Элемент не сохранен";
            } else {
                Integer id = ++manager.id;
                product.setId(id);
                manager.getCollection().add(product);
                return "Элемент сохранен в коллекцию";
            }
        } else {
            return "Коллекция пуста";
        }
    }
}
