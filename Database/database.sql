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
  `marketType` VARCHAR(45) NOT NULL,
  `priceChange` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `revenue` BIGINT NOT NULL,
  `sharePrice` DECIMAL(10,2) NOT NULL,
  `employees` INT NOT NULL,
  `age` INT NOT NULL,
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
  `initialPrice` VARCHAR(45) NOT NULL,
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


INSERT INTO `top100`.`user` (`id`, `username`, `password`, `email`, `address`, `createDate`, `bank`) VALUES ('1', 'player', 'password', 'jackallcock@yahoo.co.uk', 'Address', '2018-07-20 14:15:48.586', '1000.0');
INSERT INTO `top100`.`user` (`id`, `username`, `password`, `email`, `address`, `createDate`, `bank`) VALUES ('2', 'buyer', 'password', 'buyer@yahoo.co.uk', 'Address', '2018-07-20 14:15:48.586', '1000.0');

INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('1', 'Apple', 'Technology', '+/-0.0%', 'USA', '23500', '20.24', '123000', 42,  '1', '300');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('2', 'BP', 'Oil', '+/-0.0%', 'USA', '13500', '10.35', '12000', 109, '1', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('3', 'Admiral', 'Insurance', '+/-0.0%', 'UK', '2000', '15.35', '1200', 25, '1', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('4', 'BAE', 'Industry', '+/-0.0%', 'UK', '9000', '6.35', '2350', 18, '1', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('5', 'Barclays', 'Finance', '+/-0.0%', 'UK', '7000', '34.35', '2139', 122, '0', '150');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('6', 'Honda', 'Automobiles', '+/-0.0%', 'JAPAN', '15000', '3.45', '5643', 69, '0', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('7', 'GoldIsUs', 'Mining', '+/-0.0%', 'DUB', '124', '0.45', '120', 3, '0', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('8', 'Pixel', 'Technology', '+/-0.0%', 'SPAIN', '234', '0.50', '55', 1, '0', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('9', 'Samsung', 'Technology', '+/-0.0%', 'JAPAN', '19500', '40.35', '9493', 80, '1', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('10', 'Next', 'Retail', '+/-0.0%', 'UK', '23500', '5.65', '1230', 154, '0', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('11', 'Zara', 'Retail', '+/-0.0%', 'SPAIN', '12000', '24.78', '2356', 44, '0', '100');
INSERT INTO `top100`.`company` (`id`, `companyName`, `marketType`, `priceChange`, `country`, `revenue`, `sharePrice`, `employees`, `age`, `advantage`, `shares`) VALUES ('12', 'OilCo', 'Oil', '+/-0.0%', 'INDIA', '670', '0.10', '5', 10,  '0', '100');





