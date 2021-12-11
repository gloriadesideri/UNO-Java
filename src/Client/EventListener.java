package Client;

import Pakets.AddPlayerPacket;
import Pakets.RemovePlayerPacket;

public class EventListener {
    public void received(Object o) {
        if (o instanceof AddPlayerPacket){
            AddPlayerPacket p = (AddPlayerPacket) o;
            PlayerHandler.players.put(p.id, new Player(p.id, p.name));
            System.out.println("Added player: " + p.name);
        }else if (o instanceof RemovePlayerPacket){
           RemovePlayerPacket p = (RemovePlayerPacket) o;
           PlayerHandler.players.remove(p.id);
           System.out.println("Removed player: " + p.id);
        }
    }
}
