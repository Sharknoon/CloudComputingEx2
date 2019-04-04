package db;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * Just a simple bean holding a Image
 */
public class Image {

    private final ObjectId id = new ObjectId();
    private final String base64;
    private final String caption;
    private final String description;

    public Image(String base64, String caption, String description) {
        this.base64 = base64;
        this.caption = caption;
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public String getBase64() {
        return base64;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

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
