import {useSkills} from '../../hooks/usePortfolio';
import {SectionTitle} from '../ui/SectionTitle';
const demo=[['Java / Spring Boot','Backend',85],['React / JavaScript','Frontend',80],['PostgreSQL / MySQL','Base de données',75],['Docker / Linux','Systèmes',70],['REST API / Git','Ingénierie',80],['HTML / CSS','Frontend',85]];
export function SkillsSection(){
 const q=useSkills();
 const rows=Array.isArray(q.data)?q.data:demo.map(([name,category,level])=>({name,category,level}));
 const groups=rows.reduce((result,skill)=>{const category=skill.category||'Technique';(result[category]||= []).push(skill);return result;},{});
 return <section id="competences" className="section"><div className="container">
  <SectionTitle eyebrow="Stack" title="Compétences" description="Deux domaines complémentaires : construire l'application, puis la faire tourner de façon fiable et sécurisée."/>
  <div className="skill-groups">{Object.entries(groups).map(([category,skills])=><article className="skill-group card" key={category}><h3>{category}</h3><div className="skill-tags">{skills.map(skill=><span key={skill.id||skill.name}>{skill.name||skill.title}</span>)}</div></article>)}</div>
  {q.isError&&<p className="hint">Compétences de démonstration affichées car l’API est indisponible.</p>}
 </div></section>;
}
