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

import de.unidue.inf.is.domain.Advert;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

public class InseratorAllServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Advert> advertList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("login") != null) {
            User user = User.class.cast(session.getAttribute("user"));

            ArrayList<Integer> ids = new ArrayList<>();
            boolean filters = false;

            String sql = "Select TEXT AS text, TITEL AS title, PREIS AS price, ID AS id FROM  ANZEIGE";
            String sqlAdd = "";


            if (request.getParameter("digital") != null) {
                sqlAdd = "SELECT * FROM HATKATEGORIE WHERE KATEGORIE='Digitale Waren'";
                ids = this.loadIds(request, response, sqlAdd, ids);
                filters = true;
            }
            if (request.getParameter("house") != null) {
                sqlAdd = "SELECT * FROM HATKATEGORIE WHERE KATEGORIE='Haus & Garten'";
                ids = this.loadIds(request, response, sqlAdd, ids);
                filters = true;
            }
            if (request.getParameter("fashion") != null) {
                sqlAdd = "SELECT * FROM HATKATEGORIE WHERE KATEGORIE='Mode & Kosmetik'";
                ids = this.loadIds(request, response, sqlAdd, ids);
                filters = true;
            }
            if (request.getParameter("electronic") != null) {
                sqlAdd = "SELECT * FROM HATKATEGORIE WHERE KATEGORIE='Multimedia & Elektronik'";
                ids = this.loadIds(request, response, sqlAdd, ids);
                filters = true;
            }

            advertList = new ArrayList<Advert>();
            if (filters) {
                if (ids.size() != 0) {
                    for (int i = 0; i < ids.size(); i++) {
                        sql = "Select TEXT AS text, TITEL AS title, PREIS AS price, ID AS id FROM  ANZEIGE WHERE ID=" + ids.get(i);
                        advertList = this.loadAdverts(request, response, sql, advertList);
                    }
                } else {
                    advertList = new ArrayList<>();
                }
            } else {
                advertList = this.loadAdverts(request, response, sql, advertList);
            }
            request.setAttribute("adverts", advertList);
            request.getRequestDispatcher("inserator_all.ftl").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private List<Advert> loadAdverts(HttpServletRequest request, HttpServletResponse response, String sql, List<Advert> advertList)
            throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int k = 0;
                double price = result.getDouble("price");
                String text = result.getString("text");
                String title = result.getString("title");
                int id = result.getInt("id");
                Advert toAdd = new Advert(price, text, title, id);
                if(advertList.size() == 0){
                    advertList.add(toAdd);
                }
                for(int i = 0; i < advertList.size(); i++){
                    if(toAdd.getId() != advertList.get(i).getId()){
                        k++;
                    }
                    if(k == advertList.size()){
                        advertList.add(toAdd);
                    }
                }

            }
            result.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return advertList;
    }

    private ArrayList<Integer> loadIds(HttpServletRequest request, HttpServletResponse response, String sql, ArrayList<Integer> ids)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("anzeigeID");
                ids.add(id);
            }
            result.close();
            connection.close();
        } catch (SQLException s) {
            s.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

        return ids;
    }
}
