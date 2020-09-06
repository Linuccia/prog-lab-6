package Commands;

import DataClasses.Product;
import ProgramManager.CollectionManager;

import java.util.stream.Stream;

public class AverageOfPrice extends AbsCommand {
    public AverageOfPrice(CollectionManager manager){
        super(manager);
    }

    /**
     * Метод выводит среднее значение price всех элементов
     *
     * @return
     */
    @Override
    public String execute() {
        double averagePrice;
        if (!(manager.getCollection().size() == 0)) {
            Stream<Product> stream = manager.getCollection().stream();
            //for (Product p: manager.getCollection()){ averagePrice = averagePrice + p.getPrice(); }
            //int priceSum = stream.mapToInt(Product::getPrice).sum();
            averagePrice = (stream.mapToInt(Product::getPrice).sum()) / manager.getCollection().size();
            return "Среднее значение цены всех элементов коллекции - " + averagePrice;
        } else {
            return "Коллекция пуста";
        }
    }
}
