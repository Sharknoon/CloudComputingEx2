package db;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Just a simple bean holding a Image
 */
public class Image {

    public ObjectId id = new ObjectId();
    public String base64 = "";
    public String caption = "";
    public String description = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return Objects.equals(id, image.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Image (" +
                "id=" + id +
                ", size='" + base64.length() + '\'' +
                ", caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                ')';
    }
}
