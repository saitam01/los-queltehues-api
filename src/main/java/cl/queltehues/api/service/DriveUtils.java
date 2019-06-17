package cl.queltehues.api.service;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

    /**
     * Get Users from spreadsheet
     * @param file
     * @return List of users
     * @throws DriveException
     */
    List<User> getUsers(File file) throws DriveException {
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

    /*List<User> getUsers(File file) throws DriveException {
        try {

            List<User> userList = new ArrayList<>();
            InputStream in = drive.files().get(file.getId()).executeMediaAsInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while((line = br.readLine()) != null) {
                String[] userPass = line.split(":");

                Collection<? extends GrantedAuthority> authorities =
                        Arrays.stream(new String [] {"ROLE_USER"})
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                User user = new User(userPass[0], userPass[1], authorities);
                userList.add(user);
                //log.info(String.format("user: %s, password: %s", user.getUsername(), user.getPassword()));
            }
            return userList;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DriveException("Error on retrieve the users");
        }
    }*/
}
