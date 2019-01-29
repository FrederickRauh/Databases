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


public class MessageSendServlet extends HttpServlet {

    private static List<Message> ownMessages = new ArrayList<>();
    private static List<Message> externMessages = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true){
            String toUser = (String) session.getAttribute("toUser");
            User user = User.class.cast(session.getAttribute("user"));
            ownMessages = new ArrayList<>();
            externMessages = new ArrayList<>();

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            String sql = "SELECT * FROM NACHRICHT WHERE ABSENDER=? AND EMPFAENGER=?";

            try{
                connection = DBUtil.createConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, toUser);
                preparedStatement.setString(2,user.getUsername());
                ResultSet result = preparedStatement.executeQuery();
                while(result.next()){
                    String message = result.getString("text");
                    String sender = result.getString("absender");
                    String receiver = result.getString("empfaenger");
                    int y = result.getInt("id");
                    Message msg = new Message(receiver, sender, message, y);
                    externMessages.add(msg);
                }
                result.close();

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, toUser);
                ResultSet result2 = preparedStatement.executeQuery();
                while(result2.next()){
                    String message = result2.getString("text");
                    String sender = result2.getString("absender");
                    String receiver = result2.getString("empfaenger");
                    int x = result2.getInt("id");
                    Message msg = new Message(receiver, sender, message, x);
                    ownMessages.add(msg);
                }
                result.close();
                preparedStatement.close();
                connection.close();
            }catch(SQLException s){ s.printStackTrace();}
            catch(ClassNotFoundException c){c.printStackTrace();}

            List<Message> msgs =  new ArrayList<>();
            boolean hasMessages = false;

            if(externMessages.size() > 0){
                for(int i = 0; i < externMessages.size(); i++){
                    msgs.add(externMessages.get(i));
                }
            }
            if(ownMessages.size() > 0){
                for(int i = 0; i < ownMessages.size(); i++){
                    msgs.add(ownMessages.get(i));
                }
            }
            if(msgs.size()> 0){
                hasMessages = true;
                for(int i = 0; i < msgs.size()-1; i++){
                    for(int j = 1; j< msgs.size(); j++){
                        if(msgs.get(i).getId() > msgs.get(j).getId()){
                            Message storage = msgs.get(i);
                            msgs.set(i, msgs.get(j));
                            msgs.set(j, storage);
                        }
                    }
                }

                request.setAttribute("msgs", msgs);
            }
            request.setAttribute("hasMessages", hasMessages);
            request.setAttribute("toUser", toUser);
            request.getRequestDispatcher("message_send.ftl").forward(request,response);
        }else{
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null  && (boolean )session.getAttribute("login") == true){
            String toUser = (String) session.getAttribute("toUser");
            User user = User.class.cast(session.getAttribute("user"));
            String msg = request.getParameter("message");
            if(msg.length() > 0){
                Connection connection = null;
                PreparedStatement preparedStatement = null;

                String sql = "INSERT INTO NACHRICHT(TEXT, ABSENDER, EMPFAENGER) values(?, ?, ?)";
                try{
                    connection = DBUtil.createConnection();
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, msg);
                    preparedStatement.setString(2, user.getUsername());
                    preparedStatement.setString(3, toUser);
                    preparedStatement.executeUpdate();
                }catch(SQLException s){ s.printStackTrace();}
                catch(ClassNotFoundException c){c.printStackTrace();}
                response.sendRedirect("message");
            }else{
                request.getRequestDispatcher("message_send.ftl").forward(request, response);
            }
        }else{
            response.sendRedirect("login");
        }
    }
}
