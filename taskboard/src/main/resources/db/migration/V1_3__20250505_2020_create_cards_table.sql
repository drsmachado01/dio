CREATE TABLE IF NOT EXISTS `CARDS`(
    `ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `BOARD_COLUMN_ID` BIGINT(20) NOT NULL,
    `TITLE` VARCHAR(100) NOT NULL,
    `DESCRIPTION` VARCHAR(255) NOT NULL,
    `ORDER` INT(11) NOT NULL,
    CONSTRAINT `FK_CARDS_BOARD_COLUMNS_ID` FOREIGN KEY (`BOARD_COLUMN_ID`) REFERENCES `BOARD_COLUMNS` (`ID`)
)ENGINE=INNODB;