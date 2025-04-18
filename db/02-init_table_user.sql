CREATE TABLE IF NOT EXISTS `user` (
    `id` VARCHAR(32) NOT NULL,
    `username` VARCHAR(32) NOT NULL,
    `email` VARCHAR(320) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `verified` BOOLEAN NOT NULL DEFAULT 0,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_verify_token` (
    `user_id` VARCHAR(32) NOT NULL,
    `token` VARCHAR(32) NOT NULL UNIQUE,
    `expires_at` DATETIME NOT NULL,
    `used` BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`user_id`)
        REFERENCES `user`(`id`) 
        ON DELETE CASCADE,
    INDEX `idx_expires_at` (`expires_at`)
);

CREATE TABLE IF NOT EXISTS 'account_lock_status' (
    user_id VARCHAR(32) NOT NULL PRIMARY KEY,
    failed_attempts INT NOT NULL DEFAULT 0,
    is_locked BOOLEAN NOT NULL DEFAULT FALSE
);