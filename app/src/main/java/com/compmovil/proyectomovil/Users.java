package com.compmovil.proyectomovil;
//Equipo:
// Encinas Torres Estefania
// Peña Camarena Aaron
// Teran Soto Jose Luis
// */
public class Users {
    private String user;
    private String password;

    public Users(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
