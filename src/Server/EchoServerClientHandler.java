package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EchoServerClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private EventListener listener;

    public EchoServerClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            listener = new EventListener();
            System.out.println("New Connection");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Client Handler Started");
        try {
            while (socket.isConnected()){
                try{
                    Object obj = in.readObject();
                    System.out.println("Received: "+obj.toString());
                    listener.received(obj, this);
                }
                catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            in.close();
            out.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendObject(Object obj){
        try {
            out.writeObject(obj);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
