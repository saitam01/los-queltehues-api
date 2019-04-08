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
    public Collection<File> getFolder(String folderName) {
        try {
            return connectDrive(folderName);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<File> connectDrive(String folderName) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        Drive service = DriveConnection.connectDrive();
        // Print the names and IDs for up to 10 files.
        FileList folderList = service.files().list()
                //.setPageSize(10)
                .setQ("mimeType='application/vnd.google-apps.folder'")
                .setQ(String.format("name='%s'", folderName))
                .setFields("nextPageToken, files(id, name, mimeType)")
                .execute();
        FileList fileList;
        if (ObjectUtils.isEmpty(folderList.getFiles())) {
            log.info("No folder found.");
            return Collections.emptyList();
        } else {
            List<File> folders = folderList.getFiles();
            fileList = service.files().list()
                .setQ(String.format("'%s' in parents", folders.get(0).getId()))
                .execute();
            if(fileList.isEmpty()) {
                log.info("No files found.");
                return Collections.emptyList();
            }
            fileList.getFiles().stream()
                .forEach(file ->
                    log.info(String.format("%s (%s), %s\n", file.getName(), file.getId(), file.getMimeType())));
            return fileList.getFiles();
        }
    }
}
