--STATEMENT createUser
INSERT INTO users (
  email,
  password,
  title,
  first_name,
  middle_name,
  last_name,
  mobile_phone,
  home_phone,
  website,
  created_date,
  updated_date
) VALUES (
  :email,
  :password,
  :title,
  :first_name,
  :middle_name,
  :last_name,
  :mobile_phone,
  :home_phone,
  :website,
  :created_date,
  :updated_date
);

--STATEMENT readUser
SELECT * FROM users WHERE id = :id;

--STATEMENT readUserByEmail
SELECT * FROM users WHERE email = :email;

--STATEMENT deleteUser
DELETE FROM users WHERE id = :id;

--STATEMENT deleteUserByEmail
DELETE FROM users WHERE email = :email;

--STATEMENT updateUser
UPDATE users SET
  email = :email,
  password = :password,
  title = :title,
  first_name = :first_name,
  middle_name = :middle_name,
  last_name = :last_name,
  mobile_phone = :mobile_phone,
  home_phone = :home_phone,
  website = :website,
  updated_date = :updated_date
WHERE
  id = :id;