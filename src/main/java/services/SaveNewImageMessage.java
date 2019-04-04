package services;

public class SaveNewImageMessage {

    private final String imageCaption;
    private final String imageDesc;
    private final String imageData;

    public SaveNewImageMessage(String imageCaption, String imageDesc, String imageData) {
        this.imageCaption = imageCaption;
        this.imageDesc = imageDesc;
        this.imageData = imageData;
    }
}
