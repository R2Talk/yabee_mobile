package br.com.ca.asap.vo;

/**
 * LoginStatusVo
 *
 */
public class LoginStatusVo {
    private String name;
    private String password;
    private Boolean validated;

    public LoginStatusVo(){}

    public LoginStatusVo(String name, String password, Boolean validated){
        this.setName(name);
        this.setPassword(password);
        this.setValidated(validated);
    }

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
