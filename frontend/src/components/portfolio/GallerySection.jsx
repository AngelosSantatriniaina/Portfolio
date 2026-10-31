import {Image,MapPin} from 'lucide-react';
import {assetUrl} from '../../api/http';

export function GallerySection({profile}) {
  const portraitUrl=assetUrl(profile?.portraitPath);
  return <section id="galerie" className="section gallery-section">
    <div className="container">
      <div className="section-title"><span className="eyebrow">Galerie</span><h2>En images</h2></div>
      <figure className="gallery-card">
        {portraitUrl?<img src={portraitUrl} alt={`Portrait professionnel de ${profile?.fullName||'RANDRIANTSIANALA Santatriniaina Angelos'}`} loading="lazy"/>:
          <div className="gallery-placeholder"><Image aria-hidden="true"/><span>Ajoutez votre portrait depuis l’espace administrateur.</span></div>}
        <figcaption><strong>Portrait studio</strong><span><MapPin aria-hidden="true"/>Portrait professionnel — {profile?.location||'Moramanga, Madagascar'}.</span></figcaption>
      </figure>
    </div>
  </section>;
}
