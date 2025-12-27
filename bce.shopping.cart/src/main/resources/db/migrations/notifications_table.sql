-- In-app notifications table
CREATE TABLE IF NOT EXISTS `notifications` (
    `notification_id` INT NOT NULL AUTO_INCREMENT,
    `userid` VARCHAR(50) NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `message` TEXT NOT NULL,
    `type` VARCHAR(50) NOT NULL DEFAULT 'info',
    `is_read` TINYINT(1) NOT NULL DEFAULT 0,
    `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `link_url` VARCHAR(500),
    PRIMARY KEY (`notification_id`),
    INDEX `idx_userid` (`userid`),
    INDEX `idx_read` (`is_read`),
    INDEX `idx_created_date` (`created_date`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

