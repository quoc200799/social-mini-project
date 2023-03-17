package logic;

import entity.User;
import util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;

public class InformationLogin {
    private User user;
    private List<User> users;
    private MyFileUtil<User> userMyFileUtil = new MyFileUtil<>();

    public InformationLogin(User user) {
        List<User> userFile = userMyFileUtil.readDataFromFile("user.dat");
        users = userFile == null ? new ArrayList<>() : userFile;
        this.user = user;
    }
    public void myInfor(){
        System.out.println(user);
        System.out.println("Bạn muốn đổi thông tin cá nhân?");
        System.out.println("1. Có.");
        System.out.println("1. Không.");

    }
}
