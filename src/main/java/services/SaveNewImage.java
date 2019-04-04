package services;

import com.google.gson.Gson;
import db.DB;
import db.DBs;
import db.Image;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/saveNewImage")
public class SaveNewImage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        Gson gson = new Gson();

        SaveNewImageMessage newImageMessage = gson.fromJson(reader, SaveNewImageMessage.class);
        Image toStoreImage = new Image(newImageMessage.getImageData(),
                newImageMessage.getImageCaption(), newImageMessage.getImageDesc());

        DB db = DBs.getMongoDB();
        db.storeImage(toStoreImage);

        resp.setStatus(HttpServletResponse.SC_OK);


    }
}
