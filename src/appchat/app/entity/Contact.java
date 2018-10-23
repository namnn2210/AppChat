package appchat.app.entity;

public class Contact {
    private int contactId;
    private int friendId;

    public Contact() {
    }

    public Contact(int contactId, int friendId) {
        this.contactId = contactId;
        this.friendId = friendId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
