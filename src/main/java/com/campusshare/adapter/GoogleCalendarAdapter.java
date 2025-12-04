package com.campusshare.adapter;

import com.campusshare.model.Reservation;

import java.util.HashMap;
import java.util.Map;

/**
 * Adaptateur pour Google Calendar.
 * 
 * Pattern Adapter: Cette classe adapte l'interface de GoogleCalendarAPI
 * (système externe) à notre interface Calendrier (attendue par l'application).
 * 
 * Fonctionnement:
 * - Notre application utilise l'interface Calendrier
 * - L'adaptateur traduit les appels vers l'API Google
 * - Conversion des objets Reservation vers les paramètres Google
 * 
 * Avantages:
 * - Découplage: notre code ne dépend pas directement de l'API Google
 * - Facilité de changement: on peut remplacer Google par un autre calendrier
 * - Testabilité: on peut mocker l'interface Calendrier
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class GoogleCalendarAdapter implements Calendrier {
    
    private GoogleCalendarAPI googleCalendarAPI; // L'adaptée
    private Map<String, String> mappingIds; // reservationId -> googleEventId
    
    /**
     * Constructeur de l'adaptateur.
     * 
     * @param googleCalendarAPI L'API Google à adapter
     */
    public GoogleCalendarAdapter(GoogleCalendarAPI googleCalendarAPI) {
        this.googleCalendarAPI = googleCalendarAPI;
        this.mappingIds = new HashMap<>();
    }
    
    /**
     * Constructeur simplifié avec création de l'API.
     * 
     * @param apiKey Clé API Google
     * @param calendarId ID du calendrier
     */
    public GoogleCalendarAdapter(String apiKey, String calendarId) {
        this(new GoogleCalendarAPI(apiKey, calendarId));
    }
    
    /**
     * Adapte l'ajout d'une réservation vers l'API Google.
     * Convertit une Reservation en paramètres d'événement Google.
     */
    @Override
    public boolean ajouterEvenement(Reservation reservation) {
        System.out.println("🗓️ [Adapter] Ajout d'une réservation au calendrier Google...");
        
        // Conversion Reservation → paramètres Google
        String titre = "CampusShare: " + reservation.getAnnonce().getTitre();
        String description = String.format(
                "Réservation #%s\n" +
                "Demandeur: %s\n" +
                "Propriétaire: %s\n" +
                "Prix: %.2f€",
                reservation.getId(),
                reservation.getDemandeur().getNomComplet(),
                reservation.getProprietaire().getNomComplet(),
                reservation.getPrixTotal()
        );
        String localisation = reservation.getAnnonce().getLocalisation();
        
        // Appel à l'API Google via l'adaptée
        String googleEventId = googleCalendarAPI.createEvent(
                titre,
                reservation.getDateDebut(),
                reservation.getDateFin(),
                description,
                localisation
        );
        
        if (googleEventId != null) {
            // Mémoriser le mapping pour les futures opérations
            mappingIds.put(reservation.getId(), googleEventId);
            return true;
        }
        
        return false;
    }
    
    /**
     * Adapte la suppression d'une réservation du calendrier Google.
     */
    @Override
    public boolean supprimerEvenement(String reservationId) {
        System.out.println("🗓️ [Adapter] Suppression de la réservation du calendrier...");
        
        String googleEventId = mappingIds.get(reservationId);
        if (googleEventId == null) {
            System.out.println("  ⚠️ Événement non trouvé dans le mapping");
            return false;
        }
        
        boolean success = googleCalendarAPI.deleteEvent(googleEventId);
        if (success) {
            mappingIds.remove(reservationId);
        }
        return success;
    }
    
    /**
     * Adapte la modification d'une réservation.
     */
    @Override
    public boolean modifierEvenement(Reservation reservation) {
        System.out.println("🗓️ [Adapter] Modification de l'événement...");
        
        String googleEventId = mappingIds.get(reservation.getId());
        if (googleEventId == null) {
            // L'événement n'existe pas, on le crée
            return ajouterEvenement(reservation);
        }
        
        String titre = "CampusShare: " + reservation.getAnnonce().getTitre();
        
        return googleCalendarAPI.updateEvent(
                googleEventId,
                titre,
                reservation.getDateDebut(),
                reservation.getDateFin()
        );
    }
    
    /**
     * Adapte la vérification de disponibilité.
     */
    @Override
    public boolean verifierDisponibilite(Reservation reservation) {
        System.out.println("🗓️ [Adapter] Vérification de disponibilité...");
        
        return googleCalendarAPI.checkAvailability(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );
    }
    
    /**
     * Retourne l'API Google (pour tests ou configuration).
     */
    public GoogleCalendarAPI getGoogleCalendarAPI() {
        return googleCalendarAPI;
    }
}
