package Client;

import Pakets.RemovePlayerPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class Client implements Runnable {
    //Client.Client variables
    private String host;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running=false;
    private EventListener listener;

    //Constructor
    public Client(String host, int port){
        this.host=host;
        this.port=port;

    }
    //connect to the server
    public void connect(){
        try{
            socket=new Socket(host,port);
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
            listener=new EventListener();
            new Thread(this).start();
        }catch(ConnectException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //close the connection
    public void close(){
        try{
            running=false;
            //tell the server we are closing
            RemovePlayerPacket packet= new RemovePlayerPacket();
            sendObject(packet);
            in.close();
            out.flush();
            out.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //send data to the server
    public void sendObject(Object o){
        try{
            out.writeObject(o);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try{
            running=true;
            while(running){
                //listen for new data
                Object data=in.readObject();
                listener.received(data);
            }
        }catch (ClassNotFoundException e){
            //If i am unable to get the object
            e.printStackTrace();
        }catch (SocketException e){
            //Problem with connection
            close();
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
