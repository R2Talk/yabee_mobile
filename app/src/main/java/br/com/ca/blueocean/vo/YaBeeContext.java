package br.com.ca.blueocean.vo;

/**
 * YaBeeContext
 *
 * Identifies the scope of YaBee (Application, Initiative ou Deliverable)
 *
 * Pass as a parameter fou functions that are context dependent as messages and priorities
 *
 * @author Rodrigo Carvalho
 */
public class YaBeeContext {

    private String initiativeId = null;
    private String deliverableId = null;

    public String getInitiativeId() {
        return initiativeId;
    }

    public void setInitiativeId(String initiativeId) {
        this.initiativeId = initiativeId;
    }

    public String getDeliverableId() {
        return deliverableId;
    }

    public void setDeliverableId(String deliverableId) {
        this.deliverableId = deliverableId;
    }

}
