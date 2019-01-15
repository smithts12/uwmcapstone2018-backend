--STATEMENT createPosition
INSERT INTO positions (
  user_id,
  name,
  company_id,
  description,
  created_date,
  updated_date,
  start_date,
  end_date,
  start_pay,
  end_pay
) VALUES (
  :user_id,
  :name,
  :company_id,
  :description,
  :created_date,
  :updated_date,
  :start_date,
  :end_date,
  :start_pay,
  :end_pay
);

--STATEMENT readPosition
SELECT * FROM positions WHERE id = :id;

--STATEMENT readManyPositions
SELECT * FROM positions WHERE user_id = :user_id;

--STATEMENT deletePosition
DELETE FROM positions WHERE id = :id;

--STATEMENT updatePosition
UPDATE positions SET
  user_id = :user_id,
  name = :name,
  company_id = :company_id,
  description = :description,
  updated_date = :updated_date,
  start_date = :start_date,
  end_date = :end_date,
  start_pay = :start_pay,
  end_pay = :end_pay
WHERE
  id = :id;