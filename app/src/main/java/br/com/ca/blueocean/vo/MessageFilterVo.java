package br.com.ca.blueocean.vo;

/**
 * MessageFilterVo
 *
 * Represents a message filter. Attributes different of null must be used as selectors for retrieving messages.
 *
 * @author Rodrigo Carvalho
 *
 */
public class MessageFilterVo {

    int idFromUser; // message received from this user
    int initiative_idInitiative; // messages associated with this initiative
    int deliverable_idDeliverable; // messages associated with this deliverable

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
