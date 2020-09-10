package ProgramManager;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Connection {
    private CommandManager manager = new CommandManager();

    /**
     * Метод обеспечивает соединение между клиентом и сервером
     */
    public void Connect() {
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Введите хост");
                String host = scan.nextLine();
                System.out.println("Введите порт");
                int port = scan.nextInt();
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(host, port), 2000);
                    System.out.println("Соединение с сервером установлено!");
                    while (true){
                        manager.exchange(socket);
                    }
                } catch (IllegalArgumentException e){
                    System.out.println("Порт должен принимать значения от 1 до 65535");
                } catch (UnknownHostException e){
                    System.out.println("Введен неверный хост");
                } catch (ConnectException e){
                    System.out.println("Введенный хост недоступен");
                } catch (SocketTimeoutException e) {
                    System.out.println("Время подключения к серверу вышло");
                } catch (IOException e){
                    System.out.println("Не удалось подключиться к серверу. Повторить попытку подключения?(Введите да или нет)");
                    String answer;
                    while (!(answer = scan.nextLine()).equals("да")){
                        switch(answer){
                            case "":
                                break;
                            case "нет":
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Введено некорректное значение. Повторите ввод");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Введенный порт не является числом или выходит за пределы int");
            }
        }
    }
}
