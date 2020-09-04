package ServerManager;

import Commands.*;
import ServerDataClasses.Product;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CollectionManager {

    public static CollectionManager manager = new CollectionManager();
    private PriorityQueue<Product> collection= new PriorityQueue<>();
    public Integer id = 0;
    public Date initDate = new Date();
    public Map<String, AbsCommand> commandMap;
    private Gson json = new Gson();

    /**
     * Метод возвращает коллекцию
     *
     * @return
     */
    public PriorityQueue<Product> getCollection(){
        return collection;
    }

    {
        commandMap = new HashMap<>();
        commandMap.put("clear", new Clear(manager));
        commandMap.put("show", new Show(manager));
        commandMap.put("info", new Info(manager));
        commandMap.put("help", new Help(manager));
        commandMap.put("add", new Add(manager));
        commandMap.put("count_by_owner", new CountByOwner(manager));
        commandMap.put("add_if_min", new AddIfMin(manager));
        commandMap.put("average_of_price", new AverageOfPrice(manager));
        commandMap.put("count_less_than_price", new CountLessThanPrice(manager));
        commandMap.put("remove_greater", new RemoveGreater(manager));
        commandMap.put("remove_by_id", new RemoveById(manager));
        commandMap.put("update", new UpdateId(manager));
        commandMap.put("remove_first", new RemoveFirst(manager));
    }

    /**
     * Метод сохраняет коллекцию в файл
     */
    public void save() {
        try {
            File outfile = new File(System.getenv("ProductsFile"));
            BufferedWriter writter = new BufferedWriter(new FileWriter(outfile));
            String outJson = json.toJson(collection);
            writter.write(outJson);
            writter.close();
            System.out.println("Коллекция сохранена");
        } catch (IOException e){
            System.out.println("Ошибка записи в файл");
        }
    }

    /**
     * Метод загружает коллекцию из файла
     *
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException {
        try {
            if (!file.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файла по указанному пути не существует");
        }
        try {
            if (!file.canRead() || !file.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            System.out.println("Файл успешно загружен");
            StringBuilder result = new StringBuilder();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                result.append(nextLine);
            }
            Type collectionQueue = new TypeToken<PriorityQueue<Product>>() {
            }.getType();
            Type collectionList = new TypeToken<List<Product>>() {
            }.getType();
            try{
                List<Product> productList = json.fromJson(String.valueOf(result), collectionList);
                try{
                    for (Product p: productList) {
                        if (p.getId() == null) {
                            System.out.println("Id не может быть null");
                            return;
                        }
                        if (p.getId() <= 0) {
                            System.out.println("Id не может быть меньше или равен 0. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getName() == null) {
                            System.out.println("Имя не может быть null");
                            return;
                        }
                        if (p.getName().equals("")) {
                            System.out.println("Строка имени не может быть пустой. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getCoordinates().getX() > 857) {
                            System.out.println("Координата X не может быть больше 857. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getCoordinates().getY() == null) {
                            System.out.println("Координата Y не может быть null");
                            return;
                        }
                        if (p.getPrice() == null) {
                            System.out.println("Цена не может быть null");
                            return;
                        }
                        if (p.getPrice() <= 0) {
                            System.out.println("Цена не может быть меньше или равна 0. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getPartNumber() == null) {
                            System.out.println("Номер партии не может быть null");
                            return;
                        }
                        if ((p.getPartNumber().length() > 85) || (p.getPartNumber().length() < 15) || (p.getPartNumber().equals(""))) {
                            System.out.println("Строка с номером партии не может быть пустой и должна быть длиной от 15 до 85. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getManufactureCost() == null) {
                            System.out.println("Цена производства не может быть null");
                            return;
                        }
                        if (p.getUnitOfMeasure() == null) {
                            System.out.println("Единица измерения не может быть null");
                            return;
                        }
                        if (p.getOwner().getName() == null) {
                            System.out.println("Имя владельца не может быть null");
                            return;
                        }
                        if (p.getOwner().getName().equals("")) {
                            System.out.println("Строка имени владельца не может быть пустой. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getOwner().getWeight() == null) {
                            System.out.println("Вес владельца не может быть null");
                            return;
                        }
                        if (p.getOwner().getWeight() <= 0) {
                            System.out.println("Вес владельца не может быть меньше или равен 0. Добавлена пустая коллекция");
                            return;
                        }
                        if (p.getOwner().getEyeColor() == null) {
                            System.out.println("Цвет глаз владельца не может быть null");
                            return;
                        }
                        if (p.getOwner().getNationality() == null) {
                            System.out.println("Национальность владельца не может быть null");
                            return;
                        }
                    }
                    PriorityQueue<Product> priorityQueue = json.fromJson(result.toString(), collectionQueue);
                    for (Product p: priorityQueue) {
                        p.setCreationDate();
                        collection.add(p);
                        if (p.getId() > id) {
                            id = p.getId();
                        }
                    }
                    System.out.println("Коллекция успешно добавлена. Коллекция содержит " + collection.size() + " элемент(а/ов)");
                } catch (NullPointerException e) {
                    System.out.println("Добавлена пустая коллекция");
                }
            } catch (JsonSyntaxException e) {
                System.out.println("Ошибка синтаксиса Json. Коллекция не добавлена");
                System.exit(1);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
