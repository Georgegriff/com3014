#Patch Script to load data into all of the tables

# Populate the skills table from the skills.csv file
# Just change the file path location to be the path of the file
LOAD DATA LOCAL INFILE 'C:/Users/Daniel/Desktop/skills.csv' INTO TABLE match_making.skills (skill);

# Populate Roles Table
INSERT INTO match_making.roles(name, payment, payment_type) VALUES
  ('Junior Developer', 1000.00, 'TOTAL')
, ('Senior Developer', 2000.00, 'TOTAL')
, ('Project Manager', 2000.00, 'TOTAL')
, ('Software Tester', 1000.00, 'TOTAL')
, ('Developer', 1500.00, 'TOTAL')
, ('UX Designer', 1000.00, 'TOTAL')
, ('Network Architect', 2000.00, 'TOTAL')
, ('Graphic Designer', 500.00, 'TOTAL')
, ('Business Analyst', 1000.00, 'TOTAL')
, ('Database Administrator', 1000.00, 'TOTAL');

# Populate Qualification Levels Table
INSERT INTO match_making.qualification_levels(qualification_level) VALUES
  ('Bachelor''s Degree')
, ('Advanced Level')
, ('Diploma')
, ('GSCE');


# Populate the Users Table
INSERT INTO match_making.users(username, forename, surname, email) VALUES
  ('user1', 'Dan', 'Ashworth', 'dan@google.com')
, ('user2', 'George', 'Griffiths', 'george@google.com')
, ('user3', 'Sam', 'Waters', 'sam@google.com')
, ('user4', 'Elliot', 'Gray', 'elliot@google.com')
, ('user5', 'Joe', 'Bloggs', 'joe@google.com');

# Populate the User Qualifications Table
INSERT INTO match_making.user_qualifications(user_id, qualification_level_id, subject) VALUES
  (1, 1, 'Computer Science')
, (2, 1, 'Computer Science')
, (3, 1, 'Computer Science')
, (4, 1, 'Computer Science')
, (5, 1, 'Business');

# Populate the Users interests Table
INSERT INTO match_making.user_interests(user_id, interest) VALUES
  (1, 'Java')
, (2, 'C++')
, (2, 'JavaScript')
, (3, 'Java')
, (4, 'Oracle')
, (5, 'Analytics');

# Populate the Users Skills
INSERT INTO match_making.user_skills(user_id, skill_id) VALUES
  (1, 1)
, (1, 2)
, (1, 5)
, (2, 18)
, (2, 1)
, (2, 49)
, (3, 1)
, (3,5)
, (3, 81)
, (4, 1)
, (4, 27)
, (4, 18)
, (5, 8)
, (5, 39)
, (5, 59);

# Populate some dummy projects
INSERT INTO match_making.projects(name, description, project_start, estimated_end, location_lat, location_lon) VALUES
  ('Project 1', 'The first project in our system', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 MONTH), 51.23622,-0.570409, 1)
, ('Project 2', 'The second project in our system', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 17 WEEK), 51.23622,-0.570409, 2)
, ('Project 3', 'The third project in our system', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 5 MONTH), 51.23622,-0.570409, 2)
, ('Project 4', 'The fourth project in our system', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 57 DAY), 51.23622,-0.570409, 4)
, ('Project 5', 'The fifth project in our system', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 YEAR), 51.23622,-0.570409, 5);

# Populate Project Roles
INSERT INTO match_making.project_role(project_id, role_id) VALUES
  (1, 1)
, (1, 2)
, (1, 3)
, (2, 2)
, (2, 6)
, (2, 7)
, (3, 2)
, (3, 8)
, (3, 3)
, (4, 1)
, (4, 2)
, (4, 3)
, (5, 7)
, (5, 5)
, (5, 4);

# Populate Project Interests
INSERT INTO match_making.project_interests(project_id, interest) VALUES 
  (1, 'Java')
, (1, 'Spring')
, (2, 'HTML')
, (2, 'CSS')
, (3, 'Java')
, (3, 'Oracle')
, (4, 'C++')
, (4, 'C')
, (5, 'MySQL')
, (5, 'Java');

# Commit changes
COMMIT;