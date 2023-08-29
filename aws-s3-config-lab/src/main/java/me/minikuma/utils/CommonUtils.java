package me.minikuma.utils;

public final class CommonUtils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private CommonUtils() {

    }

    public static String convertFileName(String dir, String originFileName) {
        int fileExtensionIndex = originFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originFileName.substring(fileExtensionIndex);
        String fileName = originFileName.substring(0, fileExtensionIndex);
        String currTime = String.valueOf(System.currentTimeMillis());
        return dir + "/" + fileName + "_" + currTime + fileExtension;
    }
}
