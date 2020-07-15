package api;

import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScriptController {

    @OpenApi(
        description = "Get Script",
        operationId = "getScript",
        path = "script/:scriptName",
        method = HttpMethod.GET,
        pathParams = {@OpenApiParam(name = "scriptName", description = "Script Name")},
        tags = {"Scripts"},
        responses = {@OpenApiResponse(status = "200")}
    )
    public static void getScript(Context ctx) {
        File testButtonJS = new File("public/script/test_button.js");
        String content = "", temp = "";
        System.out.printf("Param %s\n", ctx.pathParam("scriptName", String.class).get());

        try {
            FileReader readerTemp = new FileReader(testButtonJS);
            BufferedReader jsReader = new BufferedReader(readerTemp);

            while((temp = jsReader.readLine()) != null) {
                content += temp + "\n";
            }

            jsReader.close();
            readerTemp.close();
        }catch(IOException IOE) {
            System.out.printf("IOE>%s\n", IOE.getMessage());
        }
        //content = StringEscapeUtils.escapeHtml4(content);
        ctx.html(content);
    }
}
