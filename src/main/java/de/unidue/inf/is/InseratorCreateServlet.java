package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

public class InseratorCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true) {
            request.setAttribute("answer", "");
            request.getRequestDispatcher("inserator_create.ftl").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true) {
            User user = User.class.cast(session.getAttribute("user"));

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            int price = 0;
            String text = "";
            String title = "";
            if (request.getParameter("price") != null && request.getParameter("price").length() > 0) {
                price = Integer.parseInt(request.getParameter("price"));
            }
            if (request.getParameter("text") != null) {
                text = request.getParameter("text");
            }
            if (request.getParameter("title") != null) {
                title = request.getParameter("title");
            }

            ArrayList<String> categories = new ArrayList<>();

            if (request.getParameter("digital") != null) {
                categories.add("Digitale Waren");
            }
            if (request.getParameter("house") != null) {
                categories.add("Haus & Garten");
            }
            if (request.getParameter("fashion") != null) {
                categories.add("Mode & Kosmetik");
            }
            if (request.getParameter("electronic") != null) {
                categories.add("Multimedia & Elektronik");
            }


            if (price != 0) {
                if (text.length() > 0 && text.length() <= 1000000) {
                    if (title.length() > 0 && title.length() <= 100) {
                        String sql = "INSERT INTO ANZEIGE(titel, text, preis, ersteller, status) values (?, ?, ? , ?, 'aktiv')";
                        try {
                            connection = DBUtil.createConnection();
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, title);
                            preparedStatement.setString(2, text);
                            preparedStatement.setInt(3, price);
                            preparedStatement.setString(4, user.getUsername());
                            preparedStatement.executeUpdate();
                            connection.close();

                            if (categories.size() > 0) {

                                int id = this.getAdvertId(request, response, title, text, price, user.getUsername());

                                connection = DBUtil.createConnection();
                                for (int i = 0; i < categories.size(); i++) {
                                    sql = "INSERT INTO HATKATEGORIE(anzeigeID, kategorie) values(?,?)";
                                    preparedStatement = connection.prepareStatement(sql);
                                    preparedStatement.setInt(1, id);
                                    preparedStatement.setString(2, categories.get(i));
                                    preparedStatement.executeUpdate();
                                    preparedStatement.close();
                                }
                                connection.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException c) {
                            c.printStackTrace();
                        }
                        response.sendRedirect("all");
                    }
                }
            } else {
                response.sendRedirect("create");
            }
        } else {
            response.sendRedirect("login");
        }
    }

    private int getAdvertId(HttpServletRequest request, HttpServletResponse response, String title, String text, int price, String username)
            throws ServletException, IOException {
        int id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT id FROM Anzeige WHERE titel=? AND preis=? AND ersteller=?";
        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2,price);
            preparedStatement.setString(3, username);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                id = result.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return id;
    }
}
