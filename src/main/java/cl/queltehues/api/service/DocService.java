package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class DocService {

    private DriveUtils driveUtils;

    @Autowired
    public DocService(DriveUtils driveUtils) {
        this.driveUtils = driveUtils;
    }

    public Collection<File> getFiles(String folderName) throws DriveException {
        String folderId = driveUtils.getFolderIdByName(folderName);
        return driveUtils.getFilesFromFolder(folderId);
    }
}
