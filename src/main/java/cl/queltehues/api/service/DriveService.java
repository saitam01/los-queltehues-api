package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class DriveService {

    Drive connectDrive() throws DriveException {
        try {
            return DriveConnection.connectDrive();
        } catch (IOException | GeneralSecurityException e) {
            log.error(e.getMessage());
            throw new DriveException("Drive connection fail.");
        }
    }

    Sheets connectSheets() throws DriveException {
        try {
            return DriveConnection.connectSheet();
        } catch (IOException | GeneralSecurityException e) {
            log.error(e.getMessage());
            throw new DriveException("Sheets connection fail.");
        }
    }
}
