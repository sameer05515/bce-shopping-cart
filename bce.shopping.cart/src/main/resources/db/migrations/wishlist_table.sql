-- Wishlist table for saving books for later purchase
CREATE TABLE IF NOT EXISTS `wishlist` (
    `wishlist_id` INT NOT NULL AUTO_INCREMENT,
    `userid` VARCHAR(50) NOT NULL,
    `bookid` INT NOT NULL,
    `added_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`wishlist_id`),
    UNIQUE KEY `unique_user_book` (`userid`, `bookid`),
    FOREIGN KEY (`bookid`) REFERENCES `book_details`(`bookid`) ON DELETE CASCADE,
    INDEX `idx_userid` (`userid`),
    INDEX `idx_bookid` (`bookid`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

