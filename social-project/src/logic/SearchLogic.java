package logic;

import entity.Friends;
import entity.SearchUser;
import entity.User;
import util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SearchLogic {
    private List<User> userList;
    private User user;
    private List<SearchUser> searchUsers;
    private final MyFileUtil<Friends> friendsMyFileUtil = new MyFileUtil<>();
    private final MyFileUtil<User> userMyFileUtil = new MyFileUtil<>();

    private List<Friends> friendsList;

    public SearchLogic(User user) {
        this.user = user;

        List<User> userFile = userMyFileUtil.readDataFromFile("user.dat");
        userList = userFile == null ? new ArrayList<>() : userFile;
        List<Friends> friendsFile = friendsMyFileUtil.readDataFromFile("change.dat");
        friendsList = friendsFile == null ? new ArrayList<>() : friendsFile;
    }

    private void menuSearch() {
        System.out.println("Mời bạn chọn các chức năng.");
        System.out.println("1. Xem thêm.");
        System.out.println("2. Chọn bạn bè.");
        System.out.println("3. Quay lại");
        System.out.print("Chọn chức năng: ");
    }

    public void searchChoice() {
        searchForFavorite();
        menuSearch();
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
        while (true) {
            switch (temp) {
                case 1:
                    showUser();
                    break;
                case 2:
                    choiceFriend();
                    break;
                case 3:
                    Controller controller = new Controller(user);
                    controller.controllerChoice();
                    break;

            }
        }

    }

    private void searchForFavorite() {
        searchUsers = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            SearchUser searchUser = new SearchUser(userList.get(i), totalFavorite(userList.get(i)));
            searchUsers.add(searchUser);
        }
        sortFriendSearch();
    }

    private void sortFriendSearch() {
        for (int i = 0; i < searchUsers.size() - 1; i++) {
            for (int j = i + 1; j < searchUsers.size(); j++) {
                SearchUser temp = searchUsers.get(i);
                searchUsers.set(i, searchUsers.get(j));
                searchUsers.set(j, temp);
            }
        }
        showUser();
    }

    private void showUser() {
        for (SearchUser searchUser : searchUsers) {
            System.out.println("+ ID= " + searchUser.getUser().getId());
            System.out.println("+ Tên: " + searchUser.getUser().getFullname());
        }
    }
    private int totalFavorite(User userx) {
        int total = 0;
        for (int i = 0; i < user.getFavoriteTypes().size(); i++) {
            if (userx.getFavoriteTypes().contains(user.getFavoriteTypes().get(i))) {
                total += total;
            }
        }
        return total;
    }

    private void choiceFriend() {
        String userId;
        User userAddFr;
        System.out.print("Mời bạn nhập mã ngươời bạn muốn gửi lmkb: ");
        while (true) {
            userId = new Scanner(System.in).nextLine();
            userAddFr = findUserById(userId);
            if (userAddFr != null) {
                break;
            }
            System.out.print("không có mã " + userId + ", vui lòng nhập lại: ");
        }
        boolean checkFr = true;
        for (int i = 0; i < friendsList.size(); i++) {
            if (userAddFr.getId().equals(friendsList.get(i).getId())){
                checkFr = false;
                friendsList.get(i).getUserList().add(user);
                friendsMyFileUtil.writeDataFromFile(friendsList, "change.dat");
                break;
            }
        }
        if(checkFr){
            List<User> userList1 = new ArrayList<>();
            userList1.add(user);
            Friends friends = new Friends(userAddFr.getId() ,userList1);
            friendsList.add(friends);
            friendsMyFileUtil.writeDataFromFile(friendsList, "change.dat");
        }
        System.out.println("-------------------------------");
        searchChoice();
    }

    public User findUserById(String userId) {
        for (int i = 0; i < userList.size(); i++) {
            if (Objects.equals(userId, userList.get(i).getId())) {
                return userList.get(i);
            }
        }
        return null;
    }
}