# Database Creation 
CREATE DATABASE match_making;

/* Generic Tables */

#Qualifications Table
CREATE TABLE match_making.qualification_levels(
  qualification_id INT NOT NULL AUTO_INCREMENT
, qualification_level VARCHAR(45) NOT NULL UNIQUE
, PRIMARY KEY(qualification_id)
);

#Skills Table
CREATE TABLE match_making.skills(
  skill_id INT NOT NULL AUTO_INCREMENT
, skill VARCHAR(50) NOT NULL UNIQUE
, PRIMARY KEY(skill_id)
);

/* User Tables */

#User Table
CREATE TABLE match_making.users(
  user_id INT NOT NULL AUTO_INCREMENT
, username VARCHAR(45) NOT NULL UNIQUE
, forename VARCHAR(45) NOT NULL
, surname VARCHAR(45) NOT NULL
, email VARCHAR(45) NOT NULL UNIQUE
, average_rating FLOAT 
, last_login DATE
, PRIMARY KEY(user_id)
);

#Passwords Table
CREATE TABLE match_making.passwords(
  user_id INT NOT NULL
, password VARCHAR(45) 
, salt VARCHAR(45) UNIQUE
, jwt_token VARCHAR(45)
, refresh_token VARCHAR(100)
, PRIMARY KEY(user_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);

#User Qualifications Table
CREATE TABLE match_making.user_qualifications(
  user_id INT NOT NULL
, qualification_level_id INT NOT NULL
, subject VARCHAR(45) NOT NULL
, PRIMARY KEY(user_id, qualification_level_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
, FOREIGN KEY (qualification_level_id) REFERENCES match_making.qualification_levels(qualification_id)
);

#User Interests Table
CREATE TABLE match_making.user_interests(
  interest_id INT NOT NULL AUTO_INCREMENT
, user_id INT NOT NULL
, interest VARCHAR(45) NOT NULL
, PRIMARY KEY(interest_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);

#User Skills Table
CREATE TABLE match_making.user_skills(
  user_id INT NOT NULL 
, skill_id INT NOT NULL
, PRIMARY KEY(user_id, skill_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
, FOREIGN KEY (skill_id) REFERENCES match_making.skills(skill_id)
);

/* Role Tables */

#Roles Table
CREATE TABLE match_making.roles(
  role_id INT NOT NULL AUTO_INCREMENT
, name VARCHAR(45) NOT NULL UNIQUE
, payment DECIMAL(6,2) NOT NULL
, payment_type VARCHAR(30) NOT NULL
, PRIMARY KEY (role_id)
);

#Role Skills Table
CREATE TABLE match_making.role_skills(
  role_id INT NOT NULL
, skill_id INT NOT NULL
, required BOOL NOT NULL
, PRIMARY KEY (role_id, skill_id)
, FOREIGN KEY (role_id) REFERENCES match_making.roles(role_id)
, FOREIGN KEY (skill_id) REFERENCES match_making.skills(skill_id)
);

#Role Qualifications Table
CREATE TABLE match_making.role_qualifications(
  role_id INT NOT NULL
, qualification_level_id INT NOT NULL
, subject VARCHAR(45)
, required BOOL NOT NULL
, PRIMARY KEY (role_id, qualification_level_id)
, FOREIGN KEY (role_id) REFERENCES match_making.roles(role_id)
, FOREIGN KEY (qualification_level_id) REFERENCES match_making.qualification_levels(qualification_id)
);

/* Project Tables */

#Projects Table
CREATE TABLE match_making.projects(
  project_id INT NOT NULL AUTO_INCREMENT
, name VARCHAR(100) NOT NULL UNIQUE
, description LONGTEXT NOT NULL
, project_start DATETIME
, estimated_end DATETIME
, location_lat DECIMAL(12, 8)
, location_lon DECIMAL(12,8)
, project_owner INT NOT NULL
, PRIMARY KEY (project_id)
, FOREIGN KEY (project_owner) REFERENCES match_making.users(user_id)
);

#Project Interests Table
CREATE TABLE match_making.project_interests(
  interest_id INT NOT NULL AUTO_INCREMENT
, project_id INT NOT NULL
, interest VARCHAR(45) NOT NULL
, PRIMARY KEY (interest_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
);

#Project Role Skills
CREATE TABLE match_making.project_role(
  project_id INT NOT NULL
, role_id INT NOT NULL
, PRIMARY KEY (project_id, role_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
, FOREIGN KEY (role_id) REFERENCES match_making.roles(role_id)
);

/* Matching tables */

CREATE TABLE match_making.project_matches(
  set_id INT NOT NULL AUTO_INCREMENT
, project_id INT NOT NULL
, cache_expire DATETIME NOT NULL
, status_control CHAR(1)
, PRIMARY KEY (set_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
);

CREATE TABLE match_making.project_sets(
  set_id INT NOT NULL
, user_id INT NOT NULL
, PRIMARY KEY (set_id, user_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
, FOREIGN KEY (set_id) REFERENCES match_making.project_matches(set_id)
);

CREATE TABLE match_making.user_matches(
  set_id INT NOT NULL AUTO_INCREMENT
, user_id INT NOT NULL
, cache_expire DATETIME NOT NULL
, status_control CHAR(1)
, PRIMARY KEY (set_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);

CREATE TABLE match_making.user_sets(
  set_id INT NOT NULL
, project_id INT NOT NULL
, PRIMARY KEY (set_id, project_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
, FOREIGN KEY (set_id) REFERENCES match_making.user_matches(set_id)
);

CREATE TABLE match_making.projects_approved(
  project_id INT NOT NULL
, user_id INT NOT NULL
, PRIMARY KEY (project_id, user_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);

CREATE TABLE match_making.users_approved(
  user_id INT NOT NULL
, project_id INT NOT NULL
, PRIMARY KEY (user_id, project_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);

CREATE TABLE match_making.projects_declined(
  project_id INT NOT NULL
, user_id INT NOT NULL
, PRIMARY KEY (project_id, user_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);

CREATE TABLE match_making.users_declined(
  user_id INT NOT NULL
, project_id INT NOT NULL
, PRIMARY KEY (user_id, project_id)
, FOREIGN KEY (project_id) REFERENCES match_making.projects(project_id)
, FOREIGN KEY (user_id) REFERENCES match_making.users(user_id)
);






