package org.hakifiles.api.infrastructure.utils;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;

public class GoogleCloudStorage {
    private static String projectId = "geometric-wall-456215-h7";
    private static String bucketName = "hakifiles_bucket";

    public static String uploadImage(String imageName) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        BlobId blobId = BlobId.of(bucketName, imageName);

//        storage.createFrom()
        return "a";
    }
}
