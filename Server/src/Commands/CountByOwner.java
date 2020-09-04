package Commands;

import ServerDataClasses.Person;
import ServerDataClasses.Product;
import ServerManager.CollectionManager;

import java.util.stream.Stream;

public class CountByOwner extends AbsCommand {
    public CountByOwner(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод считает элементы коллекции, чей owner равен заданному
     *
     * @param person
     * @return
     */
    @Override
    public String execute(Person person) {
        if (!(manager.getCollection().size() == 0)) {
            //int count = 0;
            //for (Product p: manager.getCollection()){ if (p.getOwner().equals(person)){ count++; } }
            Stream<Product> stream = manager.getCollection().stream();
            long count = stream.filter(collection -> collection.getOwner().equals(person)).count();
            return "Найдено " + count + " элемент(а/ов), значение поля owner которых совпадает с введенным";
        } else {
            return "Коллекция пуста";
        }
    }
}
