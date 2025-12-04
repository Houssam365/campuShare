package com.campusshare.model.annonce;

import com.campusshare.model.Categorie;
import com.campusshare.model.Utilisateur;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une annonce de service à proposer.
 * Exemples: cours particuliers, covoiturage, aide au déménagement, 
 * relecture de mémoire, baby-sitting, etc.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class ServiceAnnonce extends Annonce {
    
    private String typeService; // Tutorat, Covoiturage, Aide, etc.
    private int dureeMinutesEstimee; // Durée estimée de la prestation
    private List<DayOfWeek> joursDisponibles;
    private String horaireDisponible; // Ex: "14h-18h"
    private boolean deplacementPossible;
    private String niveauExpertise; // Débutant, Intermédiaire, Expert
    private List<String> competences; // Liste des compétences pour ce service
    
    /**
     * Constructeur de ServiceAnnonce.
     * 
     * @param id Identifiant unique
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     */
    public ServiceAnnonce(String id, String titre, String description,
                          Utilisateur proprietaire, Categorie categorie) {
        super(id, titre, description, proprietaire, categorie);
        this.joursDisponibles = new ArrayList<>();
        this.competences = new ArrayList<>();
        this.dureeMinutesEstimee = 60; // 1 heure par défaut
        this.deplacementPossible = true;
        this.niveauExpertise = "Intermédiaire";
    }
    
    @Override
    public TypeAnnonce getType() {
        return TypeAnnonce.SERVICE;
    }
    
    @Override
    public String getDetailsSpecifiques() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Détails du service ===\n");
        if (typeService != null && !typeService.isEmpty()) {
            sb.append("Type: ").append(typeService).append("\n");
        }
        sb.append("Durée estimée: ").append(dureeMinutesEstimee).append(" minutes\n");
        sb.append("Niveau d'expertise: ").append(niveauExpertise).append("\n");
        
        if (!joursDisponibles.isEmpty()) {
            sb.append("Jours disponibles: ");
            for (int i = 0; i < joursDisponibles.size(); i++) {
                sb.append(traduireJour(joursDisponibles.get(i)));
                if (i < joursDisponibles.size() - 1) sb.append(", ");
            }
            sb.append("\n");
        }
        
        if (horaireDisponible != null && !horaireDisponible.isEmpty()) {
            sb.append("Horaires: ").append(horaireDisponible).append("\n");
        }
        
        sb.append("Déplacement possible: ").append(deplacementPossible ? "Oui" : "Non").append("\n");
        
        if (!competences.isEmpty()) {
            sb.append("Compétences: ").append(String.join(", ", competences)).append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Traduit un jour de la semaine en français.
     */
    private String traduireJour(DayOfWeek jour) {
        switch (jour) {
            case MONDAY: return "Lundi";
            case TUESDAY: return "Mardi";
            case WEDNESDAY: return "Mercredi";
            case THURSDAY: return "Jeudi";
            case FRIDAY: return "Vendredi";
            case SATURDAY: return "Samedi";
            case SUNDAY: return "Dimanche";
            default: return jour.toString();
        }
    }
    
    /**
     * Ajoute un jour de disponibilité.
     * 
     * @param jour Le jour à ajouter
     */
    public void ajouterJourDisponible(DayOfWeek jour) {
        if (!joursDisponibles.contains(jour)) {
            joursDisponibles.add(jour);
        }
    }
    
    /**
     * Ajoute une compétence.
     * 
     * @param competence La compétence à ajouter
     */
    public void ajouterCompetence(String competence) {
        if (!competences.contains(competence)) {
            competences.add(competence);
        }
    }
    
    // Getters et Setters spécifiques
    
    public String getTypeService() {
        return typeService;
    }
    
    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }
    
    public int getDureeMinutesEstimee() {
        return dureeMinutesEstimee;
    }
    
    public void setDureeMinutesEstimee(int dureeMinutesEstimee) {
        this.dureeMinutesEstimee = dureeMinutesEstimee;
    }
    
    public List<DayOfWeek> getJoursDisponibles() {
        return joursDisponibles;
    }
    
    public void setJoursDisponibles(List<DayOfWeek> joursDisponibles) {
        this.joursDisponibles = joursDisponibles;
    }
    
    public String getHoraireDisponible() {
        return horaireDisponible;
    }
    
    public void setHoraireDisponible(String horaireDisponible) {
        this.horaireDisponible = horaireDisponible;
    }
    
    public boolean isDeplacementPossible() {
        return deplacementPossible;
    }
    
    public void setDeplacementPossible(boolean deplacementPossible) {
        this.deplacementPossible = deplacementPossible;
    }
    
    public String getNiveauExpertise() {
        return niveauExpertise;
    }
    
    public void setNiveauExpertise(String niveauExpertise) {
        this.niveauExpertise = niveauExpertise;
    }
    
    public List<String> getCompetences() {
        return competences;
    }
    
    public void setCompetences(List<String> competences) {
        this.competences = competences;
    }
}
