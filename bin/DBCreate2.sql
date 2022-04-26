use bugtracker;

drop table if exists admins;
drop table if exists bug;
drop table if exists TeamMembers;
drop table if exists project;
drop table if exists projectmanagers;
drop table if exists developers;
drop table if exists testers;


create table admins (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	primary key (username)
);

create table projectmanagers (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	primary key (username)
);

create table project (
	projectID varchar(255), 
	projectManagerUName varchar(255), 
	projectName varchar(255), 
	projectDesc varchar(255), 
	projectDeadline DATE,
	primary key (projectID),
	foreign key (projectManagerUName) references projectmanagers(username)
);

create table developers (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	primary key (username)
);

create table testers (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	primary key (username)
);

CREATE TYPE project_status AS ENUM ('INITIATED', 'RESOLVED', 'OVERDUE');
CREATE TYPE project_priority AS ENUM ('HIGH', 'MODERATE', 'LOW');

create table bug (
	bugID varchar(255), 
	projectID varchar(255),
	bugStatus project_status,
	bugPriority project_priority, 
	bugOwnerUName varchar(255), 
	createdDate DATE, 
	resolvedDate DATE, 
	lastUpdatedDate DATE, 
	description varchar(255), 
	resolutionDesc varchar(255),
	primary key (bugID),
	foreign key (bugOwnerUName) references testers(username),
	foreign key (projectID) references project(projectID)
);


create table TeamMembers (
	id int,
	empUName varchar(255),
	projectID varchar(255),
	foreign key (projectID) references project(projectID),
	primary key (id)
); /* NOTE: REMOVED CONSTRAINT "foreign key (empUName) references developers(username)" BECAUSE IN THIS DB, 
I'VE PUT ALL ROLES (ADMIN / PROJECT MANAGER / DEVELOPER / TESTER) IN SOME TEAM */


/* INSERT STATEMENTS */
/* admins table */
INSERT INTO admins VALUES ('John', 'Smith', 'johnsmith89', 'password89');
INSERT INTO admins VALUES ('Arushi', 'Kumar', 'arushi32001', 'thisispassword');
INSERT INTO admins VALUES ('Anushka', 'Hebbar', 'nushebbar', 'pswdhaha');

/* projectmanagers table */
INSERT INTO projectmanagers VALUES ('Anusha', 'Kabber', 'anushakabber', 'improjectmanager');
INSERT INTO projectmanagers VALUES ('Paris', 'Hilton', 'parish', 'heartbeat');
INSERT INTO projectmanagers VALUES ('Derek', 'Figg', 'derekfigg', 'thisisme');

/* project table */
INSERT INTO project VALUES (1, 'parish', 'Smart security surveillance system', 'To use contemporary AI techniques to guarantee exceptionally watchful surveillance round the clock', '2022-06-01');
INSERT INTO project VALUES (2, 'anushakabber', 'Secured Mail System', 'Guarantee security to organizational data by creating a mailing system exclusive to the organization', '2022-06-25');
INSERT INTO project VALUES (3, 'parish', 'PDF to Audio Converter', 'To use ML techiques to recognize text on a PDF and convert to mp3 format (read the text out loud)', '2022-08-15');

/* developers table */
INSERT INTO developers VALUES ('Karthik', 'Narayan', 'karthik56', 'developer1');
INSERT INTO developers VALUES ('Ray', 'Simon', 'simonray', 'developer2');
INSERT INTO developers VALUES ('Luke', 'Lockhart', 'lukelockhart', 'developer3');

/* testers table */
INSERT INTO testers VALUES ('Jane', 'Smith', 'janesmith', 'tester1');
INSERT INTO testers VALUES ('Celine', 'Dione', 'celinedione', 'tester2');
INSERT INTO testers VALUES ('Gandalf', 'Trello', 'trellogandalf', 'tester3');

/* bug table */
INSERT INTO bug VALUES (5, 3, 'INITIATED', 'MODERATE', 'janesmith', '2022-04-25', NULL, '2022-04-29', 'Input file size limit not set, dangerous if left as is', '');
INSERT INTO bug VALUES (7, 1, 'RESOLVED', 'HIGH', 'celinedione', '2022-04-05', '2022-04-07', '2022-04-07', 'System crashes on input of non-mp4 format video stream data', 'Input sanitation code added');
INSERT INTO bug VALUES (3, 2, 'OVERDUE', 'LOW', 'trellogandalf', '2022-03-31', NULL, '2022-04-11', 'Double log entries upon employee leaving organization', '');

/* 
	Fix for bugid 2 : Conditional loop fixed for employee leaving organization, logs functioning as expected 
	Fix for bugid 3 : Input file size limit set to 10 MB
*/


/* TeamMembers table */
INSERT INTO TeamMembers VALUES (1, 'johnsmith89', 2);
INSERT INTO TeamMembers VALUES (2, 'anushakabber', 2);
INSERT INTO TeamMembers VALUES (3, 'karthik56', 2);
INSERT INTO TeamMembers VALUES (4, 'trellogandalf', 2);
INSERT INTO TeamMembers VALUES (5, 'arushi32001', 1);
INSERT INTO TeamMembers VALUES (6, 'parish', 1);
INSERT INTO TeamMembers VALUES (7, 'lukelockhart', 1);
INSERT INTO TeamMembers VALUES (8, 'celinedione', 1);
INSERT INTO TeamMembers VALUES (9, 'nushebbar', 3);
INSERT INTO TeamMembers VALUES (10, 'derekfigg', 3);
INSERT INTO TeamMembers VALUES (11, 'simonray', 3);
INSERT INTO TeamMembers VALUES (12, 'janesmith', 3);