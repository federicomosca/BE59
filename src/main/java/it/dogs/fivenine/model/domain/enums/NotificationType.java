package it.dogs.fivenine.model.domain.enums;

public enum NotificationType {
    CONNECTION_REQUEST,     // Richiesta di connessione
    CONNECTION_ACCEPTED,    // Connessione accettata
    NEW_MESSAGE,           // Nuovo messaggio privato
    COLLECTION_SHARED,     // Collection condivisa
    MOVIE_RECOMMENDATION,  // Suggerimento film
    NEW_COMMENT,          // Nuovo commento
    MOVIE_REQUEST_APPROVED, // Richiesta film approvata
    MOVIE_REQUEST_REJECTED, // Richiesta film respinta
    COLLECTION_LIKED,     // Collection piaciuta
    PROFILE_MENTIONED,    // Menzionato in un commento
    SYSTEM_ANNOUNCEMENT   // Annuncio di sistema
}