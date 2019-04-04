package db;

import java.util.List;

public interface DB {

    default DB getInstance(){
        return null;
    }

    void storeImage(Image image);

    List<Image> loadImages();

}
