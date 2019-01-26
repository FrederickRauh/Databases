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

import de.unidue.inf.is.domain.Message;
import de.unidue.inf.is.utils.DBUtil;


public class MessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Message> messageList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "Select TEXT AS message, ABSENDER AS sender, empfaenger AS receiver  FROM  NACHRICHT";
        messageList = new ArrayList<Message>();
        try {
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String message = result.getString("message");
                String sender = result.getString("sender");
                messageList.add(new Message("", sender, message));
            }
            result.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        request.setAttribute("messages", messageList);
        request.getRequestDispatcher("message.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }
}
