-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: huaxiaoyu
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assistedchat`
--

DROP TABLE IF EXISTS `assistedchat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assistedchat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `optionList` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `assistedchat_id_uindex` (`id`),
  UNIQUE KEY `assistedchat_code_uindex` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistedchat`
--

LOCK TABLES `assistedchat` WRITE;
/*!40000 ALTER TABLE `assistedchat` DISABLE KEYS */;
INSERT INTO `assistedchat` VALUES (109,'决赛圈了，不会还有人没羊吧','entertainment','今天你羊了吗','羊了/漏网之鱼/天选打工人'),(110,'王者喜欢打哪个位置','entertainment','王者荣耀是一款爆火的moba手游，打王者时你喜欢什么位置呢？','打野/上单/我都行/一般最后补位'),(111,'王者最高段位','entertainment','打王者时，你打到的最高段位是什么呢？','王者/星耀/一般不注意'),(112,'喜欢篮球还是ikun','sport','篮球和ikun联系紧密，你更喜欢篮球还是ikun呢？','真喜欢篮球/ikun/纯路人'),(113,'羊的症状都有哪些','entertainment','疫情放开后很多人出现了羊的症状，你的症状有哪些呢？','高烧/全身酸痛/喉咙吞刀片/水泥鼻'),(114,'梅西or姆巴佩','sport','2022世界杯阿根廷和法国会师总决赛，梅西和姆巴佩在比赛中都有较好表现，最后梅西率队夺得世界杯冠军。你更喜欢梅西还是姆巴佩？对他们都有哪些评价呢？','梅西/姆巴佩'),(115,'LOL乌迪尔改版','entertainment','野区萧炎怎么还不削弱啊','已经拿来上分了/打不过就加入'),(116,'聊聊喜欢的歌吧','entertainment','每个人都有自己的歌单，你比较喜欢听的歌有哪些呢？',NULL),(117,'聊聊喜欢的电影吧','entertainment','每个人都有自己印象深刻的影片，你比较喜欢的电影有哪些，能聊聊自己的感想吗？',NULL),(118,'最近在读的一本书','study','你平时会经常看书吗，谈谈自己最近在读的一本书吧',NULL),(119,'最推荐的一本书','study','每个人都有自己的书单，有一些书会给我们带来启发，让我们印象深刻，你最推荐的一本书是什么呢？',NULL),(120,'聊聊喜欢的歌手','entertainment','听歌的时候，人们往往有自己更喜欢的歌手，你更喜欢哪位歌手呢？',NULL),(121,'NBA最喜欢的球星','sport','NBA有很多著名的球星，像乔丹，科比，库里，他们在篮球领域有着出色成就，在篮球之外也有着较高的知名度，你最喜欢的球星是哪位呢，能谈谈自己的想法吗？','必须是乔帮主/还得是库里/立棍单打相当炸裂/还得是欧神仙'),(122,'NBA最支持的球队','sport','NBA中有30支球队，东西部各有15支，你最支持哪个球队呢？','洛杉矶湖人/金州勇士/布鲁克林篮网'),(123,'今年看好哪支球队夺冠','sport','NBA常规赛共有82场，东西部常规赛战绩最好的各8个球队可以进入季后赛，你更看好今年哪个球队能夺冠呢？','勇士/雄鹿/凯尔特人/篮网'),(124,'聊聊羽毛球吧','sport','你平时喜欢打羽毛球吗，聊聊喜欢打羽毛球的原因吧','实验室一块/当作锻炼/解压'),(125,'一起打羽毛球的人','sport','打羽毛球不仅有意思，还可以锻炼身体，你一般和谁一起打羽毛球呢？','打球认识的球友/实验室、学院同学'),(126,'想去音乐节、livehouse、演唱会','entertainment','你喜欢去音乐节、演唱会线下欣赏音乐吗？','没错，线下才有氛围！/我喜欢独自欣赏哈哈'),(127,'聊聊乒乓球吧','sport','你平时喜欢打乒乓球吗，聊聊喜欢打乒乓球的原因吧','从小就打/国球'),(128,'聊聊网球吧','sport','你平时喜欢打网球吗，聊聊喜欢打网球的原因吧','网球裙很好看/从小就喜欢/看李娜比赛喜欢上网球/锻炼身体'),(129,'喜欢的网球明星','sport','你平时喜欢打网球吗，聊聊喜欢打网球的原因吧','李娜/费德勒/纳达尔/德约科维奇/莎拉波娃'),(130,'喜欢的足球明星','sport','足球领域有很多的明星，像梅西，C罗，内马尔，姆巴佩，你有哪些比较喜欢的球星呢？','C罗/梅西/贝克汉姆/内马尔'),(131,'关于世界杯的记忆','sport','往届世界杯中，印象深刻瞬间有哪些','06世界杯齐达内决赛勺子点球/10世界杯罗本失单刀/10世界杯苏亚雷斯上帝之手/14世界杯德国7比1巴西/'),(132,'聊聊跑步吧','sport','你平时喜欢打跑步吗，聊聊喜欢跑步的原因吧','沉浸在自己的世界/锻炼身体'),(133,'一起打跑步的人','sport','跑步可以锻炼身体，你比较喜欢一个人跑步还是约人一起去跑步呢？','个人/跑友'),(134,'骑车地点','entertainment','骑车一般喜欢去哪骑','东湖/天心洲/汤逊湖/武汉室内环线骑行'),(135,'骑车的玩伴','sport','一般和谁一起骑车','实验室同学/车友/室友/学院同学'),(136,'刺客伍六七1.18上映','entertainment','刺客伍六七第四季1.18上映，七大暗影刺客轮番来袭，大号柒能否再一次拯救梅花十三？','我觉得必定可以/大号柒太帅了/梅花十三有危险！'),(137,'2022世界杯冠军出炉，阿根廷力捧大力神杯','sport','阿根廷终于战胜法国，拿到了2022卡塔尔世界杯冠军。你们觉得今年世界杯结果如何？','阿根廷实至名归/幸运女神降临阿根廷'),(138,'2023考研结束，考研笔记该扔吗','study','一年一度的考研终于落下帷幕，对于考研党一年来的心血——考研笔记，你觉得该扔吗？',NULL),(139,'2022新年临近，好想放烟花','entertainment','时近春节，关于烟花爆竹“禁燃令”是否松绑，再一次成为舆论的热点话题。你认为过年期间是否可以燃放烟花爆竹？',NULL),(140,'羊康之后，嗓子奇痒无比','entertainment','感染新冠后，嗓子变得奇痒无比。不停咳嗽，咳得脑仁疼、胸腔疼。羊康之后，你还咳嗽吗？','咳个不停，肺快咳出来了/偶尔咳，嗓子干痒难受/不咳嗽了/我是天选打工人'),(141,'高房价能促进共同富裕？','entertainment','专家回应房价上涨能促进共同富裕','“共同”是哪位地产商的名字啊/建议专家不要建议'),(142,'一起来东湖骑车啊','sport','下一学期，即将引来放开后的学校生活，你会去东湖边骑车吗？','锻炼身体，走起/宅在寝室美滋滋'),(143,'世界杯主题曲','sport','喜欢哪一年的世界杯主题曲',NULL),(144,'字节跳动已开启2022裁员','study','从多位知情人士处获悉，字节跳动已开启裁员，据多位人士估算，公司整体优化规模约10%','字节跳槽/年末裁人，这个年都过得格外寒冷了'),(145,'刘亦菲首部现代剧来了','entertainment','谈谈对刘亦菲对印象','神仙姐姐/好美/梦华录'),(146,'《三体》动漫火热来袭','entertainment','《三体》在B站播出多日，你看了吗，感觉咋样？','还原度很高，赞！/剧情拉胯/一般般/没看过'),(147,'桌游之王就是我','entertainment','狼人杀、大富翁、三国杀，你喜欢玩桌游吗？最喜欢哪一款？','大爱桌游！/一般般');
/*!40000 ALTER TABLE `assistedchat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `department_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='院系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (154,'1','机械科学与工程学院'),(155,'2','光学与电子信息学院'),(156,'3','材料科学与工程学院'),(157,'4','能源与动力工程学院'),(158,'5','中欧清洁与可再生能源学院'),(159,'6','电气与电子工程学院'),(160,'7','电子信息与通信学院'),(161,'8','人工智能与自动化学院'),(162,'9','计算机科学与技术学院'),(163,'10','船舶与海洋工程学院'),(164,'11','土木与水利工程学院'),(165,'12','建筑与城市规划学院'),(166,'13','环境科学与工程学院'),(167,'14','航空航天学院'),(168,'15','网络空间安全学院'),(169,'16','软件学院'),(170,'17','生命科学与技术学院'),(171,'18','数学与统计学院'),(172,'19','物理学院'),(173,'20','化学与化工学院'),(174,'21','武汉光电国家研究中心'),(175,'22','武汉国际微电子学院'),(176,'23','工程科学学院（国际化示范学院）'),(177,'24','未来技术学院'),(178,'25','集成电路学院'),(179,'26','医疗装备科学与工程研究院'),(180,'27','基础医学院'),(181,'28','公共卫生学院'),(182,'29','药学院'),(183,'30','护理学院'),(184,'31','医药卫生管理学院'),(185,'32','法医学系'),(186,'33','生殖健康研究所(原计划生育研究所)'),(187,'34','口腔医学院'),(188,'35','第一临床学院'),(189,'36','第二临床学院'),(190,'37','第三临床学院'),(191,'38','哲学学院'),(192,'39','经济学院'),(193,'40','社会学院'),(194,'41','法学院'),(195,'42','马克思主义学院'),(196,'43','教育科学研究院'),(197,'44','继续教育学院（教育培训学院）'),(198,'45','人文学院'),(199,'46','外国语学院'),(200,'47','新闻与信息传播学院'),(201,'48','管理学院'),(202,'49','公共管理学院'),(203,'50','体育学院'),(204,'51','艺术学院');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sendId` int(11) NOT NULL,
  `receiveId` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `changeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `friends_id_uindex` (`id`),
  UNIQUE KEY `send` (`sendId`,`receiveId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='朋友';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (8,10,1,1,'2023-02-19 12:54:31',NULL);
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `interest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `interest_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES (18,'000001','篮球','sport'),(19,'000002','羽毛球','sport'),(20,'000003','乒乓球','sport'),(21,'000004','网球','sport'),(22,'000005','足球','sport'),(23,'000006','跑步','sport'),(24,'000007','骑车','sport'),(25,'000008','旅行户外','sport'),(26,'010001','科研','study'),(27,'010002','竞赛','study'),(28,'100001','王者荣耀','entertainment'),(29,'100002','和平精英','entertainment'),(30,'100003','原神','entertainment'),(31,'100004','CSGO','entertainment'),(32,'100005','炉石传说','entertainment'),(33,'100006','斗地主','entertainment'),(34,'100007','麻将','entertainment'),(35,'000000','运动','sport'),(36,'010000','学习','study'),(37,'100000','娱乐','entertainment');
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sendId` int(11) DEFAULT NULL,
  `receviceId` int(11) DEFAULT NULL,
  `sendText` text,
  `createTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `TextType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_id_uindex` (`id`),
  KEY `message_user_username_fk` (`sendId`),
  KEY `message_user_username_fk_2` (`receviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notice` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `sendId` int(11) DEFAULT NULL,
  `receiveId` int(11) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `changeTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`messageId`),
  UNIQUE KEY `notice_id_uindex` (`messageId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (8,1,NULL,1,10,'2023-02-19 11:31:04',NULL,1),(9,2,1,10,1,'2023-02-19 12:24:14',NULL,0),(10,1,NULL,1,10,'2023-02-19 12:44:28',NULL,0),(11,2,1,10,1,'2023-02-19 12:52:19',NULL,0),(12,2,1,10,1,'2023-02-19 12:54:17',NULL,0),(13,2,1,10,1,'2023-02-19 12:54:31',NULL,0),(14,NULL,NULL,8,10,NULL,NULL,0),(15,NULL,NULL,8,14,NULL,NULL,0),(16,NULL,NULL,12,8,NULL,NULL,0),(17,NULL,NULL,8,12,NULL,NULL,0),(18,NULL,NULL,1,8,NULL,NULL,0);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(300) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `departmentCode` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `headPhoto` text,
  `phoneNum` varchar(20) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `interestCodeList` varchar(100) DEFAULT NULL,
  `changeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',20,'male','2','2022-11-15 00:00:00',NULL,NULL,'huster1','111101,333301,333303','2022-12-20 13:58:05'),(11,'user11','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',21,NULL,NULL,'2023-02-05 15:35:36',NULL,'123456789','Huster_火星','111101,222201,333303',NULL),(6,'user2','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'111101,333301,333303',NULL),(8,'user3','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',21,NULL,NULL,'2022-12-20 13:53:27',NULL,NULL,NULL,'111101,333301,333303',NULL),(10,'user4','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',11,NULL,'4','2022-12-20 13:57:45',NULL,NULL,NULL,'111101,333301,333303','2023-02-06 15:07:25'),(12,'user5','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',11,NULL,NULL,'2023-02-05 15:43:36',NULL,NULL,'Huster_小新','111101,333301,333303','2023-02-06 15:07:13'),(13,'user6','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',21,NULL,NULL,'2023-02-13 09:17:39',NULL,NULL,'Huster_金星','111101,333301,333303',NULL),(14,'user7','$2a$10$LJxP0sefFEa3BDvNZx3EuOZ1NBN6Q.JtrrPM2YsxzQFNqCGi1I4PW',21,NULL,NULL,'2023-02-13 10:18:03',NULL,NULL,'Huster_小新','111101,333301,333303',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-26 15:34:12
