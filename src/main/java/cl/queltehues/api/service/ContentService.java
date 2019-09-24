package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService {
    private DriveUtils driveUtils;

    private static final String FOLDER_NAME = "vecinos";
    private static final String INFO_CONDOMINIO = "info-condominio";

    @Autowired
    public ContentService(DriveUtils driveUtils) {
        this.driveUtils = driveUtils;
    }

    public List getCommonExpenses(String houseNumber) throws DriveException {

        List<File> filterFiles = getFilesFromFolder();
        return driveUtils.getCommonExpenses(
                filterFiles.stream().filter(f ->
                        f.getName().equalsIgnoreCase(INFO_CONDOMINIO))
                        .findAny().orElse(null), houseNumber);
    }

    public List getDoorKeeper() throws DriveException {
        List<File> filterFiles = getFilesFromFolder();
        return driveUtils.getDoorKeeper(
                filterFiles.stream().filter(f ->
                        f.getName().equalsIgnoreCase(INFO_CONDOMINIO))
                        .findAny().orElse(null));
    }

    public List getNews() throws DriveException {
        List<File> filterFiles = getFilesFromFolder();
        return driveUtils.getNews(
                filterFiles.stream().filter(f ->
                        f.getName().equalsIgnoreCase(INFO_CONDOMINIO))
                        .findAny().orElse(null));
    }

    public List getComite() throws DriveException {
        List<File> filterFiles = getFilesFromFolder();
        return driveUtils.getComite(
                filterFiles.stream().filter(f ->
                        f.getName().equalsIgnoreCase(INFO_CONDOMINIO))
                        .findAny().orElse(null));
    }

    private List<File> getFilesFromFolder() throws DriveException {
        String folderId = driveUtils.getFolderIdByName(FOLDER_NAME);
        List<File> filesFromFolder = driveUtils.getFilesFromFolder(folderId);

        if(filesFromFolder.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return filesFromFolder.stream()
                .filter(credential -> "application/vnd.google-apps.spreadsheet"
                        .equalsIgnoreCase(credential.getMimeType()))
                .collect(Collectors.toList());
    }

}
