SELECT * FROM laststudy.boards;

CREATE TABLE `laststudy`.`board_kategorie` (
	`kategorie_index` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `kategorie_key`		NVARCHAR(8) NOT NULL UNIQUE,
    `kategorie_decription` NVARCHAR(16) NOT NULL
);

INSERT INTO `laststudy`.`board_kategorie` (
	`kategorie_key`,
    `kategorie_decription`
) VALUES 
('NOTICE','공지사항 게시판'),
('FREE','자유게시판'),
('ANONY','익명 게시판'),
('QNA','질문 게시판')
;


RENAME TABLE `laststudy`.`board_kinds` TO `laststudy`.`board_kategorie`;

DELETE FROM `laststudy`.`boards` WHERE `board_index`=1;

ALTER TABLE `laststudy`.`boards` RENAME COLUMN `board_kinds` TO `kategorie_index`;
ALTER TABLE `laststudy`.`boards` MODIFY `kategorie_index` INT UNSIGNED NOT NULL;
ALTER TABLE `laststudy`.`boards` ADD CONSTRAINT FOREIGN KEY(`kategorie_index`) REFERENCES `laststudy`.`board_kategorie`(`kategorie_index`);

ALTER TABLE `laststudy`.`boards` auto_increment = 1;
INSERT INTO `laststudy`.`boards` (
    `kategorie_index`,
    `board_title`,
    `user_index`,
    `board_text`
) VALUES (
1,
'공지사항입니다.',
1,
'안녕하십니까 운영자 누구누구입니다. 이것은 공지사항의 내용입니다.'
)

SELECT 			`board_index` AS `boardIndex`,
                `kategorie_decription` AS `kategorieDecription`,
                `board_title` AS `boardTitle`,
                `user_nickname` AS `userNickname`,
                `board_made_at` AS `boardMadeAt`,
                `board_views` AS `boardViews`
				FROM `laststudy`.`boards` 
                INNER JOIN `laststudy`.`users` ON `boards`.`user_index` = `users`.`user_index` AND
                INNER JOIN `laststudy`.`board_kategorie` ON `boards`.`kategorie_index` = `board_kategorie`.`kategorie_index` 
                LIMIT 0, 10;
                
                
                
                CREATE TABLE `laststudy`.`user_reset_codes` (
                `code_index` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                `user_index` INT UNSIGNED NOT NULL,
                `code` NVARCHAR(6) NOT NULL,
                `code_expires_at` DATETIME NOT NULL,
                FOREIGN KEY(`user_index`) REFERENCES `laststudy`.`users`(`user_index`) ON DELETE CASCADE);
                
                INSERT INTO `laststudy`.`user_reset_codes` (
                `user_index`, `code`, `code_expires_at`) VALUE 
                (?, ?, DATE_ADD(NOW(), INTERVAL 3 MINUTE))
                
                
                
                SELECT COUNT(`code_index`) AS `count` FROM `laststudy`.`user_reset_codes` WHERE `user_index` = ? AND `code` = ? AND `code_expires_at` > NOW()