package br.com.ca.asap.vo;

/**
 * UserVo
 *
 * Represents the identity, and login validation state of an user.
 *
 */
public class UserVo {
    private String name;
    private String password;
    private Boolean validated;

    /**
     * Constructor
     *
     */
    public UserVo(){}

    /**
     * Constructor
     *
     * @param name
     * @param password
     * @param validated
     */
    public UserVo(String name, String password, Boolean validated){
        this.setName(name);
        this.setPassword(password);
        this.setValidated(validated);
    }

    /**
     * Getter/Setter Methods
     *
     */


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}
