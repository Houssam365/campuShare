package com.campusshare.observer;

import com.campusshare.model.Utilisateur;
import com.campusshare.model.annonce.Annonce;

/**
 * Observateur concret qui envoie des notifications par SMS.
 * Implémentation du pattern Observer.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class SMSObserver implements Observer {
    
    private Utilisateur utilisateur;
    private String numeroTelephone;
    
    /**
     * Constructeur de l'observateur SMS.
     * 
     * @param utilisateur L'utilisateur qui recevra les SMS
     * @param numeroTelephone Numéro de téléphone de l'utilisateur
     */
    public SMSObserver(Utilisateur utilisateur, String numeroTelephone) {
        this.utilisateur = utilisateur;
        this.numeroTelephone = numeroTelephone;
    }
    
    /**
     * Envoie une notification par SMS à l'utilisateur.
     * (Simulation - en production, utiliserait Twilio ou similaire)
     * 
     * @param annonce L'annonce concernée
     * @param message Le message de notification
     */
    @Override
    public void update(Annonce annonce, String message) {
        // Simulation d'envoi de SMS
        System.out.println("💬 SMS envoyé au " + numeroTelephone);
        System.out.println("   Message: CampusShare - " + truncate(message, 100));
        System.out.println();
    }
    
    /**
     * Tronque le message si nécessaire (limite SMS).
     */
    private String truncate(String message, int maxLength) {
        if (message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength - 3) + "...";
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public String getNumeroTelephone() {
        return numeroTelephone;
    }
    
    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }
}
