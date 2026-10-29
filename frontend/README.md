# Frontend Portfolio Full Stack

## Démarrage
```bash
cp .env.example .env
npm install
npm run dev
```
Ouvre http://localhost:5173. Le backend Spring Boot est attendu sur http://localhost:8080.

## Architecture
- `app/`: router React et cache TanStack Query.
- `api/`: client Axios, endpoints publics, endpoints admin et authentification.
- `components/layout/`: en-tête, pied de page, protection admin.
- `components/portfolio/`: sections du site public.
- `features/`: emplacements pour modules métiers indépendants.
- `hooks/`: hooks de requêtes API.
- `schemas/`: validation Zod.
- `pages/`: accueil, login admin, tableau de bord.

## Contrat API à adapter
Les endpoints dans `src/api/` doivent correspondre aux `@RequestMapping` du backend. Contrat supposé : GET `/profile`, `/experiences`, `/skills`, `/services`, `/projects`, `/reviews`; POST `/contact`; POST `/auth/login`; GET/POST/PUT/DELETE `/admin/projects`; GET `/admin/messages`.
Login attend `{token: "...", username: "...", role: "ADMIN"}` ou `{accessToken: "..."}`. En développement, autoriser `http://localhost:5173` dans CORS Spring.
Les sections publiques affichent des données de démonstration si l'API est indisponible. Le contact et l'espace admin nécessitent une API compatible.
