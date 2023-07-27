package com.github.pavelkuropatin.reportcreatorapi;

public class Image {

    private String imageName;
    private byte[] imageData;
    private boolean useOriginalSize = false;

    public Image() {
    }

    public Image(final String imageName, final byte[] imageData) {
        this.imageName = imageName;
        this.imageData = imageData;
    }

    public Image(final String imageName, final byte[] imageData, final boolean useOriginalSize) {
        this.imageName = imageName;
        this.imageData = imageData;
        this.useOriginalSize = useOriginalSize;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(final byte[] imageData) {
        this.imageData = imageData;
    }

    public boolean isUseOriginalSize() {
        return useOriginalSize;
    }

    public void setUseOriginalSize(final boolean useOriginalSize) {
        this.useOriginalSize = useOriginalSize;
    }
}
