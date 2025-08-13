# 🎬 Fivenine Social Movie App - API Documentation

**Base URL**: `http://localhost:8080`  
**Authentication**: JWT Token required for most endpoints

---

## 🎥 **Movie Management** (Public Access)
- `GET /movies` - Ottieni tutti i film
- `POST /movies` - Crea nuovo film
- `GET /movies/{id}` - Ottieni film per ID
- `PUT /movies/{id}` - Aggiorna film
- `DELETE /movies/{id}` - Elimina film

---

## 👤 **User Management**
### Public Endpoints
- `POST /users/signUp` - Registrazione utente
- `POST /users/login` - Login utente
- `POST /users/confirmEmail` - Conferma cambio email
- `POST /users/confirmPassword` - Conferma cambio password

### Protected Endpoints
- `POST /users/logout` - Logout utente
- `GET /users` - Lista utenti
- `PUT /users/changeEmail` - Cambio email
- `PUT /users/changePassword` - Cambio password

---

## 🤝 **User Connections** (Social Features)
- `POST /connections/request` - Invia richiesta di connessione
- `POST /connections/{connectionId}/accept` - Accetta connessione
- `POST /connections/{connectionId}/reject` - Rifiuta connessione
- `DELETE /connections/{connectionId}` - Rimuovi connessione
- `POST /connections/block/{targetUserId}` - Blocca utente
- `GET /connections/my` - Le mie connessioni
- `GET /connections/friends` - Lista amici connessi
- `GET /connections/pending` - Richieste pendenti
- `GET /connections/pending/count` - Conteggio richieste pendenti
- `GET /connections/status/{targetUserId}` - Status connessione con utente

---

## 💌 **Private Messages**
- `POST /messages` - Invia messaggio privato
- `GET /messages/inbox` - Posta in arrivo
- `GET /messages/sent` - Messaggi inviati
- `GET /messages/conversation/{otherUserId}` - Conversazione con utente
- `GET /messages/unread/count` - Conteggio messaggi non letti
- `GET /messages/{messageId}` - Leggi messaggio specifico
- `POST /messages/{messageId}/read` - Segna come letto
- `DELETE /messages/{messageId}` - Elimina messaggio

---

## 🔔 **Notifications**
- `GET /notifications` - Tutte le notifiche
- `GET /notifications/unread` - Notifiche non lette
- `GET /notifications/unread/count` - Conteggio non lette
- `POST /notifications/{notificationId}/read` - Segna come letta
- `POST /notifications/read-all` - Segna tutte come lette
- `POST /notifications/{notificationId}/dismiss` - Dismissi notifica

---

## 🎯 **Movie Recommendations**
- `POST /recommendations` - Raccomanda film ad amico
- `GET /recommendations/received` - Raccomandazioni ricevute
- `GET /recommendations/sent` - Raccomandazioni inviate
- `GET /recommendations/unviewed/count` - Conteggio non viste
- `POST /recommendations/{recommendationId}/viewed` - Segna come vista
- `POST /recommendations/{recommendationId}/dismiss` - Dismissi raccomandazione

---

## 📚 **Enhanced Collections** (with Privacy & Descriptions)
- `GET /collections` - Ottieni tutte le collezioni
- `GET /collections/my` - Le mie collezioni
- `GET /collections/my/watchlist` - La mia watchlist
- `GET /collections/my/favourites` - I miei preferiti
- `POST /collections` - Crea collezione (con descrizione e privacy)
- `GET /collections/{id}` - Collezione per ID
- `PUT /collections/{id}` - Aggiorna collezione
- `DELETE /collections/{id}` - Elimina collezione

### Collection Movie Management
- `POST /collections/{collectionId}/movies/{movieId}` - Aggiungi film a collezione
- `DELETE /collections/{collectionId}/movies/{movieId}` - Rimuovi film da collezione
- `POST /collections/watchlist/movies/{movieId}` - Aggiungi a watchlist
- `DELETE /collections/watchlist/movies/{movieId}` - Rimuovi da watchlist
- `POST /collections/favourites/movies/{movieId}` - Aggiungi a preferiti
- `DELETE /collections/favourites/movies/{movieId}` - Rimuovi da preferiti

---

## 📤 **Collection Sharing**
- `POST /collection-shares` - Condividi collezione con amico
- `GET /collection-shares/received` - Collezioni condivise con me
- `GET /collection-shares/sent` - Collezioni che ho condiviso
- `GET /collection-shares/unviewed/count` - Conteggio condivisioni non viste
- `POST /collection-shares/{shareId}/viewed` - Segna condivisione come vista

---

## ⭐ **Rating & Reviews**
- `GET /ratings` - Ottieni rating
- `POST /ratings` - Crea rating
- `PUT /ratings/{id}` - Aggiorna rating
- `DELETE /ratings/{id}` - Elimina rating

---

## 💬 **Comments System**
- `GET /comments` - Ottieni commenti
- `POST /comments` - Crea commento
- `PUT /comments/{id}` - Aggiorna commento
- `DELETE /comments/{id}` - Elimina commento

---

## 🎭 **Movie Requests** (Admin Approval)
- `GET /movie-requests` - Lista richieste film
- `POST /movie-requests` - Nuova richiesta film
- `PUT /movie-requests/{id}/approve` - Approva richiesta
- `DELETE /movie-requests/{id}` - Elimina richiesta

---

## 🔐 **Security & Features**
- **JWT Authentication** - Sicurezza completa
- **Password Encryption** - BCrypt hashing
- **Email Verification** - Sistema conferma email
- **Audit Logging** - Tracciamento azioni utente
- **Privacy Controls** - Collezioni private/pubbliche/amici
- **Social Features** - Sistema connessioni stile Instagram/Facebook
- **Real-time Notifications** - Sistema notifiche completo
- **Message System** - Chat privata tra utenti
- **Recommendation Engine** - Suggerimenti film tra amici

---

## 📊 **Collection Privacy Levels**
- `PRIVATE` - Solo il proprietario può vedere
- `PUBLIC` - Tutti possono vedere  
- `FRIENDS` - Solo le connessioni possono vedere

---

## 🎯 **Notification Types**
- Connection requests/accepted
- New messages  
- Collection shared
- Movie recommendations
- New comments
- Movie request approved/rejected
- System announcements

---

**Status**: ✅ **SISTEMA SOCIALE COMPLETO E OPERATIVO**  
**Features**: 🤝 Connections • 💌 Messages • 🔔 Notifications • 🎯 Recommendations • 📤 Sharing

## 🔐 Security Features
- JWT Authentication implementato
- Password encoding con Spring Security
- Email verification system
- Audit logging per sicurezza

**Stato**: ✅ PROGETTO COMPLETO E PRONTO