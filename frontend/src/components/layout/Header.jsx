import {useEffect,useState} from 'react';
import {Link} from 'react-router-dom';
import {Code2,Menu,X} from 'lucide-react';
import {useProfile} from '../../hooks/usePortfolio';

const links=[['À propos','#apropos'],['Expérience','#experience'],['Compétences','#competences'],['Services','#services'],['Projets','#projets'],['Galerie','#galerie'],['CV','#cv'],['Contact','#contact']];
export function Header(){
 const [open,setOpen]=useState(false);
 const [activeSection,setActiveSection]=useState('');
 const {data:profile}=useProfile();
 const initials=(profile?.fullName||'Portfolio').trim().charAt(0).toUpperCase();
 useEffect(()=>{
  const sections=links.map(([,href])=>document.querySelector(href)).filter(Boolean);
  const observer=new IntersectionObserver(entries=>{
   const visible=entries.filter(entry=>entry.isIntersecting).sort((a,b)=>b.intersectionRatio-a.intersectionRatio)[0];
   if(visible)setActiveSection(`#${visible.target.id}`);
  },{rootMargin:'-25% 0px -65% 0px',threshold:[0,.25,.5,.75,1]});
  sections.forEach(section=>observer.observe(section));
  return ()=>observer.disconnect();
 },[]);
 useEffect(()=>{
  if(!open)return undefined;
  const closeOnEscape=event=>{if(event.key==='Escape')setOpen(false);};
  document.addEventListener('keydown',closeOnEscape);
  return ()=>document.removeEventListener('keydown',closeOnEscape);
 },[open]);
 return <header className="header"><div className="container header-inner">
  <Link to="/#top" className="brand"><span className="brand-icon">{initials}</span><span className="brand-name">{profile?.fullName||'Portfolio'}</span></Link>
  <button className="menu-button" onClick={()=>setOpen(!open)} aria-label={open?'Fermer le menu':'Ouvrir le menu'} aria-expanded={open}>{open?<X/>:<Menu/>}</button>
  <nav id="main-navigation" className={open?'nav open':'nav'} aria-label="Navigation principale">{links.map(([title,href])=><a href={`/${href}`} key={href} aria-current={activeSection===href?'location':undefined} onClick={()=>setOpen(false)}>{title}</a>)}<Link to="/admin/login" className="admin-link" onClick={()=>setOpen(false)}>Espace admin</Link></nav>
 </div></header>;
}
