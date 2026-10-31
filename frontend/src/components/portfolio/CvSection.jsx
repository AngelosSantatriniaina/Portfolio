import {ArrowUpRight,FileText} from 'lucide-react';

export function CvSection({profile}) {
  const contactUrl=profile?.whatsappUrl|| (profile?.email?`mailto:${profile.email}`:'#contact');
  const contactLabel=profile?.whatsappUrl?'Demander mon CV via WhatsApp':profile?.email?'Demander mon CV par e-mail':'Me contacter';
  return <section id="cv" className="section cv-section">
    <div className="container">
      <div className="cv-card">
        <div className="cv-icon"><FileText aria-hidden="true"/></div>
        <div><span className="eyebrow">Curriculum vitae</span><h2>Tout mon parcours en un fichier PDF</h2>
          <p>Expériences, certifications, stack technique et références : contactez-moi pour recevoir mon CV.</p>
        </div>
        <a className="button primary" href={contactUrl} target={contactUrl.startsWith('http')?'_blank':undefined}
          rel={contactUrl.startsWith('http')?'noreferrer':undefined}>{contactLabel} <ArrowUpRight aria-hidden="true"/></a>
      </div>
    </div>
  </section>;
}
