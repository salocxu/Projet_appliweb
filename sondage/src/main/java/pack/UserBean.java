package pack;
import modele.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    public void createUser(String email, String password, boolean userType) {
        Personne user = new Personne(email, password, userType);
        em.persist(user);
    }
}
