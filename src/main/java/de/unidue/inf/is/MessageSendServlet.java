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

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null){
            String toUser = (String) session.getAttribute("toUser");
            request.setAttribute("toUser", toUser);
            System.out.println(toUser);
            request.getRequestDispatcher("message_send.ftl").forward(request,response);
        }else{
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null){
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
