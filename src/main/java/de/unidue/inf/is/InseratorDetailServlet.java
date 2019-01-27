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

    private String htmlResponse;
    private static List<Comment> commentList = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("login") != null) {
            if(request.getParameter("id") != null){
                session.setAttribute("advertId", request.getParameter(("id")) );
            }
            this.load(request, response, session);

        } else {
            response.sendRedirect("login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null){
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            User user = User.class.cast(session.getAttribute("user"));
            String test = (String) session.getAttribute("advertId");
            int advertId = Integer.parseInt(test);
            String sql = "";
            try{
                connection = DBUtil.createConnection();
                if(request.getParameter("buy") != null){


                    System.out.println("buy");
                }else if(request.getParameter("edit") != null){


                    System.out.println("edit");
                }else if(request.getParameter("delete") != null){


                    System.out.println("Delete");
                }else if(request.getParameter("comment") != null){
                    int id = 0;
                    String text = request.getParameter("text");
                    if(text.length() > 0){
                        sql = "INSERT INTO KOMMENTAR(text) values (?)";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, text);
                        preparedStatement.executeUpdate();

                        sql = "SELECT * FROM KOMMENTAR";
                        preparedStatement = connection.prepareStatement(sql);
                        ResultSet result = preparedStatement.executeQuery();
                        while(result.next()){
                            if(result.getString("text").equals(text)){
                                id = result.getInt("ID");
                            }
                        }
                        result.close();
                        if(id != 0) {
                            sql = "INSERT INTO HATKOMMENTAR(kommentarID, benutzername, anzeigeID) values (?, ?, ?)";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setInt(1, id);
                            preparedStatement.setString(2, user.getUsername());
                            preparedStatement.setInt(3, advertId);
                            preparedStatement.executeUpdate();
                        }
                    }
                }
            }catch (SQLException s){
                s.printStackTrace();
            }catch (ClassNotFoundException c){
                c.printStackTrace();
            }

            String url = "detail?id="+session.getAttribute("advertId");
            response.sendRedirect(url);
        }else{
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
    private void load(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Advert advert = null;
        List<Comment> commentList = new ArrayList<>();
        User user = User.class.cast(session.getAttribute("user"));
        boolean isCreator = false;
        List<UserComment> hasComment = new ArrayList<>();
        if(session.getAttribute("advert") != null){
            advert = Advert.class.cast(session.getAttribute("advert"));
        }
        /**
         * lädt das Inserat anhand der Id die in den URL Params übergeben wird
         */
        String sql = "Select " +
                "TEXT AS text, TITEL AS title, PREIS AS price, ERSTELLUNGSDATUM AS timeOfCreation, ERSTELLER AS creator, STATUS AS status " +
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
                advert = new Advert(price, text, title, creator, status, timeStamp);
            }
            result.close();
            /**
             * lädt nun alle Verbindungen zwischen den Kommentaren und dem Inserat
             */
            sql = "SELECT * FROM HATKOMMENTAR WHERE ANZEIGEID=" + request.getParameter("id");
            preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while(result.next()){
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
            for(int i = 0; i < hasComment.size(); i++){
                sql = "SELECT * FROM KOMMENTAR WHERE ID="+hasComment.get(i).getCommentId();
                preparedStatement = connection.prepareStatement(sql);
                result = preparedStatement.executeQuery();
                while(result.next()){
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

        if (user.getUsername().equals(advert.getCreator())) { isCreator = true;}
        if (advert != null) {
            session.setAttribute("advert", advert);
            request.setAttribute("advert", advert);
        }
        if (commentList != null) {request.setAttribute("comments", commentList); }

        request.setAttribute("isCreator", isCreator);
        request.getRequestDispatcher("inserator_detail.ftl").forward(request, response);
    }
}