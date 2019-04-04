package db;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Image {

    private final ObjectId id;
    private final String base64;
    private final String caption;
    private final String description;

    public Image(ObjectId id, String base64, String caption, String description) {
        this.id = id;
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
        return id != null ? id.hashCode() : -1;
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
