CREATE DATABASE `memsys`;

CREATE TABLE `memsys`.`constants_user_status` (
	`constant_index` 			INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `constant_key` 				NVARCHAR(3) NOT NULL UNIQUE,
    `constant_description`		NVARCHAR(100) DEFAULT NULL,
    PRIMARY KEY(`constant_index`)
);

INSERT INTO `memsys`.`constants_user_status` (`constant_key`, `constant_description`)
VALUES 	('ADM', '관리자'),
		('OKY', '회원 정상 상태'),
		('SUS', '회원 정지 상태'),
		('DEL', '회원 삭제 상태');


CREATE TABLE `memsys`.`users` (
	`user_index` 				INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_email` 				NVARCHAR(20) NOT NULL UNIQUE,
    `user_password` 			NVARCHAR(128) NOT NULL,
    `user_name` 				NVARCHAR(10) NOT NULL,
    `user_nickname` 			NVARCHAR(10) NOT NULL,
    `user_contact` 				NVARCHAR(11) NOT NULL UNIQUE,
    `user_address` 				NVARCHAR(25) NOT NULL,
    `user_birth` 				NVARCHAR(6) NOT NULL,
    `user_admin_flag` 			BOOLEAN NOT NULL DEFAULT FALSE,
    `user_status` 				NVARCHAR(3) NOT NULL,
    `user_created_at` 			DATETIME NOT NULL DEFAULT NOW(),
    `user_status_changed_at` 	DATETIME NOT NULL DEFAULT NOW(),
    `user_password_modified_at` DATETIME NOT NULL DEFAULT NOW(),
    `user_signed_at` 			DATETIME NOT NULL DEFAULT NOW(),
	PRIMARY KEY(`user_index`),
    FOREIGN KEY(`user_status`) REFERENCES `memsys`.`constants_user_status` (`constant_key`)
);

-- CREATE TABLE `memsys`.`users_signed_at` (
-- 	`user_signed_index` 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
--     `user_index`			INT UNSIGNED NOT NULL,
--     `user_signed_at`		DATETIME NOT NULL DEFAULT NOW(),
--     PRIMARY KEY(`user_signed_index`),
--     FOREIGN KEY(`user_index`) REFERENCES `memsys`.`users` (`user_index`)
-- );
-- CREATE TABLE `memsys`.`users_status_changed_at` (
-- 	`user_status_changed_at_index` 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
--     `user_index`					INT UNSIGNED NOT NULL,
--     `user_status_changed_at`		DATETIME NOT NULL DEFAULT NOW(),
--     PRIMARY KEY(`user_status_changed_at_index`),
--     FOREIGN KEY(`user_index`) REFERENCES `memsys`.`users` (`user_index`)
-- );
-- CREATE TABLE `memsys`.`users_password_modified_at` (
-- 	`user_password_modified_at_index` 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
--     `user_index`						INT UNSIGNED NOT NULL,
--     `user_password_modified_at`			DATETIME NOT NULL DEFAULT NOW(),
--     PRIMARY KEY(`user_password_modified_at_index`),
--     FOREIGN KEY(`user_index`) REFERENCES `memsys`.`users` (`user_index`)
-- );


INSERT INTO `memsys`.`users` (
	`user_email` 				,
    `user_password` 			,
    `user_name` 				,
    `user_nickname` 			,
    `user_contact` 				,
    `user_address` 				,
    `user_birth` 				,
    `user_admin_flag` 			,
    `user_status` 				) VALUES (
	'admin@sample.com',
	'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff',  -- test
    '관리자',
    '관리자',
    '01012341234',
    '서울특별시 중구',
    '990101',
    TRUE,
    'ADM'
);

INSERT INTO `memsys`.`users` (
	`user_email` 				,
    `user_password` 			,
    `user_name` 				,
    `user_nickname` 			,
    `user_contact` 				,
    `user_address` 				,
    `user_birth` 				,
    `user_status` 				) VALUES (
	?,
	?,
    ?,
    ?,
    ?,
    ?,
    ?,
    'OKY'
);

SELECT 	`users`.`user_name` AS `userName`,
		`users`.`user_nickname` AS `userNickname`,
        `users`.`user_contact` AS `userContact`,
        `users`.`user_address` AS `userAddress`,
        `users`.`user_birth` AS `userBirth`,
        `users`.`user_status` AS `userStatus`,
		`users`.`user_created_at` AS `userCreatedAt`,
        `users`.`user_signed_at` AS `userSingedAt`,
        `users`.`user_status_changed_at` AS `userStatusChangedAt`,
        `users`.`user_password_modified_at` AS `userPasswordModifiedAt` 
FROM `memsys`.`users` AS `users`
-- INNER JOIN `memsys`.`users_status_changed_at` AS `usersStatusChangedAt` ON `users`.`user_index` = `usersStatusChangedAt`.`user_index`
-- INNER JOIN `memsys`.`users_signed_at` AS `usersSignedAt` ON `users`.`user_index` = `usersSignedAt`.`user_index`
-- INNER JOIN `memsys`.`users_password_modify_at` AS `usersPasswordModifyAt` ON `users`.`user_index` = `usersPasswordModifyAt`.`user_index`
WHERE `users`.`user_email` = ? AND `users`.`user_password` = ? LIMIT 1;


CREATE TABLE `memsys`.`login_attempts`
(
	`attempt_index`			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `attempt_created_at` 	DATETIME(6)		NOT NULL DEFAULT NOW(6),
    `attempt_ip`			NVARCHAR(50)	NOT NULL,
    `attempt_email`			NVARCHAR(100)	NOT NULL,
    `attempt_password`		NVARCHAR(100)	NOT NULL,
    `attempt_result`		NVARCHAR(50)	NOT NULL,
    PRIMARY KEY(`attempt_index`)
);


SELECT COUNT(`attempt_index`) AS `count` FROM `memsys`.`login_attempts` WHERE `attempt_ip` = ? AND `attempt_created_at` > DATE_SUB(NOW(), INTERVAL ? SECOND);


CREATE TABLE `memsys`.`blocked_ips`
(
	`ip_index` 		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `ip`			NVARCHAR(50)	NOT NULL,
    `ip_created_at` DATETIME(6)		NOT NULL DEFAULT NOW(6),
    `ip_expires_at` DATETIME(6)		NOT NULL,
    PRIMARY KEY(`ip_index`)
);


SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users`;

UPDATE `memsys`.`users` SET `user_status` = 'DEL', `user_status_changed_at` = NOW() WHERE `user_index` = 30 OR `user_index` = 31;