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
        request.getRequestDispatcher("anzeige_erstellen.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        double price = Integer.parseInt(request.getParameter("price"));
//        String text = request.getParameter("text");
//        String title = request.getParameter("title");
        boolean canPost = true;

//        if(price != 0){
//            System.out.println("Preis ist ok: " + price + "€");
//            if(text.length() > 0 && text.length()<=1000000){
//                System.out.println("Text ist auch ok: " + text);
//                if(title.length() > 0 && title.length() <= 100){
//                    System.out.println("Titel is auch ok: "+ title);
//                    canPost = true;
//                }
//            }
//        }



        String htmlResponse = "";
        if (canPost) {
            Connection connection = null;
            String sql = "INSERT INTO ANZEIGE (titel, text, preis, ersteller, status) VALUES ('Bitte', 'bitte', 123, 'Frederick', 'aktiv')";
            PreparedStatement preparedStatement;
            try {
                connection = DBUtil.getExternalConnection("PROJECT");
                System.out.println("Connected");
                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1, title);
//                preparedStatement.setClob(2, text);
//                preparedStatement.setBigDecimal(3, price);
                System.out.println("PreparedStatement: " + preparedStatement);
                preparedStatement.executeUpdate();
                connection.close();
            } catch (SQLException sqlE) {
                sqlE.printStackTrace();
            }
            htmlResponse = "<p>Erfolgreich ein Inserat erstellt</p>";
        } else {
            htmlResponse = "<p>Geht nicht</p>";
        }
        request.setAttribute("answer", htmlResponse);
        request.getRequestDispatcher("anzeige_erstellen.ftl").forward(request, response);
    }

}
