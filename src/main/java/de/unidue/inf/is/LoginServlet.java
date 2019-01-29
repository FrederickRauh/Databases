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

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<User> userList = new ArrayList<>();

    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession session = request.getSession();
        boolean login = false;
        session.setAttribute("login", login);
        request.getRequestDispatcher("login.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        userList = new ArrayList<User>();

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        boolean register = false;
        if(request.getParameter("register") != null) {

            register = true;
        }

        System.out.println("Login in with: " + name + " " + username);

        String sql = "SELECT * FROM BENUTZER";

        boolean login = false;

        if (name != null && name.length() > 0) {
            if (username != null) {
                try {
                    connection = DBUtil.createConnection();
                    if(register){
                        sql = "INSERT INTO BENUTZER(benutzername, name) values (?, ?)";
                        preparedStatement = connection.prepareStatement(sql);
                        String[] splitStr = name.split("\\s+");
                        String newUsername = splitStr[0].charAt(0)+"."+splitStr[1];
                        username = newUsername;
                        preparedStatement.setString(1, newUsername);
                        preparedStatement.setString(2, name);
                        preparedStatement.executeUpdate();

                        login = true;
                    }else {
                        preparedStatement = connection.prepareStatement(sql);
                        ResultSet result = preparedStatement.executeQuery();
                        while (result.next()) {
                            String u = result.getString("benutzername");
                            String n = result.getString("name");
                            String[] splitStr = n.split("\\s+");
                            User toAdd = new User(splitStr[0], splitStr[1], u);
                            userList.add(toAdd);
                            if (result.getString("benutzername").equals(username)) {
                                if (result.getString("name").equals(name)) {
                                    login = true;
                                }
                            }
                        }
                    }
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException c) {
                    c.printStackTrace();
                }
            }
        }
        if (login) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login", true);

            String[] splitStr = name.split("\\s+");
            User user = new User(splitStr[0], splitStr[1], username);

            session.setAttribute("user", user);
            session.setAttribute("users", userList);

            response.sendRedirect("all");
        } else {
            response.sendRedirect("login");
        }

    }
}
