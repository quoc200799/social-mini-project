package logic;

import entity.User;
import util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static sun.security.jgss.GSSUtil.login;

public class LoginLogic {
    private String username;
    private String password;
    private List<User> users;
    private MyFileUtil<User> userMyFileUtil = new MyFileUtil<>();
    private RegisterLogic registerLogic;

    public LoginLogic() {

        List<User> userFile = userMyFileUtil.readDataFromFile("user.dat");
        users = userFile == null ? new ArrayList<>() : userFile;
        registerLogic = new RegisterLogic(users);
    }

    private void menuLogin() {
        System.out.println("=== Chào mừng bạn đến với mang xã hội hẹn hò ===");
        System.out.println("1. Đăng nhập.");
        System.out.println("2. Đăng ký.");
        System.out.println("3. Đổi mật khẩu.");
        System.out.println("4. Thoát.");
        System.out.print("Chọn chức năng: ");
    }

    public void loginChoice() {
        menuLogin();
        int temp;
        while (true) {
            try {
                temp = chooseNumber();
                if (temp >= 1 && temp <= 4) {
                    break;
                }
                System.out.print("Chức năng từ [1, 4], mời nhập lại");
            } catch (Exception e) {
                System.out.print("Nhập sai định dạng, mời nhập lại: ");
            }
        }
        switch (temp) {
            case 1:
                show();
                login();
                break;
            case 2:
                registerLogic.register();
                System.out.println("--------------------");
                login();
                break;
            case 3:
                changePassword();
                break;
            case 4:
                System.exit(0);
                break;
        }
    }

    private void changePassword() {
        if (!checkData()) {
            System.out.println("Chưa có tên người dùng nào mời bạn đăng ký.");
            registerLogic.register();
        }
        String user;
        User newUser = null;
        System.out.print("Nhập username: ");
        while (true) {
            user = new Scanner(System.in).nextLine();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(user)) {
                    newUser = users.get(i);
                }
            }
            if (user.trim().equals("01")) {
                registerLogic.register();
                break;
            }
            System.out.print("Username không tồn tại(nếu chưa có tài khoản gõ 01),mời nhập lại: ");

        }
        System.out.print("Nhập mật khẩu cũ: ");
        String oldPass;
        while (true) {
            oldPass = new Scanner(System.in).nextLine();
            if (newUser.getPassword().equals(oldPass)) {
                break;
            }
            if (user.trim().equals("01")) {
                registerLogic.register();
                break;
            }
            System.out.print("Username không tồn tại(nếu muốn đăng kí tài khoản mới gõ 01),mời nhập lại: ");
        }
        System.out.print("Nhập mật khẩu(hơn 6 kí tự): ");
        String newPass;
        while (true) {
            newPass = new Scanner(System.in).nextLine();
            if (newPass.length() > 6 && newPass.length() <= 18) {
                break;
            }
            System.out.print("Mật khẩu phải nhiều hơn 6 kí tự, mời nhập lại: ");
        }
        newUser.setPassword(newPass);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user)) {
                users.set(i, newUser);
            }
        }
        userMyFileUtil.writeDataFromFile(users, "user.dat");
        System.out.println("----------------------");
        loginChoice();
    }


    private void login() {
        if (!checkData()) {
            System.out.println("Chưa có tên người dùng nào mời bạn đăng ký.");
            registerLogic.register();
        }
        System.out.println("Mời bạn đăng nhập");
        System.out.print("Nhập tên đăng nhập: ");
        String username;
        User userlogin = null;
        while (true) {
            username = chooseString();
            for (int i = 0; i < users.size(); i++) {
                if (username.trim().equals(users.get(i).getUsername())) {
                    userlogin = users.get(i);
                    break;
                }
            }
            if(userlogin != null){
                break;
            }
            if (username.trim().equals("01")) {
                registerLogic.register();
                break;
            }
            System.out.print("Nhập sai username hoặc nếu bạn chưa có tài khoản mời gõ 01, mời nhập lại");
        }
        this.username = username;
        this.password = inputPassword(userlogin);
        loginSuccess(userlogin);

    }

    private void loginSuccess(User userlogin) {
        Controller controller = new Controller(userlogin);
        controller.controllerChoice();
    }

    private String inputUsername(List<User> userList) {
        System.out.print("Nhập tên đăng nhập: ");
        String username;
        while (true) {
            username = chooseString();
            for (int i = 0; i < userList.size(); i++) {
                if (username.trim().equals(userList.get(i).getUsername())) {
                    break;
                }
            }
            if (username.trim().equals("01")) {
                registerLogic.register();
                break;
            }
            System.out.print("Nhập sai username hoặc nếu bạn chưa có tài khoản mời gõ 01, mời nhập lại");
        }
        return username;
    }

    private String inputPassword(User user) {
        System.out.print("Nhập mật khẩu: ");
        String oldPass;
        while (true) {
            oldPass = chooseString();
            if (oldPass.trim().equals(user.getPassword())) {
                break;
            }
            if (oldPass.trim().equals("01")) {
                registerLogic.register();
                break;
            }
            if (oldPass.trim().equals("02")) {
                login();
                break;
            }
            System.out.print("Nhập sai password hoặc nếu bạn chưa có tài khoản mời gõ 01, sai tên đăng nhập gõ 02, mời nhập lại");
        }
        return oldPass;
    }

    public int chooseNumber() {
        return new Scanner(System.in).nextInt();
    }

    public String chooseString() {
        return new Scanner(System.in).nextLine();
    }

    private boolean checkData() {
        boolean checkStaff = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) != null) {
                checkStaff = true;
                break;
            }
        }
        return checkStaff;
    }

    private void show() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }
}