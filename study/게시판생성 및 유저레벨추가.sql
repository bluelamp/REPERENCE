SELECT * FROM `laststudy`.`boards1`;
SELECT * FROM `laststudy`.`users`;
SELECT * FROM `laststudy`.`articles`;

ALTER TABLE `laststudy`.`users` ADD COLUMN `user_level` TINYINT UNSIGNED NOT NULL DEFAULT 9 AFTER `user_password`;

CREATE TABLE `laststudy`.`boards1` (
`board_index` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`board_id`	NVARCHAR(3) NOT NULL UNIQUE,
`board_name` NVARCHAR(50) NOT NULL,
`board_read_level` TINYINT UNSIGNED NOT NULL DEFAULT 9,
`board_write_level` TINYINT UNSIGNEd NOT NULL DEFAULT 9
);

INSERT INTO `laststudy`.`boards1` (`board_id`, `board_name`, `board_read_level`, `board_write_level`) VALUES
('ntc', '공지사항', 9, 1),
('fre', '자유게시판', 10, 9);

SELECT `board_read_level` AS `boardReadLevel` `board_write_level` AS `boardWriteLevel`
FROM `laststudy`.`boards1`
WHERE `board_id` =?;



/* 게시물을 만들기 */
CREATE TABLE `laststudy`.`articles` (
	`article_index` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_email`  	NVARCHAR(100) NOT NULL,
    `board_id`	NVARCHAR(3) NOT NULL,
    `article_title` NVARCHAR(500) NOT NULL,
    `article_content`  NVARCHAR(10000) NOT NULL,
    `article_written_at` DATETIME NOT NULL DEFAULT NOW(),
    `article_hit` INt UNSIGNED NOT NULL DEFAULT 0,
    
    FOREIGN KEY (`user_email`) REFERENCES `laststudy`.`users` (`user_email`) ON DELETE CASCADE,
    FOREIGN KEY (`board_id`) REFERENCES `laststudy`.`boards1` (`board_id`) ON DELETE CASCADE
);


INSERT INTO `laststudy`.`articles` (
user_email,
board_id,
article_title,
article_text
) VALUES 
('admin@sample.com', 'ntc', '운영자의 공지사항입니다.', '이것은 내용입니다.'),
('admin@sample.com', 'ntc', '매너를 지켜주세요.', '공지사항 게시판입니다. 매너를 지켜주세요.'),
('admin@sample.com', 'fre', '이곳은 자유게시판입니다.', '이것은 내용입니다.'),
('aa@naver.com', 'fre', '첫번째 게시글입니다', '이것은 첫번째 게시글 내용입니다.'),
('bb@naver.com', 'fre', '두번째 게시글입니다', '이것은 두번째 게시글 내용입니다.'),
('cc@naver.com', 'fre', '세번째 게시글입니다', '이것은 세번째 게시글 내용입니다.'),
('dd@naver.com', 'fre', '네번째 게시글입니다', '이것은 네번째 게시글 내용입니다.'),
('abcd@naver.com', 'fre', '다섯번째 게시글입니다', '이것은 다섯번째 게시글 내용입니다.'),
('aaaa@naver.com', 'fre', '여섯번째 게시글입니다', '이것은 여섯번째 게시글 내용입니다.'),
('tt@tt.com', 'fre', '일곱번째 게시글입니다', '이것은 일곱번째 게시글 내용입니다.'),
('qq@qq.com', 'fre', '여덜번째 게시글입니다', '이것은 여덜번째 게시글 내용입니다.'),
('aa@naver.com', 'fre', '아홉번째 게시글입니다', '이것은 아홉번째 게시글 내용입니다.');


INSERT INTO `laststudy`.`articles` (
user_email,
board_id,
article_title,
article_text
) VALUES 
('aa@naver.com', 'fre', '첫번째 게시글입니다', '이것은 첫번째 게시글 내용입니다.'),
('bb@naver.com', 'fre', '두번째 게시글입니다', '이것은 두번째 게시글 내용입니다.'),
('cc@naver.com', 'fre', '세번째 게시글입니다', '이것은 세번째 게시글 내용입니다.'),
('dd@naver.com', 'fre', '네번째 게시글입니다', '이것은 네번째 게시글 내용입니다.'),
('abcd@naver.com', 'fre', '다섯번째 게시글입니다', '이것은 다섯번째 게시글 내용입니다.'),
('aaaa@naver.com', 'fre', '여섯번째 게시글입니다', '이것은 여섯번째 게시글 내용입니다.'),
('tt@tt.com', 'fre', '일곱번째 게시글입니다', '이것은 일곱번째 게시글 내용입니다.'),
('qq@qq.com', 'fre', '여덜번째 게시글입니다', '이것은 여덜번째 게시글 내용입니다.'),
('aa@naver.com', 'fre', '아홉번째 게시글입니다', '이것은 아홉번째 게시글 내용입니다.');




/*
LIMIT a, b
a : 선택된 레코드들 중 a 번째 부터
-- 1 페이지 : 48 ~ 39 : a = 0
-- 2 페이지 : 38 ~ 29 : a = 10
-- 3 페이지 : 28 ~ 19 : a = 20
-- 4 페이지 : 18 ~ 9 : a = 30
-- 5 페이지 : 8 ~ 1 : a = 40
b : 한 페이지에 보여줄 게시글의 수
*/
SELECT 
`articles`.`article_index` AS `articleIndex`,
`users`.`user_nickname` AS `userNickname`,
`boards`.`board_name` AS `boardName`,
`articles`.`article_title` AS `articleTitle`,
`articles`.`article_content` AS `articleContent`,
`articles`.`article_written_at` AS `articleWrittenAt`,
`articles`.`article_hit` AS `articleHit`
FROM `laststudy`.`articles` AS `articles`
INNER JOIN `laststudy`.`boards1` AS `boards` ON `articles`.`board_id` = `boards`.`board_id`
INNER JOIn `laststudy`.`users` AS `users` ON `articles`.`user_email` = `users`.`user_email`
WHERE `boards`.`board_id` = ?
ORDER BY `article_index` DESC LIMIT ?, 10;




SELECT 
`articles`.`article_index` AS `articleIndex`,
`users`.`user_nickname` AS `userNickname`,
`boards`.`board_name` AS `boardName`,
`articles`.`article_title` AS `articleTitle`,
`articles`.`article_content` AS `articleContent`,
`articles`.`article_written_at` AS `articleWrittenAt`,
`articles`.`article_hit` AS `articleHit`
FROM `laststudy`.`articles` AS `articles`
INNER JOIN `laststudy`.`boards1` AS `boards` ON `articles`.`board_id` = `boards`.`board_id`
INNER JOIn `laststudy`.`users` AS `users` ON `articles`.`user_email` = `users`.`user_email`
WHERE `boards`.`board_id` = ? AND (TRIM(`articles`.`article_title`) LIKE ? OR TRIM(`articles`.`article_content`) LIKE ?)
ORDER BY `article_index` DESC LIMIT ?, 10;


SELECT 
COUNT(`articles`.`article_index`) AS `count`
FROM `laststudy`.`articles` AS `articles`
INNER JOIN `laststudy`.`boards1` AS `boards` ON `articles`.`board_id` = `boards`.`board_id`
INNER JOIn `laststudy`.`users` AS `users` ON `articles`.`user_email` = `users`.`user_email`
WHERE `boards`.`board_id` = 'fre' AND (`articles`.`article_title` LIKE TRIM('%입니다 %') OR `articles`.`article_content` LIKE TRIM('%입니다 %'));
