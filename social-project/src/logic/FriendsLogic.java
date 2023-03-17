package logic;

import entity.Friends;
import entity.User;
import util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FriendsLogic {
    private User user;
    private MyFileUtil<Friends> friendsMyFileUtil = new MyFileUtil<>();
    private List<Friends> friendsWait;
    private List<Friends> friendsList;


    public FriendsLogic(User user) {
        this.user = user;

        List<Friends> friendsWaitFile = friendsMyFileUtil.readDataFromFile("change.dat");
        friendsWait = friendsWaitFile == null ? new ArrayList<>() : friendsWaitFile;

        List<Friends> friendsFile = friendsMyFileUtil.readDataFromFile("friends.dat");
        friendsList = friendsFile == null ? new ArrayList<>() : friendsFile;

    }

    public void choiceFriends() {
        menuFriend();
        int temp;
        while (true) {
            try {
                temp = new Scanner(System.in).nextInt();
                if (temp >= 1 && temp <= 3) {
                    break;
                }
                System.out.print("Chức năng từ [1, 3], mời nhập lại");
            } catch (Exception e) {
                System.out.print("Nhập sai định dạng, mời nhập lại: ");
            }
        }
        switch (temp) {
            case 1:
                handleChangeWaitFriends();
                break;
            case 2:
                showFriends();
                break;
            case 3:
                Controller controller = new Controller(user);
                controller.controllerChoice();
                break;

        }
    }

    private void showFriends() {
        List<User> myFriend = null;

        for (int i = 0; i < friendsList.size(); i++) {
            if (friendsList.get(i).getId().equals(user.getId())) {
                myFriend = friendsList.get(i).getUserList();
            }
        }
        if (myFriend == null) {
            System.out.print("Không có lời mời kết bạn nào.");
        } else {
            System.out.println("Danh sách");
            for (int i = 0; i < myFriend.size(); i++) {
                System.out.println("id: " + myFriend.get(i).getId());
                System.out.println("Họ,tên : " + myFriend.get(i).getFullname());
                System.out.println("--------------------");
            }
            String ex;
            System.out.print("Gõ 'exit' để thoát: ");
            do {
                ex = new Scanner(System.in).nextLine();
            } while (!ex.trim().equals("exit"));
        }
        choiceFriends();
    }

    private void handleChangeWaitFriends() {
        List<User> userListFriendsWait = null;
        for (int i = 0; i < friendsWait.size(); i++) {
            if (friendsWait.get(i).getId().equals(user.getId())) {
                userListFriendsWait = friendsWait.get(i).getUserList();
                break;
            }
        }
        if (userListFriendsWait == null) {
            System.out.print("Không có lời mời kết bạn nào.");
        } else {
            System.out.println("Lời mời kết bạn của bạn:");
            for (int i = 0; i < userListFriendsWait.size(); i++) {
                System.out.println("id: " + userListFriendsWait.get(i).getId());
                System.out.println("Họ,tên : " + userListFriendsWait.get(i).getFullname());
                System.out.println("--------------------");
            }
            confirmFriendWait(userListFriendsWait);
        }
        choiceFriends();
    }

    private void confirmFriendWait(List<User> userListFriendsWait) {
        String idFrWait;
        User frWait = null;
        System.out.print("Nhập id của người bạn muốn thao tác(gõ 'exit' để thoát): ");
        while (true) {
            idFrWait = new Scanner(System.in).nextLine();
            for (int i = 0; i < userListFriendsWait.size(); i++) {
                if (idFrWait.trim().equals(userListFriendsWait.get(i).getId())) {
                    frWait = userListFriendsWait.get(i);
                    break;
                }
            }
            if (idFrWait.trim().equals("exit")) {
                choiceFriends();
                break;
            }
            System.out.print("Id " + frWait + " không tồn tại, mời nhập lại: ");
        }
        choiceConfirmFr(frWait);
    }

    private void choiceConfirmFr(User frWait) {
        System.out.println("Bạn có đồng ý kết bạn không? ");
        System.out.println("1. Có");
        System.out.println("2. không");
        System.out.print("Hãy chọn: ");
        int temp;
        while (true) {
            try {
                temp = new Scanner(System.in).nextInt();
                if (temp == 1) {
                    // Thêm bạn vào danh sách bạn bè của họ
                    // Thêm họ vào danh sách bạn bè của bạn
                    addFriendWithList(frWait);
                    // Xóa họ khỏi danh sách chờ lmkb
                    removeFriendWait(frWait);
                    break;
                } else if (temp == 2) {
                    removeFriendWait(frWait);
                }
                System.out.print("Chức năng từ [1, 2], mời nhập lại");
            } catch (Exception e) {
                System.out.print("Nhập sai định dạng, mời nhập lại: ");
            }
        }
    }

    private void removeFriendWait(User frWait) {
        for (int i = 0; i < friendsWait.size(); i++) {
            if (user.getId().equals(friendsWait.get(i).getId())) {
                friendsWait.get(i).getUserList().remove(frWait);
                break;
            }
        }
        friendsMyFileUtil.writeDataFromFile(friendsWait, "change.dat");

    }

    private void addFriendWithList(User frWait) {
        boolean checkFr = true;
        for (int i = 0; i < friendsList.size(); i++) {
            if (frWait.getId().equals(friendsList.get(i).getId())) {
                checkFr = false;
                friendsList.get(i).getUserList().add(user);
//                friendsMyFileUtil.writeDataFromFile(friendsList, "friend.dat");
                break;
            }
        }
        if (checkFr) {
            List<User> userList1 = new ArrayList<>();
            userList1.add(user);
            Friends friends = new Friends(frWait.getId(), userList1);
            friendsList.add(friends);
//            friendsMyFileUtil.writeDataFromFile(friendsList, "friend.dat");
        }

        boolean checkWaitFr = true;
        for (int i = 0; i < friendsList.size(); i++) {
            if (user.getId().equals(friendsList.get(i).getId())) {
                checkWaitFr = false;
                friendsList.get(i).getUserList().add(frWait);
//                friendsMyFileUtil.writeDataFromFile(friendsList, "friend.dat");
                break;
            }
        }
        if (checkWaitFr) {
            List<User> userList1 = new ArrayList<>();
            userList1.add(frWait);
            Friends friends = new Friends(user.getId(), userList1);
            friendsList.add(friends);
        }
        friendsMyFileUtil.writeDataFromFile(friendsList, "friend.dat");

    }


    private void menuFriend() {
        System.out.println("Xin chào " + user.getFullname());
        System.out.println("Mời bạn chọn chức năng");
        System.out.println("1. Xem lời mời kết bạn. ");
        System.out.println("2. Xem danh sách bạn bè. ");
        System.out.println("3. Quay lại. ");
        System.out.print("Hãy chọn: ");
    }

}
