-- SQL Manager 2007 for MySQL 4.1.2.1
-- ---------------------------------------
-- Host     : 192.168.1.231
-- Port     : 3306
-- Database : lepao_test2


--
-- Structure for the `audit_log` table : 
--

DROP TABLE IF EXISTS `audit_log`;

CREATE TABLE `audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `primary_id` varchar(36) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `time` bigint(20) NOT NULL,
  `object` text NOT NULL,
  `old_object` text,
  `description` varchar(100) DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `extra2` varchar(255) DEFAULT NULL,
  `extra3` varchar(255) DEFAULT NULL,
  `type` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Structure for the `cartitem` table : 
--

DROP TABLE IF EXISTS `cartitem`;

CREATE TABLE `cartitem` (
  `user_id` bigint(20) NOT NULL,
  `product_id` int(20) NOT NULL,
  `quantity` int(10) NOT NULL
)  ;


--
-- Structure for the `lp_address` table : 
--

DROP TABLE IF EXISTS `lp_address`;

CREATE TABLE `lp_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(255) NOT NULL,
  `real_name` varchar(255) NOT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `is_default` tinyint(255) NOT NULL DEFAULT '0',
  `remark` text,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_admin` table : 
--

DROP TABLE IF EXISTS `lp_admin`;

CREATE TABLE `lp_admin` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT '' ,
  `login_id` varchar(30) NOT NULL ,
  `password` varchar(50) NOT NULL ,
  `zone` varchar(10) DEFAULT '' ,
  `is_super` smallint(6) NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_admin_authority` table : 
--

DROP TABLE IF EXISTS `lp_admin_authority`;

CREATE TABLE `lp_admin_authority` (
  `admin_id` bigint(20) NOT NULL ,
  `authority_id` bigint(20) NOT NULL ,
)  ;

--
-- Structure for the `lp_authority` table : 
--

DROP TABLE IF EXISTS `lp_authority`;

CREATE TABLE `lp_authority` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL ,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_batch` table : 
--

DROP TABLE IF EXISTS `lp_batch`;

CREATE TABLE `lp_batch` (
  `batch_id` int(3) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`batch_id`)
)  ;

--
-- Structure for the `lp_bracelet_id` table : 
--

DROP TABLE IF EXISTS `lp_bracelet_id`;

CREATE TABLE `lp_bracelet_id` (
  `bracelet_id` char(18) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`bracelet_id`)
)  ;

--
-- Structure for the `lp_bracelet_model` table : 
--

DROP TABLE IF EXISTS `lp_bracelet_model`;

CREATE TABLE `lp_bracelet_model` (
  `model_id` int(3) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(255) NOT NULL,
  PRIMARY KEY (`model_id`)
)  ;

--
-- Structure for the `lp_category` table : 
--

DROP TABLE IF EXISTS `lp_category`;

CREATE TABLE `lp_category` (
  `id` int(11) NOT NULL,
  `category_code` varchar(24) NOT NULL,
  `parent_category_code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `tag` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_conf_hobby` table : 
--

DROP TABLE IF EXISTS `lp_conf_hobby`;

CREATE TABLE `lp_conf_hobby` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `selected_id` varchar(30) DEFAULT NULL,
  `unselected_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_coupon` table : 
--

DROP TABLE IF EXISTS `lp_coupon`;

CREATE TABLE `lp_coupon` (
  `coupon_num` varchar(255) NOT NULL,
  `expired_date` bigint(255) NOT NULL DEFAULT '1389369599000' ,
  `source` varchar(50) DEFAULT '' ,
  `activity_id` varchar(50) NOT NULL DEFAULT '' ,
  `discount_amount` decimal(10,2) DEFAULT '299.00' ,
  `user_id` bigint(20) DEFAULT '0' ,
  `is_used` tinyint(1) DEFAULT '0' ,
  `used_time` bigint(255) DEFAULT '0' ,
  `used_user_id` bigint(255) DEFAULT '0' ,
  `order_id` bigint(255) DEFAULT '0' 
)  ;

--
-- Structure for the `lp_device_model` table : 
--

DROP TABLE IF EXISTS `lp_device_model`;

CREATE TABLE `lp_device_model` (
  `model_id` int(3) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(255) NOT NULL,
  PRIMARY KEY (`model_id`)
)  ;

--
-- Structure for the `lp_export_order` table : 
--

DROP TABLE IF EXISTS `lp_export_order`;

CREATE TABLE `lp_export_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT ,
  `operator` varchar(255) NOT NULL ,
  `export_time` bigint(20) NOT NULL ,
  `status` tinyint(1) NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_hobby` table : 
--

DROP TABLE IF EXISTS `lp_hobby`;

CREATE TABLE `lp_hobby` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hobby_id` varchar(100) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `lp_identify_code` table : 
--

