# Portfolio dynamique — backend Spring Boot

Backend de départ pour un portfolio personnel administrable. Java 21, Spring Boot 3, PostgreSQL, Flyway, JWT dans cookie HttpOnly, CSRF, fichiers locaux.

## 1. Prérequis
- Java 21, Maven 3.9+ (ou Docker)
- PostgreSQL 16 si lancement local hors Docker ; Docker Compose expose sa base sur le port hôte `5433`

## 2. Configuration
Copier `.env.example` vers `.env`, puis définir des valeurs fortes :
```bash
cp .env.example .env
openssl rand -base64 48
```
Placer la sortie dans `JWT_SECRET_BASE64`. Changer `POSTGRES_PASSWORD` et `ADMIN_PASSWORD`.
Le secret JWT doit décoder au moins 32 octets. `JWT_COOKIE_SECURE=false` est réservé au développement local HTTP. En production HTTPS, mettre `true`.
Ne jamais committer `.env`.

## 3. Démarrer avec Docker
```bash
cp .env.example .env
# éditer .env
docker compose up --build -d
docker compose logs -f backend
```
API : `http://localhost:8080`; santé : `/actuator/health`; OpenAPI est à ajouter si le frontend a besoin de Swagger UI (voir section suivante).

## 4. Démarrer sans Docker
Vérifier que `backend/.env` contient les identifiants PostgreSQL et un secret JWT valide, puis lancer depuis le dossier `backend` :
```bash
cd backend
mvn spring-boot:run
```
Spring charge automatiquement `.env` depuis le dossier `backend`. Si PostgreSQL tourne dans Docker Compose, gardez `SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/portfolio`. Si PostgreSQL est installé localement, utilisez son port (souvent `5432`). Remplacer les valeurs d'exemple, notamment `JWT_SECRET_BASE64` avec la sortie de `openssl rand -base64 48`. Ne jamais committer `.env`.
Si PostgreSQL refuse encore le mot de passe après un changement de `.env`, le volume Docker existant conserve probablement l'ancien mot de passe. Depuis la racine du dépôt, ouvrir une session locale dans la base :
```bash
docker compose -f backend/compose.yaml --env-file backend/.env exec db psql -U portfolio_app -d portfolio
```
Dans `psql`, lancer `\password portfolio_app`, saisir la valeur actuelle de `SPRING_DATASOURCE_PASSWORD` deux fois, puis `\q`. Cette opération modifie le mot de passe du rôle sans supprimer les données.
Les tests (`mvn test`) utilisent une base H2 isolée et ne nécessitent pas de PostgreSQL local.

## 5. API principale
- `GET /api/v1/public/profile`
- `GET /api/v1/public/about-items`
- `GET /api/v1/public/experiences`
- `GET /api/v1/public/skills`
- `GET /api/v1/public/services`
- `GET /api/v1/public/projects`
- `GET /api/v1/public/reviews`
- `GET /api/v1/public/contact-links`
- `POST /api/v1/auth/login` JSON `{ "email": "...", "password": "..." }`
- `POST /api/v1/auth/logout`
- `GET /api/v1/auth/csrf` fournit le token CSRF
- `POST /api/v1/admin/uploads` (multipart, champ `file`, JPEG/PNG uniquement) stocke une image localement et retourne son chemin public
- `POST /api/v1/contact` enregistre le message

Toutes les routes `/api/v1/admin/**` nécessitent une session administrateur et un jeton CSRF pour les écritures. Les ressources `about-items`, `experiences`, `skills`, `skill-categories`, `projects`, `services`, `contact-links` et `reviews` disposent des opérations `GET`, `POST`, `PUT /{id}` et `DELETE /{id}` sous `/api/v1/admin/`. Les projets acceptent une liste ordonnée `imagePaths` ; `imagePath` reste disponible comme image de couverture pour compatibilité. Le profil principal se consulte avec `GET /api/v1/admin/profile` et se modifie avec `PUT /api/v1/admin/profile` ; il ne peut pas être supprimé. Les messages reçus sont disponibles via `GET /api/v1/admin/messages`, peuvent être marqués comme lus via `PATCH /{id}/read` et supprimés via `DELETE /{id}`.

## 6. Cookie / CSRF
Le login pose un cookie `PORTFOLIO_AUTH` HttpOnly, SameSite=Lax, Secure en production. Les écritures exigent un jeton CSRF : appeler `/api/v1/auth/csrf`, lire le cookie `XSRF-TOKEN` (non HttpOnly), puis envoyer sa valeur dans `X-XSRF-TOKEN`. Le navigateur doit envoyer `credentials: 'include'`.

## 7. Données de démonstration
Flyway insère des données inspirées du HTML fourni. Les liens inconnus restent NULL/vides : aucun faux lien `example.com` n'est créé. Le profil, les rubriques du portfolio et les témoignages peuvent être gérés depuis le tableau de bord admin.

## 8. Périmètre actuel
Le tableau de bord permet de modifier le profil principal et de créer, modifier, publier/masquer ou supprimer les éléments du portfolio, y compris les compétences, catégories, expériences, services, projets avec plusieurs images, coordonnées, témoignages et messages. La fiche de profil reste obligatoire afin de préserver l'affichage du site. Les endpoints admin ne doivent pas être exposés au public.
