package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Service
@Slf4j
public class DocService {

    @Autowired
    DriveUtils driveUtils;

    public Collection<File> getFiles(String folderName) throws DriveException {
        String folderId = driveUtils.getFolderIdByName(folderName);
        return driveUtils.getFilesFromFolder(folderId);
    }
}
