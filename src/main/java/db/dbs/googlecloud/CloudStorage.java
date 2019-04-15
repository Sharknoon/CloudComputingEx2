package db.dbs.googlecloud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import db.dbs.FileStorage;

import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CloudStorage implements FileStorage {

    private static final Logger log = Logger.getLogger(CloudStorage.class.getName());

    private static final String bucketName = "cloud-computing-images";
    private static final Bucket imagesBucket;

    private static final boolean initialized;

    static {
        Bucket tmpBucket = null;
        boolean tmpInitialized = false;
        try {
            GoogleCredentials credentials = Credentials.get().orElseThrow();
            // Instantiates a client
            Storage storage = StorageOptions
                    .newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();
            tmpBucket = storage.get(bucketName);
            if (tmpBucket == null) {
                tmpBucket = storage.create(BucketInfo.of(bucketName));
            }
            tmpInitialized = true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not initialize Cloud Storage", e);
        }
        imagesBucket = tmpBucket;
        initialized = tmpInitialized;
    }

    @Override
    public void storeFile(InputStream data, String id) {
        if (!initialized) {
            return;
        }
        imagesBucket.create(id, data);
    }

    @Override
    public void getFile(String id, Consumer<InputStream> dataConsumer) {
        if (!initialized) {
            return;
        }
        Blob blob = imagesBucket.get(id);
        if (blob != null) {
            dataConsumer.accept(Channels.newInputStream(blob.reader()));
        }
    }

    @Override
    public void getLinkToFile(String id, Consumer<URL> urlConsumer) {
        if (!initialized) {
            return;
        }
        Blob blob = imagesBucket.get(id);
        if (blob != null) {
            urlConsumer.accept(blob.signUrl(100, TimeUnit.DAYS));
        }
    }

}
