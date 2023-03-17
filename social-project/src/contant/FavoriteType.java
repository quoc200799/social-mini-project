package contant;

public enum FavoriteType {
    DB("Đá bóng"),
    GAME("Chơi game"),
    IT("Code"),
    HOA("Hoa"),
    DV("Chó mèo"),
    NC("Nuôi cá"),
    MUSIC("Âm Nhạc"),
    ART("Hội họa"),
    XP("Xem phim"),
    DL("Du Lịch");
    public String value;

    FavoriteType(String value) {
        this.value = value;
    }
}
