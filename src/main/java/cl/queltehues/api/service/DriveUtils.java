package cl.queltehues.api.service;

import cl.queltehues.api.domain.Comite;
import cl.queltehues.api.domain.CommonExpense;
import cl.queltehues.api.domain.DoorKeeper;
import cl.queltehues.api.domain.News;
import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DriveUtils {

    private Drive drive;
    private Sheets sheets;

    @Autowired
    public DriveUtils(DriveService driveService) throws DriveException {
        this.drive = driveService.connectDrive();
        this.sheets = driveService.connectSheets();
    }

    String getFolderIdByName(String folderName) throws DriveException {
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

    List<File> getFilesFromFolder(String id) throws DriveException {
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

    /**
     * Get Users from spreadsheet
     */
    List getUsers(File file) throws DriveException {
        if(file == null) {
            return Collections.EMPTY_LIST;
        }
        try {

            final String range = "A:C";
            List<User> userList = new ArrayList<>();
            ValueRange response = sheets.spreadsheets().values().get(file.getId(), range).execute();

            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                log.error("No data in spreadsheet.");
            } else {
                for (List row : values) {
                    Collection<? extends GrantedAuthority> authorities =
                        Arrays.stream(new String [] {"ROLE_USER"}).map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                    User user = new User(row.get(1).toString(), row.get(2).toString(), authorities);
                    userList.add(user);
                }
            }
            return userList;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DriveException("Error on retrieve the users");
        }
    }

    List getCommonExpenses(File infoCondominio, String houseNumber) throws DriveException {
        if(infoCondominio == null) {
            return Collections.EMPTY_LIST;
        }
        try {

            final String range = "gc!A:H";
            List<CommonExpense> commonExpenses = new ArrayList<>();
            ValueRange response = sheets.spreadsheets().values().get(infoCondominio.getId(), range).execute();

            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                log.error("No data in spreadsheet.");
            } else {
                for (List row : values) {
                    if(houseNumber.equalsIgnoreCase(row.get(0).toString())) {
                        String [] expenses = { row.get(1).toString(), row.get(2).toString(), row.get(3).toString(),
                                row.get(4).toString(), row.get(5).toString(), row.get(6).toString()};
                        CommonExpense commonExpense = new CommonExpense(row.get(0).toString(), expenses, row.get(7).toString());
                        commonExpenses.add(commonExpense);
                        break;
                    }
                }
            }
            return commonExpenses;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DriveException("Error on retrieve the common expenses.");
        }
    }

    List getDoorKeeper(File dkFiles) throws DriveException {
        if(dkFiles == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            final String range = "conserjes!A:D";
            List<DoorKeeper> doorKeepers = new ArrayList<>();
            ValueRange response = sheets.spreadsheets().values().get(dkFiles.getId(), range).execute();

            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                log.error("No data in spreadsheet.");
                return Collections.EMPTY_LIST;
            } else {
                for (List row : values) {
                    doorKeepers.add(new DoorKeeper(row.get(0).toString(), row.get(1).toString(),
                            row.get(2).toString(), row.get(3).toString()));
                }
            }
            return doorKeepers;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DriveException("Error on retrieve the common expenses.");
        }
    }

    List getNews(File newsFile) throws DriveException {
        if(newsFile == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            final String range = "news!A:D";
            List<News> news = new ArrayList<>();
            ValueRange response = sheets.spreadsheets().values().get(newsFile.getId(), range).execute();

            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                log.error("No data in spreadsheet.");
                return Collections.EMPTY_LIST;
            } else {
                for (List row : values) {
                    news.add(new News(row.get(0).toString(), row.get(1).toString(), row.get(2).toString()));
                }
            }
            return news;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DriveException("Error on retrieve the common expenses.");
        }
    }

    List getComite(File comiteFile) throws DriveException {
        if(comiteFile == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            final String range = "comite!A:C";
            List<Comite> comiteList = new ArrayList<>();
            ValueRange response = sheets.spreadsheets().values().get(comiteFile.getId(), range).execute();

            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                log.error("No data in spreadsheet.");
                return Collections.EMPTY_LIST;
            } else {
                for (List row : values) {
                    comiteList.add(new Comite(row.get(0).toString(), row.get(1).toString(), row.get(2).toString()));
                }
            }
            return comiteList;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DriveException("Error on retrieve the common expenses.");
        }
    }
}
