package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private int port;

    private ServerSocket serverSocket;
    private boolean running=false;
    private InetAddress address;

    public Server(int port ) {
        this.port=port;

    }
    public void start(){
        System.out.println("Server started");
        new Thread(this).start();

    }
    @Override
    public void run() {
        running=true;
        ExecutorService executor= Executors.newCachedThreadPool();
        try{
            serverSocket=new ServerSocket(port);
        }catch (IOException e){
            e.printStackTrace();
        }
        while (running){
            try{
                Socket socket=serverSocket.accept();
                executor.submit(new EchoServerClientHandler(socket));
                System.out.println("Client connected");

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        shutdown(executor);
    }

    public void shutdown(ExecutorService executor){
        running=false;
        try{
            executor.shutdown();
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
