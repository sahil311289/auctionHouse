create schema  if not exists `auction_house`;
Use auction_house;

CREATE TABLE `admin` (
  `uname` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uname`)
) ENGINE=InnoDB AUTO_INCREMENT=787 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into admin values(786,"admin");

CREATE TABLE `seller` (
  `uname` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `fname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `lname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ph` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uname`),
  UNIQUE KEY `uname_UNIQUE` (`uname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `bidder` (
  `uname` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `fname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `lname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ph` varchar(45) COLLATE utf8_bin DEFAULT NULL UNIQUE,
  `email` varchar(45) COLLATE utf8_bin DEFAULT NULL UNIQUE,
  `approved` int(11) DEFAULT '0',
  PRIMARY KEY (`uname`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(45) COLLATE utf8_bin DEFAULT NULL UNIQUE,
  `des` varchar(100) COLLATE utf8_bin DEFAULT NULL UNIQUE,
  `baseprice` double(10,2),
  `duration` long,
  `sold` int(11) DEFAULT '0',
  `BidValue` double(10,2),
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `lastBidder` int(11) DEFAULT NULL,
  `approved` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `lastBidder_idx` (`lastBidder`),
  CONSTRAINT `lastBidder` FOREIGN KEY (`lastBidder`) REFERENCES `bidder` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `seller_sells` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `productid_idx` (`productId`),
  CONSTRAINT `productid` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `uname` FOREIGN KEY (`uname`) REFERENCES `seller` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `bidder_bids` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `bidValue` double(10,2),
  `placed` int(11) DEFAULT '0',
  `wen` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uname_idx` (`uname`),
  KEY `productId_idx` (`productId`),
  CONSTRAINT `productId2` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `uname2` FOREIGN KEY (`uname`) REFERENCES `bidder` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `withdraws` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) DEFAULT NULL,
  `pname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `uname` int(11) DEFAULT NULL,
  `wen` datetime DEFAULT NULL,
  `baseprice` double(10,2),
  `lastbid` double(10,2),
  PRIMARY KEY (`id`),
  KEY `productId_idx` (`productId`),
  KEY `uname_idx` (`uname`),
  CONSTRAINT `productId4` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DELIMITER //
CREATE PROCEDURE `auction_house`.`delete_obsolete_auctions` ()
BEGIN
  update product set sold = 1 where endTime < (SELECT NOW()) and approved = 1 and BidValue IS NOT null;
    update product set approved = 0 where endTime < (SELECT NOW()) and approved = 1 and BidValue IS null;
  INSERT INTO withdraws(productId, pname, uname, wen, baseprice, lastbid) 
    select id, pname, 0, (select NOW()), baseprice, BidValue FROM product where endTime < (SELECT NOW()) and approved = 0 and sold = 0 and BidValue IS null;
  update product set sold = 3 where endTime < (SELECT NOW()) and approved = 0 and sold = 0 and BidValue IS null;
END //
DELIMITER ;

SET SQL_SAFE_UPDATES=0;
SET GLOBAL event_scheduler = ON;
SET GLOBAL event_scheduler = 1;
CREATE EVENT myevent
    ON SCHEDULE EVERY 1 SECOND
    COMMENT 'Moves auctions with no bids to withdraws table.'
    DO
      CALL delete_obsolete_auctions();