package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserProvider {

    private DriveUtils driveUtils;
    private static final String FOLDER_NAME = "vecinos";

    @Autowired
    public UserProvider(DriveUtils driveUtils) {
        this.driveUtils = driveUtils;
    }

    List getUsers() throws DriveException {
        String folderId = driveUtils.getFolderIdByName(FOLDER_NAME);
        List<File> credentials = driveUtils.getFilesFromFolder(folderId);

        if(credentials.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<File> collect = credentials.stream()
                .filter(credential -> "text/plain".equalsIgnoreCase(credential.getMimeType()))
                .collect(Collectors.toList());
        return driveUtils.getUsers(collect.get(0));
    }
}
