use bugtracker;

drop table admins;
drop table projectmanagers;
drop table developers;
drop table bug;
drop table project;

create table admins (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	empID varchar(255),
	primary key (username)
);

create table projectmanagers (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	empID varchar(255),
	primary key (username)
);

create table developers (
	fname varchar(255), 
	lname varchar(255), 
	username varchar(255), 
	password varchar(255), 
	empID varchar(255),
	primary key (username)
);

create table bug (
	bugID varchar(255), 
	bugStatus ENUM('INITIATED', 'RESOLVED', 'OVERDUE'),
	bugPriority ENUM('HIGH', 'MODERATE', 'LOW'), 
	bugOwnerUName varchar(255), 
	createdDate DATE, 
	resolvedDate DATE, 
	lastUpdatedDate DATE, 
	description varchar(255), 
	resolutionDesc varchar(255),
	primary key (bugID),
	foreign key (bugOwnerUName) references developers(username)
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
