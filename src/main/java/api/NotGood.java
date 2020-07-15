package api;

import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

public class NotGood {

    @OpenApi(
            description = "Use PathParam Get Gallery",
            operationId = "getGalleryByPathParam",
            method = HttpMethod.GET,
            path = "/bad/:url",
            pathParams = @OpenApiParam(name = "url", description = "gallery url"),
            responses = @OpenApiResponse(status = "200")
    )
    public static void getImageURL(Context ctx) {
        String urlStr = ctx.pathParam("url", String.class).get();

    }


}
