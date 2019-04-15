package db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class DBsTest {

    @Test
    void getDB() {
        DB cloud = DBs.getDB(DBs.DBInstance.GOOGLE_CLOUD_PLATFORM);
        cloud.storeObject("cap", "des", new ByteArrayInputStream(new byte[]{1, 2, 3}));
        cloud.loadImages(images -> {
            try {
                Assertions.assertTrue(images.size() > 1);
                Image image = images.get(0);
                Assertions.assertEquals("cap", image.caption);
                Assertions.assertEquals("des", image.description);
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(image.link)).build();
                byte[] send = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
                Assertions.assertArrayEquals(new byte[]{1, 2, 3}, send);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}