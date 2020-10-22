SELECT * FROM laststudy.articles;
SELECT * FROM `laststudy`.`users`;

CREATE TABLE `laststudy`.`comments_status_constant`(
`status_index` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`status_key` NVARCHAR(3) NOT NULL UNIQUE,
`status_description` NVARCHAR(100) NOT NULL
);

CREATE TABLE `laststudy`.`comments` (
`comment_index` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`article_index` BIGINT UNSIGNED NOT NULL,
`user_email` NVARCHAR(100) NOT NULL,
`comment_written_at` DATETIME NOT NULL DEFAULT NOW(),
`status_key`	NVARCHAR(3) NOT NULL DEFAULT 'OKY',
`commnet_text` NVARCHAR(1000) NOT NULL,

FOREIGN KEY (`user_email`)
	REFERENCES `laststudy`.`users`(`users_email`) ON DELETE CASCADE,
FOREIGN KEY (`article_index`)
	REFERENCES `laststudy`.`articles`(`article_index`) ON DELETE CASCADE,
FOREIGN KEY (`status_key`)
	REFERENCES `laststudy`.`comments_status_constant`(`status_key`) ON DELETE CASCADE
)