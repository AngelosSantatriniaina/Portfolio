import {useEffect, useRef, useState} from 'react';
import {ArrowLeft,ArrowRight,ArrowUpRight,FolderGit2,Github,Images,X} from 'lucide-react';
import {useProjects} from '../../hooks/usePortfolio';
import {SectionTitle} from '../ui/SectionTitle';
import {assetUrl} from '../../api/http';

const demo=[
  {id:1,title:'Portfolio Full Stack',category:'Application web',description:'Portfolio dynamique avec React, API Spring Boot et PostgreSQL.',technologies:['React','Spring Boot','PostgreSQL','Docker']},
  {id:2,title:'Assistant d’élevage',category:'IA / RAG',description:'Assistant documentaire pour retrouver des informations dans des documents spécialisés.',technologies:['Python','FastAPI','RAG','ChromaDB']},
  {id:3,title:'Gestion RH',category:'Application métier',description:'Gestion des employés et opérations avec une architecture organisée.',technologies:['Java','SQL','Maven']},
];

function ProjectGallery({gallery,onClose,onSelect}) {
  const {title,paths,index}=gallery;
  const changeIndex=delta=>onSelect((index+delta+paths.length)%paths.length);
  const dialogRef=useRef(null);
  const actionsRef=useRef({onClose,onSelect,gallery});
  actionsRef.current={onClose,onSelect,gallery};

  useEffect(()=>{
    const previousOverflow=document.body.style.overflow;
    const previousFocus=document.activeElement;
    document.body.style.overflow='hidden';
    dialogRef.current?.focus();
    const onKeyDown=event=>{
      const current=actionsRef.current;
      if(event.key==='Escape')current.onClose();
      if(event.key==='ArrowLeft'){
        const {index:currentIndex,paths:currentPaths}=current.gallery;
        current.onSelect((currentIndex-1+currentPaths.length)%currentPaths.length);
      }
      if(event.key==='ArrowRight'){
        const {index:currentIndex,paths:currentPaths}=current.gallery;
        current.onSelect((currentIndex+1)%currentPaths.length);
      }
      if(event.key==='Tab'&&dialogRef.current){
        const focusable=dialogRef.current.querySelectorAll('button:not(:disabled)');
        const first=focusable[0];
        const last=focusable[focusable.length-1];
        if(event.shiftKey&&document.activeElement===first){event.preventDefault();last?.focus();}
        else if(!event.shiftKey&&document.activeElement===last){event.preventDefault();first?.focus();}
      }
    };
    document.addEventListener('keydown',onKeyDown);
    return ()=>{
      document.body.style.overflow=previousOverflow;
      document.removeEventListener('keydown',onKeyDown);
      previousFocus?.focus();
    };
  },[]);

  return <div className="project-lightbox" role="presentation" onMouseDown={event=>{if(event.target===event.currentTarget)onClose();}}>
    <section ref={dialogRef} className="project-lightbox-dialog" role="dialog" aria-modal="true" aria-labelledby="project-lightbox-title" tabIndex={-1}>
      <header className="lightbox-header"><div><h2 id="project-lightbox-title">{title}</h2><p>Image {index+1} sur {paths.length}</p></div>
        <button type="button" className="lightbox-close" onClick={onClose} aria-label="Fermer la galerie"><X/></button>
      </header>
      <div className="lightbox-stage">
        <img src={assetUrl(paths[index])} alt={`${title} — image ${index+1}`} key={paths[index]}/>
        {paths.length>1&&<>
          <button type="button" className="lightbox-nav lightbox-prev" onClick={()=>changeIndex(-1)} aria-label="Image précédente"><ArrowLeft/></button>
          <button type="button" className="lightbox-nav lightbox-next" onClick={()=>changeIndex(1)} aria-label="Image suivante"><ArrowRight/></button>
        </>}
      </div>
      {paths.length>1&&<div className="lightbox-thumbnails" aria-label="Choisir une image">
        {paths.map((path,thumbnailIndex)=><button type="button" key={`${path}-${thumbnailIndex}`}
          className={thumbnailIndex===index?'selected':''} aria-label={`Afficher l’image ${thumbnailIndex+1}`}
          aria-current={thumbnailIndex===index?'true':undefined} onClick={()=>onSelect(thumbnailIndex)}>
          <img src={assetUrl(path)} alt="" loading="lazy"/>
        </button>)}
      </div>}
      <p className="lightbox-help">Utilise les flèches du clavier pour parcourir les images · Échap pour fermer</p>
    </section>
  </div>;
}

export function ProjectsSection(){
  const query=useProjects();
  const items=Array.isArray(query.data)?query.data:demo;
  const [gallery,setGallery]=useState(null);
  return <section id="projets" className="section">
    <div className="container">
      <SectionTitle eyebrow="Réalisations" title="Projets réalisés" description="Applications de gestion développées en contexte universitaire, client et personnel. Le code source est disponible lorsque le dépôt est public."/>
      <div className="projects">{items.map((project,index)=>{
        const paths=project.imagePaths?.length?project.imagePaths:project.imagePath?[project.imagePath]:[];
        const title=project.title||project.name;
        const technologies=project.technologies||project.techStack||[];
        return <article className="project card" key={project.id||title}>
          <button type="button" className={`project-art art-${index%3}`} disabled={!paths.length}
            aria-label={paths.length?`Agrandir les images du projet ${title}, ${paths.length} image${paths.length===1?'':'s'}`:`Aucune image disponible pour ${title}`}
            onClick={()=>setGallery({title,paths,index:0})}>
            {paths.length?<img src={assetUrl(paths[0])} alt={`Capture d’écran du projet ${title}`} loading="lazy"/>:<FolderGit2 aria-hidden="true"/>}
            <span>{project.featured?'À la une':project.category||'Développement'}{project.year?` · ${project.year}`:''}</span>
            {paths.length>1&&<span className="project-image-count"><Images aria-hidden="true"/>{paths.length}</span>}
            {paths.length>0&&<span className="project-image-hint">Cliquer pour agrandir</span>}
          </button>
          <div className="project-body"><h3>{title}</h3><p>{project.description||project.summary}</p>
            {technologies.length>0&&<div className="tags">{technologies.map(technology=><span key={typeof technology==='string'?technology:technology.name}>{typeof technology==='string'?technology:technology.name}</span>)}</div>}
            <div className="project-links">{project.repositoryUrl&&<a href={project.repositoryUrl} target="_blank" rel="noreferrer"><Github/> Code source</a>}{project.demoUrl&&<a href={project.demoUrl} target="_blank" rel="noreferrer">Démo <ArrowUpRight/></a>}</div>
          </div>
        </article>;
      })}</div>
      {query.isError&&<p className="hint">Projets de démonstration affichés : API non disponible.</p>}
    </div>
    {gallery&&<ProjectGallery gallery={gallery} onClose={()=>setGallery(null)} onSelect={index=>setGallery(current=>({...current,index}))}/>}
  </section>;
}
