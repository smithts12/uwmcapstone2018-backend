--STATEMENT createEducation
INSERT INTO educations (
  user_id,
  street_1,
  street_2,
  city,
  state,
  zip_code,
  school_name,
  degree,
  field_of_study,
  start_date,
  end_date,
  created_date
) VALUES (
  :user_id,
  :street_1,
  :street_2,
  :city,
  :state,
  :zip_code,
  :school_name,
  :degree,
  :field_of_study,
  :start_date,
  :end_date,
  :created_date
);

--STATEMENT readEducation
SELECT * FROM educations WHERE id = :id;

--STATEMENT readManyEducations
SELECT * FROM educations WHERE user_id = :user_id;

--STATEMENT deleteEducation
DELETE FROM educations WHERE id = :id;

--STATEMENT updateEducation
UPDATE educations SET
  user_id = :user_id,
  street_1 = :street_1,
  street_2 = :street_2,
  city = :city,
  state = :state,
  zip_code = :zip_code,
  school_name = :school_name,
  degree = :degree,
  field_of_study = :field_of_study,
  start_date = :start_date,
  end_date = :end_date,
  updated_date = :updated_date
WHERE
  id = :id;
