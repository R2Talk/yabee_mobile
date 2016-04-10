package br.com.ca.asap.vo;

/**
 * InitiativeVo
 *
 * @author Rodrigo Carvalho
 */
public class InitiativeVo {

    private String initiativeId;
    private String initiativeTitle;
    private String initiativeDescription;

    public InitiativeVo(String id, String title, String description){
        this.initiativeId = id;
        this.initiativeTitle = title;
        this.initiativeDescription = description;
    }

    public String getInitiativeId() {
        return initiativeId;
    }

    public void setInitiativeId(String initiativeId) {
        this.initiativeId = initiativeId;
    }

    public String getInitiativeTitle() {
        return initiativeTitle;
    }

    public void setInitiativeTitle(String initiativeTitle) {
        this.initiativeTitle = initiativeTitle;
    }

    public String getInitiativeDescription() {
        return initiativeDescription;
    }

    public void setInitiativeDescription(String initiativeDescription) {
        this.initiativeDescription = initiativeDescription;
    }

}
