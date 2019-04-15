package db.dbs;

import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public interface FileStorage {

    void storeFile(InputStream data, String id);

    void getFile(String id, Consumer<InputStream> dataConsumer);

    void getLinkToFile(String id, Consumer<URL> urlConsumer);

}