DROP TABLE IF EXISTS `lp_identify_code`;

CREATE TABLE `lp_identify_code` (
  `identify_code` varchar(255) NOT NULL,
  `create_time` bigint(20) DEFAULT NULL
)  ;

--
-- Structure for the `lp_inventory` table : 
--

DROP TABLE IF EXISTS `lp_inventory`;

CREATE TABLE `lp_inventory` (
  `product_id` int(11) NOT NULL,
  `quantity` int(255) NOT NULL,
  `expend1` varchar(255) DEFAULT NULL,
  `expend2` varchar(255) DEFAULT NULL,
  `not_pay_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
)  ;

--
-- Structure for the `lp_invoice` table : 
--

DROP TABLE IF EXISTS `lp_invoice`;

CREATE TABLE `lp_invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `invoice_number` varchar(255) NOT NULL ,
  `user_id` bigint(20) NOT NULL ,
  `order_id` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL ,
  `invoice_type` int(1) DEFAULT NULL ,
  `sum_amount` decimal(10,2) DEFAULT NULL ,
  `title` varchar(50) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `lp_mobile_type_test` table : 
--

DROP TABLE IF EXISTS `lp_mobile_type_test`;

CREATE TABLE `lp_mobile_type_test` (
  `qq` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `workstation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_order` table : 
--

DROP TABLE IF EXISTS `lp_order`;

CREATE TABLE `lp_order` (
  `id` varchar(20) NOT NULL ,
  `user_id` bigint(255) NOT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `sum_amount` decimal(10,2) DEFAULT NULL,
  `discount_type` int(255) DEFAULT '0',
  `discount_amount` decimal(10,2) DEFAULT '0.00',
  `status` tinyint(255) DEFAULT '0' ,
  `delivery_time_type` int(11) DEFAULT '0' ,
  `pay_type` tinyint(255) DEFAULT '9' ,
  `pay_gateway_tx_num` varchar(255) DEFAULT NULL ,
  `pay_gateway_tx_status` int(255) DEFAULT '1' ,
  `pay_gateway_type` tinyint(1) DEFAULT '0' ,
  `pay_time` bigint(20) DEFAULT '0' ,
  `pay_detail` text ,
  `address_id` bigint(20) DEFAULT NULL,
  `type` int(2) DEFAULT '1',
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_order_item` table : 
--

DROP TABLE IF EXISTS `lp_order_item`;

CREATE TABLE `lp_order_item` (
  `order_id` varchar(16) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `num` int(11) NOT NULL,
  `expend1` varchar(255) DEFAULT NULL
)  ;

--
-- Structure for the `lp_order_logistics` table : 
--

DROP TABLE IF EXISTS `lp_order_logistics`;

CREATE TABLE `lp_order_logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(20) NOT NULL,
  `logistics_id` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_order_status` table : 
--

DROP TABLE IF EXISTS `lp_order_status`;

CREATE TABLE `lp_order_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(20) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `status_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `lp_origin_channel` table : 
--

DROP TABLE IF EXISTS `lp_origin_channel`;

CREATE TABLE `lp_origin_channel` (
  `origin_channel_id` int(3) NOT NULL AUTO_INCREMENT,
  `origin_channel_name` varchar(255) NOT NULL,
  PRIMARY KEY (`origin_channel_id`)
)  ;

--
-- Structure for the `lp_product` table : 
--

DROP TABLE IF EXISTS `lp_product`;

CREATE TABLE `lp_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) DEFAULT NULL ,
  `name` varchar(255) NOT NULL ,
  `price` decimal(10,2) DEFAULT NULL ,
  `description` varchar(255) NOT NULL ,
  `type` varchar(25) DEFAULT '' ,
  `image` varchar(250) DEFAULT '' ,
  `favorable_price` decimal(11,2) NOT NULL ,
  `delivery_time` bigint(30) DEFAULT '0' ,
  `color_number` varchar(8) DEFAULT '--000000' ,
  `is_withdraw` tinyint(8) DEFAULT '0' ,
  `limit_number` int(11) NOT NULL DEFAULT '0' ,
  `color` varchar(10) DEFAULT NULL ,
  `category_id` int(11) DEFAULT NULL ,
  `buy_num` int(11) DEFAULT '0' ,
  `is_discard` tinyint(8) DEFAULT '0' ,
  `create_time` bigint(20) DEFAULT '0' ,
  `freight` decimal(10,2) DEFAULT '0.00' ,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `lp_product_return` table : 
--

DROP TABLE IF EXISTS `lp_product_return`;

CREATE TABLE `lp_product_return` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `returnId` varchar(25) NOT NULL,
  `name` varchar(25) NOT NULL,
  `mobile` varchar(25) NOT NULL,
  `order_id` varchar(25) NOT NULL,
  `buy_channel` tinyint(4) DEFAULT '0',
  `request_service` tinyint(4) DEFAULT '0',
  `request_reason` text,
  `images` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_promotion` table : 
--

DROP TABLE IF EXISTS `lp_promotion`;

CREATE TABLE `lp_promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `links` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_reset_password` table : 
--

DROP TABLE IF EXISTS `lp_reset_password`;

CREATE TABLE `lp_reset_password` (
  `uuid` varchar(255) NOT NULL,
  `reset_time` bigint(20) NOT NULL,
  PRIMARY KEY (`uuid`)
)  ;

--
-- Structure for the `lp_survey` table : 
--

DROP TABLE IF EXISTS `lp_survey`;

CREATE TABLE `lp_survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '',
  `sex` tinyint(4) DEFAULT '0',
  `birthday` varchar(255) DEFAULT '1970',
  `province` varchar(20) DEFAULT '',
  `city` varchar(255) DEFAULT '',
  `address` varchar(255) DEFAULT '',
  `career` tinyint(4) DEFAULT '0',
  `education` tinyint(4) DEFAULT '0',
  `income` tinyint(4) DEFAULT '0',
  `mobile_type` varchar(255) DEFAULT '',
  `mobile` varchar(255) DEFAULT '',
  `experience` varchar(255) DEFAULT '',
  `where_know_lepao` varchar(255) DEFAULT '',
  `three_apps` varchar(255) DEFAULT '',
  `if_used_any_app` tinyint(4) DEFAULT '0',
  `used_app_name` varchar(255) DEFAULT '',
  `if_sport` tinyint(4) DEFAULT '0',
  `which_sports` varchar(255) DEFAULT '',
  `with_who` tinyint(4) DEFAULT '0',
  `where_sport` varchar(255) DEFAULT '',
  `join_sport` tinyint(4) DEFAULT '0',
  `why_buy` varchar(255) DEFAULT '',
  `expect_function` varchar(255) DEFAULT '',
  `which_function_you_like` varchar(255) DEFAULT '',
  `where_sport_other` varchar(255) DEFAULT '',
  `where_you_know_lepao_other` varchar(255) DEFAULT '',
  `if_used_sport_other` varchar(255) DEFAULT NULL,
  `mobile_brand` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_tickets` table : 
--

DROP TABLE IF EXISTS `lp_tickets`;

CREATE TABLE `lp_tickets` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_user` table : 
--

DROP TABLE IF EXISTS `lp_user`;

CREATE TABLE `lp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `portrait` varchar(100) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `birthday` bigint(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `zip_code` varchar(50) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `platform_type` varchar(255) DEFAULT NULL,
  `platform_id` varchar(255) DEFAULT NULL,
  `is_active` int(11) DEFAULT NULL,
  `lepao_coupon` int(11) DEFAULT NULL,
  `last_login_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `lp_user_info` table : 
--

DROP TABLE IF EXISTS `lp_user_info`;

CREATE TABLE `lp_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `telphone` varchar(50) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zip_code` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `lp_color` varchar(100) DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `send_code` table : 
--

DROP TABLE IF EXISTS `send_code`;

CREATE TABLE `send_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `telphone` varchar(50) DEFAULT NULL,
  `telcode` varchar(50) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_alarm_clock` table : 
--

DROP TABLE IF EXISTS `t_alarm_clock`;

CREATE TABLE `t_alarm_clock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `alert_time` bigint(20) NOT NULL,
  `cycle` varchar(25) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `genus` varchar(10) DEFAULT '' ,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_app_sport` table : 
--

DROP TABLE IF EXISTS `t_app_sport`;

CREATE TABLE `t_app_sport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT '0' ,
  `genus` varchar(255) DEFAULT '' ,
  `steps` int(11) DEFAULT '0' ,
  `distance` int(11) DEFAULT '0',
  `time` bigint(20) DEFAULT '0' ,
  `calorie` varchar(10) DEFAULT '',
  `active_time` bigint(20) DEFAULT '0' ,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_app_version` table : 
--

DROP TABLE IF EXISTS `t_app_version`;

CREATE TABLE `t_app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version_name` varchar(50) DEFAULT NULL,
  `facility` varchar(100) DEFAULT NULL,
  `updateUrl` varchar(255) DEFAULT NULL,
  `createTime` timestamp DEFAULT NULL ,
  `version_code` int(11) DEFAULT NULL,
  `facility_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_bracelet_num` table : 
--

DROP TABLE IF EXISTS `t_bracelet_num`;

CREATE TABLE `t_bracelet_num` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT '0',
  `bracelet_num` varchar(255) DEFAULT '',
  `create_time` bigint(20) DEFAULT '0',
  `active_time` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_bracelet_sport` table : 
--

DROP TABLE IF EXISTS `t_bracelet_sport`;

CREATE TABLE `t_bracelet_sport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(255) DEFAULT '0',
  `genus` varchar(255)  DEFAULT '',
  `steps` int(11) DEFAULT '0',
  `distance` int(11) DEFAULT '0',
  `time` bigint(20) DEFAULT '0',
  `calorie` varchar(10) DEFAULT '',
  `active_time` bigint(20) DEFAULT '0' ,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_confirmed_device` table : 
--

DROP TABLE IF EXISTS `t_confirmed_device`;

CREATE TABLE `t_confirmed_device` (
  `device` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `product` varchar(100) DEFAULT NULL,
  `is_reverse` smallint(6) DEFAULT NULL ,
  `use_stream` smallint(6) DEFAULT NULL ,
  `record_type` smallint(6) DEFAULT NULL ,
  `algo_type` smallint(6) DEFAULT NULL ,
  `remark` text,
  `prewave` tinyint(4) DEFAULT '0'
)  ;

--
-- Structure for the `t_confirmed_device_2` table : 
--

DROP TABLE IF EXISTS `t_confirmed_device_2`;

CREATE TABLE `t_confirmed_device_2` (
  `device` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `product` varchar(100) DEFAULT NULL,
  `is_reverse` smallint(6) DEFAULT NULL ,
  `use_stream` smallint(6) DEFAULT NULL ,
  `record_type` smallint(6) DEFAULT NULL ,
  `algo_type` smallint(6) DEFAULT NULL ,
  `remark` text
)  ;

--
-- Structure for the `t_daily_goal` table : 
--

DROP TABLE IF EXISTS `t_daily_goal`;

CREATE TABLE `t_daily_goal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calories` int(11) DEFAULT NULL,
  `steps` int(11) DEFAULT NULL,
  `meters` int(11) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_default_goal` table : 
--

DROP TABLE IF EXISTS `t_default_goal`;

CREATE TABLE `t_default_goal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `minCalories` int(11) DEFAULT NULL,
  `maxCalories` int(11) DEFAULT NULL,
  `calories` int(11) DEFAULT NULL,
  `minSteps` int(11) DEFAULT NULL,
  `maxSteps` int(11) DEFAULT NULL,
  `steps` int(11) DEFAULT NULL,
  `minMeters` int(11) DEFAULT NULL,
  `maxMeters` int(11) DEFAULT NULL,
  `meters` int(11) DEFAULT NULL,
  `minAge` int(11) DEFAULT NULL,
  `maxAge` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_device` table : 
--

DROP TABLE IF EXISTS `t_device`;

CREATE TABLE `t_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(22) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `product_type` int(11) DEFAULT NULL,
  `product_no` varchar(3) DEFAULT NULL,
  `area` varchar(3) DEFAULT NULL,
  `product_line` varchar(3) DEFAULT NULL,
  `extra_code` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_device_info` table : 
--

DROP TABLE IF EXISTS `t_device_info`;

CREATE TABLE `t_device_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `udid` varchar(255) DEFAULT '',
  `client_version` varchar(255) DEFAULT '',
  `device` varchar(255) DEFAULT '',
  `model` varchar(255) DEFAULT '',
  `product` varchar(255) DEFAULT '',
  `phone_version` varchar(255) DEFAULT '',
  `ratio` varchar(255) DEFAULT '',
  `network` varchar(255) DEFAULT '',
  `op` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_diagram` table : 
--

DROP TABLE IF EXISTS `t_diagram`;

CREATE TABLE `t_diagram` (
  `id` varchar(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `trainingId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_diagram02` table : 
--

DROP TABLE IF EXISTS `t_diagram02`;

CREATE TABLE `t_diagram02` (
  `id` varchar(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `trainingId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_error_info` table : 
--

DROP TABLE IF EXISTS `t_error_info`;

CREATE TABLE `t_error_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `code` varchar(40) DEFAULT '',
  `detail` text,
  `genus` varchar(10) DEFAULT '' ,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_excution` table : 
--

DROP TABLE IF EXISTS `t_excution`;

CREATE TABLE `t_excution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` bigint(20) DEFAULT NULL,
  `isDone` tinyint(4) DEFAULT NULL,
  `usedMinutes` int(11) DEFAULT NULL,
  `remainPostponeTimes` int(11) DEFAULT NULL,
  `scheduleId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_failed_device` table : 
--

DROP TABLE IF EXISTS `t_failed_device`;

CREATE TABLE `t_failed_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `product` varchar(100) DEFAULT NULL,
  `failed_times` bigint(20) DEFAULT '1',
  `complaint_times` bigint(20) DEFAULT NULL,
  `success_times` bigint(20) DEFAULT NULL,
  `not_complete_success_times` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_feedback` table : 
--

DROP TABLE IF EXISTS `t_feedback`;

CREATE TABLE `t_feedback` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `feedBackType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_long_sit` table : 
--

DROP TABLE IF EXISTS `t_long_sit`;

CREATE TABLE `t_long_sit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `genus` varchar(255) DEFAULT '',
  `state` tinyint(4) DEFAULT '0',
  `duration` int(11) DEFAULT '0',
  `start_time` bigint(20) DEFAULT '0',
  `end_time` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`,`user_id`)
)  ;

--
-- Structure for the `t_measure` table : 
--

DROP TABLE IF EXISTS `t_measure`;

CREATE TABLE `t_measure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calories` int(11) DEFAULT NULL,
  `steps` int(11) DEFAULT NULL,
  `meters` int(11) DEFAULT NULL,
  `velocity` int(11) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `recordTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_original_data` table : 
--

DROP TABLE IF EXISTS `t_original_data`;

CREATE TABLE `t_original_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `day_index` int(11) NOT NULL,
  `state` tinyint(1) NOT NULL,
  `is_sleep` tinyint(1) NOT NULL,
  `begin` bigint(20) NOT NULL,
  `duration` int(11) NOT NULL,
  `steps` int(11) NOT NULL,
  `distance` int(11) NOT NULL,
  `genus` varchar(10) DEFAULT '' ,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_programme` table : 
--

DROP TABLE IF EXISTS `t_programme`;

CREATE TABLE `t_programme` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `maxPostponeTimes` int(11) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `programmeType` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_schedule` table : 
--

DROP TABLE IF EXISTS `t_schedule`;

CREATE TABLE `t_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `programmeId` varchar(50) NOT NULL,
  `beginTime` bigint(20) DEFAULT NULL,
  `remainPostponeTimes` int(20) DEFAULT NULL,
  `alermTimeInWorkingDay` bigint(20) DEFAULT NULL,
  `alermTimeInWeekend` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_single_movement` table : 
--

DROP TABLE IF EXISTS `t_single_movement`;

CREATE TABLE `t_single_movement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `startTime` bigint(20) DEFAULT NULL,
  `endTime` bigint(20) DEFAULT NULL,
  `timeCount` int(11) DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `positions` text,
  `source` int(11) DEFAULT NULL,
  `pattern` int(11) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `xScale` varchar(255) DEFAULT NULL,
  `speedYScale` varchar(255) DEFAULT NULL,
  `altitudes` varchar(255) DEFAULT NULL,
  `speeds` varchar(255) DEFAULT NULL,
  `altitudeYScale` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_sleep_data` table : 
--

DROP TABLE IF EXISTS `t_sleep_data`;

CREATE TABLE `t_sleep_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0',
  `genus` varchar(255) DEFAULT '',
  `start_time` bigint(20) DEFAULT '0',
  `end_time` bigint(20) DEFAULT '0',
  `belong_day` bigint(20) DEFAULT '0' ,
  `state` tinyint(4) DEFAULT '0' ,
  `duration` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_sync_info` table : 
--

DROP TABLE IF EXISTS `t_sync_info`;

CREATE TABLE `t_sync_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `genus` varchar(10) DEFAULT NULL,
  `device` varchar(100) DEFAULT NULL,
  `product` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `type` smallint(6) DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `info` longtext,
  `date` varchar(25) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `api_level` varchar(40) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `in_white_list` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_synchronous` table : 
--

DROP TABLE IF EXISTS `t_synchronous`;

CREATE TABLE `t_synchronous` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `syncTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)   ;

--
-- Structure for the `t_to_confirm_device` table : 
--

DROP TABLE IF EXISTS `t_to_confirm_device`;

CREATE TABLE `t_to_confirm_device` (
  `device` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `product` varchar(100) DEFAULT NULL,
  `is_reverse` smallint(6) DEFAULT NULL ,
  `use_stream` smallint(6) DEFAULT NULL ,
  `record_type` smallint(6) DEFAULT NULL ,
  `algo_type` smallint(6) DEFAULT NULL ,
  `success_count` int(11) DEFAULT '0',
  `total_count` int(11) DEFAULT '0',
  `remark` text,
  `create_time` varchar(20) DEFAULT NULL,
  `in_white_list` smallint(2) DEFAULT '0'
)  ;

--
-- Structure for the `t_training` table : 
--

DROP TABLE IF EXISTS `t_training`;

CREATE TABLE `t_training` (
  `id` varchar(11) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '',
  `summary` varchar(255) DEFAULT NULL,
  `volume` int(11) DEFAULT NULL,
  `unit` varchar(50) DEFAULT NULL,
  `trainingDayId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_training_day` table : 
--

DROP TABLE IF EXISTS `t_training_day`;

CREATE TABLE `t_training_day` (
  `id` varchar(11) NOT NULL,
  `days` int(11) DEFAULT NULL,
  `programmeId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--
-- Structure for the `t_update_desc` table : 
--

DROP TABLE IF EXISTS `t_update_desc`;

CREATE TABLE `t_update_desc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desc` varchar(255) DEFAULT NULL,
  `versionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Structure for the `t_user_settings` table : 
--

DROP TABLE IF EXISTS `t_user_settings`;

CREATE TABLE `t_user_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `goal` int(11) DEFAULT '0',
  `bracelet_sensitive` tinyint(4) DEFAULT '0',
  `auto_switch_sleep_mode` tinyint(4) DEFAULT '0',
  `app_pedometer` tinyint(4) DEFAULT '0',
  `genus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`)
)   ;


