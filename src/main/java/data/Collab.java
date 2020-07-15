package data;

import io.javalin.websocket.WsContext;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;//use in multi runnable

public class Collab {
    public String doc;
    public Set<WsContext> clients;

    public Collab() {
        this.doc = "";
        this.clients = ConcurrentHashMap.newKeySet();
    }
}
