CREATE TABLE addresses (
  id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT(20) NOT NULL,
  street_1 VARCHAR(255) DEFAULT NULL,
  street_2 VARCHAR(255) DEFAULT NULL,
  city VARCHAR(255) DEFAULT NULL,
  state VARCHAR(255) DEFAULT NULL,
  zip_code VARCHAR(255) DEFAULT NULL,
  created_date BIGINT(20) NOT NULL,
  updated_date BIGINT(20) DEFAULT NULL
);