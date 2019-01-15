--STATEMENT createCertification
INSERT INTO certifications (
  user_id,
  name,
  created_date,
  updated_date,
  authority,
  license_number,
  acquired_date,
  expired_date,
  website
) VALUES (
  :user_id,
  :name,
  :created_date,
  :updated_date,
  :authority,
  :license_number,
  :acquired_date,
  :expired_date,
  :website
);

--STATEMENT readCertification
SELECT * FROM certifications WHERE id = :id;

--STATEMENT readManyCertifications
SELECT * FROM certifications WHERE user_id = :user_id;

--STATEMENT deleteCertification
DELETE FROM certifications WHERE id = :id;

--STATEMENT updateCertification
UPDATE certifications SET
  user_id = :user_id,
  name = :name,
  updated_date = :updated_date,
  authority = :authority,
  license_number = :license_number,
  acquired_date = :acquired_date,
  expired_date = :expired_date,
  website = :website
WHERE
  id = :id;