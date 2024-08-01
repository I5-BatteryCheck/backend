package I5.webserver.global.Util;

import java.io.File;

public class FileUtils {

    public static String getPathWithoutFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        File file = new File(filePath);
        return file.getParent();
    }

    public static String getExtensionName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }
}
