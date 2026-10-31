import {ArrowRight,MapPin} from 'lucide-react';
import {assetUrl} from '../../api/http';

export function HeroSection({profile}) {
  const name = profile?.fullName || 'Portfolio';
  const portraitUrl = assetUrl(profile?.portraitPath);
  const headline = profile?.headline || 'Développeur Java Fullstack / Administrateur Systèmes & Réseaux';
  return <section id="top" className="hero"><div className="hero-grid-lines" aria-hidden="true"/>
    <div className="hero-glow" aria-hidden="true"/>
    <div className="container hero-grid"><div className="hero-copy">
      <p className="eyebrow">Ingénierie logicielle &amp; infrastructure</p>
      <h1>{name}</h1>
      <p className="hero-role">{headline.split(/\s*\/\s*/).map((part,index)=><span key={part} className={index===1?'role-accent':''}>{index>0&&<b aria-hidden="true"> / </b>}{part}</span>)}</p>
      <p className="hero-description">{profile?.bio || "Je développe des applications de gestion de bout en bout — Spring Boot côté back-end, React sur le web, JavaFX sur desktop et Flutter sur mobile — et j'administre les environnements sur lesquels elles tournent."}</p>
      {profile?.location&&<p className="hero-location"><MapPin aria-hidden="true"/>{profile.location}</p>}
      <div className="actions"><a className="button primary" href="/#projets">Voir mes projets en ligne <ArrowRight/></a></div>
      <dl className="hero-stats"><div><dd>3+</dd><dt>ans de pratique</dt></div><div><dd>10+</dd><dt>projets réalisés</dt></div><div><dd>10+</dd><dt>technologies maîtrisées</dt></div></dl>
    </div><div className="hero-portrait-wrap">
      <div className="hero-portrait">{portraitUrl?<img src={portraitUrl} alt={`${name}, ${headline}`} fetchPriority="high"/>:<div className="hero-portrait-placeholder" aria-label="Photo de profil non renseignée"><span>{name.split(/\s+/).slice(0,2).map(part=>part[0]).join('').toUpperCase()}</span></div>}</div>
      <div className="portrait-caption"><span className="availability-dot"/><span>Disponible pour missions &amp; formations</span></div>
    </div></div>
  </section>;
}
