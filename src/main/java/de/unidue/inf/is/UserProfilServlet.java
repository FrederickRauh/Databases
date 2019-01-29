package de.unidue.inf.is;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.domain.Advert;
import de.unidue.inf.is.utils.DBUtil;
import sun.security.pkcs11.Secmod;

public final class UserProfilServlet extends HttpServlet {

    private static List<Advert> userAdvertList = new ArrayList<>();
    private static List<Advert> userCartList = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session = request.getSession();

            if(session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true){
                String n = (String) session.getAttribute("detailUser");
                User user = this.loadUser(request, response, n);
                userAdvertList = new ArrayList<>();
                userCartList = new ArrayList<>();

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                String sql = "SELECT * FROM ANZEIGE WHERE ersteller=?";
                userAdvertList = this.loadAdverts(request, response, sql, 0, user.getUsername(), userAdvertList);
                sql = "SELECT  * FROM KAUFT WHERE benutzername=?";

                ArrayList<Integer> ids = this.loadAdvertIds(request, response, sql, user.getUsername());
                sql = "SELECT * FROM ANZEIGE WHERE id=?";

                if(ids.size() > 0){
                    for(int i = 0; i < ids.size(); i++){
                        this.userCartList = this.loadAdverts(request,response,sql,ids.get(i), user.getUsername(), userCartList);
                    }
                }

                boolean hasAdverts = false;
                boolean hasCart = false;
                request.setAttribute("userAdverts", userAdvertList);
                if(userAdvertList.size() > 0){ hasAdverts = true; }
                request.setAttribute("has", hasAdverts);
                request.setAttribute("cartAdverts", userCartList);
                if(userCartList.size() > 0){hasCart = true;}
                request.setAttribute("cart", hasCart);
                request.setAttribute("user", user);
                request.getRequestDispatcher("user_profil.ftl").forward(request, response);
            }else{
                response.sendRedirect("login");
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("detail?id="+request.getParameter("advertId"));
    }

    /**
     * lädt den User anhand des username
     * @param request
     * @param response
     * @param username
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private User loadUser(HttpServletRequest request, HttpServletResponse response, String username)
            throws ServletException, IOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM BENUTZER WHERE benutzername=?";

        try{
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                String usern = result.getString("benutzername");
                String name = result.getString("name");
                String[] splitStr = name.split("\\s+");
                user = new User(splitStr[0], splitStr[1], usern);
            }

        }catch (SQLException s){s.printStackTrace();}
        catch (ClassNotFoundException c){c.printStackTrace();}

        return user;
    }

    /**
     * lädt die Adverts sowohl eigene Angebote wie auch gekaufte
     * @param request
     * @param response
     * @param sql
     * @param id
     * @param username
     * @param advertList
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private List<Advert> loadAdverts(HttpServletRequest request, HttpServletResponse response, String sql, int id ,String username, List<Advert> advertList)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            if(id == 0){
                preparedStatement.setString(1, username);
            }else{
                preparedStatement.setInt(1, id);
            }
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                String title = result.getString("titel");
                int price = result.getInt("preis");
                String text = result.getString("text");
                String creator = result.getString("ersteller");
                String status = result.getString("status");
                String timeStamp = result.getString("erstellungsdatum");
                int i = result.getInt("id");

                Advert toAdd = new Advert(price, text, title, creator, status, timeStamp);
                toAdd.setId(i);
                advertList.add(toAdd);
            }

        }catch (SQLException s){s.printStackTrace();}
        catch (ClassNotFoundException c){c.printStackTrace();}

        return advertList;
    }

    /**
     * lädt die Ids der zu ladenden Adverts(für gekaufte)
     * @param request
     * @param response
     * @param sql
     * @param username
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private ArrayList<Integer> loadAdvertIds(HttpServletRequest request, HttpServletResponse response, String sql, String username)
            throws ServletException, IOException {
        ArrayList<Integer> advertIds = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                int id = result.getInt("anzeigeID");
               advertIds.add(id);
            }
        }catch (SQLException s){s.printStackTrace();}
        catch (ClassNotFoundException c){c.printStackTrace();}

        return advertIds;
    }
}