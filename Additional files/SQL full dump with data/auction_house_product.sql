CREATE DATABASE  IF NOT EXISTS `auction_house` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `auction_house`;
-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: auction_house
-- ------------------------------------------------------
-- Server version	5.7.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `des` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `baseprice` double(10,2) DEFAULT NULL,
  `duration` mediumtext COLLATE utf8_bin,
  `sold` int(11) DEFAULT '0',
  `BidValue` double(10,2) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `lastBidder` int(11) DEFAULT NULL,
  `approved` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pname` (`pname`),
  UNIQUE KEY `des` (`des`),
  KEY `lastBidder_idx` (`lastBidder`),
  CONSTRAINT `lastBidder` FOREIGN KEY (`lastBidder`) REFERENCES `bidder` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'car','A vintage styled car',3000.00,'900000',1,3060.50,'2016-11-13 11:45:42','2016-11-13 12:00:42',4,1),(2,'house','A big good ventilated house near USC',1000000.00,'7210000',0,1001000.50,'2016-11-13 11:38:17','2016-11-13 13:38:27',4,1),(3,'dining table','A big table for seating 7 people',250.00,'20000',3,NULL,'2016-11-13 11:45:47','2016-11-13 11:46:07',NULL,0),(4,'laptop','A gaming laptop',850.00,'135000',1,855.00,'2016-11-13 11:45:51','2016-11-13 11:48:06',3,1),(5,'jewellery','antique jewellery',500.00,'20000',3,NULL,'2016-11-13 11:55:05','2016-11-13 11:55:25',NULL,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-13 12:12:13
