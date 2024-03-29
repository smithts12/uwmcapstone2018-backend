CREATE TABLE contacts (
  id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
  company_id BIGINT(20) DEFAULT NULL,
  position VARCHAR(255) DEFAULT NULL,
  first_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  phone_number VARCHAR(255) DEFAULT NULL,
  notes VARCHAR(255) DEFAULT NULL,
  created_date BIGINT(20) NOT NULL,
  updated_date BIGINT(20) DEFAULT NULL
);

CREATE TABLE educations (
  id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT(20) NOT NULL,
  address_id BIGINT(20) DEFAULT NULL,
  school_name VARCHAR(255) DEFAULT NULL,
  degree VARCHAR(255) DEFAULT NULL,
  field_of_study VARCHAR(255) DEFAULT NULL,
  start_date BIGINT(20) DEFAULT NULL,
  end_date BIGINT(20) DEFAULT NULL,
  created_date BIGINT(20) NOT NULL,
  updated_date BIGINT(20) DEFAULT NULL
);