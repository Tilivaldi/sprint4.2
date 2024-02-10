package db;

import model.Item;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DbManager {
    private static Connection connection;

    static{
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/BitlabDB-first",
                    "postgres",
                    "123456"
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Item> getItems(){
        List <Item> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from items"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Item item = new Item();
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setPrice(resultSet.getDouble("price"));
                items.add(item);

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return items;
    }

    public static String auth(String email, String password){
        User user = getUserByEmail(email);
        if (user==null){
            return "emailError";
        }
        if (!Objects.equals(user.getPassword(),password)){
            return "passwordError";
        }
        return "success";
    }

    public static User getUserByEmail(String email){
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from users" +
                            "where EMAIL = ?"
            );
            statement.setString(1,email);
            var resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setEmail(email);
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("FULL_NAME"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
