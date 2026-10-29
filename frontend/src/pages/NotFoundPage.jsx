import {Link} from 'react-router-dom';
export function NotFoundPage(){return <section className="container not-found"><span className="eyebrow">ERREUR 404</span><h1>Cette page n’existe pas.</h1><p>Le lien est incorrect ou la page a été déplacée.</p><Link className="button primary" to="/">Retour à l’accueil</Link></section>;}
