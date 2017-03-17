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
        collection = database.getCollection("users");
    }

    public static DataDAO getInstance(){
        if(instance == null){
            instance = new DataDAO();
        }
        return instance;
    }

    public void insertData(String token, List data){

        if(getDataByToken(token).get("_id") == null){
            Document doc = new Document("_id", token)
                    .append("data", data);
            collection.insertOne(doc);
        } else {
            Document doc = new Document("$push", new Document("$each", data));
            collection.updateOne(eq("_id", token), doc);
        }

    }

    public Document getDataByToken(String token){
        return collection.find(eq("_id", token)).first();
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
