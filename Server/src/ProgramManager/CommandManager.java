package ProgramManager;

import Commands.AbsCommand;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class CommandManager {
    private Map<String, AbsCommand> commandMap;

    public CommandManager(Map<String, AbsCommand> commandMap){
        this.commandMap = commandMap;
    }

    /**
     * Метод обеспечивает получение команд с клиента
     *
     * @param key
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public SerCommand receive(SelectionKey key) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        SerCommand command;
        SocketChannel channel = (SocketChannel) key.channel();
        int available = channel.read(buffer);
        while (available > 0) {
            available = channel.read(buffer);
        }
        byte[] buf = buffer.array();
        ObjectInputStream serialize = new ObjectInputStream(new ByteArrayInputStream(buf));
        command = (SerCommand) serialize.readObject();
        serialize.close();
        buffer.clear();
        System.out.println("На сервер поступила команда " + command.getCommand());
        key.interestOps(SelectionKey.OP_WRITE);
        return command;
    }

    /**
     * Метод обеспечивает выполнение команд и отправку результата на клиент
     *
     * @param key
     * @param command
     * @throws IOException
     */
    public void send(SelectionKey key, SerCommand command) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream toClient = new ObjectOutputStream(out);
        switch (command.getCommand()) {
            case "help":
            case "info":
            case "show":
            case "clear":
            case "remove_first":
            case "average_of_price": {
                toClient.writeObject(commandMap.get(command.getCommand()).execute());
            }
            break;
            case "count_by_owner": {
                toClient.writeObject(commandMap.get(command.getCommand()).execute(command.getPerson()));
            }
            break;
            case "add":
            case "add_if_min": {
                toClient.writeObject(commandMap.get(command.getCommand()).execute(command.getProduct()));
            }
            break;
            case "remove_greater":
            case "remove_by_id":
            case "count_less_than_price": {
                toClient.writeObject(commandMap.get(command.getCommand()).execute(command.getArg()));
            }
            break;
            case "update": {
                toClient.writeObject(commandMap.get(command.getCommand()).execute(command.getArg(), command.getProduct()));
            }
            break;
        }
        ByteBuffer buffer = ByteBuffer.wrap(out.toByteArray());
        int available = channel.write(buffer);
        while (available > 0) {
            available = channel.write(buffer);
        }
        out.close();
        toClient.close();
        key.interestOps(SelectionKey.OP_READ);
    }
}
