import {Code2,Database,Network,Sparkles} from 'lucide-react';
import {SectionTitle} from '../ui/SectionTitle';
import {useAboutItems} from '../../hooks/usePortfolio';
const icons={code:Code2,database:Database,network:Network,sparkles:Sparkles};
export function AboutSection({profile}){
 const query=useAboutItems();
 const items=Array.isArray(query.data)?query.data:[];
 const roles=(profile?.headline||'Développeur Java Fullstack / Administrateur Systèmes & Réseaux').replace(/\s*\/\s*/g,' · ');
 return <section id="apropos" className="section alternate"><div className="container">
  <SectionTitle eyebrow="Profil" title="À propos"/>
  <div className="about-layout"><div className="about-copy">
   <p>{profile?.bio||"Développeur polyvalent, je conçois des applications métier complètes : back-end Spring Boot, API REST et bases de données relationnelles, avec une interface adaptée au web, au desktop ou au mobile."}</p>
   <p>Mes projets couvrent la gestion des ressources humaines, la gestion de stock et la création de plans de maison en 2D.</p>
   <p>J’administre également les environnements sur lesquels ces applications sont déployées : serveurs, réseaux, sécurité, sauvegardes et supervision.</p>
   {profile?.email&&<a className="about-email" href={`mailto:${profile.email}`}>{profile.email}</a>}
  </div>
  <aside className="about-highlights">
   <article className="card about-brief">
    <span className="card-label">En bref</span>
    <dl className="about-brief-list">
     <div><dt>Rôles</dt><dd>{roles}</dd></div>
     {profile?.location&&<div><dt>Localisation</dt><dd>{profile.location}</dd></div>}
     {profile?.email&&<div><dt>E-mail</dt><dd><a href={`mailto:${profile.email}`}>{profile.email}</a></dd></div>}
    </dl>
   </article>
   {items.map(item=>{const Icon=icons[item.icon]||Code2;return <article className="card" key={item.id}><div className="feature-icon"><Icon/></div><h3>{item.title}</h3><p>{item.description}</p></article>})}
  </aside>
  </div>
 </div></section>;
}
