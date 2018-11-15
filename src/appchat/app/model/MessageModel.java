package appchat.app.model;

import appchat.app.entity.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageModel {

    public boolean insert(Message message) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO messages(senderid, content, created_at) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,message.getSenderid());
            ps.setString(2,message.getContent());
            ps.setString(3, message.getCreatedat());
            ps.execute();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }
}
