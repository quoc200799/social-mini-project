package entity;

import java.util.Objects;

public class SearchUser {
    private User user;
    private int totalSameFavorite;

    public SearchUser(User user, int totalSameFavorite) {
        this.user = user;
        this.totalSameFavorite = totalSameFavorite;
    }

    public User getUser() {
        return user;
    }
}