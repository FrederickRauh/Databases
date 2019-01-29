package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

public final class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<User> userList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        userList = new ArrayList<>();

        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true) {
            User user = User.class.cast(session.getAttribute("user"));
            userList = (List<User>) session.getAttribute("users");

            if (userList.size() == 0) {
                String sql = "SELECT * FROM BENUTZER";

                try {
                    connection = DBUtil.createConnection();
                    preparedStatement = connection.prepareStatement(sql);
                    ResultSet result = preparedStatement.executeQuery();
                    while (result.next()) {
                        String username = result.getString("benutzername");
                        String name = result.getString("name");
                        String[] splitStr = name.split("\\s+");
                        User toAdd = new User(splitStr[0], splitStr[1], username);
                        userList.add(toAdd);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException c) {
                    c.printStackTrace();
                }
            }
            request.setAttribute("users", userList);
            request.getRequestDispatcher("user.ftl").forward(request, response);
        }else{
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true){
            String toUser = (String) request.getParameter("username");
            session.setAttribute("toUser", toUser);

            if(request.getParameter("loadProfil") != null){
                session.setAttribute("detailUser", request.getParameter("username"));
                response.sendRedirect("userProfil");
            }else {
                response.sendRedirect("send");
            }
        }else{
            response.sendRedirect("login");
        }
    }
}
