package logic;

import entity.User;
import util.MyFileUtil;

import java.util.List;

public class RegisterLogic {
    private List<User> users;

    private final MyFileUtil<User> userMyFileUtil = new MyFileUtil<>();

    public RegisterLogic(List<User> users) {
        this.users = users;
    }

    public void register() {
        System.out.println("Mời bạn đăng ký tài khoản mới");
        User user = new User();
        user.inforInput();
        System.out.println(user);
        users.add(user);

        userMyFileUtil.writeDataFromFile(users, "user.dat");
    }

}