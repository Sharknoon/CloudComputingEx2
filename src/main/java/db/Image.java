package db;

import java.util.Objects;

/**
 * Just a simple bean holding a Image
 */
public final class Image {

    public final String link;
    public final String caption;
    public final String description;

    Image() {
        this.link = "";
        this.caption = "";
        this.description = "";
    }

    public Image(String link, String caption, String description) {
        this.link = link;
        this.caption = caption;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return Objects.equals(link, image.link);

    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }

    @Override
    public String toString() {
        return "Image (" +
                "link='" + link + '\'' +
                ", caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                ')';
    }
}
