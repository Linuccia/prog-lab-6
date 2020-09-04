package ServerManager;


import ServerDataClasses.Person;
import ServerDataClasses.Product;

import java.io.Serializable;

/**
 * Класс для отправки команд в виде объекта
 */
public class SerCommand implements Serializable {
    private static final long serialVersionUID = 17L;
    Product product;
    Person owner;
    String command;
    String args;
    Integer arg;

    public SerCommand(String command){
        this.command=command;
    }

    public SerCommand(String command, String args){
        this.command = command;
        this.args = args;
    }

    public SerCommand(String command, Integer arg){
        this.command = command;
        this.arg = arg;
    }

    public SerCommand(String command, Product product){
        this.command = command;
        this.product = product;
    }

    public SerCommand(String command, Person owner){
        this.command = command;
        this.owner = owner;
    }

    public SerCommand(String command, Integer arg, Product product){
        this.command = command;
        this.arg = arg;
        this.product = product;
    }

    public String getCommand(){return command;}
    public String getArgs(){return args;}
    public Product getProduct(){return product;}
    public Person getOwner(){return owner;}
    public Integer getArg(){return arg;}
}