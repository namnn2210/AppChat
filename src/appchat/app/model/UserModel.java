package appchat.app.model;

import appchat.app.entity.User;
import javafx.scene.control.Alert;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.HashMap;

public class UserModel {

    private HashMap<String, String> errors = null;

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
}
