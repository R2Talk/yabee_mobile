package br.com.ca.asap.vo;

import java.util.Date;

/**
 * MessageVo
 *
 * Represents a message and identifies users id of sender and receiver.
 *
 * @author Rodrigo Carvalho
 *
 */

public class MessageVo {
    int idMessage;
    String text;
    Date date;
    int idSenderUser;
    int User_idUser;

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdSenderUser() {
        return idSenderUser;
    }

    public void setIdSenderUser(int idSenderUser) {
        this.idSenderUser = idSenderUser;
    }

    public int getUser_idUser() {
        return User_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        User_idUser = user_idUser;
    }
}