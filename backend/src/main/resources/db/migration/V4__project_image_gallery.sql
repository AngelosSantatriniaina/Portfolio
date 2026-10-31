CREATE TABLE project_images (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    image_path VARCHAR(255) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0
);

INSERT INTO project_images (project_id, image_path, sort_order)
SELECT id, image_path, 0
FROM projects
WHERE image_path IS NOT NULL AND image_path <> '';
