package Client;

import Pakets.AddPlayerPacket;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("localhost",8081);
        client.connect();
        System.out.println("Connected");
        AddPlayerPacket packet = new AddPlayerPacket();
        packet.id = 1;
        packet.name = "Test";
        client.sendObject(packet);
        System.out.println("Sent");
    }
}
