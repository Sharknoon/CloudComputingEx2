package services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/saveNewImage")
public class SaveNewImage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String imageCaption = req.getParameter("imageCaption");
        String imageDesc = req.getParameter("imageDesc");

        BufferedReader reader = req.getReader();
       // Gson gson = new Gson();

      //  SaveNewImageMessage newImageMessage = gson.fromJson(reader, SaveNewImageMessage.class);

        resp.setStatus(HttpServletResponse.SC_OK);


    }
}
