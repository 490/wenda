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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_id` int(11) NOT NULL,
  `to_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `has_read` int(11) NOT NULL,
  `conversation_id` varchar(64) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,4,281,'用户123赞了你的评论,http://127.0.0.1:8080/question/63',0,'4_281','2019-02-23 23:38:49'),(2,4,281,'用户123赞了你的评论,http://127.0.0.1:8080/question/63',0,'4_281','2019-02-23 23:41:22'),(3,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-23 23:55:36'),(4,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-23 23:56:02'),(5,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-23 23:58:08'),(6,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-24 00:26:58'),(7,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-24 00:27:16'),(8,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-24 00:44:57'),(9,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-24 00:46:26'),(10,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-24 00:58:47'),(11,4,6,'用户123关注了你，http://127.0.0.1:8080/user8',0,'4_6','2019-02-24 01:04:47');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
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
