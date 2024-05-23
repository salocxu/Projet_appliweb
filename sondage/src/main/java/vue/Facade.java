package vue;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import modele.Historique;
import modele.Sondage;
import modele.Personne;
import modele.Groupe;
import modele.Question;
import modele.Reponse;
import modele.Commentaire;


/**
 * Facade
 */
@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;


  public void ajoutPersonne(String nom, String prenom, String mail, String mdp) {
    Personne personne = new Personne(prenom, nom, mail, mdp);
    em.persist(personne);
  }
  //creer les differents groupes 
  public void creationGroupe(String nom) {
    Groupe groupe = new Groupe(nom);
    em.persist(groupe);
  }
  // ajouter personne par peronnse dans le groupe
  public void ajouterGroupe(int personneId, int groupeId) {
    Personne personne = em.find(Personne.class, personneId);
    Groupe groupe = em.find(Groupe.class, groupeId);
    if (!(groupe == null) && !(personne == null)) {
      groupe.getPersonnes().add(personne);
    } else {
      throw new RuntimeException("groupe (" + groupe + ") ou personne (" + personne + ") est null");
    }
  }

  public void ajoutSondage(List<Groupe> groupes, List<Question> questions) {
    Sondage sondage = new Sondage(groupes, questions);
    em.persist(sondage);
  }

  public void ajoutQuestion(String question) {
    Question q = new Question(question);
    em.persist(q);
  }
  // voir si mettre en public pour que que la question puisse ajouter 
  public void ajoutReponse(String reponse, Question question) {
    Reponse r = new Reponse(reponse, question);
    em.persist(r);
  }

  public void ajoutCommentaire(String commentaire, String auteur) {
    Commentaire c = new Commentaire(commentaire, auteur);
    em.persist(c);
  }

  public void historique(String nom,int numero_sondage) {
    Historique personne = new Historique(nom, numero_sondage);
    em.persist(personne);
  }
  public boolean aRepondu(int id_personne, int id_sondage) {
    Personne personne = em.find(Personne.class, id_personne);
    Sondage sondage = em.find(Sondage.class, id_sondage);
    if (personne == null || sondage == null) {
      throw new RuntimeException("personne (" + personne + ") ou sondage (" + sondage + ") est null");
    }
    for (Groupe groupe : sondage.getGroupe()) {
      if (groupe.getPersonnes().contains(personne)) {
        return true;
      }
    }
    return false;
  }

  /// faire en sorte de szvoir si une personne a repondu au sondage et creer une liste
}
