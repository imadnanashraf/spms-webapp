DROP DATABASE IF EXISTS `student_perfornmance_monitoring`;

CREATE DATABASE IF NOT EXISTS `student_perfornmance_monitoring`;

USE `student_perfornmance_monitoring`;

CREATE TABLE `faculty` (

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`first_name` varchar(50) NOT NULL,
	`last_name` varchar(50) NOT NULL,
	`email` varchar(50) NOT NULL,
	`password` char(80) NOT NULL,
	`enabled` TINYINT(1) DEFAULT NULL,
	`dept` varchar(50) NOT NULL,
	`profile_url` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)

)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `f_roles`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`role` varchar(50) DEFAULT NULL,
	`faculty_id` int(11) DEFAULT NULL,
    PRIMARY KEY(`id`),
	FOREIGN KEY (`faculty_id`) REFERENCES `faculty`(`id`)
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `allot_students`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`semester` int(11) DEFAULT NULL,
	`batch` int(11) NOT NULL,
	`dept` varchar(50) NOT NULL,
	`learner_type` varchar(50) DEFAULT NULL,
	`faculty_id` int(11) DEFAULT NULL,
    PRIMARY KEY(`id`),
	FOREIGN KEY (`faculty_id`) REFERENCES `faculty`(`id`)
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `push_notification`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`dept` varchar(50) NOT NULL,
	`batch` int(11) NOT NULL,	
	`notification` varchar(600) DEFAULT NULL,
    PRIMARY KEY(`id`)	
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `message_center`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`from_email` varchar(50) NOT NULL,
	`to_email` varchar(50) NOT NULL,	
	`message` varchar(600) DEFAULT NULL,
    PRIMARY KEY(`id`)	
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `student` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`first_name` varchar(50) NOT NULL,
	`last_name` varchar(50) NOT NULL,
	`contact_no` varchar(10) NOT NULL,
	`fathers_name` varchar(50) NOT NULL,
	`guardian_contact_no` varchar(10) NOT NULL,
	`email` varchar(50) NOT NULL,
	`password` char(80) NOT NULL,
	`enabled` TINYINT(1) DEFAULT NULL,
	`dept` varchar(50) NOT NULL,
	`profile_url` varchar(255) DEFAULT NULL,
	`batch` int(11) DEFAULT NULL,
	`university_enroll` BIGINT(20) DEFAULT NULL,
	PRIMARY KEY (`id`)
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `s_roles` (
	
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`role` varchar(50) DEFAULT NULL,
	`student_id` int(11) DEFAULT NULL,
    PRIMARY KEY(`id`),
	FOREIGN KEY (`student_id`) REFERENCES `student`(`id`)

)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `student_sem_data` (

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`semester` int(11) DEFAULT NULL,
	`overall_subject_assessment` FLOAT(11) DEFAULT NULL,
	`overall_previous_result`  FLOAT(11) DEFAULT NULL,
	`overall_internal_result` FLOAT(11) DEFAULT NULL,
	`overall_evaluation_result` FLOAT(11) DEFAULT NULL,
	`learner_type` varchar(50) DEFAULT NULL,
	`internal_result_url` varchar(255) NOT NULL,
	`previous_result_url` varchar(255) NOT NULL,
	`student_id` int(11) DEFAULT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`student_id`) REFERENCES `student`(`id`)
	
)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `read_notification`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`notification_id` int(11) DEFAULT NULL,
	`student_id` int(11) DEFAULT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`student_id`) REFERENCES `student`(`id`)
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `read_student_message`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`message_id` int(11) DEFAULT NULL,
	`student_id` int(11) DEFAULT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`student_id`) REFERENCES `student`(`id`)
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `read_faculty_message`(

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`message_id` int(11) DEFAULT NULL,
	`faculty_id` int(11) DEFAULT NULL,
    PRIMARY KEY(`id`),
	FOREIGN KEY (`faculty_id`) REFERENCES `faculty`(`id`)
	
)   ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `subject_list` (

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`subject_name` varchar(50) NOT NULL,
	`batch` int(11) NOT NULL,
	`semester` int(11) NOT NULL,
	`faculty_email` varchar(50) NOT NULL,
	`dept` varchar(50) NOT NULL,
	PRIMARY KEY(`id`)
	
)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `percentage_controller` (

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`internal_result_threshold` int (11) NOT NULL,
	`previous_result_threshold` int (11) NOT NULL,
	`class_result_threshold` int(11) NOT NULL,
	`overall_result_threshold` int (11) NOT NULL,
	`dept` varchar(50) NOT NULL,
	PRIMARY KEY(`id`)

)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `student_per_subject` (

	`id` int(11) NOT NULL AUTO_INCREMENT,
	`semester` int(11) NOT NULL,
	`subject_name` varchar(50) NOT NULL,
	`behaviour`int(11) NOT NULL,
	`focus` int(11) NOT NULL,
	`attendance` int(11) NOT NULL,
	`per_subject_overall` FLOAT(11) DEFAULT NULL,
	`student_id` int(11) DEFAULT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`student_id`) REFERENCES `student`(`id`)

)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `student_verification_token` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`token` varchar(255) NOT NULL,
	`created_date` datetime NOT NULL,
	`expiry_date` datetime NOT NULL,
	`student_id` int(11) NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`student_id`) REFERENCES `student`(`id`)
)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `faculty_verification_token` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`token` varchar(255) NOT NULL,
	`created_date` datetime NOT NULL,
	`expiry_date` datetime NOT NULL,
	`faculty_id` int(11) NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`faculty_id`) REFERENCES `faculty`(`id`)
)	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-------for cse email = hodcse@gmail.com----------------------
-------for civil email = hodcivil@gmail.com------------------
-------for electrical email = hodelectrical@gmail.com--------
-------for e&c email = hode&c@gmail.com----------------------
-------for mechanical email = hodmechanical@gmail.com--------

