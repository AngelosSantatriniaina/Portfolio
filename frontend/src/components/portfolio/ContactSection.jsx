import {useState} from 'react';
import {CheckCircle2,Github,Linkedin,Mail,MapPin,MessageCircle,Send} from 'lucide-react';
import {SectionTitle} from '../ui/SectionTitle';
import {useSendContact} from '../../hooks/useContact';
import {useContactLinks, useProfile} from '../../hooks/usePortfolio';
import {contactSchema} from '../../schemas/contactSchema';
import {getErrorMessage} from '../../api/http';

const initial={name:'',email:'',subject:'',message:''};
const icons={github:Github,linkedin:Linkedin,mail:Mail,'message-circle':MessageCircle,whatsapp:MessageCircle};

export function ContactSection(){
 const [form,setForm]=useState(initial);
 const [errors,setErrors]=useState({});
 const mutation=useSendContact();
 const profile=useProfile();
 const links=useContactLinks();
 const contactLinks=(links.data||[]).filter(link=>link.url.toLowerCase()!==`mailto:${profile.data?.email||''}`.toLowerCase());
 const update=event=>setForm({...form,[event.target.name]:event.target.value});
 async function submit(event){
  event.preventDefault();
  const result=contactSchema.safeParse(form);
  if(!result.success){const next={};result.error.issues.forEach(issue=>next[issue.path[0]]=issue.message);setErrors(next);return;}
  setErrors({});
  try{await mutation.mutateAsync(result.data);setForm(initial);}catch{}
 }
 return <section id="contact" className="section contact"><div className="container">
  <SectionTitle eyebrow="Échangeons" title="Me contacter" description="Un poste, une mission, une formation ou une application à développer ? Écrivez-moi, je réponds sous 24 h."/>
  <div className="contact-grid"><aside><h3>Créons quelque chose d’utile.</h3><p>Décrivez votre besoin et je vous répondrai dès que possible.</p>
   {profile.data?.email&&<div className="contact-detail"><Mail/><div><small>E-mail</small><a href={`mailto:${profile.data.email}`}>{profile.data.email}</a></div></div>}
   {profile.data?.location&&<div className="contact-detail"><MapPin/><div><small>Localisation</small><span>{profile.data.location}</span></div></div>}
   {contactLinks.map(link=>{const Icon=icons[link.icon?.toLowerCase()]||Mail;return <div className="contact-detail" key={link.id}><Icon/><div><small>{link.label}</small><a href={link.url} target={link.url.startsWith('http')?'_blank':undefined} rel={link.url.startsWith('http')?'noreferrer':undefined}>{link.url}</a></div></div>;})}
  </aside><form className="form" onSubmit={submit} noValidate>
   <div className="form-row">{[['name','Nom complet','Votre nom'],['email','E-mail','vous@exemple.com']].map(([name,label,placeholder])=><label key={name}>{label}<input name={name} value={form[name]} onChange={update} placeholder={placeholder} type={name==='email'?'email':'text'}/>{errors[name]&&<small className="error">{errors[name]}</small>}</label>)}</div>
   <label>Sujet<input name="subject" value={form.subject} onChange={update} placeholder="Objet du message"/>{errors.subject&&<small className="error">{errors.subject}</small>}</label>
   <label>Message<textarea name="message" rows="5" value={form.message} onChange={update} placeholder="Parlez-moi de votre projet…"/>{errors.message&&<small className="error">{errors.message}</small>}</label>
   {mutation.isSuccess&&<p className="success"><CheckCircle2/> Message envoyé avec succès.</p>}
   {mutation.isError&&<p className="error">{getErrorMessage(mutation.error,'Échec de l’envoi du message.')}</p>}
   <button className="button primary" disabled={mutation.isPending}>{mutation.isPending?'Envoi…':'Envoyer le message'} <Send/></button>
  </form></div>
 </div></section>;
}
