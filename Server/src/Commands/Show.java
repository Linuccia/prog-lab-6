package Commands;

import DataClasses.Product;
import ProgramManager.CollectionManager;
import com.google.gson.Gson;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Show extends AbsCommand {
    Gson json = new Gson();
    public Show(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод выводит все элементы коллекции
     *
     * @return
     */
    @Override
    public String execute() {
        if (manager.getCollection().size() != 0) {
            Stream<Product> stream = manager.getCollection().stream();
            return stream.map(collection -> json.toJson(collection)).collect(Collectors.joining("\n"));
        } else {
            return "Коллекция пуста";
        }
    }
}
