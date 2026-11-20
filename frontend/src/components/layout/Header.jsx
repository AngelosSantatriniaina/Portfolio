import {useEffect,useState} from 'react';
import {Link} from 'react-router-dom';
import {Menu,Moon,Sun,X} from 'lucide-react';
import {useProfile} from '../../hooks/usePortfolio';
import {assetUrl} from '../../api/http';

const links=[['À propos','#apropos'],['Expérience','#experience'],['Compétences','#competences'],['Services','#services'],['Projets','#projets'],['CV','#cv'],['Contact','#contact']];
export function Header(){
 const [open,setOpen]=useState(false);
 const [theme,setTheme]=useState(()=>localStorage.getItem('portfolio-theme')||'light');
 const [effectiveTheme,setEffectiveTheme]=useState(()=>window.matchMedia('(prefers-color-scheme: light)').matches?'light':'dark');
 const [activeSection,setActiveSection]=useState('');
 const {data:profile}=useProfile();
 const initials=(profile?.fullName||'Portfolio').trim().charAt(0).toUpperCase();
 const portraitUrl=assetUrl(profile?.portraitPath);
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
 useEffect(()=>{
  const root=document.documentElement;
  const media=window.matchMedia('(prefers-color-scheme: light)');
  const apply=()=>{
   const nextTheme=theme==='system'?(media.matches?'light':'dark'):theme;
   root.dataset.theme=nextTheme;
   setEffectiveTheme(nextTheme);
  };
  apply();
  media.addEventListener?.('change',apply);
  return ()=>media.removeEventListener?.('change',apply);
 },[theme]);
 const toggleTheme=()=>{
  const next=theme==='light'?'dark':theme==='dark'?'system':'light';
  setTheme(next);
  localStorage.setItem('portfolio-theme',next);
 };
 useEffect(()=>{document.body.style.overflow=open?'hidden':'';return()=>{document.body.style.overflow='';};},[open]);
 return <header className="header"><div className="container header-inner">
  <Link to="/#top" className="brand"><span className="brand-icon">{initials}</span><span className="brand-name">{profile?.fullName||'Portfolio'}</span></Link>
  <nav id="main-navigation" className={open?'nav open':'nav'} aria-label="Navigation principale">{links.map(([title,href])=><a href={`/${href}`} key={href} aria-current={activeSection===href?'location':undefined} onClick={()=>setOpen(false)}>{title}</a>)}</nav>
  <button className="theme-button" onClick={toggleTheme} aria-label={`Activer le thème ${theme==='light'?'sombre':theme==='dark'?'système':'clair'}`} title="Changer de thème">{effectiveTheme==='light'?<Moon aria-hidden="true"/>:<Sun aria-hidden="true"/>}</button>
  <button className="menu-button" onClick={()=>setOpen(!open)} aria-label={open?'Fermer le menu':'Ouvrir le menu'} aria-expanded={open} aria-controls="main-navigation">{open?<X/>:<Menu/>}</button>
  <Link to="/admin/login" className="admin-link" aria-label="Connexion administrateur" title="Connexion administrateur">{portraitUrl?<img className="admin-avatar" src={portraitUrl} alt="" width="30" height="30"/>:<span className="admin-avatar-placeholder" aria-hidden="true">{initials}</span>}</Link>
 </div></header>;
}
