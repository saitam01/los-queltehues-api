package cl.queltehues.api.service;

import cl.queltehues.api.aspect.LoggingInfo;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DocService {

    @LoggingInfo
    public Collection<File> getBoletas() {
        try {
            return connectDrive();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<File> connectDrive() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        Drive service = DriveConnection.connectDrive();
        // Print the names and IDs for up to 10 files.
        FileList fileList = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name, mimeType)")
                .execute();

        if (ObjectUtils.isEmpty(fileList.getFiles())) {
            log.info("No files found.");
        } else {
            log.info("Files:");
            fileList.getFiles().stream()
                    .forEach(file ->
                            log.info(String.format("%s (%s), %s\n", file.getName(), file.getId(), file.getMimeType()))
                    );
        }
        return fileList.getFiles();
    }
}
