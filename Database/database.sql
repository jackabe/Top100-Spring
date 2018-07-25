DROP SCHEMA top100;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Schema top100
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `top100` DEFAULT CHARACTER SET utf8mb4 ;
USE `top100` ;

-- -----------------------------------------------------
-- Table `top100`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `top100`.`company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `companyName` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `revenue` DECIMAL(10,2) NOT NULL,
  `sharePrice` DECIMAL(10,2) NOT NULL,
  `employees` VARCHAR(45) NOT NULL,
  `advantage` TINYINT(4) NOT NULL,
  `shares` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7;


-- -----------------------------------------------------
-- Table `top100`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `top100`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `createDate` VARCHAR(45) NOT NULL DEFAULT 'NOW()',
  `bank` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2;


-- -----------------------------------------------------
-- Table `mydb`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `top100`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `companyId` INT NOT NULL,
  `amount` INT NOT NULL,
  `price` VARCHAR(45) NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `companyId_idx` (`companyId` ASC),
  INDEX `userId_idx` (`userId` ASC),
  CONSTRAINT `companyId`
    FOREIGN KEY (`companyId`)
    REFERENCES `top100`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userId`
    FOREIGN KEY (`userId`)
    REFERENCES `top100`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`trade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `top100`.`trade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `buyingPlayer` INT NOT NULL DEFAULT 0,
  `sellingPlayer` INT NOT NULL DEFAULT 0,
  `transactionId` INT NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `type` VARCHAR(10)  NOT NULL,
  `sharePrice` DECIMAL(10,2) NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `date` VARCHAR(45) NOT NULL DEFAULT 'now()',
  PRIMARY KEY (`id`),
  INDEX `buyingPlayer_idx` (`buyingPlayer` ASC),
  INDEX `sellingPlayer_idx` (`sellingPlayer` ASC),
  INDEX `transactionId_idx` (`transactionId` ASC),
  CONSTRAINT `buyingPlayer`
    FOREIGN KEY (`buyingPlayer`)
    REFERENCES `top100`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sellingPlayer`
    FOREIGN KEY (`sellingPlayer`)
    REFERENCES `top100`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionId`
    FOREIGN KEY (`transactionId`)
    REFERENCES `top100`.`transaction` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `top100` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO `top100`.`user` (`id`, `username`, `password`, `email`, `address`, `createDate`, `bank`) VALUES ('1', 'player', 'password', 'jackallcock@yahoo.co.uk', 'Address', '2018-07-20 14:15:48.586', '500.0');

INSERT INTO `top100`.`company` (`id`, `companyName`, `country`, `revenue`, `sharePrice`, `employees`, `advantage`, `shares`) VALUES ('1', 'Apple', 'USA', '4542245', '2.35', '4221', '1', 10000);

INSERT INTO `top100`.`transaction` (`id`, `companyId`, `amount`, `price`) VALUES ('1', '1', '100', '1.24');

