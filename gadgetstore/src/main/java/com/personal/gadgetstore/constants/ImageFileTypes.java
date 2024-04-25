package com.personal.gadgetstore.constants;

public enum ImageFileTypes {
    PNG(".png"), JPG(".jpg"), JPEG(".jpeg");

    String fileType;

    ImageFileTypes(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public static boolean isFileTypeAllowed(String fileType) {
        for (ImageFileTypes type : ImageFileTypes.values()) {
            if (type.getFileType()
                    .equalsIgnoreCase(fileType)) {
                return true;
            }
        }
        return false;
    }
}
