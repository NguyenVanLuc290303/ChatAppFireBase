package com.example.appchat_mob403.DTO;

public class UserCurrent {
    private static UserCurrent instance;
    private User currentUser;

   private UserCurrent (){

   }

    public static synchronized UserCurrent getInstance() {
        if (instance == null) {
            instance = new UserCurrent();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
