SELECT * FROM laststudy.articles;
SELECT * FROM laststudy.users;

UPDATE `laststudy`.`users` SET `user_email`='nan@nan' WHERE `user_index` = 15;

INSERT INTO `laststudy`.`users` 
(
`user_email`,
`user_password`,
`user_level`,
`user_name`,
`user_nickname`,
`user_contact`
) VALUE (
'nan',
'nan',
10,
'비회원',
'비회원',
'nan'
);



/* 해당 유저가 있는지 확인 */


INSERT INTO `laststudy`.`articles` (
`article_title`,
`article_content`,
`user_email`,
`board_id`
) VALUE (
?,
?,
?,
?
);


SELECT `article`.`article_index` AS `articleIndex`
`user`.`user_nickname` AS `userNickname`,
`article`.`article_title` AS `articleTitle`,
`article`.`article_content` AS `articleContent`,
`article`.`article_written_at` AS `articleWrittenAt`,
`article`.`article_hit` AS `articleHit`
FROM `laststudy`.`articles` AS `artcle`
INNER JOIN `laststudy`.`users` AS `user` ON `article`.`user_email` = `user`.`user_email`
WHERE `article_index`= ?