------- hod password = hodssm@123----------------------------

INSERT INTO `faculty` (`id`, `first_name`, `last_name`, `email`, `password`, `enabled`, `dept`,`profile_url`)
VALUES ('151', 'HOD', 'CSE', 'hodcse@gmail.com', '$2a$10$pFoymq3g7ALjiD5XCTTHDudgBo8cwkYOnAnkQREh0ch274/5pOt7e', '1', 'CSE','defaultprofile.jpg');

INSERT INTO `faculty` (`id`, `first_name`, `last_name`, `email`, `password`, `enabled`, `dept`,`profile_url`)
VALUES ('152', 'HOD', 'CIVIL', 'hodcivil@gmail.com', '$2a$10$pFoymq3g7ALjiD5XCTTHDudgBo8cwkYOnAnkQREh0ch274/5pOt7e', '1', 'CIVIL','defaultprofile.jpg');

INSERT INTO `faculty` (`id`, `first_name`, `last_name`, `email`, `password`, `enabled`, `dept`,`profile_url`)
VALUES ('153', 'HOD', 'ELECTRICAL', 'hodelectrical@gmail.com', '$2a$10$pFoymq3g7ALjiD5XCTTHDudgBo8cwkYOnAnkQREh0ch274/5pOt7e', '1', 'ELECTRICAL','defaultprofile.jpg');

INSERT INTO `faculty` (`id`, `first_name`, `last_name`, `email`, `password`, `enabled`, `dept`,`profile_url`)
VALUES ('154', 'HOD', 'E&C', 'hode&c@gmail.com', '$2a$10$pFoymq3g7ALjiD5XCTTHDudgBo8cwkYOnAnkQREh0ch274/5pOt7e', '1', 'E&C','defaultprofile.jpg');

INSERT INTO `faculty` (`id`, `first_name`, `last_name`, `email`, `password`, `enabled`, `dept`,`profile_url`)
VALUES ('155', 'HOD', 'MECHANICAL', 'hodmechanical@gmail.com', '$2a$10$pFoymq3g7ALjiD5XCTTHDudgBo8cwkYOnAnkQREh0ch274/5pOt7e', '1', 'MECHANICAL','defaultprofile.jpg');

INSERT INTO `f_roles` (`id`, `role`, `faculty_id`) 
VALUES 
('1', 'ROLE_HOD', '151'),
('2', 'ROLE_HOD', '152'),
('3', 'ROLE_HOD', '153'),
('4', 'ROLE_HOD', '154'),
('5', 'ROLE_HOD', '155'),
('11', 'ROLE_FACULTY', '151'),
('12', 'ROLE_FACULTY', '152'),
('13', 'ROLE_FACULTY', '153'),
('14', 'ROLE_FACULTY', '154'),
('15', 'ROLE_FACULTY', '155');



