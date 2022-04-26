use bugtracker;

drop table if exists admins;
drop table if exists bug;
drop table if exists TeamMembers;
drop table if exists project;
drop table if exists projectmanagers;
drop table if exists developers;

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

create table bug (
	bugID varchar(255), 
	projectID varchar(255),
	bugStatus ENUM('INITIATED', 'RESOLVED', 'OVERDUE'),
	bugPriority ENUM('HIGH', 'MODERATE', 'LOW'), 
	bugOwnerUName varchar(255), 
	createdDate DATE, 
	resolvedDate DATE, 
	lastUpdatedDate DATE, 
	description varchar(255), 
	resolutionDesc varchar(255),
	primary key (bugID),
	foreign key (bugOwnerUName) references developers(username),
	foreign key (projectID) references project(projectID)
);

create table TeamMembers (
	id int,
	empUName varchar(255),
	projectID varchar(255),
	foreign key (empUName) references developers(username),
	foreign key (projectID) references project(projectID),
	primary key (id)
);

