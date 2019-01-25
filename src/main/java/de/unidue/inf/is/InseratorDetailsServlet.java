package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.utils.DBUtil;

public class InseratorDetailsServlet extends HttpServlet {

    private String anzeigeID;
    private String benutzername = "m.sven";// userID
    private String htmlResponse;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String deleteOption = "visibility: hidden;";
        String editOption = "visibilty: hidden";
        String buyOption = "";
        String zuUser_profil = "";

        if (anzeigeID == null) {
            anzeigeID = request.getParameter("param");
        }
        htmlResponse = "";

        Connection con = null;
        PreparedStatement stm = null;
        String sql = "SELECT a.id as ID, a.titel AS Titel, a.text AS Text, a.preis AS Preis, a.benutzername AS Benutzername, a.status as Status, "
                + "FROM Anzeige a " + "JOIN Benutzer b ON a.ersteller = b.benutzername " + "WHERE i.id = " + anzeigeID; // oder b.name

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
                }


                //Titel, Erstellungsdatum
                // Benutzername des Anbieters (Verkäufer), Beschreibung, und Preis sollen angezeigt werden
                htmlResponse += "<div>" + titel + ersteller + ":<p>" + text + "</p>" + "<br> <br>" + "</div>";
            }
                rs.close();
                con.close();

            } catch(SQLException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            request.setAttribute("idP", anzeigeID);
            anzeigeID = null;
            request.setAttribute("deleteOption", deleteOption);
            request.setAttribute("editOption", editOption);
            request.setAttribute("buyOption", buyOption);
            request.setAttribute("titel", htmlResponse);
            request.setAttribute("text", htmlResponse);
            request.setAttribute("preis", htmlResponse);
            request.setAttribute("erstellungsdatum", htmlResponse);
            request.setAttribute("benutzername", zuUser_profil);
            request.getRequestDispatcher("anzeige_details.ftl").forward(request, response);


        }





    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Boolean deleted = false;
        anzeigeID = request.getParameter("ID");

        // Kaufen Button

        if (request.getParameter("Kaufen") != null) {
            Connection con = null;
            String sql = "INSERT INTO Kauft (benutzername, anzeigeID, kaufdatum)  VALUES ('" + benutzername + "', " + anzeigeID
                    + " , kaufdatum)";
            PreparedStatement stm;
            try {
                con = DBUtil.getConnection("project");
                stm = con.prepareStatement(sql);
                stm.executeUpdate();
                con.close();

                // TODO: Seite Aktualisieren

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            //   Edit Button
        } else if (request.getParameter("Editieren") != null) {
            request.getRequestDispatcher("anzeige_editieren").forward(request, response);

            //   Benutzername Button
        } else if (request.getParameter("zuUser_profil") != null) {
            request.getRequestDispatcher("user_profil").forward(request, response);


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
                anzeigeID= null;
                request.getRequestDispatcher("hauptseite").forward(request, response);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        if(!deleted) {
            doGet(request, response);
        }



    }
}
