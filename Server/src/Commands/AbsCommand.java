package Commands;

import ServerDataClasses.Person;
import ServerDataClasses.Product;
import ServerManager.CollectionManager;

/**
 * Абстрактный класс-родитель для команд
 */
public abstract class AbsCommand {

    CollectionManager manager;

    public AbsCommand(CollectionManager manager) {
        this.manager = manager;
    }

    public String execute() {
        return null;
    }

    public String execute(Integer args) {
        return null;
    }

    public String execute(Product product) {
        return null;
    }

    public String execute(Integer args, Product product) {
        return null;
    }

    public String execute(String args) {
        return null;
    }

    public String execute(Person person){
        return null;
    }

}
