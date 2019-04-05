package db;

public final class DBs {

    /**
     * Returns a instance to the Mongo DB
     *
     * @return A Instance pointing to a MongoDB
     */
    public static DB getMongoDB() {
        return new MongoDB();
    }

}
