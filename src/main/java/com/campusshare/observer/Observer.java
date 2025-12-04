package com.campusshare.observer;

import com.campusshare.model.annonce.Annonce;

/**
 * Interface Observer du pattern Observer.
 * 
 * Les classes qui implémentent cette interface peuvent s'abonner
 * à des Observables et recevoir des notifications de changement.
 * 
 * Dans CampusShare, différents types de notifications peuvent être
 * implémentés: Email, Push, SMS, etc.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public interface Observer {
    
    /**
     * Méthode appelée lorsqu'un Observable notifie ses observateurs.
     * 
     * @param annonce L'annonce qui a changé
     * @param message Le message décrivant le changement
     */
    void update(Annonce annonce, String message);
}
