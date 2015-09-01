package kr.co.composer.logintest.bo;

/**
 * Created by composer10 on 2015. 8. 25..
 */
public class JoinForm {
    private String userId;
    private String password;
    private String passwordConfirm;

    public void setLoginId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }
}
