CREATE DATABASE IF NOT EXISTS `pathbook` DEFAULT CHARACTER SET 'UTF8MB4';

CREATE ROLE IF NOT EXISTS 'pathbook_dev', 'pathbook_read', 'pathbook_write';
GRANT ALL ON `pathbook%` . * TO 'pathbook_dev';
GRANT SELECT ON `pathbook%` . * TO 'pathbook_read';
GRANT INSERT, UPDATE, DELETE ON `pathbook%` . * TO 'pathbook_write';

CREATE USER IF NOT EXISTS 'pathbook'@'%' IDENTIFIED BY 'pathbook';
CREATE USER IF NOT EXISTS 'pathbook'@'localhost' IDENTIFIED BY 'pathbook';
GRANT 'pathbook_dev' TO 'pathbook'@'%';
GRANT 'pathbook_dev' TO 'pathbook'@'localhost';

SET DEFAULT ROLE ALL TO
    'pathbook'@'%',
    'pathbook'@'localhost';
