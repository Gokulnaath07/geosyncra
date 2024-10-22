package com.teamSeven.GeoSyncra.Services;

import com.google.api.client.http.FileContent;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.teamSeven.GeoSyncra.Configs.ImageEntity;
import com.teamSeven.GeoSyncra.Repository.ImageRepository;
import com.teamSeven.GeoSyncra.Repository.ImageRes;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@org.springframework.stereotype.Service
public class ImageService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

    @Autowired
    private ImageRepository imageRepository;

    private static String getPathToGoodleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }

    public ImageRes uploadImageToDrive(File file, String name, String description, String location)
            throws GeneralSecurityException, IOException {
        ImageRes imageRes = new ImageRes();

        try {
            String folderId = "1QM5f_2mBYxSJ6Tu-dRJBhVlHP5e4W-q7";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);

            // Save URL and additional data to the database
            ImageEntity imageEntity = new ImageEntity(imageUrl, name, description, location);
            imageRepository.save(imageEntity);
            file.delete();

            imageRes.setStatus(200);
            imageRes.setMessage("Image and data successfully uploaded");
            imageRes.setUrl(imageUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            imageRes.setStatus(500);
            imageRes.setMessage(e.getMessage());
        }
        return imageRes;
    }


    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }
}
