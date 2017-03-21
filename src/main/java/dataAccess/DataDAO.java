package dataAccess;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DataDAO {
    private static DataDAO instance = null;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    private DataDAO(){
        database = DatabaseConnection.getInstance().getDatabase();
        collection = database.getCollection("data");
    }

    public static DataDAO getInstance(){
        if(instance == null){
            instance = new DataDAO();
        }
        return instance;
    }

    public String insertData(String token, List data){
        String message = null;
        if(getDataByToken(token).get("_id") != null){
            for(Object object: data){
                Document doc = new Document("$push", new Document("data", object));
                collection.updateOne(eq("_id", token), doc);
            }
            message = "Succesfully inserted data.";
        }
        return message;
    }

    public Document getDataByToken(String token){
        return collection.find(eq("_id", token)).first();
    }

    public void insertToken(String token) {
        Document doc = new Document("_id", token);
        collection.insertOne(doc);
    }

    public ArrayList<String> getAllTokens(){
        ArrayList<String> allTokens = new ArrayList<String>();

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while(cursor.hasNext()){
                allTokens.add(cursor.next().get("_id").toString());
            }
        } finally {
            cursor.close();
        }
        return allTokens;
    }

}
