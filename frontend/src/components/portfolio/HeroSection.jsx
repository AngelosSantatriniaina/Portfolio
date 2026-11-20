import {useEffect,useRef,useState} from 'react';
import {ArrowDown,ArrowRight,MapPin} from 'lucide-react';
import {assetUrl} from '../../api/http';

export function HeroSection({profile}) {
  const heroRef=useRef(null);
  const [isVisible,setIsVisible]=useState(false);
  const name = profile?.fullName || 'Portfolio';
  const portraitUrl = assetUrl(profile?.portraitPath);
  const headline = profile?.headline || 'Développeur Java Fullstack / Administrateur Systèmes & Réseaux';
  useEffect(()=>{
    const hero=heroRef.current;
    if(!hero)return undefined;
    if(!('IntersectionObserver' in window)){setIsVisible(true);return undefined;}
    const observer=new IntersectionObserver(([entry])=>setIsVisible(entry.isIntersecting),{threshold:.15});
    observer.observe(hero);
    return ()=>observer.disconnect();
  },[]);
  return <section ref={heroRef} id="top" className={isVisible?'hero hero-visible':'hero'}><div className="hero-grid-lines" aria-hidden="true"/>
    <div className="hero-glow" aria-hidden="true"/>
    <div className="container hero-grid"><div className="hero-copy">
      <p className="eyebrow">Ingénierie logicielle &amp; infrastructure</p>
      <h1>{name}</h1>
      <p className="hero-role" aria-label={headline}>{headline.split(/\s*\/\s*/).map((part,index)=><span key={part} className={`role-line ${index===1?'role-accent':''}`} aria-hidden="true"><span className="role-typing" style={{'--role-width':`${part.length}ch`,'--role-steps':part.length}}>{part}</span></span>)}</p>
      <p className="hero-description">{profile?.bio || "Je développe des applications de gestion de bout en bout — Spring Boot côté back-end, React sur le web, JavaFX sur desktop et Flutter sur mobile — et j'administre les environnements sur lesquels elles tournent."}</p>
      {profile?.location&&<p className="hero-location"><MapPin aria-hidden="true"/>{profile.location}</p>}
      <div className="actions"><a className="button primary" href="/#projets">Voir mes projets en ligne <ArrowRight/></a></div>
      <dl className="hero-stats"><div><dd>3+</dd><dt>ans de pratique</dt></div><div><dd>10+</dd><dt>projets réalisés</dt></div><div><dd>10+</dd><dt>technologies maîtrisées</dt></div></dl>
    </div><div className="hero-portrait-wrap">
      <figure className="hero-portrait-card">
        <div className="hero-portrait">{portraitUrl?<img src={portraitUrl} alt={`${name}, ${headline}`} fetchPriority="high"/>:<div className="hero-portrait-placeholder" aria-label="Photo de profil non renseignée"><span>{name.split(/\s+/).slice(0,2).map(part=>part[0]).join('').toUpperCase()}</span></div>}</div>
        <figcaption className="portrait-meta"><span>FIG. 01 / PORTRAIT STUDIO</span><span>MG / 01</span></figcaption>
        <div className="portrait-caption"><span className="availability-dot"/><span>Disponible pour missions &amp; formations</span></div>
      </figure>
    </div></div>
    <a className="scroll-cue" href="/#apropos"><ArrowDown aria-hidden="true"/> Défiler pour découvrir</a>
  </section>;
}
