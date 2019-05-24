package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
@Slf4j
public class DriveUtils {

    private Drive drive;

    @Autowired
    public DriveUtils(DriveService driveService) throws DriveException {
        this.drive = driveService.connectDrive();
    }

    public String getFolderIdByName(String folderName) throws DriveException {
        try {
            FileList folderList = drive.files().list()
                    .setQ("mimeType='application/vnd.google-apps.folder'")
                    .setQ(String.format("name='%s'", folderName))
                    .setFields("nextPageToken, files(id)")
                    .execute();
            if (folderList.isEmpty() || folderList.getFiles().isEmpty()) {
                throw new DriveException("No folder found.");
            }
            return folderList.getFiles().get(0).getId();
        } catch (IOException e) {
            throw new DriveException(e.getMessage());
        }
    }

    public List<File> getFilesFromFolder(String id) throws DriveException {
        try {
            FileList folderList = drive.files().list()
                    .setQ(String.format("'%s' in parents", id))
                    .setFields("nextPageToken, files(id, name, mimeType, parents)")
                    .execute();
            if (folderList.isEmpty() || folderList.getFiles().isEmpty()) {
                throw new DriveException("No files found.");
            }
            return folderList.getFiles();
        } catch (IOException e) {
            throw new DriveException("Error when retrieve files");
        }
    }

    public File getFile(File file) throws DriveException {
        try {
            InputStream in = drive.files().get(file.getId()).executeMediaAsInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String line;

            StringBuilder responseData = new StringBuilder();
            while((line = br.readLine()) != null) {
                responseData.append(line);
            }
            log.info(responseData.toString());
            return null;
        } catch (IOException e) {
            throw new DriveException(e.getMessage());
        }
    }

}