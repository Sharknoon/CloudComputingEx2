package db;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBImpl implements DB {

    private static final Logger log = Logger.getLogger(DBImpl.class.getName());
    private static final Executor threadPool = Executors.newCachedThreadPool();

    private static final String username = "simon";
    private static final String password = "Master2020";
    private static final String database = "cc";
    private static final String collectionName = "images";
    private final MongoCollection<Image> collection;
    private boolean initialized = false;

    public DBImpl() {
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        MongoCollection<Image> collection = null;
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
                                    CodecRegistries.fromRegistries(
                                            MongoClients.getDefaultCodecRegistry(),
                                            CodecRegistries.fromProviders(
                                                    PojoCodecProvider.builder()
                                                            .automatic(true)
                                                            .build()
                                            )
                                    )
                            )
                            .build()
            );
            mongoDatabase = mongoClient.getDatabase(database);
            collection = mongoDatabase.getCollection(collectionName, Image.class);
            initialized = true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not connect to Database", e);
        }
        this.collection = collection;
    }

    @Override
    public void storeImage(Image image) {
        if (initialized) {
            collection.insertOne(image).subscribe(new Subscriber<>() {
                @Override
                public void onSubscribe(Subscription s) {
                    s.request(1);// <--- Data requested and the insertion will now occur
                }

                @Override
                public void onNext(Success success) {
                    log.log(Level.INFO, "Storing image in Database " + image);
                }

                @Override
                public void onError(Throwable t) {
                    log.log(Level.WARNING, "Could not store image in database", t);
                }

                @Override
                public void onComplete() {
                    log.log(Level.INFO, "Successfully stored image in Database " + image);
                }
            });
        }
    }

    @Override
    public void loadImages(Consumer<Image> imageConsumer) {
        if (!initialized) {
            return;
        }
        collection.find().limit(50).subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Image image) {
                imageConsumer.accept(image);
            }

            @Override
            public void onError(Throwable t) {
                log.log(Level.WARNING, "Could not get images from the database", t);
            }

            @Override
            public void onComplete() {
                log.log(Level.INFO, "Successfully returned images from the Database");
            }
        });
    }
}
