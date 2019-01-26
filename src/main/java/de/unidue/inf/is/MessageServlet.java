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

import de.unidue.inf.is.domain.Message;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;


public class MessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Message> messageIn = new ArrayList<>();
    private static List<Message> messageOut = new ArrayList<>();

    private static List<List<Message>> messageList = new ArrayList<List<Message>>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        messageIn = new ArrayList<Message>();
        messageOut = new ArrayList<Message>();

        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null){
            User user = User.class.cast(session.getAttribute("user"));

            String sql = "Select * FROM  NACHRICHT WHERE empfaenger = '" + user.getUsername() + "'" ;

            System.out.println(sql);

            try {
                connection = DBUtil.createConnection();
                preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    String message = result.getString("text");
                    String sender = result.getString("absender");
                    String receiver = result.getString("empfaenger");
                    if(receiver.equals(user.getUsername())){
                        messageIn.add(new Message(receiver, sender, message));
                    }else{
                        messageOut.add(new Message(receiver, sender, message));
                    }

                }
                result.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        System.out.println(messageIn.size());
        System.out.println(messageOut.size());

        request.setAttribute("messagesIn", messageIn);
        request.setAttribute("messagesOut", messageOut);



        request.getRequestDispatcher("message.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }
}
