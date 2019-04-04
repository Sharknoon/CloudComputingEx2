package db;

import org.bson.types.ObjectId;
import sun.security.util.ObjectIdentifier;

public class Image {

    private final ObjectId id;
    private final String base64;
    private final String caption;
    private final String description;
    private final long timestamp;

    public Image(ObjectId id, String base64, String caption, String description, long timestamp) {
        this.id = id;
        this.base64 = base64;
        this.caption = caption;
        this.description = description;
        this.timestamp = timestamp;
    }
}
