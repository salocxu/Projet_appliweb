package modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Adresse
 */
@Entity
public class Sondage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String ville;

  private String rue;

  @ManyToOne
  private Historique owner; /* can be null */

  public Sondage() {}

  public Sondage (String ville, String rue) {
    this.ville = ville;
    this.rue = rue;
  }

  public int getId() {
    return id;
  }

  public String getVille() {
    return ville;
  }

  public String getRue() {
    return rue;
  }

  public Historique getOwner() {
    return owner;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public void setRue(String rue) {
    this.rue = rue;
  }

  public void setOwner(Historique owner) {
    this.owner = owner;
  }
}
