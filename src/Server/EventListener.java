package Server;

import Pakets.AddPlayerPacket;
import Pakets.RemovePlayerPacket;

public class EventListener {
    public void received(Object p , EchoServerClientHandler c) {
        if(p instanceof AddPlayerPacket){
            AddPlayerPacket packet = (AddPlayerPacket) p;
            System.out.println("Received AddPlayerPacket: "+ p.toString());
            c.sendObject(packet);
            System.out.println("Sent AddPlayerPacket");
        }

    }
}
