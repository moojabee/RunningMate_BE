DROP DATABASE IF EXISTS running;
CREATE DATABASE running;
USE running;

CREATE TABLE `User` (
    `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(20) NOT NULL,
    `nickname` VARCHAR(20) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `created_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `private` TINYINT(1) NULL DEFAULT 1,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `Board` (
    `board_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) NOT NULL,
    `content` TEXT NOT NULL,
    `posted_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`board_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Comment` (
    `comment_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `board_id` BIGINT(20) NOT NULL,
    `user_id` BIGINT(20) NOT NULL,
    `content` TEXT NOT NULL,
	`posted_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`comment_id`),
    FOREIGN KEY (`board_id`) REFERENCES `Board`(`board_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Party` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) NOT NULL,
    `room_id` VARCHAR(36) NOT NULL,
    `parted_date` DATE NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Chatroom` (
    `room_id` VARCHAR(36) NOT NULL,
    `room_userCount` BIGINT(20) NOT NULL,
    `room_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`room_id`)
);

CREATE TABLE `Chatmessage` (
    `message_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `room_id` VARCHAR(36) NOT NULL,
    `user_id` BIGINT(20) NOT NULL,
    `sended_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `content` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`message_id`),
    FOREIGN KEY (`room_id`) REFERENCES `Chatroom`(`room_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Record` (
    `record_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) NOT NULL,
    `startTime` TIMESTAMP NULL ,
    `endTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `distance` FLOAT NOT NULL,
    PRIMARY KEY (`record_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Following` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) NOT NULL,
    `following_id` BIGINT(20) NOT NULL,
    `status` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`following_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Follower` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) NOT NULL,
    `follower_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`follower_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Like` (
    `like_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) NOT NULL,
    `board_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`like_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`board_id`) REFERENCES `Board`(`board_id`) ON DELETE CASCADE
);

CREATE TABLE `Store` (
    `img_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `board_id` BIGINT(20) NOT NULL,
    `filename` VARCHAR(4096) NOT NULL,
    PRIMARY KEY (`img_id`),
    FOREIGN KEY (`board_id`) REFERENCES `Board`(`board_id`) ON DELETE CASCADE
);

CREATE TABLE `RunImg` (
    `runimg_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `record_id` BIGINT(20) NOT NULL,
    `filename` VARCHAR(4096) NOT NULL,
    PRIMARY KEY (`runimg_id`),
    FOREIGN KEY (`record_id`) REFERENCES `Record`(`record_id`) ON DELETE CASCADE
);


INSERT INTO `User` (`email`, `password`, `name`, `nickname`, `address`) VALUES
('tlgmdtl1118@gmail.com', 'pwd1', '홍길동', 'seoul1', '서울특별시 강남구'),
('user2@example.com', 'pwd2', '김영희', 'seoul2', '서울특별시 서초구'),
('user3@example.com', 'pwd3', '이철수', 'seoul3', '서울특별시 송파구'),
('user4@example.com', 'pwd4', '박민수', 'seoul4', '서울특별시 마포구'),
('user5@example.com', 'pwd5', '정수진', 'seoul5', '서울특별시 성동구'),
('user6@example.com', 'pwd6', '최현준', 'seoul6', '서울특별시 영등포구'),
('user7@example.com', 'pwd7', '강미영', 'seoul7', '서울특별시 중구'),
('user8@example.com', 'pwd8', '윤상혁', 'seoul8', '서울특별시 용산구'),
('user9@example.com', 'pwd9', '배수빈', 'seoul9', '서울특별시 은평구'),
('user10@example.com', 'pwd10', '김하늘', 'seoul10', '서울특별시 종로구');

select *
from user;