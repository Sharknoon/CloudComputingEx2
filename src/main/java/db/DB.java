package db;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * The interface for the Database
 */
public interface DB {

    /**
     * Stores a image in the Database asynchronously
     *
     * @param caption     The caption of the image
     * @param description The description of the image
     * @param data        The image itself as Inputstream
     */
    void storeObject(String caption, String description, InputStream data);

    /**
     * Returns the images of the database
     *
     * @param imageConsumer The consumer of the asynchronously supplied images
     */
    void loadImages(Consumer<List<Image>> imageConsumer);

}
