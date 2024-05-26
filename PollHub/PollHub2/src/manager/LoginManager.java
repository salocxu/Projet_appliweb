package manager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import beans.Etudiant;
import beans.Professeur;

@Stateless
public class LoginManager {

    @PersistenceContext
    private EntityManager em;

    public Etudiant checkLoginE(String mail, String mdp) {
        try {
            return em.createQuery("SELECT p FROM Personne p WHERE p.mail = :mail AND p.mdp = :mdp", Etudiant.class)
                     .setParameter("mail", mail)
                     .setParameter("mdp", mdp)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void createPeople(Etudiant personne) {
        em.persist(personne);
    }
    
    public Professeur checkLoginP(String mail, String mdp) {
        try {
            return em.createQuery("SELECT p FROM Personne p WHERE p.mail = :mail AND p.mdp = :mdp", Professeur.class)
                     .setParameter("mail", mail)
                     .setParameter("mdp", mdp)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
