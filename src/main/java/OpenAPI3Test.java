import api.*;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import res.ErrorResponse;
import sun.font.Script;

import static io.javalin.apibuilder.ApiBuilder.*;

public class OpenAPI3Test {

    private static long time = 0;

    public static void main(String[] args) {
        Javalin.create(config -> {
           config.registerPlugin(getConfiguredOpenApiPlugin());
           //config.defaultContentType = "application/json";
        }).routes(() -> {


            System.out.printf("%d\n", time++);
            //localhost:9090/
            get(Home::getIndex);

            //localhost:9090/bad
            path("bad", () -> {

                //localhost:9090/bad/:url
                path(":url", () -> {
                    get(NotGood::getImageURL);
                });
            });

            //localhost:9090/script
            path("script", () -> {

                //localhost:9090/script/:scriptName
               path(":scriptName", () -> {
                   get(ScriptController::getScript);
               });
            });

            //localhost:9090/img
            path("img", () -> {
                get(ImageController::getDefaultImage);

                //localhost:9090/img/TEST
                path("TEST", () -> {
                    get(ImageController::getAllWallpaperTest);
                });

                //localhost:9090/img/get_wallpaper
                path("get_wallpaper", () -> {
                    get(ImageController::getWallpaper);
                });

                //localhost:9090/img/:imgName
                path(":imgName", () -> {
                   get(ImageController::getImageByName);
                });
            });


            path("users", () -> {
                get(UserController::getAll);
                post(UserController::create);
                path(":userId", () -> {
                    get(UserController::getOne);
                    //path("userId" ,() -> patch(UserController::update));
                    patch(UserController::update);
                    delete(UserController::delete);

                });
            });
        }).start(9090);
    }

    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info = new Info().version("1.0").title("User API").description("Demo API with 5 operations");
        OpenApiOptions options = new OpenApiOptions(info).
                activateAnnotationScanningFor("OpenAPI3Test")
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger-ui"))
                .reDoc(new ReDocOptions("/redoc"))
                .defaultDocumentation(doc -> {
                    doc.json("500", ErrorResponse.class);
                    doc.json("505", ErrorResponse.class);
                });
        return new OpenApiPlugin(options);
    }
}
