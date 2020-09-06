package ProgramManager;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;


public class Server {
    public static void main(String[] args) throws IOException {
        CollectionManager manager = new CollectionManager();
        System.out.println("Сервер запускается...");
        Connection connection = new Connection();
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    System.out.println("Отключение сервера...");
                    manager.save();
                }
            });
            manager.load(new File(System.getenv("ProductsFile")));
            connection.connect();
        } catch (NullPointerException e) {
            System.out.println("Имя файла должно быть передано через переменную окружения ProductsFile");
        } catch (NoSuchElementException e) {
            System.out.println("Выход из программы...");
        }
    }

}
