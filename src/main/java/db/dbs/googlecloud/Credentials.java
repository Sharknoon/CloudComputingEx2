package db.dbs.googlecloud;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

class Credentials {

    private static final Logger log = Logger.getLogger(Credentials.class.getName());

    private static final GoogleCredentials credentials;

    static {
        GoogleCredentials tmpCredentials = null;
        try {
            InputStream inputStream = Credentials.class.getResourceAsStream("/serviceAccount.json");
            tmpCredentials = GoogleCredentials.fromStream(inputStream);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not read credentials", e);
        }
        credentials = tmpCredentials;
    }

    static Optional<GoogleCredentials> get() {
        return Optional.ofNullable(credentials);
    }

    public static void main(String[] args) {

    }

}
