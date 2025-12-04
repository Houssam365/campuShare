package com.campusshare.observer;

/**
 * Interface Observable (Subject) du pattern Observer.
 * 
 * Permet à un objet de notifier automatiquement tous ses observateurs
 * lorsqu'un changement d'état se produit.
 * 
 * Dans CampusShare, les annonces sont des Observables qui notifient
 * les utilisateurs intéressés lors de changements.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public interface Observable {
    
    /**
     * Attache un observateur à cet observable.
     * L'observateur sera notifié lors des changements.
     * 
     * @param observer L'observateur à attacher
     */
    void attach(Observer observer);
    
    /**
     * Détache un observateur de cet observable.
     * L'observateur ne recevra plus de notifications.
     * 
     * @param observer L'observateur à détacher
     */
    void detach(Observer observer);
    
    /**
     * Notifie tous les observateurs attachés d'un changement.
     * 
     * @param message Le message décrivant le changement
     */
    void notifyObservers(String message);
}
