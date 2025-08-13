# 🎬 FIVENINE - Social Movie Platform (Standalone)

Versione eseguibile autonoma della piattaforma sociale per film con database integrato H2.

## 🚀 Avvio Rapido

### Windows
```bash
run-fivenine.bat
```

### Linux/macOS
```bash
./run-fivenine.sh
```

### Manuale
```bash
java -Dspring.profiles.active=prod -jar target/fivenine-0.0.1-SNAPSHOT.jar
```

## 📱 Accesso

- **Frontend (Terminal UI)**: http://localhost:8080
- **Backend API**: http://localhost:8080/api/*
- **Profilo**: Produzione con database H2 embedded

## 🎮 Come Usare l'Interfaccia Terminal

1. **Registrazione**: Digita `user signup` e segui i prompt
2. **Login**: Digita `user login` e inserisci credenziali
3. **Aiuto**: Digita `help` per vedere tutti i comandi
4. **Contesti disponibili**:
   - `user` - gestione utenti
   - `movie` - gestione film
   - `social` - connessioni e messaggi
   - `collection` - collezioni private/pubbliche
   - `recommend` - raccomandazioni film
   - `admin` - amministrazione (solo admin)

## 🔥 Funzionalità Principali

### 🎬 **Film e Collezioni**
- Visualizza database completo film
- Crea collezioni personalizzate
- Privacy: PRIVATE/PUBLIC/FRIENDS
- Aggiungi/rimuovi film dalle collezioni
- Richiedi nuovi film agli admin

### 👥 **Social Network**
- **Connessioni**: invia richieste di amicizia
- **Messaggi privati**: sistema inbox/sent completo
- **Notifiche**: sistema notifiche in tempo reale
- **Raccomandazioni**: suggerisci film agli amici
- **Condivisione**: condividi collezioni con altri utenti

### 💬 **Comandi Sociali Esempio**
```
social connect          # Richiesta di amicizia
social friends          # Lista amici
social message          # Invia messaggio privato
social inbox            # Leggi messaggi ricevuti
social notifications    # Visualizza notifiche
recommend send          # Raccomanda un film
collection create       # Crea nuova collezione
collection share        # Condividi collezione
```

## 🗂️ Database

- **Tipo**: H2 embedded (file locale)
- **Posizione**: `./data/fivenine.mv.db`
- **Persistenza**: I dati vengono salvati tra le sessioni
- **Reset**: Elimina la cartella `data/` per reset completo

## 🎯 Caratteristiche Tecniche

- **Backend**: Spring Boot 3.4.3 + H2 Database
- **Frontend**: React 19.1.0 (terminal interface)
- **Sicurezza**: JWT authentication
- **API**: REST con 35+ endpoint
- **Input**: Solo tastiera (no mouse required)
- **Architettura**: Monolitica (JAR singolo ~80MB)

## 🛠️ Requisiti Sistema

- Java 17 o superiore
- ~100MB spazio disco
- Porta 8080 disponibile

## 🔧 Troubleshooting

### Porta 8080 occupata
```bash
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/macOS
```

### Java non trovato
Scarica Java 17+ da: https://adoptium.net/

### Reset database
```bash
rm -rf data/  # Linux/macOS
rmdir /s data # Windows
```

## 📊 Statistiche Progetto

- **122 classi Java**
- **17 repository JPA**
- **35+ endpoint REST**
- **5 contesti di comando**
- **Sistema sociale completo**
- **Input sequenziale testuale**

## 🎉 Pronto per l'uso!

L'applicazione è completamente autonoma e non richiede installazioni aggiuntive oltre Java. Tutti i dati vengono salvati localmente nel database H2 embedded.

**Buon divertimento con Fivenine!** 🍿🎬