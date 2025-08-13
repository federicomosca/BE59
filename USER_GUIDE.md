# 🎬 Fivenine Social Movie App - Guida Utente

## 🚀 Come Iniziare (Shell/Terminal)

### 1. Registrazione
```bash
curl -X POST "http://localhost:8080/users/signUp" \
  -H "Content-Type: application/json" \
  -d '{"username":"tuousername","email":"tua@email.com","password":"tuapassword"}'
```

### 2. Login (dopo conferma email)
```bash
curl -X POST "http://localhost:8080/users/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"tuousername","password":"tuapassword"}'
```
*Salva il token JWT dalla risposta!*

---

## 🎥 Gestione Film

### Vedere tutti i film
```bash
curl -X GET "http://localhost:8080/movies"
```

### Aggiungere un nuovo film
```bash
curl -X POST "http://localhost:8080/movies" \
  -H "Content-Type: application/json" \
  -d '{"title":"Il Padrino","director":"Francis Ford Coppola","releaseDate":"1972-03-24"}'
```

---

## 📚 Gestire le Tue Collezioni

### Creare una collezione privata
```bash
curl -X POST "http://localhost:8080/collections" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{
    "name":"I Miei Film Horror",
    "description":"Collezione dei migliori film horror che ho visto",
    "visibility":"PRIVATE",
    "type":"custom"
  }'
```

### Creare una collezione pubblica
```bash
curl -X POST "http://localhost:8080/collections" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{
    "name":"Top Film 2024",
    "description":"I migliori film del 2024 secondo me",
    "visibility":"PUBLIC",
    "type":"custom"
  }'
```

### Vedere le tue collezioni
```bash
curl -X GET "http://localhost:8080/collections/my" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

### Aggiungere un film alla collezione
```bash
curl -X POST "http://localhost:8080/collections/1/movies/1" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

---

## 🤝 Sistema Sociale - Connessioni

### Inviare richiesta di amicizia
```bash
curl -X POST "http://localhost:8080/connections/request" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{"targetUserId":2,"message":"Ciao! Vedo che amiamo gli stessi film!"}'
```

### Vedere richieste di amicizia pendenti
```bash
curl -X GET "http://localhost:8080/connections/pending" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

### Accettare una richiesta
```bash
curl -X POST "http://localhost:8080/connections/1/accept" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

### Vedere i tuoi amici
```bash
curl -X GET "http://localhost:8080/connections/friends" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

---

## 💌 Messaggi Privati

### Inviare un messaggio
```bash
curl -X POST "http://localhost:8080/messages" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{
    "recipientId":2,
    "subject":"Film da vedere",
    "content":"Ciao! Ti consiglio di vedere Inception, è fantastico!"
  }'
```

### Leggere la posta in arrivo
```bash
curl -X GET "http://localhost:8080/messages/inbox" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

### Vedere quanti messaggi non letti hai
```bash
curl -X GET "http://localhost:8080/messages/unread/count" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

---

## 🎯 Raccomandare Film agli Amici

### Raccomandare un film
```bash
curl -X POST "http://localhost:8080/recommendations" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{
    "recipientId":2,
    "movieId":1,
    "message":"Questo film è incredibile! Devi vederlo assolutamente!"
  }'
```

### Vedere le raccomandazioni ricevute
```bash
curl -X GET "http://localhost:8080/recommendations/received" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

---

## 📤 Condividere Collezioni

### Condividere una collezione con un amico
```bash
curl -X POST "http://localhost:8080/collection-shares" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{
    "collectionId":1,
    "targetUserId":2,
    "message":"Dai un\'occhiata alla mia collezione di fantascienza!"
  }'
```

### Vedere le collezioni condivise con te
```bash
curl -X GET "http://localhost:8080/collection-shares/received" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

---

## 🔔 Notifiche

### Vedere tutte le notifiche
```bash
curl -X GET "http://localhost:8080/notifications" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

### Contare le notifiche non lette
```bash
curl -X GET "http://localhost:8080/notifications/unread/count" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

### Segnare tutte come lette
```bash
curl -X POST "http://localhost:8080/notifications/read-all" \
  -H "Authorization: Bearer TUO_JWT_TOKEN"
```

---

## 🎭 Richiedere Nuovi Film (all'Admin)

### Richiedere l'aggiunta di un film
```bash
curl -X POST "http://localhost:8080/movie-requests" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TUO_JWT_TOKEN" \
  -d '{
    "title":"Blade Runner 2049",
    "director":"Denis Villeneuve",
    "year":2017,
    "reason":"Film fantastico, dovrebbe essere nel database!"
  }'
```

---

## 📊 Dashboard Personale

### Conteggi rapidi (crea uno script bash)
```bash
#!/bin/bash
TOKEN="TUO_JWT_TOKEN"

echo "=== LA TUA DASHBOARD FIVENINE ==="
echo ""

echo "📬 Messaggi non letti:"
curl -s -H "Authorization: Bearer $TOKEN" "http://localhost:8080/messages/unread/count" | grep -o '"count":[0-9]*' | cut -d':' -f2

echo "🔔 Notifiche non lette:"
curl -s -H "Authorization: Bearer $TOKEN" "http://localhost:8080/notifications/unread/count" | grep -o '"count":[0-9]*' | cut -d':' -f2

echo "🤝 Richieste di amicizia pendenti:"
curl -s -H "Authorization: Bearer $TOKEN" "http://localhost:8080/connections/pending/count" | grep -o '"count":[0-9]*' | cut -d':' -f2

echo "🎯 Raccomandazioni film non viste:"
curl -s -H "Authorization: Bearer $TOKEN" "http://localhost:8080/recommendations/unviewed/count" | grep -o '"count":[0-9]*' | cut -d':' -f2

echo ""
echo "✅ Dashboard aggiornata!"
```

---

## 🎯 Flusso Utente Completo

1. **Registrati** → Conferma email → **Login**
2. **Esplora film** disponibili
3. **Crea collezioni** (watchlist, preferiti, custom)
4. **Cerca altri utenti** e **invia richieste di amicizia**
5. **Accetta connessioni** e costruisci la tua rete sociale
6. **Invia messaggi privati** ai tuoi amici
7. **Raccomanda film** che ami
8. **Condividi collezioni** interessanti
9. **Richiedi nuovi film** che mancano dal database
10. **Controlla notifiche** per rimanere aggiornato

---

**🎉 Divertiti con la tua esperienza sociale cinematografica!**