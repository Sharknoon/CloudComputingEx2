package db.dbs.googlecloud;

import db.DB;
import db.Image;
import db.dbs.DocumentStorage;
import db.dbs.FileStorage;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class CloudPlatform implements DB {

    private final FileStorage fileStorage = new CloudStorage();
    private final DocumentStorage<Image> documentStorage = new CloudFirestore<>(Image.class);

    @Override
    public void storeObject(String caption, String description, InputStream data) {
        String id = UUID.randomUUID().toString();
        fileStorage.storeFile(data, id);
        fileStorage.getLinkToFile(id, url -> {
            Image image = new Image(String.valueOf(url), caption, description);
            documentStorage.storeDocument(image);
        });
    }

    @Override
    public void loadImages(Consumer<List<Image>> imageConsumer) {
        documentStorage.loadDocuments(50, imageConsumer);
    }
}
