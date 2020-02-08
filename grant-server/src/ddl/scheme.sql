-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.4.11-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- sso 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `sso` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sso`;

-- 테이블 sso.000_service 구조 내보내기
CREATE TABLE IF NOT EXISTS `000_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.000_service:1 rows 내보내기
/*!40000 ALTER TABLE `000_service` DISABLE KEYS */;
INSERT INTO `000_service` (`id`, `name`) VALUES
	(1, 'some_service');
/*!40000 ALTER TABLE `000_service` ENABLE KEYS */;

-- 테이블 sso.001_client 구조 내보내기
CREATE TABLE IF NOT EXISTS `001_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `secret_key` varchar(255) NOT NULL,
  `redirect_url` varchar(255) NOT NULL,
  `reg_date` datetime NOT NULL DEFAULT current_timestamp(),
  `refresh_token_allow_minutes` int(11) NOT NULL DEFAULT 300,
  `access_token_allow_minutes` int(11) NOT NULL DEFAULT 30,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.001_client:1 rows 내보내기
/*!40000 ALTER TABLE `001_client` DISABLE KEYS */;
INSERT INTO `001_client` (`id`, `name`, `secret_key`, `redirect_url`, `reg_date`, `refresh_token_allow_minutes`, `access_token_allow_minutes`) VALUES
	(1, 'my_book', 'secretKey', 'http://127.0.0.1:38080/login/oauth2/code/mySSO', '2020-02-07 23:46:14', 300, 30);
/*!40000 ALTER TABLE `001_client` ENABLE KEYS */;

-- 테이블 sso.001_client_scope 구조 내보내기
CREATE TABLE IF NOT EXISTS `001_client_scope` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scope` varchar(255) NOT NULL,
  `client_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdo3cf07cvx14sx4ojw4a0990w` (`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.001_client_scope:1 rows 내보내기
/*!40000 ALTER TABLE `001_client_scope` DISABLE KEYS */;
INSERT INTO `001_client_scope` (`id`, `scope`, `client_id`) VALUES
	(1, 'user_grant:read', 1);
/*!40000 ALTER TABLE `001_client_scope` ENABLE KEYS */;

-- 테이블 sso.001_client_with_service 구조 내보내기
CREATE TABLE IF NOT EXISTS `001_client_with_service` (
  `client_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`service_id`),
  KEY `FKfq9t4j0dwy8iwtb5g768taeto` (`client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.001_client_with_service:1 rows 내보내기
/*!40000 ALTER TABLE `001_client_with_service` DISABLE KEYS */;
INSERT INTO `001_client_with_service` (`client_id`, `service_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `001_client_with_service` ENABLE KEYS */;

-- 테이블 sso.005_user 구조 내보내기
CREATE TABLE IF NOT EXISTS `005_user` (
  `no` int(11) NOT NULL,
  `id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `ip_check` bit(1) NOT NULL,
  `global_lock` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `need_change_password` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `reg_date` datetime NOT NULL DEFAULT current_timestamp(),
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.005_user:1 rows 내보내기
/*!40000 ALTER TABLE `005_user` DISABLE KEYS */;
INSERT INTO `005_user` (`no`, `id`, `email`, `ip_check`, `global_lock`, `name`, `need_change_password`, `password`, `reg_date`, `description`) VALUES
	(1, 'test_user', 'test@book_service.com', b'0', b'0', 'test', b'0', 'password_1234', '2020-02-07 23:45:45', NULL);
/*!40000 ALTER TABLE `005_user` ENABLE KEYS */;

-- 테이블 sso.007a_user_legacy_auth 구조 내보내기
CREATE TABLE IF NOT EXISTS `007a_user_legacy_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `client_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth` (`auth`),
  KEY `FKp4gtsljkp9c1t809p03qt4qr6` (`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.007a_user_legacy_auth:1 rows 내보내기
/*!40000 ALTER TABLE `007a_user_legacy_auth` DISABLE KEYS */;
INSERT INTO `007a_user_legacy_auth` (`id`, `auth`, `description`, `client_id`) VALUES
	(1, 'hello_auth', NULL, 1);
/*!40000 ALTER TABLE `007a_user_legacy_auth` ENABLE KEYS */;

-- 테이블 sso.007b_user_authorities_legacy 구조 내보내기
CREATE TABLE IF NOT EXISTS `007b_user_authorities_legacy` (
  `id` int(11) NOT NULL,
  `legacy_auth_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhg47mm191nn2chxhqkvoxk02i` (`legacy_auth_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.007b_user_authorities_legacy:1 rows 내보내기
/*!40000 ALTER TABLE `007b_user_authorities_legacy` DISABLE KEYS */;
INSERT INTO `007b_user_authorities_legacy` (`id`, `legacy_auth_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `007b_user_authorities_legacy` ENABLE KEYS */;

-- 테이블 sso.007_user_authorities 구조 내보내기
CREATE TABLE IF NOT EXISTS `007_user_authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_type` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `user_no` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5hrpst80vjklfrjchsllhqk4` (`client_id`),
  KEY `FK4jc9mcfaoj7m9kju3w6chh7gi` (`user_no`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.007_user_authorities:1 rows 내보내기
/*!40000 ALTER TABLE `007_user_authorities` DISABLE KEYS */;
INSERT INTO `007_user_authorities` (`id`, `auth_type`, `client_id`, `user_no`) VALUES
	(1, 1, 1, 1);
/*!40000 ALTER TABLE `007_user_authorities` ENABLE KEYS */;

-- 테이블 sso.008_book_app_menu 구조 내보내기
CREATE TABLE IF NOT EXISTS `008_book_app_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `type` varchar(31) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `method` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_code` (`type`,`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.008_book_app_menu:0 rows 내보내기
/*!40000 ALTER TABLE `008_book_app_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `008_book_app_menu` ENABLE KEYS */;

-- 테이블 sso.008_new_user_authorities 구조 내보내기
CREATE TABLE IF NOT EXISTS `008_new_user_authorities` (
  `id` int(11) NOT NULL,
  `book_app_auth_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKthsxio8w7j9h2ungmg3aw492f` (`book_app_auth_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.008_new_user_authorities:0 rows 내보내기
/*!40000 ALTER TABLE `008_new_user_authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `008_new_user_authorities` ENABLE KEYS */;

-- 테이블 sso.008_user_authorities_book_app 구조 내보내기
CREATE TABLE IF NOT EXISTS `008_user_authorities_book_app` (
  `authority_type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `authority_role` varchar(255) DEFAULT NULL,
  `authority_menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK92hcruuxdaaddkpqsj7nm15y0` (`authority_menu_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 테이블 데이터 sso.008_user_authorities_book_app:0 rows 내보내기
/*!40000 ALTER TABLE `008_user_authorities_book_app` DISABLE KEYS */;
/*!40000 ALTER TABLE `008_user_authorities_book_app` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
