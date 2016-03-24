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
    Date datetime;
    int User_idUser;
    int idFromUser;
    int initiative_idInitiative;
    int deliverable_idDeliverable;


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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getUser_idUser() {
        return User_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        User_idUser = user_idUser;
    }

    public int getIdFromUser() {
        return idFromUser;
    }

    public void setIdFromUser(int idFromUser) {
        this.idFromUser = idFromUser;
    }

    public int getInitiative_idInitiative() {
        return initiative_idInitiative;
    }

    public void setInitiative_idInitiative(int initiative_idInitiative) {
        this.initiative_idInitiative = initiative_idInitiative;
    }

    public int getDeliverable_idDeliverable() {
        return deliverable_idDeliverable;
    }

    public void setDeliverable_idDeliverable(int deliverable_idDeliverable) {
        this.deliverable_idDeliverable = deliverable_idDeliverable;
    }
}
