INSERT INTO profiles (id,full_name,headline,bio,location,email,github_url,whatsapp_url)
VALUES (1,'RANDRIANTSIANALA Santatriniaina Angelos',
'Développeur Fullstack Java & Administrateur Systèmes et Réseaux',
'Développeur Java Fullstack et administrateur systèmes et réseaux. Conception d’applications web et desktop, API REST, bases de données et administration Linux/réseaux.',
'Moramanga, Madagascar','angelossantatriniaina@gmail.com',
'https://github.com/AngelosSantatriniaina','https://wa.me/261345338020');

INSERT INTO experiences (title,organization,location,period_label,summary,sort_order) VALUES
('Développeur — Application GRH (personnel & congés)','Projet universitaire','Madagascar','2024','Développement d’une API REST avec Spring Boot pour la gestion du personnel et des congés.',1),
('Développeur — Gestion de quincaillerie','Projet','Moramanga, Madagascar','2024','Application desktop pour gérer les articles, le stock, les ventes et les rapports.',2),
('Développeur — Création de plans de maison 2D','Projet personnel','Madagascar','2023','Outil de dessin 2D : murs, pièces, cotations et sauvegarde/réouverture des plans.',3),
('Projets personnels de développement','Projets personnels','Madagascar','Continu','Conception et amélioration continue de projets logiciels.',4);

INSERT INTO skill_categories(name,sort_order) VALUES
('Développement',1),('Systèmes & Réseaux',2);

INSERT INTO skills(category_id,name,sort_order)
SELECT c.id, s.name, s.ord FROM skill_categories c
JOIN (VALUES
 ('Développement','Java',1),('Développement','Spring Boot',2),('Développement','API REST',3),
 ('Développement','Hibernate / JPA',4),('Développement','React JS',5),('Développement','JavaFX',6),
 ('Développement','Flutter',7),('Développement','HTML / CSS',8),('Développement','JavaScript',9),
 ('Développement','MySQL',10),('Développement','PostgreSQL',11),('Développement','Git',12),('Développement','Python',13),
 ('Systèmes & Réseaux','Linux (Debian / Ubuntu)',1),('Systèmes & Réseaux','Windows Server',2),
 ('Systèmes & Réseaux','Nginx / Apache',3),('Systèmes & Réseaux','Docker',4),('Systèmes & Réseaux','TCP/IP · VLAN',5),
 ('Systèmes & Réseaux','VPN / IPsec',6),('Systèmes & Réseaux','pfSense / Pare-feu',7),
 ('Systèmes & Réseaux','Active Directory',8),('Systèmes & Réseaux','Zabbix / Grafana',9),
 ('Systèmes & Réseaux','Sauvegarde & PRA',10),('Systèmes & Réseaux','Bash scripting',11),('Systèmes & Réseaux','SSH / TLS',12)
) AS s(category_name,name,ord) ON c.name=s.category_name;

INSERT INTO services(title,description,icon,sort_order) VALUES
('Applications web','Développement d’applications web avec React et Spring Boot.','monitor',1),
('Applications desktop','Applications de gestion desktop avec Java et JavaFX.','app-window',2),
('Applications mobiles','Conception d’applications mobiles avec Flutter.','smartphone',3),
('API REST & bases de données','Conception d’API REST, modélisation et intégration de bases de données.','database',4),
('Systèmes & réseaux','Administration systèmes, réseaux, sécurité et accompagnement technique.','network',5);

INSERT INTO projects(title,description,repository_url,demo_url,project_year,sort_order,featured,published) VALUES
('Gestion des ressources humaines (GRH)','Application de gestion du personnel et des congés. Stack : Spring Boot, Java, MySQL et React JS.',NULL,NULL,2024,1,TRUE,TRUE),
('Gestion de quincaillerie','Application desktop pour les articles, le stock, les ventes et les rapports. Stack : JavaFX, Java et MySQL.',NULL,NULL,2024,2,TRUE,TRUE),
('PlanMaison2D','Application de dessin de plans de maison en 2D. Stack : Java et JavaFX.', 'https://github.com/AngelosSantatriniaina/PlanMaison2D.git',NULL,2023,3,FALSE,TRUE);

INSERT INTO project_technologies(project_id,technology)
SELECT p.id, t.technology FROM projects p
JOIN (VALUES
 ('Gestion des ressources humaines (GRH)','Spring Boot'),('Gestion des ressources humaines (GRH)','Java'),
 ('Gestion des ressources humaines (GRH)','MySQL'),('Gestion des ressources humaines (GRH)','React JS'),
 ('Gestion de quincaillerie','JavaFX'),('Gestion de quincaillerie','Java'),('Gestion de quincaillerie','MySQL'),
 ('PlanMaison2D','Java'),('PlanMaison2D','JavaFX')
) AS t(project_title,technology) ON p.title=t.project_title;

INSERT INTO contact_links(label,url,icon,sort_order) VALUES
('GitHub','https://github.com/AngelosSantatriniaina','github',1),
('WhatsApp','https://wa.me/261345338020','message-circle',2),
('Email','mailto:angelossantatriniaina@gmail.com','mail',3);
