package db;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public final class MongoDB implements DB {

    private static final Logger log = Logger.getLogger(MongoDB.class.getName());

    private static final String username = "simon";
    private static final String password = "Master2020";
    private static final String database = "cc";
    private static final String collectionName = "images";

    private static final MongoCollection<Image> collection;
    private static final boolean initialized;

    static {
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        MongoCollection<Image> mongoCollection = null;
        boolean mongoInitialized = false;
        try {
            mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .credential(
                                    MongoCredential.createCredential(
                                            username,
                                            database,
                                            password.toCharArray()
                                    )
                            )
                            .codecRegistry(
                                    fromRegistries(
                                            MongoClientSettings.getDefaultCodecRegistry(),
                                            fromProviders(
                                                    PojoCodecProvider.builder().automatic(true).build()
                                            )
                                    )
                            )
                            .build()
            );
            mongoDatabase = mongoClient.getDatabase(database);
            mongoCollection = mongoDatabase.getCollection(collectionName, Image.class);
            //To definitely get no Nullpointer
            if (mongoCollection != null) {
                mongoInitialized = true;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not connect to Database", e);
        }
        collection = mongoCollection;
        initialized = mongoInitialized;
    }

    /**
     * Dont allow others to create Instances of this class
     */
    MongoDB() {
    }

    @Override
    public void storeImage(Image image) {
        if (!initialized) {
            return;
        }
        collection.insertOne(image);
        log.log(Level.INFO, "Successfully stored image in Database " + image);

    }

    @Override
    public void loadImages(Consumer<List<Image>> imageConsumer) {
        if (!initialized) {
            return;
        }
        final int limit = 50;
        var list = new ArrayList<Image>();
        collection.find()
                .sort(Sorts.descending("_id"))
                .limit(limit)
                .into(list);
        log.log(Level.INFO, "Successfully returned images from the Database");
        imageConsumer.accept(list);
    }
}
