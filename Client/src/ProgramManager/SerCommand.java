package ProgramManager;

import DataClasses.Person;
import DataClasses.Product;

import java.io.Serializable;

/**
 * Класс для отправления команд в виде объекта
 */
public class SerCommand implements Serializable {
    private static final long serialVersionUID = 17L;
    Product product;
    Person person;
    String command;
    Integer arg;

    public SerCommand(String command){
        this.command=command;
    }

    public SerCommand(String command, Product product){
        this.command=command;
        this.product=product;
    }

    public SerCommand(String command, Person person){
        this.command=command;
        this.person=person;
    }

    public SerCommand(String command, Integer arg){
        this.command=command;
        this.arg=arg;
    }

    public SerCommand(String command, Integer arg, Product product){
        this.command=command;
        this.arg=arg;
        this.product=product;
    }

    public String getCommand(){return command;}
    public Product getProduct(){return product;}
    public Person getPerson(){return person;}
    public Integer getArg(){return arg;}
}
