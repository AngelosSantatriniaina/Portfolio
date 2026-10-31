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

La page d’accueil suit la présentation de référence : profil, expérience, compétences, services, projets, galerie du portrait, CV et contact. Le menu fixe utilise les ancres de ces sections et la galerie reprend la photo configurée dans l’espace admin.

Dans l’onglet « Projets » de l’espace admin, plusieurs images JPEG/PNG peuvent être ajoutées ou retirées avant l’enregistrement ; la première image est utilisée comme couverture. Sur le portfolio, cliquer sur une couverture ouvre une visionneuse avec vignettes, navigation clavier et commandes précédent/suivant.

## Contrat API à adapter
Les endpoints dans `src/api/` doivent correspondre aux `@RequestMapping` du backend. Le profil administrateur accepte un téléversement JPEG/PNG depuis l’onglet « Profil & identité » ; l’image est envoyée au backend et son chemin est enregistré avec le profil.
Login attend `{token: "...", username: "...", role: "ADMIN"}` ou `{accessToken: "..."}`. En développement, autoriser `http://localhost:5173` dans CORS Spring.
Les sections publiques affichent des données de démonstration si l'API est indisponible. Le contact et l'espace admin nécessitent une API compatible.
