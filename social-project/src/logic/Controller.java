package logic;

import entity.User;

import java.util.Scanner;

public class Controller {
    private User user;

    public Controller(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void controller() {

    }

    private void menuController() {
        System.out.println("Xin chào " + user.getFullname());
        System.out.println("Mời bạn chọn các chức năng.");
        System.out.println("1. Tìm kiếm bạn bè mới");
        System.out.println("2. Xem danh sách bạn bè");
        System.out.println("3. Xem thông tin cá nhân");
        System.out.println("4. Nhắn tin");
        System.out.println("5. Đăng xuất");
        System.out.print("Chọn chức năng: ");
    }

    public void controllerChoice() {
        menuController();
        int temp;
        while (true) {
            try {
                temp = new Scanner(System.in).nextInt();
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
                SearchLogic searchLogic = new SearchLogic(user);
                searchLogic.searchChoice();
                break;
            case 2:
                FriendsLogic friendsLogic = new FriendsLogic(user);
                friendsLogic.choiceFriends();
                break;
            case 3:
                InformationLogin informationLogin = new InformationLogin(user);
                informationLogin.myInfor();
                break;
            case 4:

                break;
            case 5:
                 LoginLogic loginLogic = new LoginLogic();
                loginLogic.loginChoice();
                break;
        }
    }
}