package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Messenger implements Serializable {
    private User user;
    private String time;
    private String content;

    public Messenger(User user, String content) {
        this.user = user;
        this.content = content;
        this.time = addTime();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void chatting(){


    }
    private String addTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
      return   formatter.format(date);
    }

    @Override
    public String toString() {
        return  user.getFullname() +": ( " + time + " )\n" +
                content +'\n';
    }
}
