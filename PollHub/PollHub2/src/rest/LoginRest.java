package rest;

import java.io.StringReader;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import manager.LoginManager;
import beans.Etudiant;
import manager.Facade;
import beans.Professeur;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginRest {
    
    @EJB
    private LoginManager loginManager;
    
    @EJB
    private Facade facade;

    
    @POST
    @Path("connexion")
    public Response login(String json) {
        System.out.println("login");
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject jsonObject = reader.readObject();
            String mail = jsonObject.getString("mail");
            String password = jsonObject.getString("mdp");
            
            System.out.print(mail);
            boolean emailExists=false;
            Etudiant etudiant = loginManager.checkLoginE(mail, password);
            if (etudiant != null) {
                JsonObject response = Json.createObjectBuilder()
                        .add("prenom", etudiant.getPrenom())
                        .add("nom", etudiant.getNom())
                        .build();
                
                emailExists = true;
                return Response.ok(response).build();
            } 
            
            Professeur professeur = loginManager.checkLoginP(mail, password);
            if (professeur != null) {
                JsonObject response = Json.createObjectBuilder()
                        .add("prenom", professeur.getPrenom())
                        .add("nom", professeur.getNom())
                        .build();
                emailExists = true;
                return Response.ok(response).build();
            }

            // Check if the email exists in either table
        
            if (!emailExists) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Identifiant n'existe pas")
                               .build();
            }
            
            return Response.status(Response.Status.UNAUTHORIZED).build();
            
        } catch (JsonException e) {
            return Response.serverError().entity("Erreur de traitement du JSON.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Une erreur s'est produite.").build();
        }
    }


 
    @POST
    @Path("creation")
    public Response createPeople(String json) {
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject jsonObject = reader.readObject();
            String prenom = jsonObject.getString("prenom");
            String nom = jsonObject.getString("nom");
            String mail = jsonObject.getString("mail");
            String mdp = jsonObject.getString("mdp");

            // Convert 'role' to boolean
            boolean role = jsonObject.getBoolean("role");

            Integer year = null;
            if (jsonObject.containsKey("year") && !jsonObject.isNull("year")) {
                try {
                    year = Integer.parseInt(jsonObject.getString("year"));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid year format: " + jsonObject.getString("year"));
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid year format").build();
                }
            }

            String major = jsonObject.containsKey("major") ? jsonObject.getString("major") : null;
            String specialty = jsonObject.containsKey("specialty") ? jsonObject.getString("specialty") : null;

            // Add debug logs
            System.out.println("Creating person with the following details:");
            System.out.println("Nom: " + nom);
            System.out.println("Prenom: " + prenom);
            System.out.println("Mail: " + mail);
            System.out.println("Mdp: " + mdp);
            System.out.println("Role: " + role);
            System.out.println("Year: " + year);
            System.out.println("Major: " + major);
            System.out.println("Specialty: " + specialty);

            facade.creerPersonne(nom, prenom, mail, mdp, role, year, major, specialty); // Utilisation de la classe Facade pour créer la personne
            return Response.ok("Utilisateur créé avec succès").build();
        } catch (JsonException e) {
            e.printStackTrace();
            return Response.serverError().entity("Erreur de traitement du JSON.").build();
        } catch (ClassCastException e) {
            e.printStackTrace();
            return Response.serverError().entity("Erreur de type de données.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Une erreur s'est produite.").build();
        }
    }

    
}



