package entity;

import contant.FavoriteType;
import contant.Gender;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String fullname;
    private String address;
    private String phone;
    private Date birthday;
    private String username;
    private String password;
    private Gender gender;
    private ArrayList<FavoriteType> favoriteTypes;


    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return birthday;
    }

    public void setDate(Date birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<FavoriteType> getFavoriteTypes() {
        return favoriteTypes;
    }

    public void setFavoriteTypes(ArrayList<FavoriteType> favoriteTypes) {
        this.favoriteTypes = favoriteTypes;
    }

    public void inforInput() {

        this.id = UUID.randomUUID().toString();
        System.out.print("Nhập họ tên người dùng: ");
        StringBuilder fullname = new StringBuilder("");
        while (true) {
            String name = new Scanner(System.in).nextLine();
            String regex = "\\s+";
            String[] split = name.trim().split(regex);
            for (int i = 0; i < split.length; i++) {
                for (int j = 0; j < split[i].length(); j++) {
                    if (j == 0) {
                        fullname.append(" ").append(String.valueOf(split[i].charAt(j)).toUpperCase());
                    } else {
                        fullname.append(String.valueOf(split[i].charAt(j)).toLowerCase());
                    }
                }
            }
            if (!name.trim().equals("") && split.length > 1) {
                break;
            }
            System.out.print("Họ, tên phải nhiều hơn 2 từ, mời nhập lại:");
        }
        System.out.println("Chọn giới tính: ");
        System.out.println("1. Nam");
        System.out.println("2. Nữ");
        System.out.print("Mời bạn chọn: ");
        int temp;
        while (true) {
            try {
                temp = new Scanner(System.in).nextInt();
                if (temp == 1) {
                    this.gender = Gender.MAN;
                    break;
                } else if (temp == 2) {
                    this.gender = Gender.WOMAN;
                    break;
                }
                System.out.print("Chức năng từ [1, 2], mời nhập lại: ");
            } catch (Exception e) {
                System.out.print("Nhập sai định dạng, mời nhập lại: ");
            }

        }
        this.fullname = fullname.toString().trim();
        System.out.print("Nhập số điện thoại: ");
        String phone;
        while (true) {
            phone = new Scanner(System.in).nextLine();
            if (phone.trim().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                break;
            }
            System.out.print("Số điện thoại không đúng định dạng, mời nhập lại: ");
        }
        this.phone = phone;

        System.out.print("Nhập địa chỉ: ");
        String address;
        while (true) {
            address = new Scanner(System.in).nextLine();
            if (!address.trim().equals("")) {
                break;
            }
            System.out.print("Địa chỉ không được để trống, mời nhập lại: ");
        }
        this.address = address;
        this.birthday = addDate();
        System.out.print("Nhập tên đăng nhập: ");
        String username;
        while (true) {
            username = new Scanner(System.in).nextLine();
            if (username.length() >= 4) {
                break;
            } else {

            }
            System.out.print("Tên đăng nhập hơn 4 kí tự và không bị trùng, mời nhập lại: ");
        }
        this.username = username;

        System.out.print("Nhập mật khẩu(hơn 6 kí tự): ");
        String pass;
        while (true) {
            pass = new Scanner(System.in).nextLine();
            if (pass.length() >= 6 && pass.length() <= 18) {
                break;
            }
            System.out.print("Mật khẩu phải nhiều hơn 6 kí tự, mời nhập lại: ");
        }
        this.password = pass;
        this.favoriteTypes = choiceFavoriteTypes();
    }



    private ArrayList<FavoriteType> choiceFavoriteTypes() {
        ArrayList<FavoriteType> favoriteTypeArrayList = new ArrayList<>();
        System.out.println("Sở thích của bạn là: ");
        System.out.println("1. Đá bóng");
        System.out.println("2. Chơi game");
        System.out.println("3. Code");
        System.out.println("4. Hoa");
        System.out.println("5. Chó mèo");
        System.out.println("6. Nuôi cá");
        System.out.println("7. Âm nhạc");
        System.out.println("8. Hội họa");
        System.out.println("9. Xem phim");
        System.out.println("10. Du lịch");
        System.out.println("0. Thoát");
        int temp;
        while (true) {
            while (true) {
                try {
                    temp = new Scanner(System.in).nextInt();
                    if (temp >= 0 && temp <= 10) {
                        break;
                    }
                    System.out.print("Chức năng từ [0, 10], mời nhập lại");
                } catch (Exception e) {
                    System.out.print("Nhập sai định dạng, mời nhập lại: ");
                }
            }
            if (temp == 0) {
                break;
            }
            switch (temp) {
                case 1:
                    if (favoriteTypeArrayList.contains(FavoriteType.DB)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.DB);
                    }
                    break;
                case 2:
                    if (favoriteTypeArrayList.contains(FavoriteType.GAME)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.GAME);
                    }
                    break;
                case 3:
                    if (favoriteTypeArrayList.contains(FavoriteType.IT)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.IT);
                    }
                    break;
                case 4:
                    if (favoriteTypeArrayList.contains(FavoriteType.HOA)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.HOA);
                    }
                    break;

                case 5:
                    if (favoriteTypeArrayList.contains(FavoriteType.DV)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.DV);
                    }
                    break;
                case 6:
                    if (favoriteTypeArrayList.contains(FavoriteType.NC)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.NC);
                    }
                    break;
                case 7:
                    if (favoriteTypeArrayList.contains(FavoriteType.MUSIC)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.MUSIC);
                    }
                    break;
                case 8:
                    if (favoriteTypeArrayList.contains(FavoriteType.ART)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.ART);
                    }
                    break;
                case 9:
                    if (favoriteTypeArrayList.contains(FavoriteType.XP)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.XP);
                    }
                    break;
                case 10:
                    if (favoriteTypeArrayList.contains(FavoriteType.DL)) {
                        System.out.println("Sở thích này đã tồn tại mời bạn chọn sở thích khác: ");
                    } else {
                        favoriteTypeArrayList.add(FavoriteType.DL);
                    }
                    break;

            }
        }
        return favoriteTypeArrayList;
    }

    private Date addDate() {
        System.out.print("Nhập ngày sinh theo định dạng [dd/MM/yyyy]: ");
        String str;
        while (true) {
            try {
                str = new Scanner(System.in).nextLine();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false);
                Date now = new Date();
                if (format.parse(str).getTime() < now.getTime()) {
                    return format.parse(str);
                }
                System.out.print("Ngày sinh không được lớn hơn ngày hôm nay, mời nhập lại: ");
            } catch (ParseException e) {
                System.out.print("Nhập sai định dạng ngày, mời nhập lại: ");
            }
        }

    }

    @Override
    public String toString() {
        String temp =favoriteTypes.get(0).value;
        for (int i = 1; i < favoriteTypes.size(); i++) {
            temp += ", " + favoriteTypes.get(i).value;
        }
        return "Thông tin {\n" +
                " id='" + id + "' ," + '\n' +
                " Họ, tên ='" + fullname + "' ," + '\n' +
                " Giới tính ='" + gender.value + "' ," + '\n' +
                " Địa chỉ ='" + address + "' ," + '\n' +
                " Số điện thoại ='" + phone + "' ," + '\n' +
                " Sinh nhật =" + birthday + "' ," + '\n' +
                " Tên đăng nhập ='" + username + "' ," + '\n' +
                " Mật khẩu ='" + password + "' ," + '\n' +
                " Sở thích =" + temp + "'" +
                '}';
    }
}
