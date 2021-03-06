package appchat.app.model;

import appchat.app.entity.User;
import javafx.scene.control.Alert;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {

    private HashMap<String, String> errors = null;
    private User user = null;

    public boolean insert(User user) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO users ( username , password, salt, fullname, birthdate, gender, address, email, phone ) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassWord());
            ps.setString(3, user.getSalt());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getBirthDate());
            ps.setInt(6, user.getGender());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getEmail());
            ps.setString(9, user.getPhone());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public User select(String _username) {
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM users WHERE username='" + _username + "'";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String salt = rs.getString("salt");
                String fullName = rs.getString("fullname");
                String birthDate = rs.getString("birthdate");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int status = rs.getInt("status");
                user = new User(id,username,password,salt,fullName,birthDate,gender,address,email,phone,status);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public boolean changePassword(User user, String password) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE users SET users.password = ? WHERE users.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,password);
            ps.setInt(2,user.getId());
            ps.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(User user,String fullname, String email, String phone, String address, String date, int gender) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE users SET users.fullname = ?, users.email = ?, users.phone = ?, users.address = ?, users.birthdate = ?, users.gender = ? WHERE users.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,fullname);
            ps.setString(2,email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5,date);
            ps.setInt(6,gender);
            ps.setInt(7,user.getId());
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean checkExistEmail(String email) {
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM users WHERE email='" + email + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public boolean checkExistUser(String username) {
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM users WHERE username='" + username + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public ArrayList<User> getListUser(ArrayList<Integer> listIdFriend){
        ArrayList<User> listUser = new ArrayList<>();
        try {
            for (int id : listIdFriend) {
                Statement statement = DBConnection.getInstance().getConnection().createStatement();
                String sql = "SELECT * FROM users WHERE id='" + id + "'";
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    int userid = rs.getInt("id");
                    listUser.add(new User(userid,username,password));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listUser;
    }
}
