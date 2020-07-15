package api;

import data.Document;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import j2html.TagCreator;
import j2html.TagCreator.*;
import org.apache.commons.lang3.StringEscapeUtils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static j2html.TagCreator.*;

public class Home {
    private static final String JQUERY_URL = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js";
    //private static Object StringEscapeUtils;

    @OpenApi(
            summary = "Get Index.html",
            operationId = "getIndex",
            path = "/",
            method = HttpMethod.GET,
            responses = {@OpenApiResponse(status = "200", content = @OpenApiContent(from = String.class))}
            )
    public static void getIndex(Context ctx) {
        //ctx.result(doc.content);
        //attrs(.String) -> class = String
        //attrs(#String) -> id = String
        ctx.contentType("text/html");
        ctx.html(html(
                head(


                        title("test")
                ),
                body(

                        h1("Hello World"),
                        button(
                                attrs("#test_button"),
                                "Test"
                        ),
                        div(),
                        script().withSrc(JQUERY_URL),
                        script().withSrc("http://localhost:9090/script/test_button.js")
                )
        ).render()
        );
    }

    private static String getTestButtonJS() {
        File testButtonJS = new File("public/script/test_button.js");
        String content = "", temp = "";

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
        content = StringEscapeUtils.escapeHtml4(content);
        return content;
    }

}
