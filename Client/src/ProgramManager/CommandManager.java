package ProgramManager;

import DataClasses.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CommandManager {
    public boolean script;
    private ArrayList<File> scriptCycle = new ArrayList<>();
    private BufferedReader commandReader;

    /**
     * Метод обеспечивает отправление команд на сервер
     *
     * @param socket
     * @param command
     * @throws IOException
     */
    public void sendCom(Socket socket, SerCommand command) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(output);
        toServer.writeObject(command);
        byte[] out = output.toByteArray();
        socket.getOutputStream().write(out);
    }

    /**
     * Метод обеспечивает получение ответа от сервера
     *
     * @param socket
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void getAns(Socket socket) throws IOException, ClassNotFoundException {
        String answer;
        ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
        answer = (String) fromServer.readObject();
        if (answer.equals("exit")) {
            System.out.println("Завершение работы программы...");
            System.exit(0);
        } else {
            System.out.println(answer);
        }
    }

    /**
     * Метод обеспечивает обработку команд для отправления на сервер и получения ответа
     *
     * @param socket
     */
    public void exchange(Socket socket, String command){
        String[] ComAndArgs = command.trim().split(" ");
        try{
            if (ComAndArgs.length == 1){
                switch (ComAndArgs[0]){
                    case " ":
                        break;
                    case "help":
                    case "info":
                    case "show":
                    case "clear":
                    case "remove_first":
                    case "average_of_price":{
                        SerCommand send = new SerCommand(ComAndArgs[0]);
                        sendCom(socket, send);
                        getAns(socket);
                    }
                    break;
                    case "add":
                    case "add_if_min":{
                        SerCommand send = new SerCommand(ComAndArgs[0], addProduct());
                        sendCom(socket, send);
                        getAns(socket);
                    }
                    break;
                    case "exit":
                        System.out.println("Завершение работы программы...");
                        System.exit(0);
                    default:
                        System.out.println("Введена неизвестная команда. Повторите ввод");
                }
            }
            else if (ComAndArgs.length == 2){
                switch (ComAndArgs[0]){
                    case "remove_by_id":
                    case "remove_greater":
                    case "count_less_than_price":
                        try{
                            SerCommand send = new SerCommand(ComAndArgs[0], Integer.parseInt(ComAndArgs[1]));
                            sendCom(socket, send);
                            getAns(socket);
                        } catch (NumberFormatException e){
                            System.out.println("Введенный аргумент не является или выходит за пределы int. Повторите ввод");
                        }
                        break;
                    case "update":
                        try{
                            SerCommand send = new SerCommand(ComAndArgs[0], Integer.parseInt(ComAndArgs[1]), addProduct());
                            sendCom(socket, send);
                            getAns(socket);
                        } catch (NumberFormatException e){
                            System.out.println("Введенный аргумент не является или выходит за пределы int. Повторите ввод");
                        }
                        break;
                    case "execute_script": {
                        script = true;
                        File file = new File(ComAndArgs[1]);
                        if (!file.exists())
                            System.out.println("Файла с данным именем не существует");
                        else if (!file.canRead())
                            System.out.println("Отсутствуют права на чтение данного файла");
                        else if (scriptCycle.contains(file)) {
                            System.out.println("Выполение данного скрипта зациклится:"
                                    + ComAndArgs[1] + "\nКоманда не выполнена");
                        } else {
                            scriptCycle.add(file);
                            try {
                                commandReader = new BufferedReader(new FileReader(file));
                                String line = commandReader.readLine();
                                while (line != null) {
                                    exchange(socket, line);
                                    System.out.println();
                                    line = commandReader.readLine();
                                }
                                System.out.println("Скрипт успешно выполнен");
                            } catch (IOException ex) {
                                System.out.println("Ошибка чтения скрипта");
                            }
                            scriptCycle.remove(scriptCycle.size() - 1);
                        }
                        script = false;
                        break;
                    }
                    default:
                        System.out.println("Введена неизвестная команда или не введен аргумент. Повторите ввод");
                }
            }
            else if (ComAndArgs.length == 5){
                if (ComAndArgs[0].equals("count_by_owner")){
                    String personName;
                    if (ComAndArgs[1].equals("")) {
                        System.out.println("Имя владельца не может быть null");
                        return;
                    } else {
                        personName = ComAndArgs[0];
                    }

                    double weight;
                    try {
                        if ((ComAndArgs[2].equals("")) || (Double.parseDouble(ComAndArgs[2]) <= 0)) {
                            System.out.println("Вес не может быть null или меньше/равен 0");
                            return;
                        } else {
                            weight = Double.parseDouble(ComAndArgs[2]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введенное значение не является или выходит за пределы double");
                        return;
                    }

                    Color eyeColor;
                    try {
                        eyeColor = Color.valueOf(ComAndArgs[3].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введенного цвета не существует");
                        return;
                    } catch (NullPointerException e) {
                        System.out.println("Цвет не может быть null");
                        return;
                    }

                    Country nationality;
                    try {
                        nationality = Country.valueOf(ComAndArgs[4].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введенной страны не существует");
                        return;
                    } catch (NullPointerException e) {
                        System.out.println("Страна не может быть null");
                        return;
                    }

                    Person owner = new Person(personName, weight, eyeColor, nationality);

                    SerCommand send = new SerCommand(ComAndArgs[0], owner);
                    sendCom(socket, send);
                    getAns(socket);

                } else {
                    System.out.println("Введена неизвестная команда или не введены аргументы. Повторите ввод");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Метод создает элемент для коллекции
     *
     * @return
     */
    public Product addProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product;
        UnitOfMeasure unitOfMeasure = null;
        Color eyeColor = null;
        Country nationality = null;
        StringParser pars = new StringParser();

        if (script) {
            try {
                String[] addParam = new String[11];
                for (int i = 0; i < addParam.length; i++) {
                    addParam[i] = commandReader.readLine();
                }
                String name = pars.strParse(addParam[0]);
                int x = pars.intParse(addParam[1]);
                Double y = pars.dblParse(addParam[2]);
                Integer price = pars.intParse(addParam[3]);
                String partNumber = pars.strParse(addParam[4]);
                Long manufactureCost = Long.parseLong(addParam[5]);
                unitOfMeasure = UnitOfMeasure.valueOf(addParam[6]);
                String perName = pars.strParse(addParam[7]);
                Double weight = pars.dblParse(addParam[8]);
                eyeColor = Color.valueOf(addParam[9]);
                nationality = Country.valueOf(addParam[10]);
                Integer id = 0;
                product = new Product(id, name, new Coordinates(x, y), price, partNumber, manufactureCost, unitOfMeasure,
                        new Person(perName, weight, eyeColor, nationality));
            } catch (Exception e) {
                return null;
            }
        } else {

            String name = pars.strParse("название продукта");

            String strX;
            int x = 858;
            do {
                try {
                    System.out.println("Введите значение поля координата x (значение должно быть меньше или равно 857)");
                    strX = scanner.nextLine().trim();
                    x = Integer.parseInt(strX);
                    if (x >= 858) {
                        System.out.println("Значение не может быть больше 857. Повторите ввод");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Введенное значение не является целым числом или выходит за пределы int. Повторите ввод");
                }
            } while (x >= 858);

            Double y = pars.dblParse("координата y");

            Integer price;
            do {
                price = pars.intParse("цена");
                if (price <= 0) {
                    System.out.println("Цена не может быть меньше или равна 0");
                }
            } while (price <= 0);

            int length;
            String partNumber;
            do {
                partNumber = pars.strParse("номер партии");
                length = partNumber.length();
                if ((length > 85) || (length < 15)) {
                    System.out.println("Номер партии не может быть длиннее 85 или короче 15. Повторите ввод");
                }
            } while ((length > 85) || (length < 15));

            String strManufactureCost;
            Long manufactureCost = null;
            do {
                try {
                    System.out.println("Введите значение поля цена изготовления");
                    strManufactureCost = scanner.nextLine().trim();
                    if (strManufactureCost.equals("")) {
                        System.out.println("Цена изготовления не может быть null. Повторите ввод");
                    } else {
                        manufactureCost = Long.parseLong(strManufactureCost);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Введенное значение не является или выходит за пределы long. Повторите ввод");
                }
            } while (manufactureCost == null);

            String strUnitOfMeasure;
            do {
                System.out.println("Введите одну из единиц измерения: " + Arrays.toString(UnitOfMeasure.values()));
                strUnitOfMeasure = scanner.nextLine().trim().toUpperCase();
                try {
                    unitOfMeasure = UnitOfMeasure.valueOf(strUnitOfMeasure);
                } catch (IllegalArgumentException e) {
                    System.out.println("Данной единицы измерения не существует. Повторите ввод");
                } catch (NullPointerException e) {
                    System.out.println("Единица измерения не может быть null. Повторите ввод");
                }
            } while (unitOfMeasure == null);

            String personName = pars.strParse("имя владельца");

            Double weight;

            do {
                weight = pars.dblParse("вес");
            } while (weight <= 0);

            String strEyeColor;
            do {
                System.out.println("Введите один из цветов глаз: " + Arrays.toString(Color.values()));
                strEyeColor = scanner.nextLine().trim().toUpperCase();
                try {
                    eyeColor = Color.valueOf(strEyeColor);
                } catch (IllegalArgumentException e) {
                    System.out.println("Данного цвета не существует. Повторите ввод");
                } catch (NullPointerException e) {
                    System.out.println("Цвет не может быть null. Повторите ввод");
                }
            } while (eyeColor == null);

            String strNationality;
            do {
                System.out.println("Введите одну из стран: " + Arrays.toString(Country.values()));
                strNationality = scanner.nextLine().trim().toUpperCase();
                try {
                    nationality = Country.valueOf(strNationality);
                } catch (IllegalArgumentException e) {
                    System.out.println("Данной страны не существует. Повторите ввод");
                } catch (NullPointerException e) {
                    System.out.println("Страна не может быть null. Повторите ввод");
                }
            } while (nationality == null);

            Integer id = 1;

            product = new Product(id, name, new Coordinates(x, y), price, partNumber, manufactureCost, unitOfMeasure, new Person(personName, weight, eyeColor, nationality));
        }
            return product;

    }

}
