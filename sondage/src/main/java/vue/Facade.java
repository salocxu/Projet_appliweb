package vue;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import modele.Historique;
import modele.Sondage;

/**
 * Facade
 */
@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;

  public void ajoutPersonne(String nom, String prenom) {
    Historique personne = new Historique(prenom, nom);
    em.persist(personne);
  }

  public void ajoutAdresse(String rue, String ville) {
    Sondage adresse = new Sondage(ville, rue);
    em.persist(adresse);
  }

  public Collection<Historique> listePersonnes() {
    return em.createQuery("SELECT p FROM Personne p",
        Historique.class).getResultList();
  }

  public Collection<Sondage> listeAdresses() {
    return em.createQuery("SELECT a FROM Adresse a",
        Sondage.class).getResultList();
  }

  public void associer(int personneId, int adresseId) {
    Historique personne = em.find(Historique.class, personneId);
    Sondage adresse = em.find(Sondage.class, adresseId);
    if (!(adresse == null) && !(personne == null)) {
      adresse.setOwner(personne);
    } else {
      throw new RuntimeException("adresse (" + adresse + ") ou personne (" + personne + ") est null");
    }
  }

  public HashMap<Historique, Set<Sondage>> lister() {
    HashMap<Historique, Set<Sondage>> assoc = new HashMap<>();
    for (Historique p : listePersonnes()) {
      assoc.put(p, new HashSet<>(p.getAdresses()));
    }
    return assoc;
  }
}
