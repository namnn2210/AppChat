package appchat.app.entity;

public class Participant {
    private int id;
    private int conversationid;
    private int userid;

    public Participant() {
    }

    public Participant(int id, int conversationid, int userid) {
        this.id = id;
        this.conversationid = conversationid;
        this.userid = userid;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
