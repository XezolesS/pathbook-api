CREATE TABLE IF NOT EXISTS `post` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `author_id` VARCHAR(32),
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`author_id`)
        REFERENCES `user`(`id`)
        ON DELETE SET NULL
);
