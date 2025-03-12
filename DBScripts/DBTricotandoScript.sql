-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tricotandoDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tricotandoDB` DEFAULT CHARACTER SET utf8 ;
USE `tricotandoDB` ;

-- -----------------------------------------------------
-- Table `tricotandoDB`.`Items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tricotandoDB`.`Items` (
  `idItem` INT NOT NULL AUTO_INCREMENT,
  `itemBarcode` VARCHAR(45) NOT NULL,
  `itemName` VARCHAR(255) NOT NULL,
  `itemBrand` VARCHAR(255) NOT NULL,
  `itemPrice` DECIMAL(10,2) NOT NULL,
  `itemPurchasePrice` DECIMAL(10,2) NOT NULL,
  `itemStock` FLOAT NOT NULL,
  `itemSafetyStock` FLOAT NOT NULL,
  `itemColor` VARCHAR(255) NOT NULL,
  `itemDesc` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idItem`),
  UNIQUE INDEX `idItem_UNIQUE` (`idItem` ASC) VISIBLE,
  UNIQUE INDEX `itemCodBarra_UNIQUE` (`itemBarcode` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tricotandoDB`.`Sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tricotandoDB`.`Sales` (
  `idSale` INT NOT NULL AUTO_INCREMENT,
  `dateSale` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idSale`),
  UNIQUE INDEX `idSaídas_UNIQUE` (`idSale` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tricotandoDB`.`Items_Sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tricotandoDB`.`Items_Sales` (
  `Sales_idSale` INT NOT NULL,
  `Items_idItem` INT NOT NULL,
  `soldAmount` FLOAT NOT NULL,
  `soldPrice` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`Sales_idSale`, `Items_idItem`),
  INDEX `fk_Sales_has_Items_Items1_idx` (`Items_idItem` ASC) VISIBLE,
  INDEX `fk_Sales_has_Items_Sales_idx` (`Sales_idSale` ASC) VISIBLE,
  CONSTRAINT `fk_Sales_has_Items_Sales`
    FOREIGN KEY (`Sales_idSale`)
    REFERENCES `tricotandoDB`.`Sales` (`idSale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sales_has_Items_Items1`
    FOREIGN KEY (`Items_idItem`)
    REFERENCES `tricotandoDB`.`Items` (`idItem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

DELIMITER $$
CREATE TRIGGER reduce_stock AFTER INSERT ON Items_Sales FOR EACH ROW
BEGIN
	UPDATE Items
    SET itemStock = itemStock - NEW.soldAmount
    WHERE idItem = NEW.Items_idItem;
END$$