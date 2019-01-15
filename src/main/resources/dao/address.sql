--STATEMENT createAddress
INSERT INTO addresses (
  user_id,
  street_1,
  street_2,
  city,
  state,
  zip_code,
  created_date
) VALUES (
  :user_id,
  :street_1,
  :street_2,
  :city,
  :state,
  :zip_code,
  :created_date
);

--STATEMENT readAddress
SELECT * FROM addresses WHERE id = :id;

--STATEMENT readManyAddresses
SELECT * FROM addresses WHERE user_id = :user_id;

--STATEMENT deleteAddress
DELETE FROM addresses WHERE id = :id;

--STATEMENT updateAddress
UPDATE addresses SET
  user_id = :user_id,
  street_1 = :street_1,
  street_2 = :street_2,
  city = :city,
  state = :state,
  zip_code = :zip_code,
  updated_date = :updated_date
WHERE
  id = :id;