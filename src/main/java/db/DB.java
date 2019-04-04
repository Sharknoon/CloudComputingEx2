package db;

import java.util.function.Consumer;

public interface DB {

    default DB getInstance() {
        return new DBImpl();
    }

    void storeImage(Image image);

    void loadImages(Consumer<Image> imageConsumer);

}
