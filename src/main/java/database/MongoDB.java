package database;

import java.util.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;


//use "mongod" start mongodb
public class MongoDB {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final String URL = "localhost";
    private static final int MONGO_PORT = 27017;

    public static void main(String[] args) {
        setup();
    }

    private static void setup() {
        try {
            MongoClient client = new MongoClient(URL, MONGO_PORT);
            MongoDatabase db = client.getDatabase("test");
            MongoIterable<String> dbNameList = db.listCollectionNames();

            for(String s : dbNameList) {
                printf("%s\n", s);
            }
            println("choose a database :");
            String dbNameSelect = INPUT.next();

            MongoCollection<Document> colls = db.getCollection(dbNameSelect);

            for(Document doc : colls.find()) {
                doc.remove("_id");
                printf("data(JSON): %s\n", pretty(doc.toJson()));
            }
        } catch(Exception e) {
            println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    private static String pretty(String json) {
        json = json.replaceAll(", ", ", \n");
        json = json.replaceAll("\\{", "{\n");
        json = json.replaceAll("}", "\n}");
        return "\n" + json;
    }

    private static void println(String dataln) {
        System.out.println(dataln);
    }

    private static void print(String data) {
        System.out.print(data);
    }

    private static void printf(String format, Object ... args) {
        System.out.printf(format, args);
    }
}
