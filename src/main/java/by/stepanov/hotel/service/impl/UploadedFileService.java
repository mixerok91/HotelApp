package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class UploadedFileService {
    private final static String SAVE_IMAGE_DIRECTORY = "images\\rooms";

    private static final Logger log = Logger.getLogger(UploadedFileService.class);

    public static String saveUploadedFile(String absolutePath, Collection<Part> parts, String roomNumber) throws ServiceException {
        String savePath = absolutePath + SAVE_IMAGE_DIRECTORY;
        String path = null;

        boolean haveUploadedFile = parts.stream()
                .anyMatch(part -> part.getSubmittedFileName() != null && !"".equals(part.getSubmittedFileName()));

        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        if (haveUploadedFile) {
            String fileName;

            for (Part part : parts) {
                fileName = "room " + roomNumber;
                path = savePath + File.separator + fileName + ".jpg";
                try {
                    part.write(path);
                } catch (IOException e) {
                    log.error("IOException", e);
                    throw new ServiceException(e);
                }

                log.info("Image saved to '" + path + "'");

                path = SAVE_IMAGE_DIRECTORY + File.separator + fileName + ".jpg";

                log.info("Path set to room with number: '" + roomNumber + "'");
            }
        }
        return path;
    }
}
