import {useState} from 'react';
import {Link} from 'react-router-dom';
import {Code2,Menu,X} from 'lucide-react';
const links=[['À propos','#about'],['Compétences','#skills'],['Expériences','#experience'],['Projets','#projects'],['Services','#services'],['Contact','#contact']];
export function Header(){const [open,setOpen]=useState(false);return <header className="header"><div className="container header-inner"><Link to="/" className="brand"><span className="brand-icon"><Code2/></span> Portfolio<span className="accent">.</span></Link><button className="menu-button" onClick={()=>setOpen(!open)} aria-label="Menu">{open?<X/>:<Menu/>}</button><nav className={open?'nav open':'nav'}>{links.map(([t,h])=><a href={h} key={h} onClick={()=>setOpen(false)}>{t}</a>)}<Link to="/admin/login" className="admin-link" onClick={()=>setOpen(false)}>Espace admin</Link></nav></div></header>;}
