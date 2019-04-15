package db.dbs.googlecloud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import db.dbs.DocumentStorage;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CloudFirestore<T> implements DocumentStorage<T> {

    private static final Logger log = Logger.getLogger(CloudFirestore.class.getName());

    private static final String collectionName = "images";

    private static final CollectionReference imagesCollection;
    private static final boolean initialized;

    static {
        CollectionReference tmpImagesCollection = null;
        boolean tmpInitialized = false;
        try {
            GoogleCredentials credentials = Credentials.get().orElseThrow();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

            Firestore firestore = FirestoreClient.getFirestore(firebaseApp);
            tmpImagesCollection = firestore.collection(collectionName);
            tmpInitialized = true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not connect to Database", e);
        }
        imagesCollection = tmpImagesCollection;
        initialized = tmpInitialized;
    }

    private final Class<T> documentType;

    CloudFirestore(Class<T> documentType) {
        this.documentType = documentType;
    }

    @Override
    public void storeDocument(T document) {
        if (!initialized) {
            return;
        }
        try {
            imagesCollection
                    .document(UUID.randomUUID().toString())
                    .set(document)
                    .get();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not storeFile image to Database", e);
        }
    }

    @Override
    public void loadDocuments(int amount, Consumer<List<T>> documentConsumer) {
        if (!initialized) {
            return;
        }
        try {
            documentConsumer.accept(imagesCollection
                    .limit(amount)
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(d -> d.toObject(documentType))
                    .collect(Collectors.toList()))
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
