package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.utils.DBUtil;

public class InseratorCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("answer", "");
        request.getRequestDispatcher("inserator.ftl").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String htmlResponse = "";
        String input = request.getParameter("createInput");
        if (input.length() <= 280 && input.length() > 0) {
            Connection connection = null;
            String sql = "INSERT INTO INSERATOR (text, creator) VALUES (?, 'Test')";
            PreparedStatement preparedStatement;
            try {
                connection = DBUtil.getConnection("inserator");
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, input);
                preparedStatement.executeUpdate();
                connection.close();
            } catch (SQLException sqlE) {
                sqlE.printStackTrace();
            }
            htmlResponse = "<p>Erfolgreich ein Inserat erstellt</p>";
        } else {
            if (input.length() > 280) {
                htmlResponse = "<p>Text zu lang, bitte benutze weniger als 280 Zeichen</p>";
            } else {
                htmlResponse = "<p>Du musst schon ein Text eingeben</p>";
            }
        }
        request.setAttribute("answer", htmlResponse);
        request.getRequestDispatcher("anzeige_erstellen.ftl").forward(request, response);
    }

}
