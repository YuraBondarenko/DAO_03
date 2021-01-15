CREATE SCHEMA taxi_service DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`new_table` (
                                            `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(225) NOT NULL,
                                            `country` VARCHAR(225) NOT NULL,
                                            `deleted` TINYINT NOT NULL DEFAULT 0,
                                            PRIMARY KEY (`id`));
