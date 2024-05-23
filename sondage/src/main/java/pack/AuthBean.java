package pack;
import modele.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class AuthBean {

    @PersistenceContext
    private EntityManager em;

    public boolean authenticate(String mail, String mdp) {
        try {
            TypedQuery<Personne> query = em.createQuery(
                "SELECT u FROM User u WHERE u.mail = :mail AND u.mdp = :mdp", Personne.class);
            query.setParameter("mail", mail);
            query.setParameter("mdp", mdp);
            
            Personne user = query.getSingleResult();
            return user != null;
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    public void addUser(String mail, String mdp) {
        try {
            Personne user = new Personne();
            user.setEmail(mail);
            user.setPassword(mdp);
            em.persist(user);
            System.out.println("User added successfully: " + mail);
        } catch (Exception e) {
            System.out.println("Failed to add user: " + e.getMessage());
            throw e;
        }
    }
}
