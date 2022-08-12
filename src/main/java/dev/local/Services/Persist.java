package dev.local.Services;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import dev.local.Entities.Item;
import dev.local.Entities.Items;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static dev.local.Application.prop;
import static dev.local.Utils.Constants.*;
import static dev.local.Utils.Utils.log;

public class Persist {

    private static final ConnectionString connectionString = new ConnectionString((String) prop.get(ENV_MONGO_CONNECTION_URL));
    private static final MongoClient mongoClient = MongoClients.create(connectionString);
    private static final MongoDatabase mongoDatabase = mongoClient.getDatabase((String) prop.get(ENV_MONGO_DATABASE));
    private static final MongoCollection<Document> mongoCollection = mongoDatabase.getCollection((String) prop.get(ENV_MONGO_COLLECTION));
    private static final Gson gson = new Gson();
    public Persist(boolean drop) {
        if (drop) mongoCollection.drop();
        checkConnection();
    }

    private void checkConnection() {
        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = mongoDatabase.runCommand(command);

            log(LOG_MSG_CONNECT_SUCCESS, Persist.class.toString());
        } catch (MongoException me) {
            System.err.println(LOG_MSG_MONGO_EXCEPTION + me);
            throw new Error(LOG_MSG_MONGO_ERROR);
        }
    }

    public void many(HashMap<String, Item> docCloned) {
        try {
            log(LOG_MSG_GO_INSERT + docCloned.size() + LOG_MSG_ELEMENTS, Persist.class.toString());
            List<Document> docList = new ArrayList<>();
            docCloned.forEach((k,v) -> docList.add(Document.parse(gson.toJson(v))));
            InsertManyResult result = mongoCollection.insertMany(docList);
            log(LOG_MSG_INSERT_ID_LENGTH + result.getInsertedIds().size(), Persist.class.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getAll() {
        log(LOG_MSG_GET_ALL_DOCS, Persist.class.toString());

        try {
            int total = 0;
            for (Document document : mongoCollection.find()) {
                HashSet<String> insertedIdSet = Items.insertedIdSet;
                insertedIdSet.add(document.getString("id"));
                Items.loadedItemSize++;
                total++;
            }
            log(LOG_MSG_TOTAL_EL_DB_COUNT + total, Persist.class.toString());
        } catch (MongoException me) {
            me.printStackTrace();
        }

        log(LOG_MSG_FETCHED, Persist.class.toString());
    }

}
