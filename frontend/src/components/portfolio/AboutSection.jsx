import {Code2,Database,Network,Sparkles} from 'lucide-react';
import {SectionTitle} from '../ui/SectionTitle';
import {useAboutItems} from '../../hooks/usePortfolio';
const icons={code:Code2,database:Database,network:Network,sparkles:Sparkles};
export function AboutSection({profile}){
 const query=useAboutItems();
 const items=Array.isArray(query.data)?query.data:[];
 return <section id="apropos" className="section alternate"><div className="container">
  <SectionTitle eyebrow="Profil" title="À propos"/>
  <div className="about-layout"><div className="about-copy">
   <p>{profile?.bio||"Développeur polyvalent, je conçois des applications métier complètes : back-end Spring Boot, API REST et bases de données relationnelles, avec une interface adaptée au web, au desktop ou au mobile."}</p>
   <p>Mes projets couvrent la gestion des ressources humaines, la gestion de stock et la création de plans de maison en 2D.</p>
   <p>J’administre également les environnements sur lesquels ces applications sont déployées : serveurs, réseaux, sécurité, sauvegardes et supervision.</p>
   {profile?.email&&<a className="about-email" href={`mailto:${profile.email}`}>{profile.email}</a>}
  </div>
  {items.length>0&&<div className="about-highlights">{items.map(item=>{const Icon=icons[item.icon]||Code2;return <article className="card" key={item.id}><div className="feature-icon"><Icon/></div><h3>{item.title}</h3><p>{item.description}</p></article>})}</div>}
  </div>
 </div></section>;
}
