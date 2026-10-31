import {useEffect, useState} from 'react';
import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {Link, useNavigate} from 'react-router-dom';
import {BriefcaseBusiness, Code2, FolderKanban, Mail, MessageSquareQuote, Pencil, Plus, ServerCog, Trash2, UserRound, Wrench, LogOut, ImagePlus, X} from 'lucide-react';
import {adminApi} from '../api/adminApi';
import {authApi} from '../api/authApi';
import {assetUrl, getErrorMessage} from '../api/http';

const field = (name, label, type = 'text', options = {}) => ({name, label, type, ...options});

const resources = {
  aboutItems: {
    title: 'À propos',
    singular: 'élément',
    api: adminApi.aboutItems,
    queryKey: ['admin-about-items'],
    publicKeys: [['about-items']],
    fields: [
      field('title', 'Titre', 'text', {required: true}),
      field('description', 'Description', 'textarea', {required: true}),
      field('icon', 'Icône'),
      field('sortOrder', 'Ordre', 'number'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {title: '', description: '', icon: '', sortOrder: 0, published: true},
    label: item => item.title,
    summary: item => item.description,
  },
  experiences: {
    title: 'Expériences',
    singular: 'expérience',
    api: adminApi.experiences,
    queryKey: ['admin-experiences'],
    publicKeys: [['experiences']],
    fields: [
      field('title', 'Poste / titre', 'text', {required: true}),
      field('organization', 'Organisation'),
      field('location', 'Lieu'),
      field('period', 'Période'),
      field('summary', 'Description', 'textarea', {required: true}),
      field('sortOrder', 'Ordre', 'number'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {title: '', organization: '', location: '', period: '', summary: '', sortOrder: 0, published: true},
    label: item => item.title,
    summary: item => [item.organization, item.period, item.location].filter(Boolean).join(' · '),
  },
  skills: {
    title: 'Compétences',
    singular: 'compétence',
    api: adminApi.skills,
    queryKey: ['admin-skills'],
    publicKeys: [['skills']],
    fields: [
      field('name', 'Compétence', 'text', {required: true}),
      field('category', 'Catégorie', 'text', {required: true}),
      field('sortOrder', 'Ordre', 'number'),
      field('level', 'Niveau (%)', 'number'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {name: '', category: '', sortOrder: 0, level: 75, published: true},
    label: item => item.name,
    summary: item => item.category,
  },
  skillCategories: {
    title: 'Catégories de compétences',
    singular: 'catégorie',
    api: adminApi.skillCategories,
    queryKey: ['admin-skill-categories'],
    publicKeys: [['skills']],
    fields: [
      field('name', 'Nom', 'text', {required: true}),
      field('sortOrder', 'Ordre', 'number'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {name: '', sortOrder: 0, published: true},
    label: item => item.name,
    summary: item => `Ordre d’affichage : ${item.sortOrder}`,
  },
  services: {
    title: 'Services',
    singular: 'service',
    api: adminApi.services,
    queryKey: ['admin-services'],
    publicKeys: [['services']],
    fields: [
      field('title', 'Titre', 'text', {required: true}),
      field('description', 'Description', 'textarea', {required: true}),
      field('icon', 'Icône'),
      field('sortOrder', 'Ordre', 'number'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {title: '', description: '', icon: '', sortOrder: 0, published: true},
    label: item => item.title,
    summary: item => item.description,
  },
  projects: {
    title: 'Projets',
    singular: 'projet',
    api: adminApi.projects,
    queryKey: ['admin-projects'],
    publicKeys: [['projects']],
    fields: [
      field('title', 'Titre', 'text', {required: true}),
      field('description', 'Description', 'textarea', {required: true}),
      field('category', 'Catégorie', 'text', {required: true}),
      field('technologies', 'Technologies (séparées par des virgules)'),
      field('repositoryUrl', 'Lien du code source', 'url'),
      field('demoUrl', 'Lien de démonstration', 'url'),
      field('year', 'Année', 'number'),
      field('sortOrder', 'Ordre', 'number'),
      field('featured', 'Projet mis en avant', 'checkbox'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    imageUpload: true,
    initial: {title: '', description: '', category: 'Développement', technologies: '', imagePath: '', imagePaths: [], repositoryUrl: '', demoUrl: '', year: '', sortOrder: 0, featured: false, published: true},
    toForm: item => ({...item, imagePaths: item.imagePaths?.length ? item.imagePaths : item.imagePath ? [item.imagePath] : [], technologies: (item.technologies || []).join(', '), year: item.year ?? ''}),
    toPayload: form => ({...form, year: form.year === '' ? null : Number(form.year), technologies: form.technologies.split(',').map(value => value.trim()).filter(Boolean), imagePath: form.imagePaths[0] || null}),
    uploadImage: adminApi.uploadImage,
    label: item => item.title,
    summary: item => `${item.description}${item.imagePaths?.length ? ` · ${item.imagePaths.length} image${item.imagePaths.length > 1 ? 's' : ''}` : item.imagePath ? ' · 1 image' : ''}`,
  },
  contactLinks: {
    title: 'Coordonnées et liens',
    singular: 'lien',
    api: adminApi.contactLinks,
    queryKey: ['admin-contact-links'],
    publicKeys: [['contact-links']],
    fields: [
      field('label', 'Libellé', 'text', {required: true}),
      field('url', 'Adresse ou URL', 'text', {required: true}),
      field('icon', 'Icône'),
      field('sortOrder', 'Ordre', 'number'),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {label: '', url: '', icon: '', sortOrder: 0, published: true},
    label: item => item.label,
    summary: item => item.url,
  },
  reviews: {
    title: 'Témoignages',
    singular: 'témoignage',
    api: adminApi.reviews,
    queryKey: ['admin-reviews'],
    publicKeys: [['reviews']],
    fields: [
      field('authorName', 'Auteur', 'text', {required: true}),
      field('content', 'Témoignage', 'textarea', {required: true}),
      field('rating', 'Note sur 5', 'number', {required: true}),
      field('published', 'Visible sur le site', 'checkbox'),
    ],
    initial: {authorName: '', content: '', rating: 5, published: true},
    label: item => item.authorName,
    summary: item => item.content,
  },
};

const tabs = [
  ['profile', 'Profil & identité', UserRound],
  ['aboutItems', 'À propos', Code2],
  ['skills', 'Compétences', Code2],
  ['skillCategories', 'Catégories', Wrench],
  ['experiences', 'Expériences', BriefcaseBusiness],
  ['projects', 'Projets', FolderKanban],
  ['services', 'Services', ServerCog],
  ['contactLinks', 'Coordonnées', Mail],
  ['reviews', 'Témoignages', MessageSquareQuote],
  ['messages', 'Messages reçus', Mail],
];

function ContentField({definition, value, onChange}) {
  const {name, label, type, required} = definition;
  if (type === 'checkbox') {
    return <label className="admin-checkbox"><input type="checkbox" checked={Boolean(value)} onChange={event => onChange(name, event.target.checked)}/>{label}</label>;
  }
  const Element = type === 'textarea' ? 'textarea' : 'input';
  return <label>{label}<Element name={name} type={Element === 'input' ? type : undefined} required={required}
    value={value ?? ''} rows={type === 'textarea' ? 4 : undefined}
    onChange={event => onChange(name, event.target.value)}/></label>;
}

function ProjectImagesField({paths, files, disabled, onPathsChange, onFilesChange, onError}) {
  const [filePreviews, setFilePreviews] = useState([]);
  useEffect(() => {
    const urls = files.map(file => URL.createObjectURL(file));
    setFilePreviews(urls);
    return () => urls.forEach(url => URL.revokeObjectURL(url));
  }, [files]);

  const selectImages = event => {
    const selected = Array.from(event.target.files || []);
    event.target.value = '';
    const invalidType = selected.find(file => !['image/jpeg', 'image/png'].includes(file.type));
    if (invalidType) {
      onError('Les images doivent être au format JPEG ou PNG.');
      return;
    }
    const oversized = selected.find(file => file.size > 5 * 1024 * 1024);
    if (oversized) {
      onError(`« ${oversized.name} » dépasse la limite de 5 Mo.`);
      return;
    }
    onFilesChange(previous => [...previous, ...selected]);
    onError('');
  };

  return <div className="project-images-field">
    <span className="field-label">Images du projet</span>
    <p className="muted">Ajoute plusieurs captures ou photos (JPEG/PNG, 5 Mo maximum par image). La première sera la couverture.</p>
    <label className={`image-upload-button${disabled?' disabled':''}`}>
      <ImagePlus aria-hidden="true"/> Ajouter des images
      <input type="file" accept="image/jpeg,image/png" multiple disabled={disabled} onChange={selectImages}/>
    </label>
    {(paths.length > 0 || files.length > 0) ?
      <div className="project-image-list">
        {paths.map((path, index) => <div className="project-image-item" key={`${path}-${index}`}>
          <img src={assetUrl(path)} alt={`Image ${index + 1} du projet`}/>
          <span>{index === 0 ? 'Couverture' : `Image ${index + 1}`}</span>
          <button type="button" className="image-remove-button" disabled={disabled} aria-label={`Retirer l’image ${index + 1}`}
            onClick={() => onPathsChange(paths.filter((_, pathIndex) => pathIndex !== index))}><X/></button>
        </div>)}
        {files.map((file, index) => <div className="project-image-item" key={`${file.name}-${file.lastModified}-${index}`}>
          {filePreviews[index] && <img src={filePreviews[index]} alt={`Aperçu de ${file.name}`}/>}
          <span>{file.name}</span>
          <button type="button" className="image-remove-button" disabled={disabled} aria-label={`Retirer ${file.name}`}
            onClick={() => onFilesChange(previous => previous.filter((_, fileIndex) => fileIndex !== index))}><X/></button>
        </div>)}
      </div> : <p className="image-upload-empty">Aucune image sélectionnée.</p>}
  </div>;
}

function ProfileEditor() {
  const queryClient = useQueryClient();
  const query = useQuery({queryKey: ['admin-profile'], queryFn: adminApi.getProfile});
  const [form, setForm] = useState(null);
  const [notice, setNotice] = useState('');
  const [noticeIsError, setNoticeIsError] = useState(false);
  const [portraitFile, setPortraitFile] = useState(null);
  const [portraitPreview, setPortraitPreview] = useState('');
  const fields = [
    field('fullName', 'Nom complet', 'text', {required: true}),
    field('headline', 'Titre professionnel', 'text', {required: true}),
    field('bio', 'Biographie', 'textarea'),
    field('location', 'Localisation'),
    field('email', 'Adresse e-mail', 'email'),
    field('githubUrl', 'Lien GitHub', 'url'),
    field('linkedinUrl', 'Lien LinkedIn', 'url'),
    field('whatsappUrl', 'Lien WhatsApp', 'url'),
  ];
  useEffect(() => {
    if (!portraitFile) {
      setPortraitPreview('');
      return undefined;
    }
    const previewUrl = URL.createObjectURL(portraitFile);
    setPortraitPreview(previewUrl);
    return () => URL.revokeObjectURL(previewUrl);
  }, [portraitFile]);
  const update = useMutation({
    mutationFn: async ({profile, image}) => {
      let portraitPath = profile.portraitPath;
      if (image) {
        try {
          const uploadedImage = await adminApi.uploadImage(image);
          portraitPath = uploadedImage.path;
        } catch (error) {
          const contextualError = new Error(
            `Téléversement de la photo impossible : ${getErrorMessage(error, 'vérifie le format et la taille de l’image.')}`,
          );
          contextualError.userMessage = contextualError.message;
          throw contextualError;
        }
      }
      try {
        return await adminApi.updateProfile({...profile, portraitPath});
      } catch (error) {
        const contextualError = new Error(
          `Photo téléversée, mais enregistrement du profil impossible : ${getErrorMessage(error, 'réessaie dans un instant.')}`,
        );
        contextualError.userMessage = contextualError.message;
        throw contextualError;
      }
    },
    onSuccess: data => {
      setForm(data);
      setPortraitFile(null);
      setNotice('Profil enregistré.');
      setNoticeIsError(false);
      queryClient.invalidateQueries({queryKey: ['profile']});
    },
    onError: error => {
      setNotice(getErrorMessage(error, 'Téléversement ou enregistrement du profil impossible.'));
      setNoticeIsError(true);
    },
  });
  const current = form || query.data || {};
  const change = (name, value) => setForm(previous => ({...(previous || query.data || {}), [name]: value}));
  const selectPortrait = event => {
    const image = event.target.files?.[0];
    event.target.value = '';
    if (!image) return;
    if (!['image/jpeg', 'image/png'].includes(image.type)) {
      setPortraitFile(null);
      setNotice('Choisis une image au format JPEG ou PNG.');
      setNoticeIsError(true);
      return;
    }
    if (image.size > 5 * 1024 * 1024) {
      setPortraitFile(null);
      setNotice('La taille de l’image ne doit pas dépasser 5 Mo.');
      setNoticeIsError(true);
      return;
    }
    setPortraitFile(image);
    setNotice('');
    setNoticeIsError(false);
  };
  const submit = event => {
    event.preventDefault();
    update.mutate({profile: current, image: portraitFile});
  };
  return <section>
    <div className="panel-heading"><div><h2>Profil & identité</h2><p>Le profil principal est obligatoire et ne peut pas être supprimé.</p></div></div>
    {query.isLoading ? <p>Chargement du profil…</p> : query.isError ? <p className="error">{getErrorMessage(query.error, 'Chargement du profil impossible.')}</p> :
      <form className="form admin-content-form" onSubmit={submit}>
        {fields.map(definition => <ContentField key={definition.name} definition={definition} value={current[definition.name]} onChange={change}/>)}
        <label>Photo de profil
          {(portraitPreview || current.portraitPath) && <img className="admin-portrait-preview"
            src={portraitPreview || assetUrl(current.portraitPath)} alt="Aperçu de la photo de profil"/>}
          <input type="file" accept="image/jpeg,image/png" disabled={update.isPending} onChange={selectPortrait}/>
          <small className="muted">{portraitFile ? `${portraitFile.name} — prête à être téléversée` : 'Formats JPEG ou PNG, 5 Mo maximum.'}</small>
        </label>
        {notice && <p className={noticeIsError ? 'error' : 'success'}>{notice}</p>}
        <button className="button primary" disabled={update.isPending}>{update.isPending ? 'Téléversement et enregistrement…' : 'Enregistrer le profil'}</button>
      </form>}
  </section>;
}

function ResourceManager({config}) {
  const queryClient = useQueryClient();
  const query = useQuery({queryKey: config.queryKey, queryFn: config.api.list});
  const [form, setForm] = useState(config.initial);
  const [imageFiles, setImageFiles] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [notice, setNotice] = useState('');
  const [noticeIsError, setNoticeIsError] = useState(false);
  const save = useMutation({
    mutationFn: async ({id, payload, files}) => {
      if (config.imageUpload && files.length) {
        try {
          const uploadedImages = [];
          for (const file of files) uploadedImages.push(await config.uploadImage(file));
          payload.imagePaths = [...(payload.imagePaths || []), ...uploadedImages.map(image => image.path)];
          payload.imagePath = payload.imagePaths[0] || null;
        } catch (error) {
          const contextualError = new Error(`Téléversement des images impossible : ${getErrorMessage(error, 'vérifie le format et la taille des fichiers.')}`);
          contextualError.userMessage = contextualError.message;
          throw contextualError;
        }
      }
      try {
        return id ? await config.api.update(id, payload) : await config.api.create(payload);
      } catch (error) {
        const contextualError = new Error(
          `${config.imageUpload && files.length ? 'Images téléversées, mais ' : ''}enregistrement de ${config.singular} impossible : ${getErrorMessage(error, 'réessaie dans un instant.')}`,
        );
        contextualError.userMessage = contextualError.message;
        throw contextualError;
      }
    },
    onSuccess: () => {
      setForm(config.initial);
      setImageFiles([]);
      setEditingId(null);
      setNotice(`${config.singular[0].toUpperCase()}${config.singular.slice(1)} enregistré(e).`);
      setNoticeIsError(false);
      queryClient.invalidateQueries({queryKey: config.queryKey});
      config.publicKeys.forEach(queryKey => queryClient.invalidateQueries({queryKey}));
    },
    onError: error => {
      setNotice(getErrorMessage(error, `Enregistrement de ${config.singular} impossible.`));
      setNoticeIsError(true);
    },
  });
  const remove = useMutation({
    mutationFn: config.api.remove,
    onSuccess: () => {
      setNotice(`${config.singular[0].toUpperCase()}${config.singular.slice(1)} supprimé(e).`);
      setNoticeIsError(false);
      queryClient.invalidateQueries({queryKey: config.queryKey});
      config.publicKeys.forEach(queryKey => queryClient.invalidateQueries({queryKey}));
    },
    onError: error => {
      setNotice(getErrorMessage(error, `Suppression de ${config.singular} impossible.`));
      setNoticeIsError(true);
    },
  });
  const change = (name, value) => setForm(previous => ({...previous, [name]: value}));
  const edit = item => {
    setForm(config.toForm ? config.toForm(item) : {...config.initial, ...item});
    setImageFiles([]);
    setEditingId(item.id);
    setNotice('');
    setNoticeIsError(false);
  };
  const submit = event => {
    event.preventDefault();
    const payload = config.toPayload ? config.toPayload(form) : {...form};
    config.fields.filter(item => item.type === 'number').forEach(item => {
      if (payload[item.name] !== '' && payload[item.name] != null) payload[item.name] = Number(payload[item.name]);
    });
    save.mutate({id: editingId, payload, files: imageFiles});
  };
  return <section>
    <div className="panel-heading"><div><h2>{config.title}</h2><p>Ajoute, modifie, supprime ou masque les éléments du site.</p></div></div>
    <form className="form admin-content-form" onSubmit={submit}>
      <h3>{editingId ? `Modifier ${config.singular}` : `Ajouter ${config.singular}`}</h3>
      {config.fields.map(definition => <ContentField key={definition.name} definition={definition} value={form[definition.name]} onChange={change}/>)}
      {config.imageUpload&&<ProjectImagesField paths={form.imagePaths||[]} files={imageFiles} disabled={save.isPending}
        onPathsChange={paths=>change('imagePaths',paths)} onFilesChange={setImageFiles}
        onError={message=>{setNotice(message);setNoticeIsError(Boolean(message));}}/>}
      {notice && <p className={noticeIsError ? 'error' : 'success'}>{notice}</p>}
      <div className="admin-actions">
        <button className="button primary" disabled={save.isPending}>{save.isPending ? 'Enregistrement…' : editingId ? 'Enregistrer les modifications' : 'Ajouter'}</button>
        {editingId && <button className="button secondary" type="button" onClick={() => {setForm(config.initial);setImageFiles([]);setEditingId(null);setNotice('');}}>Annuler</button>}
      </div>
    </form>
    <h3 className="list-title">{config.title} existant(e)s</h3>
    {query.isLoading ? <p>Chargement…</p> : query.isError ? <p className="error">{getErrorMessage(query.error, 'Chargement impossible.')}</p> :
      !(query.data || []).length ? <p className="muted">Aucun élément enregistré.</p> :
        query.data.map(item => <article className="admin-item" key={item.id}>
          <div><strong>{config.label(item)}</strong>{config.summary(item) && <p>{config.summary(item)}</p>}
            <small>{item.published ? 'Publié' : 'Masqué'}{item.sortOrder != null ? ` · ordre ${item.sortOrder}` : ''}</small>
          </div>
          <div className="admin-item-actions">
            <button className="icon-button" onClick={() => edit(item)} aria-label={`Modifier ${config.label(item)}`}><Pencil/></button>
            <button className="delete-button" onClick={() => {
              if (window.confirm(`Supprimer « ${config.label(item)} » ?`)) remove.mutate(item.id);
            }} aria-label={`Supprimer ${config.label(item)}`}><Trash2/></button>
          </div>
        </article>)}
  </section>;
}

function MessagesManager() {
  const queryClient = useQueryClient();
  const query = useQuery({queryKey: ['admin-messages'], queryFn: adminApi.getMessages});
  const [notice, setNotice] = useState('');
  const refresh = () => queryClient.invalidateQueries({queryKey: ['admin-messages']});
  const read = useMutation({mutationFn: adminApi.markMessageRead, onSuccess: refresh, onError: error => setNotice(getErrorMessage(error))});
  const remove = useMutation({mutationFn: adminApi.deleteMessage, onSuccess: refresh, onError: error => setNotice(getErrorMessage(error))});
  return <section>
    <div className="panel-heading"><div><h2>Messages reçus</h2><p>Consulte et supprime les messages envoyés depuis le formulaire.</p></div></div>
    {notice && <p className="error">{notice}</p>}
    {query.isLoading ? <p>Chargement…</p> : query.isError ? <p className="error">{getErrorMessage(query.error, 'Chargement des messages impossible.')}</p> :
      !(query.data || []).length ? <p className="muted">Aucun message reçu.</p> :
        query.data.map(message => <article className="admin-item" key={message.id}>
          <div><strong>{message.subject}</strong><p>{message.name} · <a href={`mailto:${message.email}`}>{message.email}</a></p>
            <p>{message.message}</p><small>{new Date(message.createdAt).toLocaleString()} · {message.readByAdmin ? 'Lu' : 'Non lu'}</small>
          </div>
          <div className="admin-item-actions">
            {!message.readByAdmin && <button className="button secondary" onClick={() => read.mutate(message.id)}>Marquer comme lu</button>}
            <button className="delete-button" aria-label="Supprimer le message" onClick={() => {
              if (window.confirm('Supprimer ce message ?')) remove.mutate(message.id);
            }}><Trash2/></button>
          </div>
        </article>)}
  </section>;
}

export function AdminDashboardPage() {
  const [tab, setTab] = useState('profile');
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const [logoutError, setLogoutError] = useState('');
  const active = tabs.find(([key]) => key === tab);
  async function logout() {
    try {
      await authApi.logout();
      queryClient.clear();
      navigate('/admin/login', {replace: true});
    } catch (error) {
      setLogoutError(getErrorMessage(error, 'Déconnexion impossible.'));
    }
  }
  return <section className="admin-page"><div className="container">
    <div className="admin-heading"><div><span className="eyebrow">ESPACE ADMINISTRATEUR</span><h1>Tableau de bord</h1><p>Gère tout le contenu de ton portfolio.</p></div>
      <button className="button secondary" onClick={logout}><LogOut/> Déconnexion</button>
    </div>
    {logoutError && <p className="error">{logoutError}</p>}
    <div className="admin-layout">
      <aside className="admin-nav">{tabs.map(([key, title, Icon]) =>
        <button key={key} className={tab === key ? 'selected' : ''} onClick={() => setTab(key)}><Icon/> {title}</button>)}
        <Link to="/"><FolderKanban/> Voir le site</Link>
      </aside>
      <div className="admin-panel">
        {tab === 'profile' ? <ProfileEditor/> : tab === 'messages' ? <MessagesManager/> : <ResourceManager key={tab} config={resources[tab]}/>}
      </div>
    </div>
  </div></section>;
}
