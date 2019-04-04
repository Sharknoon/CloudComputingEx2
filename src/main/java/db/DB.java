package db;

import java.util.List;

public interface DB {
    
    void storeImage(Image image);

    List<Image> loadImages();

}
