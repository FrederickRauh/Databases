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

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.UserStore;
import de.unidue.inf.is.utils.DBUtil;


/**
 * Einfaches Beispiel, das die Verwendung des {@link UserStore}s zeigt.
 */
public final class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<User> userList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "Select BENUTZERNAME AS username, NAME AS name, EINTRITTSDATUM AS timeOfCreation  FROM  BENUTZER";

        userList = new ArrayList<User>();

        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String username = result.getString("username");
                String name = result.getString("name");
                String[] splitStr = name.split("\\s+");
                User toAdd = new User(splitStr[0], splitStr[1], username);
                userList.add(toAdd);
            }
            result.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        request.setAttribute("users", userList);
        request.getRequestDispatcher("user.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String firstname = request.getParameter("firstName").toLowerCase();
        String lastname = request.getParameter("lastName").toLowerCase();
        String username = "";

        boolean canPost = false;

        if (firstname.length() > 0 && firstname.length() <= 50) {
            if (lastname.length() > 0 && lastname.length() <= 50) {
                username = lastname.substring(0,1) + "." + firstname;
                this.userList.add(new User(firstname, lastname, username));
                canPost = true;
            }
        }

        String htmlResponse = "";
        if(canPost){
        String sql = "INSERT INTO BENUTZER(benutzername, name) values (?, ?)";
        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, firstname + " " + lastname);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        htmlResponse = "<p>Erfolgreich ein Benutzer erstellt</p>";
    } else

    {
        htmlResponse = "<p>Geht nicht</p>";
    }
        request.setAttribute("answer" ,htmlResponse);
        request.setAttribute("users" ,userList);
        request.getRequestDispatcher("user.ftl").

    forward(request, response);
}
}
