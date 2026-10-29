# Portfolio dynamique — backend Spring Boot

Backend de départ pour un portfolio personnel administrable. Java 21, Spring Boot 3, PostgreSQL, Flyway, JWT dans cookie HttpOnly, CSRF, fichiers locaux.

## 1. Prérequis
- Java 21, Maven 3.9+ (ou Docker)
- PostgreSQL 16 si lancement local hors Docker

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
Créer une base `portfolio`, exporter les variables de `.env` dans le shell (ou utiliser un outil dotenv), puis :
```bash
mvn spring-boot:run
```
Spring ne charge pas `.env` automatiquement : il faut exporter les variables ou lancer Docker Compose.
Les tests (`mvn test`) utilisent une base H2 isolée et ne nécessitent pas de PostgreSQL local.

## 5. API principale
- `GET /api/v1/public/profile`
- `GET /api/v1/public/experiences`
- `GET /api/v1/public/skills`
- `GET /api/v1/public/services`
- `GET /api/v1/public/projects`
- `POST /api/v1/auth/login` JSON `{ "email": "...", "password": "..." }`
- `POST /api/v1/auth/logout`
- `GET /api/v1/auth/csrf` fournit le token CSRF
- `POST /api/v1/admin/uploads` (multipart, champ `file`, JPEG/PNG uniquement) stocke une image localement et retourne son chemin public\n- `POST /api/v1/contact` enregistre le message
- `GET /api/v1/admin/projects` (authentification admin requise)
- `POST /api/v1/admin/projects` (authentification admin + CSRF)

## 6. Cookie / CSRF
Le login pose un cookie `PORTFOLIO_AUTH` HttpOnly, SameSite=Lax, Secure en production. Les écritures exigent un jeton CSRF : appeler `/api/v1/auth/csrf`, lire le cookie `XSRF-TOKEN` (non HttpOnly), puis envoyer sa valeur dans `X-XSRF-TOKEN`. Le navigateur doit envoyer `credentials: 'include'`.

## 7. Données de démonstration
Flyway insère des données inspirées du HTML fourni. Les liens inconnus restent NULL/vides : aucun faux lien `example.com` n'est créé. Le profil peut être édité après connexion dans l'admin (l'API de mise à jour du profil est à compléter lors de l'étape admin).

## 8. Périmètre actuel
Ce livrable met en place l'ossature compilable et les flux essentiels : profil, expériences, compétences, services, projets, contact, login/logout, sécurité, migrations et seed. Les DTO/entités CRUD admin sont fournis pour projets ; les CRUD admin complets de toutes les autres rubriques, gestion des photos, avis/réactions, mail SMTP et OpenAPI UI seront étendus au fil des prochaines sous-étapes. Ne pas exposer les endpoints admin au public.
