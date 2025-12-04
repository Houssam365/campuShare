package com.campusshare.adapter;

import java.time.LocalDateTime;

/**
 * Classe simulant l'API Google Calendar (système externe).
 * Cette classe représente une API tierce avec sa propre interface.
 * 
 * Pattern Adapter: C'est la classe "Adaptée" - elle a une interface
 * incompatible avec celle attendue par notre application.
 * 
 * En production, cette classe représenterait l'API réelle de Google Calendar.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class GoogleCalendarAPI {
    
    private String apiKey;
    private String calendarId;
    
    /**
     * Constructeur de l'API Google Calendar.
     * 
     * @param apiKey Clé API Google
     * @param calendarId ID du calendrier
     */
    public GoogleCalendarAPI(String apiKey, String calendarId) {
        this.apiKey = apiKey;
        this.calendarId = calendarId;
    }
    
    /**
     * Crée un événement dans Google Calendar.
     * Interface spécifique à Google (différente de notre interface Calendrier).
     * 
     * @param eventTitle Titre de l'événement
     * @param startTime Heure de début
     * @param endTime Heure de fin
     * @param description Description
     * @param location Lieu
     * @return ID de l'événement créé, ou null si échec
     */
    public String createEvent(String eventTitle, LocalDateTime startTime, 
                              LocalDateTime endTime, String description, String location) {
        // Simulation de l'appel à l'API Google
        System.out.println("  [GoogleCalendarAPI] Création d'événement...");
        System.out.println("    → Calendrier: " + calendarId);
        System.out.println("    → Titre: " + eventTitle);
        System.out.println("    → Début: " + startTime);
        System.out.println("    → Fin: " + endTime);
        
        // Simulation: génère un ID d'événement Google
        String eventId = "gcal_" + System.currentTimeMillis();
        System.out.println("    → Événement créé avec ID: " + eventId);
        
        return eventId;
    }
    
    /**
     * Supprime un événement de Google Calendar.
     * 
     * @param eventId ID de l'événement Google
     * @return true si la suppression a réussi
     */
    public boolean deleteEvent(String eventId) {
        System.out.println("  [GoogleCalendarAPI] Suppression de l'événement: " + eventId);
        // Simulation
        return true;
    }
    
    /**
     * Met à jour un événement existant.
     * 
     * @param eventId ID de l'événement
     * @param eventTitle Nouveau titre
     * @param startTime Nouvelle heure de début
     * @param endTime Nouvelle heure de fin
     * @return true si la mise à jour a réussi
     */
    public boolean updateEvent(String eventId, String eventTitle, 
                               LocalDateTime startTime, LocalDateTime endTime) {
        System.out.println("  [GoogleCalendarAPI] Mise à jour de l'événement: " + eventId);
        System.out.println("    → Nouveau titre: " + eventTitle);
        // Simulation
        return true;
    }
    
    /**
     * Vérifie si un créneau est libre dans le calendrier.
     * 
     * @param startTime Début du créneau
     * @param endTime Fin du créneau
     * @return true si le créneau est libre
     */
    public boolean checkAvailability(LocalDateTime startTime, LocalDateTime endTime) {
        System.out.println("  [GoogleCalendarAPI] Vérification de disponibilité...");
        System.out.println("    → De: " + startTime + " à " + endTime);
        // Simulation: toujours disponible
        return true;
    }
    
    // Getters
    
    public String getApiKey() {
        return apiKey;
    }
    
    public String getCalendarId() {
        return calendarId;
    }
}
