--STATEMENT createProject
INSERT INTO projects (
  user_id,
  position_id,
  education_id,
  title,
  description,
  start_date,
  end_date,
  created_date
) VALUES (
  :user_id,
  :position_id,
  :education_id,
  :title,
  :description,
  :start_date,
  :end_date,
  :created_date
);

--STATEMENT readProject
SELECT * FROM projects WHERE id = :id;

--STATEMENT readManyProjects
SELECT * FROM projects WHERE user_id = :user_id;

--STATEMENT deleteProject
DELETE FROM projects WHERE id = :id;

--STATEMENT updateProject
UPDATE projects SET
  user_id = :user_id,
  position_id = :position_id,
  education_id = :education_id,
  title = :title,
  description = :description,
  start_date = :start_date,
  end_date = :end_date,
  updated_date = :updated_date
WHERE
  id = :id;
