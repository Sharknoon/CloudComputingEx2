package db.dbs;

import java.util.List;
import java.util.function.Consumer;

public interface DocumentStorage<T> {

    void storeDocument(T document);

    void loadDocuments(int amount, Consumer<List<T>> documentConsumer);

}
