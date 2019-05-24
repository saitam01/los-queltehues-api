package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.security.jwt.TokenProvider;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityService {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private DriveUtils driveUtils;

    public Collection<Object> validate(String token) throws DriveException {
        User user = tokenProvider.getUser(token);
        String folderId = driveUtils.getFolderIdByName("vecinos");
        List<File> credenciales = driveUtils.getFilesFromFolder(folderId);
        credenciales.forEach(credencial -> {
            try {
                File file = driveUtils.getFile(credencial);
            } catch (DriveException e) {
                e.printStackTrace();
            }
        });
        return Arrays.asList(user);
    }
}
