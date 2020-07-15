package data;

public class Image {
    String imgName;
    String imgBytes;
    String imgType;

    public Image(String imgName, byte[] imgBytes, String imgType) {
        this.imgName = imgName;
        this.imgBytes = imgBytes.toString();
        this.imgType = imgType;
    }

    public Image() {

    }
}
