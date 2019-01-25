package de.unidue.inf.is;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.db2.jcc.am.SqlException;
import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.utils.DBUtil;

public class InseratorAllServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static List<Anzeige> anzeigeList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "Select TEXT AS text, TITEL AS title, PREIS AS price FROM  ANZEIGE";

        System.out.println(sql);

        try{
            connection = DBUtil.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                double price = result.getDouble("price");
                String text = result.getString("text");
                String title = result.getString("title");
                anzeigeList.add(new Anzeige(price, text, title));
            }
            System.out.println(anzeigeList);
            result.close();
            connection.close();

        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e1){
            e1.printStackTrace();
        }



        request.setAttribute("anzeigen", anzeigeList);
        request.getRequestDispatcher("inserator_all.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
