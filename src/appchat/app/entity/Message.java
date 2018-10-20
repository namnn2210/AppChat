package appchat.app.entity;

public class Message {
    private int id;
    private int conversationid;
    private int senderid;
    private int receiverid;
    private String message;
    private String createdat;

    public Message() {
    }

    public Message(int id, int conversationid, int senderid, int receiverid, String message, String createdat) {
        this.id = id;
        this.conversationid = conversationid;
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.message = message;
        this.createdat = createdat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationid() {
        return conversationid;
    }

    public void setConversationid(int conversationid) {
        this.conversationid = conversationid;
    }

    public int getSenderid() {
        return senderid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public int getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(int receiverid) {
        this.receiverid = receiverid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
}
