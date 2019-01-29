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
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Advert;
import de.unidue.inf.is.domain.Comment;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.domain.UserComment;
import de.unidue.inf.is.utils.DBUtil;

public class InseratorDetailServlet extends HttpServlet {

    private static List<Comment> commentList = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true) {
            if (request.getParameter("id") != null) {
                session.setAttribute("advertId", request.getParameter(("id")));
            }
            this.load(request, response, session);

        } else {
            response.sendRedirect("login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true) {

            User user = User.class.cast(session.getAttribute("user"));
            int advertId = Integer.parseInt((String) session.getAttribute("advertId"));
            String url = "detail?id=" + session.getAttribute("advertId");

            if (request.getParameter("buy") != null) {
                this.buy(request, response, user, advertId, url);
            } else if (request.getParameter("edit") != null) {
               response.sendRedirect("edit");
            } else if (request.getParameter("delete") != null) {
                this.delete(request, response, advertId);
            } else if (request.getParameter("comment") != null) {
                this.comment(request, response, session, user, advertId, url);
            } else if(request.getParameter("profil") != null){
                Advert advert = Advert.class.cast(session.getAttribute("advert"));
                String creatorNamae = advert.getCreator();
                session.setAttribute("detailUser", creatorNamae);
                response.sendRedirect("userProfil");
            }
        } else {
            response.sendRedirect("login");
        }
    }

    /**
     * Methode ist für das laden der Daten auf der detail Seite verantwortlich
     * @param request
     * @param response
     * @param session
     * @throws ServletException
     * @throws IOException
     */
    private void load(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Advert advert = null;
        String buyer = "";
        String buyTime = "";
        List<Comment> commentList = new ArrayList<>();
        User user = User.class.cast(session.getAttribute("user"));
        boolean isCreator = false;
        List<UserComment> hasComment = new ArrayList<>();
        if (session.getAttribute("advert") != null) {
            advert = Advert.class.cast(session.getAttribute("advert"));
        }
        /**
         * lädt das Inserat anhand der Id die in den URL Params übergeben wird
         */
        String sql = "Select " +
                "TEXT AS text, TITEL AS title, PREIS AS price, ERSTELLUNGSDATUM AS timeOfCreation, ERSTELLER AS creator, STATUS AS status, ID AS id " +
                "FROM  ANZEIGE WHERE id=" + request.getParameter("id");

        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String text = result.getString("text");
                String title = result.getString("title");
                double price = result.getDouble("price");
                String timeStamp = result.getString("timeOfCreation");
                String creator = result.getString("creator");
                String status = result.getString("status");
                int anzeigeIDhier = result.getInt("id");
                advert = new Advert(price, text, title, creator, status, timeStamp);
                advert.setId(anzeigeIDhier);
            }
            result.close();

            /**
             * falls verkauft wird hier mit angezeigt von wem gekauft
             */
            if(advert.getStatus().equals("verkauft")){
                sql = "SELECT * FROM KAUFT WHERE anzeigeId=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, advert.getId());
                result = preparedStatement.executeQuery();
                while (result.next()) {
                    buyer = result.getString("benutzername");
                    buyTime = result.getString("kaufdatum");
                }
                result.close();
            }

            /**
             * lädt nun alle Verbindungen zwischen den Kommentaren und dem Inserat
             */
            sql = "SELECT * FROM HATKOMMENTAR WHERE ANZEIGEID=" + request.getParameter("id");
            preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                String uName = result.getString("BENUTZERNAME");
                int commentId = result.getInt("KOMMENTARID");
                int advertId = result.getInt("ANZEIGEID");
                UserComment userComment = new UserComment(commentId, uName, advertId);
                hasComment.add(userComment);
            }
            result.close();
            /**
             * lädt nun alle Kommentare aus der Kommentare Tabelle
             */
            for (int i = 0; i < hasComment.size(); i++) {
                sql = "SELECT * FROM KOMMENTAR WHERE ID=" + hasComment.get(i).getCommentId();
                preparedStatement = connection.prepareStatement(sql);
                result = preparedStatement.executeQuery();
                while (result.next()) {
                    String text = result.getString("text");
                    String timeStamp = result.getString("erstellungsdatum");
                    Comment comment = new Comment(hasComment.get(i).getUsername(), text, timeStamp);
                    commentList.add(comment);
                }
                result.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        if (user.getUsername().equals(advert.getCreator())) {
            isCreator = true;
        }
        if (advert != null) {
            session.setAttribute("advert", advert);
            request.setAttribute("advert", advert);
        }
        if (commentList != null) {
            request.setAttribute("comments", commentList);
        }
        request.setAttribute("buyer", buyer);
        request.setAttribute("buyTime", buyTime);
        request.setAttribute("isCreator", isCreator);
        request.getRequestDispatcher("inserator_detail.ftl").forward(request, response);
    }

    /**
     * Methode für das kaufen eines Inserates
     * @param request
     * @param response
     * @param user
     * @param advertId
     * @param url
     * @throws ServletException
     * @throws IOException
     */
    private void buy(HttpServletRequest request, HttpServletResponse response, User user, int advertId, String url)
            throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO KAUFT(benutzername, anzeigeID) values(?, ?)";

        try{
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2,advertId);
            preparedStatement.executeUpdate();
        }catch(SQLException s) {s.printStackTrace();}
        catch(ClassNotFoundException c) {c.printStackTrace();}
        response.sendRedirect(url);
    }

    /**
     * Methode für das löschen eines Inserates
     * @param request
     * @param response
     * @param advertId
     * @throws ServletException
     * @throws IOException
     */
    private void delete(HttpServletRequest request, HttpServletResponse response, int advertId)
            throws ServletException, IOException {

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            String sql = "DELETE FROM ANZEIGE WHERE ID="+advertId;
            try{
                connection = DBUtil.createConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }catch(SQLException e) {e.printStackTrace();}
            catch(ClassNotFoundException c){c.printStackTrace();}
            response.sendRedirect("all");
    }
    /**
     * Methode für das Kommentieren
     * @param request
     * @param response
     * @param session
     * @param user
     * @param advertId
     * @param url
     * @throws ServletException
     * @throws IOException
     */
    private void comment(HttpServletRequest request, HttpServletResponse response, HttpSession session, User user, int advertId, String url)
            throws ServletException, IOException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int id = 0;
        String sql = "";
        String text = request.getParameter("text");

        if (text.length() > 0) {
            try {
                sql = "INSERT INTO KOMMENTAR(text) values (?)";
                connection = DBUtil.createConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, text);
                preparedStatement.executeUpdate();

                sql = "SELECT * FROM KOMMENTAR";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    if (result.getString("text").equals(text)) {
                        id = result.getInt("ID");
                    }
                }
                result.close();
                if (id != 0) {
                    sql = "INSERT INTO HATKOMMENTAR(kommentarID, benutzername, anzeigeID) values (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, user.getUsername());
                    preparedStatement.setInt(3, advertId);
                    preparedStatement.executeUpdate();
                }
            }catch(SQLException s){
                s.printStackTrace();
            }catch(ClassNotFoundException c){
                c.printStackTrace();
            }
        }
        response.sendRedirect(url);
    }
}