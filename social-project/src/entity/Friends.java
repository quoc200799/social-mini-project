package entity;

import java.util.List;

public class Friends {
    private String id;
    private List<User> userList;

    public Friends(String id, List<User> userList) {
        this.id = id;
        this.userList = userList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
