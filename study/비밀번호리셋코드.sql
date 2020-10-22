CREATE TABLE `laststudy`.`user_reset_codes` (
`code_index` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`user_index` INT UNSIGNED NOT NULL,
`code` NVARCHAR(6) NOT NULL,
`code_expires_at` DATETIME NOT NULL,
FOREIGN KEY(`user_index`) REFERENCES `laststudy`.`users`(`user_index`) ON DELETE CASCADE);


/* 리셋 코드를 DB에 삽입 */
INSERT INTO `laststudy`.`user_reset_codes` (
`user_index`, `code`, `code_expires_at`) VALUE 
(?, ?, DATE_ADD(NOW(), INTERVAL 3 MINUTE));

/* 리셋 코드를 DB에 삽입 */
SELECT COUNT(`code_index`) AS `count` FROM `laststudy`.`user_reset_codes` WHERE `user_index` = ? AND `code` = ? AND `code_expires_at` > NOW();


/* 비밀번호를 주어진 값으로 설정하는 과정 */
UPDATE `laststudy`.`users` SET `user_password`=? WHERE `user_index` = ?;