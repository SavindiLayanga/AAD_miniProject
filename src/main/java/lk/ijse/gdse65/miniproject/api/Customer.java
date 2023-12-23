package lk.ijse.gdse65.miniproject.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "customer",urlPatterns = "/customer",
        initParams = {
                @WebInitParam(name = "db-user",value = "root"),
                @WebInitParam(name = "db-pw",value = "1234"),
                @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/javaee"),
                @WebInitParam(name = "db-class",value= "com.mysql.cj.jdbc.Driver")
        }
        ,loadOnStartup = 5
)
public class Customer extends HttpServlet {
    Connection connection;
    String SAVE_DATA = "INSERT INTO customer(name,email,city) VALUES (?,?,?)";
    String READ_DATA = "SELECT * FROM customer";

    @Override
    public void init() throws ServletException {
        String userName = getServletConfig().getInitParameter("db-user");
        String password = getServletConfig().getInitParameter("db-pw");
        String url = getServletConfig().getInitParameter("db-url");

        try {
            Class.forName(getServletConfig().getInitParameter("db-class"));
            this.connection =DriverManager.getConnection(url,userName,password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customer_name = req.getParameter("name");
        String email = req.getParameter("email");
        String city = req.getParameter("city");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA);
            preparedStatement.setString(1,customer_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,city);

            if (preparedStatement.executeUpdate() !=0){
                System.out.println("Data Save");
            }else {
                System.out.println("Not Save");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        /Read Data/
        String id = req.getParameter("id");
        if(id == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_DATA);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                String name = resultSet.getString(1);
                String cust_email = resultSet.getString(2);
                String cust_city = resultSet.getString(3);

                System.out.println(name + " " + cust_email + " " + cust_city);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    //update = doPut
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customer_name = req.getParameter("name");
        String email = req.getParameter("email");
        String city = req.getParameter("city");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");

        try {
            String updateQuery = "UPDATE customer SET email=?, city=? WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, customer_name);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated != 0) {
                System.out.println("Data Updated");
            } else {
                System.out.println("Update Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customer_name = req.getParameter("name");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");

        try {
            String deleteQuery = "DELETE FROM customer WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, customer_name);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted != 0) {
                System.out.println("Data Deleted");
            } else {
                System.out.println("Delete Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}