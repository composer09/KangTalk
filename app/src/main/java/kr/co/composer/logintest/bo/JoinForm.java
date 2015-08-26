package kr.co.composer.logintest.bo;

/**
 * Created by composer10 on 2015. 8. 25..
 */
public class JoinForm {
    private String loginId;
    private String password;
    private String passwordConfirm;

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }
}
