package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Advert;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

public class InseratorEditServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null){
            User user = User.class.cast(session.getAttribute("user"));
            Advert advert = Advert.class.cast(session.getAttribute("advert"));
            if(user.getUsername().equals(advert.getCreator())) {
                request.getRequestDispatcher("inserator_edit.ftl").forward(request, response);
            }else{
                System.out.println(user.getUsername());
                System.out.println(advert.getCreator());
                response.sendRedirect("detail?id="+advert.getId());
            }
        }else{
            response.sendRedirect("login");
        }
    }
    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null){

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            double price = Integer.parseInt(request.getParameter("price"));
            String text = request.getParameter("text");
            String title = request.getParameter("title");
            if(price != 0){
                if(text.length() > 0 && text.length()<=1000000){
                    if(title.length() > 0 && title.length() <= 100){
                        int id =Integer.parseInt((String) session.getAttribute("advertId"));
                        String sql = "UPDATE ANZEIGE SET TITEL='"+title+"', PREIS='"+price+"', TEXT='"+text+"' WHERE ID="+id;
                        try {
                            connection = DBUtil.createConnection();
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.executeUpdate();
                            connection.close();
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException c) {
                            c.printStackTrace();
                        }
                        String url = "detail?id=" + session.getAttribute("advertId");
                        response.sendRedirect(url);
                    }
                }
            }
        }else{
            response.sendRedirect("login");
        }
    }
}
