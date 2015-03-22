package controllers.users.dto;

/**
 * Created by alanhawrot on 22.03.15.
 */
public class UserPOST {

    private String email;
    private String password;

    public UserPOST() {
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
}
