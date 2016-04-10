package br.com.ca.asap.vo;

/**
 * UserVo
 *
 * Represents the identity, and login validation state of an user.
 *
 * BEWARE: yabee/hive data bridge object
 *
 * @author Rodrigo Carvalho
 */
public class UserVo {
    private Integer userId;
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
     * @param userId
     * @param name
     * @param password
     * @param validated
     */
    public UserVo(Integer userId, String name, String password, Boolean validated){
        this.setUserId(userId);
        this.setName(name);
        this.setPassword(password);
        this.setValidated(validated);
    }

    /**
     * Getter/Setter Methods
     *
     */

    public Integer getUserId() { return userId;}

    public void setUserId(Integer userId) {this.userId = userId;}

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
