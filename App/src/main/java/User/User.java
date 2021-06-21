/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

/**
 *
 * @author User
 */
public class User {
    private int id;
    private String name, password, sevleveltwo;

    public User(String name, String password, String sevleveltwo) {
        this.name = name;
        this.password = password;
        this.sevleveltwo = sevleveltwo;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSevleveltwo() {
        return sevleveltwo;
    }

    public void setSevleveltwo(String sevleveltwo) {
        this.sevleveltwo = sevleveltwo;
    }
    
    
    
}
