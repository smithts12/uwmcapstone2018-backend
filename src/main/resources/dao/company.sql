--STATEMENT createCompany
INSERT INTO companies (
  user_id,
  name,
  street_1,
  street_2,
  city,
  state,
  zip_code,
  phone_number,
  website,
  created_date,
  updated_date
) VALUES (
  :user_id,
  :name,
  :street_1,
  :street_2,
  :city,
  :state,
  :zip_code,
  :phone_number,
  :website,
  :created_date,
  :updated_date
);

--STATEMENT readCompany
SELECT * FROM companies WHERE id = :id;

--STATEMENT readManyCompanies
SELECT * FROM companies WHERE user_id = :user_id;

--STATEMENT deleteCompany
DELETE FROM companies WHERE id = :id;

--STATEMENT updateCompany
UPDATE companies SET
  user_id = :user_id,
  name = :name,
  street_1 = :street_1,
  street_2 = :street_2,
  city = :city,
  state = :state,
  zip_code = :zip_code,
  phone_number = :phone_number,
  website = :website,
  updated_date = :updated_date
WHERE
  id = :id;