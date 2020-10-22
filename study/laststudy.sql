SELECT * FROM laststudy.users;


INSERT INTO `laststudy`.`users` (
`user_email`,
`user_password`,
`user_name`,
`user_nickname`,
`user_contact`
) VALUES (
	?,
    ?,
    ?,
    ?,
    ?
)

CREATE TABLE `laststudy`.`tables` (
	`table_index` 		INT UNSIGNED NOT NULL AUTO_INCREMENT,
    
    
    CONSTRAINT 
);

SELECT `user_email` AS `userEmail`, `user_password` AS `userPassword`,
			`user_name` AS `userName`, `user_nickname` AS `userNickname`, `user_contact` AS `userContact` FROM `laststudy`.`users` WHERE `user_email` = ? AND `user_password` = ?;
            
            
CREATE TABLE `laststudy`.`boards` (
	`board_index`	INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `board_kinds`	NVARCHAR(20) NOT NULL,
    `board_title`	NVARCHAR(100) NOT NULL,
    `user_index`	INT UNSIGNED NOT NULL,
    `board_made_at` DATETIME NOT NULL DEFAULT NOW(),
    `board_views`	INT UNSIGNED NOT NULL DEFAULT 0,
    
    `board_text`	TEXT(10000) NOT NULL,
    
    PRIMARY KEY(`board_index`),
    FOREIGN KEY(`user_index`)
		REFERENCES `laststudy`.`users`(`user_index`)
);

UPDATE `laststudy`.`boards` SET `board_views` = `board_views` + 1 WHERE `board_index` = 1;

INSERT INTO `laststudy`.`boards` (
`board_kinds`,
`board_title`,
`user_index`,
`board_text`) VALUES (
'공지사항',
'공지입니다.',
1,
'안녕하세요. 공지사항 내용입니다. 공지사항입니다.'
);

UPDATE `laststudy`.`boards` SET `board_views` = `board_views` + 1 WHERE `board_index` = 1;
SELECT `board_index` AS `boardIndex`,
`board_kinds` AS `boardKinds`,
`board_title` AS `boardTitle`,
`user_nickname` AS `userNickname`,
`board_made_at` AS `boardMadeAt`,
`board_views` AS `boardViews`
 FROM `laststudy`.`boards` 
 INNER JOIN `laststudy`.`users` ON `boards`.`user_index` = `users`.`user_index` LIMIT 0, 10;
