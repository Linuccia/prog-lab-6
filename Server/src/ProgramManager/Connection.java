package ProgramManager;

import java.io.IOException;
import java.net.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class Connection {
    private CollectionManager manager = new CollectionManager();
    private CommandManager comManager = new CommandManager(manager.commandMap);
    private SerCommand command;
    private Scanner scan = new Scanner(System.in);

    /**
     * Метод обеспечивает соединение с клиентом
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        while (true) {
            try {
                System.out.println("Введите порт");
                int port = Integer.parseInt(scan.nextLine());
                Selector selector = Selector.open();
                try (ServerSocketChannel socketChannel = ServerSocketChannel.open()) {
                    socketChannel.bind(new InetSocketAddress(port));
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    System.out.println("Сервер запущен и ожидает подключения");
                    while (selector.isOpen()) {
                        int count = selector.select();
                        if (count == 0) {
                            continue;
                        }
                        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                        while (iter.hasNext()) {
                            SelectionKey key = iter.next();
                            try {
                                if (key.isAcceptable()) {
                                    SocketChannel channel = socketChannel.accept();
                                    System.out.println("Клиент успешно подключился к серверу");
                                    channel.configureBlocking(false);
                                    channel.register(selector, SelectionKey.OP_READ);
                                }
                                if (key.isReadable()) {
                                    command = comManager.receive(key);
                                }
                                if (key.isWritable()) {
                                    comManager.send(key, command);
                                }
                                iter.remove();
                            } catch (Exception e) {
                                //System.out.println("Сервер отключен");
                                e.printStackTrace();
                                key.cancel();
                            }
                        }
                    }
                }
            } catch (BindException e) {
                System.out.println("Данный порт уже используется");
            } catch (NumberFormatException e) {
                System.out.println("Введенный порт не является числом");
            } catch (IllegalArgumentException e) {
                System.out.println("Порт должен принимать значения от 1 до 65535");
            } catch (SocketException e) {
                System.out.println("Данный порт недопустим к использованию");
            }
        }
    }
}
