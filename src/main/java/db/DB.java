package db;

import java.util.function.Consumer;

public interface DB {

    void storeImage(Image image);

    void loadImages(Consumer<Image> imageConsumer);

}
