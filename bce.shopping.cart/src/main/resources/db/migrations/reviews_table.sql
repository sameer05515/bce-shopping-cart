-- Book reviews and ratings table
CREATE TABLE IF NOT EXISTS `book_reviews` (
    `review_id` INT NOT NULL AUTO_INCREMENT,
    `bookid` INT NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `rating` INT NOT NULL CHECK (`rating` >= 1 AND `rating` <= 5),
    `review_text` TEXT,
    `review_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `is_approved` TINYINT(1) NOT NULL DEFAULT 0,
    `helpful_count` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`bookid`) REFERENCES `book_details`(`bookid`) ON DELETE CASCADE,
    INDEX `idx_bookid` (`bookid`),
    INDEX `idx_userid` (`userid`),
    INDEX `idx_approved` (`is_approved`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Review helpful votes table
CREATE TABLE IF NOT EXISTS `review_votes` (
    `vote_id` INT NOT NULL AUTO_INCREMENT,
    `review_id` INT NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `is_helpful` TINYINT(1) NOT NULL,
    `vote_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`vote_id`),
    UNIQUE KEY `unique_user_review` (`review_id`, `userid`),
    FOREIGN KEY (`review_id`) REFERENCES `book_reviews`(`review_id`) ON DELETE CASCADE,
    INDEX `idx_review_id` (`review_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

