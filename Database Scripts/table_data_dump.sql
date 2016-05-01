# User data dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/USER_DATA.csv' 
INTO TABLE match_making.users 
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(username,name,surname, email, average_rating, last_login);

# Change usernames to be user || id
UPDATE match_making.users
SET username = CONCAT('user', user_id)
WHERE user_id != 0;

# Password dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/PASSWORD_DATA.csv' 
INTO TABLE match_making.passwords
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(user_id, password, salt);

# Strip of the last space from the password. Run if passwords are not working
UPDATE match_making.passwords
SET password = SUBSTRING(password, 1, CHAR_LENGTH(password)-1);

# User Qualification dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/QUALIFICATION_DATA.csv' 
INTO TABLE match_making.user_qualifications
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(user_id, qualification_level_id, subject);

# User Interest dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/INTEREST_DATA.csv' 
INTO TABLE match_making.user_interests
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(user_id, interest);

# User Skill dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/SKILL_DATA.csv' 
INTO TABLE match_making.user_skills
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(user_id, skill_id, months_of_experience);

# Project dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/PROJECT_DATA.csv' 
INTO TABLE match_making.projects
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(name, description, project_start, estimated_end, location_lat, location_lon, project_owner);

# Project Interests dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/PROJECT_INTEREST_DATA.csv' 
INTO TABLE match_making.project_interests
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(project_id, interest);

# Project Roles dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/PROJECT_ROLES_DATA.csv' 
INTO TABLE match_making.project_roles
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(project_id, role_id);

# Role Skill dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/ROLE_SKILL_DATA.csv' 
INTO TABLE match_making.role_skills
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(project_role_id, skill_id, required);

# Role Qualification dump
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/ROLE_QUALIFICATION_DATA.csv' 
INTO TABLE match_making.role_qualifications
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' 
(project_role_id, qualification_level_id, subject, required);

commit;

