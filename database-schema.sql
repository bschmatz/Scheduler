--
-- Table structure for table `event_registrations`
--
DROP TABLE IF EXISTS `event_registrations`;

CREATE TABLE `event_registrations` (
  `registration_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `event_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`registration_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `event_registrations_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`)
);

--
-- Table structure for table `events`
--
DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `event_room` varchar(255) DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `event_date` date DEFAULT NULL,
  `event_start_time` time DEFAULT NULL,
  `event_end_time` time DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `room_id` (`event_room`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `events_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `users` (`user_id`)
);

--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);

--
-- Table structure for table `wishes`
--
DROP TABLE IF EXISTS `wishes`;

CREATE TABLE `wishes` (
  `wish_id` int NOT NULL AUTO_INCREMENT,
  `assistant_name` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `start_time` varchar(45) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  `event_name` varchar(45) DEFAULT NULL,
  `room` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wish_id`)
);