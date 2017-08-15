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
-- Table structure for table `bidder_bids`
--

DROP TABLE IF EXISTS `bidder_bids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bidder_bids` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `bidValue` double(10,2) DEFAULT NULL,
  `placed` int(11) DEFAULT '0',
  `wen` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uname_idx` (`uname`),
  KEY `productId_idx` (`productId`),
  CONSTRAINT `productId2` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `uname2` FOREIGN KEY (`uname`) REFERENCES `bidder` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidder_bids`
--

LOCK TABLES `bidder_bids` WRITE;
/*!40000 ALTER TABLE `bidder_bids` DISABLE KEYS */;
INSERT INTO `bidder_bids` VALUES (1,3,2,1000010.00,1,'2016-11-13 11:44:37'),(2,3,4,855.00,1,'2016-11-13 11:47:11'),(3,4,2,1001000.50,1,'2016-11-13 11:49:52'),(4,4,1,3050.00,1,'2016-11-13 11:50:35'),(5,4,1,3060.50,1,'2016-11-13 11:56:12');
/*!40000 ALTER TABLE `bidder_bids` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-13 12:12:14
