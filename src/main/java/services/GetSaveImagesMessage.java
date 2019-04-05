package services;

import db.Image;

import java.util.List;

public class GetSaveImagesMessage {

    private final List<Image> images;

    public GetSaveImagesMessage(List<Image> images) {
        this.images = images;
    }
}
