package appchat.app.model;

import appchat.app.entity.Contact;
import appchat.app.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class ContactModel {
    public boolean insert(Contact contact) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO contacts ( contactid , friendid ) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, contact.getContactId());
            ps.setInt(2, contact.getFriendId());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Integer> getListContact (int id){
        ArrayList<Integer> listId = new ArrayList<Integer>();
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM contacts WHERE contactid='" + id + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                listId.add(rs.getInt("friendid"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listId;
    }

    public boolean checkExistContact(Contact contact) {
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM contacts WHERE contactid='" + contact.getContactId() + "' AND friendid='"+contact.getFriendId()+"'";
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
