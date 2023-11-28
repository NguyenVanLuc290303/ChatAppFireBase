package com.example.appchat_mob403.DTO;

public class Chat {
    public String idUser;
    public String content;

    public Chat(String idUser, String content) {
        this.idUser = idUser;
        this.content = content;
    }

    public Chat() {
    }

    public String getIdUser() {
        return idUser;
    }

    public String getContent() {
        return content;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
