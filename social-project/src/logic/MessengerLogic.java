package logic;

import entity.Conversation;
import entity.Friends;
import entity.Messenger;
import entity.User;
import util.MyFileUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MessengerLogic {
    private User user;
    private MyFileUtil<Friends> friendsMyFileUtil = new MyFileUtil<>();
    private List<Friends> friendsList;
    private List<User> myFriends;
    private MyFileUtil<Conversation> conversationMyFileUtil = new MyFileUtil<>();
    private List<Conversation> conversations;
    private Stack<Messenger> messengers;


    public MessengerLogic(User user) {
        this.user = user;

        List<Friends> friendsFile = friendsMyFileUtil.readDataFromFile("friends.dat");
        friendsList = friendsFile == null ? new ArrayList<>() : friendsFile;

        List<Conversation> conversationFile = conversationMyFileUtil.readDataFromFile("messenger.dat");
        conversations = conversationFile == null ? new ArrayList<>() : conversationFile;
    }

    private void menuMessage() {
        System.out.println("Hãy chọn chức năng: ");
        System.out.println("1. Nhắn tin");
        System.out.println("2. Xem lịch sử cuộc trò chuyện");
        System.out.println("3. Quay lại");
        System.out.print("Hãy chọn: ");
    }

    public void choiceMessenger() {
        menuMessage();
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
                User fr = choiceFr();
                choiceFriendContact(fr);
                break;
            case 2:
                User findFriend = choiceFr();
                showHistoryMessenger(findFriend);
                break;
            case 3:
                Controller controller = new Controller(user);
                controller.controllerChoice();
                break;

        }
    }

    private void showHistoryMessenger(User findFriend) {
        boolean check = true;
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).getContacts().contains(user.getId()) && conversations.get(i).getContacts().contains(findFriend.getId())) {
                messengers = conversations.get(i).getMessengers();
                check = false;
                break;
            }

        }
        if (check) {
            System.out.println("Không có lịch sử cuộc trò chuyện nào!");
            System.out.println("------------------------------");
            choiceMessenger();
        } else {
            int size = Math.min(messengers.size(), 5);
            for (int i = 0; i < size; i++) {
                System.out.println(messengers.get(i));
            }
        }
    }

    private User choiceFr() {
        boolean checkFr = true;
        for (int i = 0; i < friendsList.size(); i++) {
            if (user.getId().equals(friendsList.get(i).getId())) {
                myFriends = friendsList.get(i).getUserList();
                checkFr = false;
                break;
            }
        }
        if (checkFr) {
            System.out.println("Bạn chưa có bạn bè!");
            System.out.println("----------------------");
            Controller controller = new Controller(user);
            controller.controllerChoice();
        }

        System.out.println("Hãy chọn bạn bè của bạn");
        for (int i = 0; i < myFriends.size(); i++) {
            System.out.println("id: " + myFriends.get(i).getId());
            System.out.println("Họ,tên : " + myFriends.get(i).getFullname());
            System.out.println("--------------------");
        }
        String idfrContact;
        User frContact = null;
        System.out.print("Nhập id của người bạn muốn thao tác(gõ 'exit' để thoát): ");
        while (true) {
            idfrContact = new Scanner(System.in).nextLine();
            for (int i = 0; i < myFriends.size(); i++) {
                if (idfrContact.trim().equals(myFriends.get(i).getId())) {
                    frContact = myFriends.get(i);
                    break;
                }
            }
            if (frContact != null) {
                break;
            }
            if (idfrContact.trim().equals("exit")) {
                choiceMessenger();
                break;
            }
            System.out.print("Id " + idfrContact + " không tồn tại, mời nhập lại: ");
        }
        return frContact;
    }

    private void choiceFriendContact(User frContact) {
        List<String> contacts = new ArrayList<>();
        boolean checkfrContact = true;
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).getContacts().contains(user.getId()) && conversations.get(i).getContacts().contains(frContact.getId())) {
                messengers = conversations.get(i).getMessengers();
                checkfrContact = false;
                break;
            }

        }
        if (checkfrContact) {
            contacts.add(frContact.getId());
            contacts.add(user.getId());
            messengers = new Stack<>();
        }
        choiceRole(frContact, contacts);
    }

    private void choiceRole(User frContact, List<String> contacts) {
        System.out.println("Bạn là ai?");
        System.out.println("1. người dùng.");
        System.out.println("2. người đến sau.");
        System.out.print("Hãy chọn: ");
        int temp;
        while (true) {
            try {
                temp = new Scanner(System.in).nextInt();
                if (temp >= 1 && temp <= 2) {
                    break;
                }
                System.out.print("Chức năng từ [1, 2], mời nhập lại");
            } catch (Exception e) {
                System.out.print("Nhập sai định dạng, mời nhập lại: ");
            }
        }
        switch (temp) {
            case 1:
                serverRole(frContact, contacts);
                break;
            case 2:
                clientRole(frContact);
                break;
        }
    }

    private void serverRole(User frContact, List<String> contacts) {
        ArrayList<Socket> clients = new ArrayList<>();

        System.out.println("Khởi động đoạn chatting");
        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            Socket socket = serverSocket.accept();
            System.out.println("Kết nối với " + frContact.getFullname());
            System.out.println("Thoát cuộc trò chuyện 'exit'.");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // đọc
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String s;
                    try {
                        s = dataInputStream.readUTF();
                        System.out.println(frContact.getFullname() + ": " + s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    Messenger messenger = new Messenger(frContact, s);
                    messengers.add(messenger);

                }
            });
            thread.start();
            // ghi
            while (true) {
                System.out.println(user.getFullname() + ": ");

                String mess;
                try {
                    mess = new Scanner(System.in).nextLine();

                    Messenger messenger = new Messenger(user, mess);
                    messengers.add(messenger);

                    dataOutputStream.writeUTF(mess);
                    dataOutputStream.flush();
                    if (mess.trim().equalsIgnoreCase("exit")) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Conversation conversation = new Conversation(contacts, messengers);
        boolean checkfrContact = true;
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).getContacts().contains(user.getId()) && conversations.get(i).getContacts().contains(frContact.getId())) {
                conversations.set(i, conversation);
                checkfrContact = false;
                break;
            }
        }
        if (checkfrContact) {
            conversations.add(conversation);
        }
        conversationMyFileUtil.writeDataFromFile(conversations, "messenger.dat");
        choiceMessenger();

    }

    private void clientRole(User frContact) {

        try (Socket socket = new Socket("localhost", 1234)) {

            System.out.println("Đã kết nối với " + frContact.getFullname());

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // đọc
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String s;
                    try {
                        s = dataInputStream.readUTF();
                        System.out.println(frContact.getFullname() + ": " + s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            // ghi
            while (true) {
                System.out.println(user.getFullname() + ": ");

                String mess = new Scanner(System.in).nextLine();
                if (mess.trim().equalsIgnoreCase("exit")) {
                    socket.close();
                    break;
                }
                try {
                dataOutputStream.writeUTF(mess);
                dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        choiceMessenger();
    }

}
