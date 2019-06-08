-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: wenda
-- ------------------------------------------------------
-- Server version	5.6.26-enterprise-commercial-advanced-log

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
-- Table structure for table `login_ticket`
--

DROP TABLE IF EXISTS `login_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `expired` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `ticket` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_ticket`
--

LOCK TABLES `login_ticket` WRITE;
/*!40000 ALTER TABLE `login_ticket` DISABLE KEYS */;
INSERT INTO `login_ticket` VALUES (1,8,'2019-02-20 00:13:17',0,'e506ecaf-7432-4230-a1c7-cb4e759ca7d2'),(2,8,'2019-02-20 13:49:43',0,'a650b622c06d4169ac14f45bbb46c9a9'),(3,8,'2019-02-20 13:49:52',0,'ae24206d53d54d11a5346f0a3f1728a9'),(4,8,'2019-02-21 01:06:40',0,'7856f6b468a647d2b24bad2475622062'),(5,8,'2019-02-21 11:52:37',0,'110cc61ca7e14a92b994f40addb18d2b'),(6,8,'2019-02-21 11:54:04',0,'57e322f1fc5040e9b510643350a95953'),(7,8,'2019-02-24 16:34:09',1,'1b98b803f1e440f29dc9cead7543651c'),(8,8,'2019-02-24 20:16:53',0,'3181f9d9eed64e80a8f031ace3239d22'),(9,8,'2019-02-24 20:25:30',0,'a8f5c8d5907e486fa499778e90f8ce7c');
/*!40000 ALTER TABLE `login_ticket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-08 13:34:47
