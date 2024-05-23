package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private boolean autorisation;

    public Personne() {
    }

    public Personne(String email, String password, boolean userType) {
        this.email = email;
        this.autorisation = userType;
        this.password = password;
    }

    public Long getId() {
        return id;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAutorisation() {
        return autorisation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAutorisation(boolean autorisation) {
        this.autorisation = autorisation;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    


    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }

}
