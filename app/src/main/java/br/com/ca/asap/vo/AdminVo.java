package br.com.ca.asap.vo;

/**
 * AdminVo
 *
 * Represents the identity of an administrator for reporting purposes
 *
 * @author Rodrigo Carvalho
 */
public class AdminVo {

    private String pmoId;
    private String name;

    public AdminVo(String id, String name){
        this.pmoId = id;
        this.name = name;
    }

    public String getPmoId() {
        return pmoId;
    }

    public void setPmoId(String pmoId) {
        this.pmoId = pmoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
