CREATE TABLE IF NOT EXISTS `user` (
    `id` VARCHAR(32) NOT NULL,
    `username` VARCHAR(32) NOT NULL,
    `email` VARCHAR(320) NOT NULL,
    `password` VARCHAR(64) NOT NULL,
    `verified` BOOLEAN NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

DELIMITER //
CREATE TRIGGER `trigger_user_insert`
    BEFORE INSERT ON `user`
    FOR EACH ROW 
    BEGIN
        SET NEW.`password` = SHA2(NEW.`password`, 256);
    END//
DELIMITER ;
