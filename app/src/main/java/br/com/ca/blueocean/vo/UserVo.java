package br.com.ca.blueocean.vo;

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
    private String email;
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
     * @param email
     * @param password
     * @param validated
     */
    public UserVo(Integer userId, String name, String email, String password, Boolean validated){
        this.setUserId(userId);
        this.setName(name);
        this.setEmail(email);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
