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
            String sql = "INSERT INTO users ( username , password, confirmpassword, fullname, birthdate, gender, address, email, phone ) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassWord());
            ps.setString(3, user.getConfirmPassword());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getBirthDate());
            ps.setInt(6, user.getGender());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getEmail());
            ps.setString(9, user.getPhone());
            if (!checkExistUser(user.getUserName())) {
                errors = user.isValid();
                if (errors.size() == 0) {
                    ps.execute();
                }
                else {
                    signUpFailedAlert();
                    return false;
                }
            }
            else {
                checkExistAlert();
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void checkExistAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Username existed");
        alert.setHeaderText(null);
        alert.setContentText("Username is existed. Please try another username");
    }

    private void signUpFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Signup Failed");
        alert.setHeaderText(null);
        alert.setContentText("Please fix following errors and try again");
        for (String message : errors.values()) {
            alert.setContentText(message);
        }
        alert.showAndWait();
    }

    public boolean checkExistUser(String username) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM users WHERE username='" + username + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
