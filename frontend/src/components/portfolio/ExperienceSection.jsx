import {BriefcaseBusiness} from 'lucide-react';
import {useExperiences} from '../../hooks/usePortfolio';
import {SectionTitle} from '../ui/SectionTitle';
const demo=[{role:'Développeur Full Stack',company:'Projets personnels',period:'2024 — Aujourd’hui',description:'Applications avec React, Java, Spring Boot et bases de données SQL.'},{role:'Administration systèmes & réseaux',company:'Formation et pratique',period:'En continu',description:'Linux, services réseau, Docker, gestion de bases de données et dépannage.'}];
export function ExperienceSection(){
  const q=useExperiences();
  const items=Array.isArray(q.data)?q.data:demo;
  return <section id="experience" className="section alternate">
    <div className="container">
      <SectionTitle eyebrow="Parcours" title="Expérience professionnelle" description="Des missions de développement et d'administration menées en entreprise comme en indépendant."/>
      <div className="timeline">{items.map((e,i)=>{
        const period=e.period||e.startDate||'Expérience';
        return <article className="timeline-item" key={e.id||i}>
          <span className="timeline-icon" aria-hidden="true"><BriefcaseBusiness/></span>
          <div className="card">
            <div className="timeline-meta">
              <small className="timeline-period">{period}</small>
              {e.location&&<small className="timeline-location">{e.location}</small>}
            </div>
            <h3>{e.role||e.title||e.position}</h3>
            <h4>{e.company||e.organization}</h4>
            <p>{e.description||e.summary}</p>
          </div>
        </article>;
      })}</div>
    </div>
  </section>;
}
