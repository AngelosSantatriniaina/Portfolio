CREATE TABLE admin_users (
 id BIGSERIAL PRIMARY KEY,
 email VARCHAR(190) NOT NULL UNIQUE,
 password_hash VARCHAR(100) NOT NULL,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE profiles (
 id BIGINT PRIMARY KEY CHECK (id = 1),
 full_name VARCHAR(180) NOT NULL,
 headline VARCHAR(180) NOT NULL,
 bio VARCHAR(1000),
 location VARCHAR(160),
 email VARCHAR(255),
 github_url VARCHAR(255),
 whatsapp_url VARCHAR(255),
 portrait_path VARCHAR(255)
);
CREATE TABLE experiences (
 id BIGSERIAL PRIMARY KEY,
 title VARCHAR(180) NOT NULL,
 organization VARCHAR(180),
 location VARCHAR(180),
 period_label VARCHAR(100),
 summary VARCHAR(1000),
 sort_order INT NOT NULL DEFAULT 0,
 published BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE skill_categories (
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR(120) NOT NULL UNIQUE,
 sort_order INT NOT NULL DEFAULT 0,
 published BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE skills (
 id BIGSERIAL PRIMARY KEY,
 category_id BIGINT NOT NULL REFERENCES skill_categories(id) ON DELETE CASCADE,
 name VARCHAR(120) NOT NULL,
 sort_order INT NOT NULL DEFAULT 0,
 published BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE services (
 id BIGSERIAL PRIMARY KEY,
 title VARCHAR(180) NOT NULL,
 description VARCHAR(1000) NOT NULL,
 icon VARCHAR(80),
 sort_order INT NOT NULL DEFAULT 0,
 published BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE projects (
 id BIGSERIAL PRIMARY KEY,
 title VARCHAR(180) NOT NULL,
 description VARCHAR(2000) NOT NULL,
 image_path VARCHAR(255),
 repository_url VARCHAR(500),
 demo_url VARCHAR(500),
 project_year INT,
 sort_order INT NOT NULL DEFAULT 0,
 featured BOOLEAN NOT NULL DEFAULT FALSE,
 published BOOLEAN NOT NULL DEFAULT TRUE,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE project_technologies (
 id BIGSERIAL PRIMARY KEY,
 project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
 technology VARCHAR(100) NOT NULL
);
CREATE TABLE contact_links (
 id BIGSERIAL PRIMARY KEY,
 label VARCHAR(100) NOT NULL,
 url VARCHAR(500) NOT NULL,
 icon VARCHAR(80),
 sort_order INT NOT NULL DEFAULT 0,
 published BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE messages (
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR(150) NOT NULL,
 email VARCHAR(190) NOT NULL,
 subject VARCHAR(180) NOT NULL,
 message VARCHAR(5000) NOT NULL,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 read_by_admin BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX idx_experiences_published_order ON experiences(published, sort_order);
CREATE INDEX idx_skills_category ON skills(category_id);
CREATE INDEX idx_projects_published_order ON projects(published, sort_order);
CREATE INDEX idx_messages_created_at ON messages(created_at DESC);
