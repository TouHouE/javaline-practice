import io.javalin.Javalin;

public class JavalinTest {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(9090);
        app.get("/", ctx -> ctx.result("Test"));
    }
}
