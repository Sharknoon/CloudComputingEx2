package db;

public class DBs {

    public static DB getInstance() {
        return new DBImpl();
    }

}
