package api;

import data.Image;
import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import io.javalin.plugin.openapi.annotations.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ImageController {

    @OpenApi(
            summary = "Get Wallpaper",
            operationId = "getWallpaper",
            path = "/img/get_wallpaper",
            method = HttpMethod.GET,
            responses = {@OpenApiResponse(status = "200", content = {@OpenApiContent(from = BufferedImage[].class)})}
    )
    public static void getWallpaper(Context ctx) {
        File imgFolder = new File("wallpaper");
        File img[] = imgFolder.listFiles();
        BufferedImage img_buf[] = new BufferedImage[img.length];
        String ct = "";

        for(File f : img) {

            if(f.getName().split("\\.").length == 2) {
                ct += f.getName().split("\\.")[0] + "+";
            } else {
                System.out.printf("%s isn't a Image\n", f.getName());
            }
        }
        ctx.html(ct);
    }

    @OpenApi(
            description = "Get default Image",
            operationId = "getDefaultImage",
            path = "/img",
            method = HttpMethod.GET,
            responses = @OpenApiResponse(status = "200")
    )
    public static void getDefaultImage(Context ctx) {
        ctx.contentType("image/jpeg");

        File img = new File("wallpaper/beatrice.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imgByte = null;
        try {
            BufferedImage imgBuf = ImageIO.read(img);
            ImageIO.write(imgBuf, "jpg", baos);
            baos.flush();
            imgByte = baos.toByteArray();
            baos.close();
        } catch (IOException IOE) {
            System.out.printf("IOE>%s\n", IOE.getMessage());
        }

        ctx.result(imgByte);
    }

    @OpenApi(
            description = "Get Image by ImageName",
            operationId = "getImageByImageName",
            path = "/img/:imgName",
            method = HttpMethod.GET,
            pathParams = @OpenApiParam(name = "imgName", description = "Image Name not include execute"),
            responses = @OpenApiResponse(status = "200")
    )
    public static void getImageByName(Context ctx) {
        boolean error = true;
        File wallpaperFolder = new File("wallpaper");
        File wallpaperImg[] = wallpaperFolder.listFiles();
        String imgName = ctx.pathParam("imgName", String.class).get();
        System.out.printf("imgName -> %s\n", imgName);
        String ctnType = "";

        for(File f : wallpaperImg) {

            if(f.getName().split("\\.")[0].equals(imgName)) {

                if(f.getName().split("\\.")[1].equals("jpg")) {
                    ctnType = "jpeg";
                } else {
                    ctnType = "png";
                }
                ctx.result(getImageBytes(f)).contentType("image/" + ctnType);
                System.out.printf("Found\n");
                break;
            }

        }
    }
/**/
    @OpenApi(
            description = "Test Get All image in one handshake",
            operationId = "testGetAllImageInOneHandshake",
            path = "/img/TEST",
            method = HttpMethod.GET,
            responses = @OpenApiResponse(status = "200")
    )
    public static void getAllWallpaperTest(Context ctx) {
        File imgFolder = new File("wallpaper");
        File[] imgList = imgFolder.listFiles();
        ArrayList<Image> imgs = new ArrayList<>();
        JSONObject json = new JSONObject();

        for(File img : imgList) {

            String imgInfo[] = img.getName().split("\\.");

            if(imgInfo.length > 1) {
                System.out.printf("imgName:%s\nimgInfo[0]:%s\nimgInfo[1]:%s\n", img.getName(), imgInfo[0], imgInfo[1]);
                imgs.add(new Image(imgInfo[0], getImageBytes(img), imgInfo[1]));
                HashMap data = new HashMap();
                data.put("type", imgInfo[1]);
                data.put("data", getImageBytes(img));
                json.put(imgInfo[0], data);
            }

        }

        String JSON = json.toString();
        System.out.printf("In JSON:\n%s\n", JSON);
        ctx.contentType("text/json");
        ctx.result(JSON);
    }

    private static byte[] getImageBytes(File imgFile) {
        byte[] imgBytes = null;

        try {
            InputStream is;
            BufferedImage imgBuf = ImageIO.read(imgFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imgBuf, imgFile.getName().split("\\.")[1], baos);
            baos.flush();
            imgBytes = baos.toByteArray();
            baos.close();
        } catch(IOException IOE) {
            System.out.printf("IOE>%s\n", IOE.getMessage());
        }

        return imgBytes;
    }
}
/*
* {
*   "name":
*
*
* */