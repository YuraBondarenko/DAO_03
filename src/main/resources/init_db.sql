CREATE SCHEMA taxi_service DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufacturers` (
                                            `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(225) NOT NULL,
                                            `country` VARCHAR(225) NOT NULL,
                                            `deleted` TINYINT NOT NULL DEFAULT 0,
                                            PRIMARY KEY (`id`));

CREATE TABLE `taxi_service`.`drivers` (
                                          `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                          `name` VARCHAR(225) NOT NULL,
                                          `licenceNumber` VARCHAR(225) NOT NULL,
                                          `deleted` TINYINT NOT NULL DEFAULT 0,
                                          PRIMARY KEY (`id`));

CREATE TABLE `taxi_service`.`cars` (
                                       `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                       `manufacturer_id` BIGINT(11) NOT NULL,
                                       `model` VARCHAR(225) NOT NULL,
                                       `deleted` TINYINT NOT NULL DEFAULT 0,
                                       PRIMARY KEY (`id`));

ALTER TABLE `taxi_service`.`cars`
    ADD INDEX `cars_manufacturers_fk_idx` (`manufacturer_id` ASC) VISIBLE;
;
ALTER TABLE `taxi_service`.`cars`
    ADD CONSTRAINT `cars_manufacturers_fk`
        FOREIGN KEY (`manufacturer_id`)
            REFERENCES `taxi_service`.`manufacturers` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `taxi_service`.`cars_drivers` (
                                               `driver_id` BIGINT(11) NOT NULL,
                                               `car_id` BIGINT(11) NOT NULL);

ALTER TABLE `taxi_service`.`cars_drivers`
    ADD INDEX `cars_drivers_drivers_fk_idx` (`driver_id` ASC) VISIBLE,
ADD INDEX `cars_drivers_cars_fk_idx` (`car_id` ASC) VISIBLE;
;
ALTER TABLE `taxi_service`.`cars_drivers`
    ADD CONSTRAINT `cars_drivers_drivers_fk`
        FOREIGN KEY (`driver_id`)
            REFERENCES `taxi_service`.`drivers` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
ADD CONSTRAINT `cars_drivers_cars_fk`
  FOREIGN KEY (`car_id`)
  REFERENCES `taxi_service`.`cars` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
