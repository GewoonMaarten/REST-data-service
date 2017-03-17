package dataAccess;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private MongoClient mongoClient;
    private MongoDatabase database;


    private DatabaseConnection(){
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("DataService");
    }

    public static DatabaseConnection getInstance() {
        if(instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }
}
