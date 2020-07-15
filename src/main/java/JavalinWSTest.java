import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import data.*;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;

public class JavalinWSTest {

    private static Map<String, Collab> collabs = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).ws("/docs/:doc-id", ws -> {
            ws.onConnect(ctx -> {
                ctx.headerMap().forEach((K, V) -> System.out.printf("%s -> %s\n", K, V));

                System.out.printf("In Ws.onConnect()" + ctx.session.toString() + "\n");
                if(getCollab(ctx) == null) {
                    createCollab(ctx);
                }
                getCollab(ctx).clients.add(ctx);
                ctx.send(getCollab(ctx).doc);

            });
            ws.onMessage(ctx -> {
                System.out.printf(ctx.toString() + "\n");
                getCollab(ctx).doc = ctx.message();
                getCollab(ctx).clients.stream().filter(c -> c.session.isOpen()).forEach(s -> {
                    s.send(getCollab(ctx).doc);
                });

            });

            ws.onClose(ctx -> {
                System.out.printf(ctx.toString() + "\n");
                getCollab(ctx).clients.remove(ctx);
            });
        }).start(9090);
    }

    private static Collab getCollab(WsContext ctx) {
        return collabs.get(ctx.pathParam("doc-id"));
    }

    public static void createCollab(WsContext ctx) {
        collabs.put(ctx.pathParam("doc-id"), new Collab());
    }
}






