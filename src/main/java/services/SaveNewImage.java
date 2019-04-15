package services;

import com.google.gson.Gson;
import db.DB;
import db.DBs;
import db.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
@WebServlet("/saveNewImage")
public class SaveNewImage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            String imageCaption = req.getParameter("imageCaption");
            String imageDesc = req.getParameter("imageDesc");

            Part imageData = req.getPart("imageData");
            InputStream imageContent = imageData.getInputStream();

            DB db = DBs.getDB(DBs.DBInstance.GOOGLE_CLOUD_PLATFORM);
            db.storeObject(imageCaption, imageDesc, imageContent);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ServletException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
