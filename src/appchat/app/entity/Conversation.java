package appchat.app.entity;

public class Conversation {
    private int conid;
    private String title;
    private int creatorid;
    private int channelid;
    private String createat;
    private int status;

    public Conversation() {
    }

    public Conversation(int conid, String title, int creatorid, int channelid, String createat, int status) {
        this.conid = conid;
        this.title = title;
        this.creatorid = creatorid;
        this.channelid = channelid;
        this.createat = createat;
        this.status = status;
    }

    public int getConid() {
        return conid;
    }

    public void setConid(int conid) {
        this.conid = conid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(int creatorid) {
        this.creatorid = creatorid;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
