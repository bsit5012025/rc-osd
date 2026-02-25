package org.rocs.osd.model.login;

public class Login {

    private long id;

    private String username;

    private String password;

    public Login() {
    }

    // Constructor with all values
    public Login(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //This will get the Username
    public String getUsername() {
        return username;
    }

    //This will set the Enrollment ID
    public void setUsername(String username) {
        this.username = username;
    }

    //This will get the Password
    public String getPassword() {
        return password;
    }

    //This will set the Password
    public void setPassword(String password) {
        this.password = password;
    }
}
