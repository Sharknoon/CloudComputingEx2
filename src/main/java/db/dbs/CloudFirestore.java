package db.dbs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.mongodb.client.MongoCollection;
import db.DB;
import db.Image;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CloudFirestore implements DB {

    private static final Logger log = Logger.getLogger(CloudFirestore.class.getName());

    private static final String collectionName = "images";

    private static final CollectionReference imagesCollection;
    private static final boolean initialized;


    static {
        CollectionReference tmpImagesCollection = null;
        boolean tmpInitialized = false;
        try {
            Path path = Paths.get("src", "main", "resources", "serviceAccount.json");
            InputStream inputStream = Files.newInputStream(path);
            // Use a service account
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore firestore = FirestoreClient.getFirestore();
            tmpImagesCollection = firestore.collection(collectionName);
            tmpInitialized = true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not connect to Database", e);
        }
        imagesCollection = tmpImagesCollection;
        initialized = tmpInitialized;
    }

    public static void main(String[] args) {

    }

    @Override
    public void storeImage(Image image) {
        if (!initialized){return;}
        imagesCollection.add(image);
    }

    @Override
    public void loadImages(Consumer<List<Image>> imageConsumer) {

    }
}
