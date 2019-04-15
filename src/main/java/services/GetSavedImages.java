package services;

import com.google.gson.Gson;
import db.DB;
import db.DBs;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getSavedImages")
public class GetSavedImages extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DB db = DBs.getMongoDB();
        db.loadImages(images -> {

            try {
                Gson gson = new Gson();
                GetSaveImagesMessage imageMessage = new GetSaveImagesMessage(images);
                String imagesMessageJson = gson.toJson(imageMessage);

                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                out.print(imagesMessageJson);
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();
           //     resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                  resp.setStatus(HttpServletResponse.SC_OK);
            }
        });
    }
}
