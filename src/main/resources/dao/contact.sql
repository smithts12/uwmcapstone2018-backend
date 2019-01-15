--STATEMENT createContact
INSERT INTO contacts (
  user_id,
  company_id,
  position,
  first_name,
  last_name,
  email,
  phone_number,
  notes,
  created_date
) VALUES (
  :user_id,
  :company_id,
  :position,
  :first_name,
  :last_name,
  :email,
  :phone_number,
  :notes,
  :created_date
);

--STATEMENT readContact
SELECT * FROM contacts WHERE id = :id;

--STATEMENT readManyContacts
SELECT * FROM contacts WHERE user_id = :user_id;

--STATEMENT deleteContact
DELETE FROM contacts WHERE id = :id;

--STATEMENT updateContact
UPDATE contacts SET
  user_id = :user_id,
  company_id = :company_id,
  position = :position,
  first_name = :first_name,
  last_name = :last_name,
  email = :email,
  phone_number = :phone_number,
  notes = :notes,
  updated_date = :updated_date
WHERE
  id = :id;