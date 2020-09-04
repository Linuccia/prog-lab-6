package Commands;

import ServerDataClasses.Product;
import ServerManager.CollectionManager;

import java.util.stream.Stream;

public class CountLessThanPrice extends AbsCommand {
    public CountLessThanPrice(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод считает элементы коллекции, чей price меньше заданного
     *
     * @param args
     * @return
     */
    @Override
    public String execute(Integer args) {

        if (!(manager.getCollection().size() == 0)) {
            //int count = 0;
            //for (Product p: manager.getCollection()){ if (p.getPrice() < args) { count ++; } }
            Stream<Product> stream = manager.getCollection().stream();
            long count = stream.filter(collection -> collection.getPrice() < args).count();
            return "Найдено " + count + " элемент(а/ов), значение цены котор(ых/ого) меньше " + args;
        } else {
            return "Коллекция пуста";
        }
    }
}
