-- ----------------------------
-- Table structure for hotpot_function
-- ----------------------------
CREATE TABLE IF NOT EXISTS `hotpot_function` (
  `id` varchar(36) NOT NULL,
  `function_name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `is_menu` varchar(3) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enabled` varchar(3) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `image1_id` varchar(255) DEFAULT NULL,
  `image2_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotpot_function
-- ----------------------------
INSERT IGNORE INTO `hotpot_function` VALUES ('1', '高级管理', '1', '', 'Y', null, 'Y', '0', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('11', '菜单管理', '11', '{\"path\":\"/menuConfig\", \"component\": \"common/menuConfig/menuConfig.vue\", \"type\": \"div\", \"openMethod\": \"default\"}', 'Y', null, 'Y', '1', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('111', '接口列表配置', '111', '{\"path\":\"/menuConfig/restConfig\", \"component\": \"common/menuConfig/restConfig.vue\", \"type\": \"div\", \"openMethod\": \"default\"}', 'N', null, 'Y', '11', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('112', '高级查询配置', '112', '{\"path\":\"/menuConfig/tabConfig\", \"component\": \"common/menuConfig/tabConfig.vue\", \"type\": \"div\", \"openMethod\": \"default\"}', 'N', null, 'Y', '11', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('113', '模板配置', '113', '{\"path\":\"/menuConfig/framework\", \"component\": \"common/menuConfig/frameworkConfig.vue\", \"type\": \"div\", \"openMethod\": \"default\"}', 'N', null, 'Y', '11', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('12', '字典管理', '12', '{\"path\":\"/dictionary\", \"component\": \"common/dictionary/dictionary.vue\", \"type\": \"div\", \"openMethod\": \"default\"}', 'Y', null, 'Y', '1', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('13', '角色管理', '13', '{\"component\":\"common/roleEdit/roleConfig.vue\",\"openMethod\":\"default\",\"path\":\"/roleConfig\",\"type\":\"div\",\"url\":\"\"}', 'Y', null, 'Y', '1', null, '', null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('131', '角色配置', '131', '{\"path\":\"/roleConfig/editRole\", \"component\": \"common/roleEdit/addRole.vue\", \"type\": \"div\", \"openMethod\": \"default\"}', 'N', null, 'Y', '13', null, null, null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('16', '文件管理', '16', '{\"component\":\"common/fileMag.vue\",\"openMethod\":\"default\",\"path\":\"/fileMag\",\"type\":\"div\",\"url\":\"\"}', 'Y', null, 'Y', '1', null, '', null, null, null, null);
INSERT IGNORE INTO `hotpot_function` VALUES ('17', '应用监控', '17', '{\"component\":\"common/sba.vue\",\"openMethod\":\"default\",\"path\":\"/bootAdmin\",\"type\":\"div\",\"url\":\"\"}', 'Y', null, 'Y', '1', null, '', null, null, null, null);

-- ----------------------------
-- Table structure for hotpot_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `hotpot_role` (
  `id` varchar(36) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `del_flag` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotpot_role
-- ----------------------------
INSERT IGNORE INTO `hotpot_role` VALUES ('1', 'test', 'test', null, null, null, null, '0');
INSERT IGNORE INTO `hotpot_role` VALUES ('9999', 'administrator', 'admin', null, null, null, null, '0');

-- ----------------------------
-- Table structure for hotpot_role_function
-- ----------------------------
CREATE TABLE IF NOT EXISTS `hotpot_role_function` (
  `function_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`function_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotpot_role_function
-- ----------------------------
INSERT IGNORE INTO `hotpot_role_function` VALUES ('1', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('11', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('111', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('112', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('113', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('12', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('13', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('131', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('16', '9999', '2018-05-02 18:37:59', 'sys');
INSERT IGNORE INTO `hotpot_role_function` VALUES ('17', '9999', '2018-05-02 18:37:59', 'sys');

-- ----------------------------
-- Table structure for hotpot_role_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `hotpot_role_user` (
  `role_id` varchar(36) NOT NULL,
  `party_id` varchar(36) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `type` varchar(3) NOT NULL COMMENT '0.人员 1.组织 2.团队',
  PRIMARY KEY (`role_id`,`party_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
