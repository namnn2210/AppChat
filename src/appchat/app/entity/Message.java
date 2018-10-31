package appchat.app.entity;

public class Message {
    private int id;
    private int conversationid;
    private int senderid;
    private String content;
    private String createdat;

    public Message() {
    }

    public Message(int id, int conversationid, int senderid, String content, String createdat) {
        this.id = id;
        this.conversationid = conversationid;
        this.senderid = senderid;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
}
