package manager;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.*;


@Stateless
public class Facade {
	
	@PersistenceContext
	EntityManager em;
	
	public void creerPersonne(String nom, String prenom, String mail, String password, boolean role, int year, String major, String speciality) {
		
		if(!role) {
			Etudiant etudiant = new Etudiant(nom,prenom,mail,password,year, major, speciality);
			em.persist(etudiant);
		} else {
			Professeur professeur = new Professeur(nom,prenom,mail,password);
			em.persist(professeur);
		}
		
		
	}
	
	public void creerQuestion(String questions) {
		Question question = new Question(questions);
		em.persist(question);
	}
	
	public void creerReponse(String reponses) {
		Reponse reponse = new Reponse(reponses);
		em.persist(reponse);
	}
	
	public void creerGroupe(String nom) {
		Groupe groupe= new Groupe(nom);
		em.persist(groupe);
	}
	
	
	public void creerSondage(List<Question> questions, List<Groupe> groupes) {
		Sondage sondage = new Sondage(groupes,questions);
		em.persist(sondage);
	}

	public void creerHistorique(String nom, int numero_sondage) {
		Historique historique = new Historique(nom,numero_sondage);
		em.persist(historique);
	}
	
	public void creerCommentaire(String commentaire, int auteur) {
		Commentaire com = new Commentaire(commentaire,auteur);
		em.persist(com);
	}
}
