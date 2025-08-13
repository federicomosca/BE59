package it.dogs.fivenine.model.domain.enums;

public enum ConnectionStatus {
    PENDING,    // Richiesta inviata, in attesa di risposta
    ACCEPTED,   // Connessione accettata
    REJECTED,   // Richiesta respinta
    BLOCKED     // Utente bloccato
}