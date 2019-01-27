package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.Comment;

import de.unidue.inf.is.domain.Advert;
import de.unidue.inf.is.utils.DBUtil;

public class InseratorDetailsServlet extends HttpServlet {

    private String anzeigeID;
    private String benutzername = "m.sven";// userID
    private String htmlResponse;
    private static List<Comment> commentList = new ArrayList<>();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String deleteOption = "visibility: hidden";
        String editOption = "visibilty: hidden";
        String buyOption = "visibility: visible";
        String zuUser_profil = "";

        if (anzeigeID == null) {
            anzeigeID = request.getParameter("param");
        }
        htmlResponse = "";

        Connection con = null;
        PreparedStatement stm = null;
        String sql = "SELECT a.id as ID, a.titel AS Titel, a.text AS Text, a.preis AS Preis, a.benutzername AS Benutzername, a.status as Status, "
                + "FROM Anzeige a " + "JOIN Benutzer b ON a.ersteller = b.benutzername " + "WHERE a.id = " + anzeigeID;

        try {
            con = DBUtil.getConnection("project");
            stm = con.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                String titel = rs.getString("Titel");
                String text = rs.getString("Text");
                double preis = rs.getDouble("Preis");
                String ersteller = rs.getString("Ersteller");
                Timestamp erstellungsdatum = rs.getTimestamp("Erstellungsdatum");
                int id = rs.getInt("ID");
                if (id == Integer.parseInt(anzeigeID) && benutzername.equals(ersteller)) {
                    deleteOption = "visibility: visible;";
                    editOption = "visibility: visible";
                    buyOption = "visibility: hidden";
                }


                // ToDo Titel, Erstellungsdatum Benutzername des Anbieters (Verkäufer), Beschreibung, und Preis sollen angezeigt werden
                htmlResponse += "<div>" + titel + "<br> <br>" + ersteller + "<br> <br>" + ":<p>" + text + "</p>" + "<br> <br>" + preis + "</div>";

            }
            rs.close();
            con.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //request.setAttribute("idP", anzeigeID);
        //anzeigeID = null;
        request.setAttribute("deleteOption", deleteOption);
        request.setAttribute("editOption", editOption);
        request.setAttribute("buyOption", buyOption);
        request.setAttribute("titel", htmlResponse);
        request.setAttribute("text", htmlResponse);
        request.setAttribute("preis", htmlResponse);
        request.setAttribute("erstellungsdatum", htmlResponse);
        request.setAttribute("benutzername", zuUser_profil);
        request.setAttribute("comments", commentList);
        request.getRequestDispatcher("anzeige_details.ftl").forward(request, response);

        //um Kommentare aufzulisten

        commentList = new ArrayList<Comment>();
        if (request.getAttribute("comments") != null) { // wie kann hier überprüft werden, ob es Kommentare zu der Anzeige gibt

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            String sql2 = "Select ID as id, TEXT AS text, Erstellungsdatum as erstellungsdatum FROM  Kommentar";

            try {
                connection = DBUtil.createConnection();
                preparedStatement = connection.prepareStatement(sql2);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    int id = result.getInt("id");
                    String text = result.getString("text");
                    String erstellungsdatum = result.getString("erstellungsdatum");
                    // ist abstract? Comment toAdd = new Comment(id, text, erstellungsdatum);
                    //commentList.add(toAdd);
                }
                result.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            request.setAttribute("comments", commentList);
            request.getRequestDispatcher("anzeige_detais.ftl").forward(request, response);
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Boolean deleted = false;
        anzeigeID = request.getParameter("anzeigeID");

        // Kaufen Button, Status muss nicht geändert werden, da ein trigger existiert
        // Eintrag aus parent table wird automatisch gelöscht, da on delete cascade in kauft benutzt wird

        if (request.getParameter("Kaufen") != null) {
            Connection con = null;
            String sql = "INSERT INTO Kauft (benutzername, anzeigeID)  VALUES (?, 'm.sven')";
            PreparedStatement stm;
            try {
                con = DBUtil.getConnection("project");
                stm = con.prepareStatement(sql);
                stm.executeUpdate();
                con.close();

                // TODO: Seite Aktualisieren
                request.getRequestDispatcher("anzeige_details.ftl").forward(request, response);

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            //   Edit Button
        } else if (request.getParameter("Editieren") != null) {
            request.getRequestDispatcher("anzeige_editieren.ftl").forward(request, response);

            //   Benutzername Button
        } else if (request.getParameter("zuUser_profil") != null) {
            request.getRequestDispatcher("user_profil.ftl").forward(request, response);


            // Delete button
        } else if (request.getParameter("Löschen") != null) {
            deleted = true;
            String sql = "DELETE FROM Anzeige WHERE id=" + anzeigeID;
            Connection con;
            try {
                con = DBUtil.getConnection("project");
                PreparedStatement stm = con.prepareStatement(sql);
                stm.executeUpdate();
                sql = "DELETE FROM Kommentare WHERE anzeige= " + anzeigeID;
                stm = con.prepareStatement(sql);
                stm.executeUpdate();
                stm.close();
                con.close();
                anzeigeID = null;
                request.getRequestDispatcher("inserator_all.ftl").forward(request, response);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        if (!deleted) {
            doGet(request, response);
        }

        String htmlResponse = " ";
        String input = request.getParameter("createComment");
        if (input.length() <= 280 && input.length() > 0) {
            Connection connection = null;
            String sql = "INSERT INTO KOMMENTAR (id, text, erstellungsdatum) VALUES (?, 'Test')";
            PreparedStatement preparedStatement;
            try {
                connection = DBUtil.getExternalConnection("project");
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, input);
                preparedStatement.executeUpdate();
                connection.close();
            } catch (SQLException sqlE) {
                sqlE.printStackTrace();
            }
            htmlResponse = "<p>Erfolgreich ein Comment erstellt</p>";
        } else {
            if (input.length() > 280) {
                htmlResponse = "<p>Text zu lang, bitte benutze weniger als 280 Zeichen</p>";
            } else {
                htmlResponse = "<p>Du musst schon ein Text eingeben</p>";
            }
        }
        request.setAttribute("answer", htmlResponse);
        request.getRequestDispatcher("inserator_comment.ftl").forward(request, response);
    }
}




