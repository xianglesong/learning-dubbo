CREATE SCHEMA `dubbo_demo` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `usernumber` double DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `classid` double DEFAULT NULL,
  `credit` int(11) DEFAULT NULL,
  `identity` int(11) DEFAULT NULL,
  `imageurl` varchar(45) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `createtime` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
