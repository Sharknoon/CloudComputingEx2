package db;

import db.dbs.CloudFirestore;
import db.dbs.MongoDB;

public final class DBs {

    /**
     * Returns a instance to the Mongo DB
     *
     * @return A Instance pointing to a MongoDB
     */
    public static DB getMongoDB() {
        return new MongoDB();
    }

    public static DB getCloudFirestore(){
        return new CloudFirestore();
    }

}
