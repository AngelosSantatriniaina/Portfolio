ALTER TABLE profiles ADD COLUMN linkedin_url VARCHAR(255);
ALTER TABLE skills ADD COLUMN level INT NOT NULL DEFAULT 75 CHECK (level BETWEEN 0 AND 100);
ALTER TABLE projects ADD COLUMN category VARCHAR(120) NOT NULL DEFAULT 'Développement';
UPDATE projects SET category = CASE title
    WHEN 'Gestion des ressources humaines (GRH)' THEN 'Application métier'
    WHEN 'Gestion de quincaillerie' THEN 'Application desktop'
    ELSE 'Application Java'
END;

CREATE TABLE about_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(180) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    icon VARCHAR(80),
    sort_order INT NOT NULL DEFAULT 0,
    published BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO about_items (title, description, icon, sort_order, published) VALUES
('Développement', 'Interfaces accessibles et applications robustes.', 'code', 1, TRUE),
('Données', 'API REST et bases relationnelles.', 'database', 2, TRUE),
('Systèmes & réseaux', 'Infrastructure, Linux et déploiement.', 'network', 3, TRUE),
('Amélioration continue', 'Code lisible, apprentissage et résolution de problèmes.', 'sparkles', 4, TRUE);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    author_name VARCHAR(150) NOT NULL,
    content VARCHAR(2000) NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    published BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO reviews (author_name, content, rating, published) VALUES
('Collaboration technique', 'Une approche organisée, avec une attention portée à la qualité du code et à la communication.', 5, TRUE),
('Retour de projet', 'Capacité à apprendre rapidement, tester des solutions et avancer étape par étape.', 5, TRUE);
