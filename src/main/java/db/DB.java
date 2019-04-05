package db;

import java.util.List;
import java.util.function.Consumer;

/**
 * The interface for the Database
 */
public interface DB {

    /**
     * Stores a image in the Database asynchronously
     *
     * @param image The image to be stored
     */
    void storeImage(Image image);

    /**
     * Returns the images of the database
     *
     * @param imageConsumer The consumer of the asynchronously supplied images
     */
    void loadImages(Consumer<List<Image>> imageConsumer);

}
