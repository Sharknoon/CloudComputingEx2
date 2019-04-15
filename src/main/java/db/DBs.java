package db;

import db.dbs.googlecloud.CloudPlatform;
import db.dbs.mongodb.MongoDB;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public final class DBs {

    /**
     * Returns a instance of the specified DBInstance
     *
     * @return A Instance pointing to the requested DBInstance
     */
    public static DB getDB(DBInstance db) {
        switch (db) {
            case MONGO_DB:
                return new MongoDB();
            case GOOGLE_CLOUD_PLATFORM:
                return new CloudPlatform();
            default:
                return new NOOP_DB();
        }
    }


    /**
     * A enum of all available Database instances
     */
    public enum DBInstance {
        MONGO_DB,
        GOOGLE_CLOUD_PLATFORM
    }

    /**
     * Just a default no-op Database, to avoid null
     * Here you can see the strength of callbacks, no need for returns :)
     */
    public static class NOOP_DB implements DB {

        @Override
        public void storeObject(String caption, String description, InputStream data) {
        }

        @Override
        public void loadImages(Consumer<List<Image>> imageConsumer) {
        }
    }

}
