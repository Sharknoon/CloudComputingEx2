package services;

import db.Image;

import java.util.List;

public class GetSavedImagesMessage {

    private final List<Image> images;

    public GetSavedImagesMessage(List<Image> images) {
        this.images = images;
    }
}
