/*
Navicat MySQL Data Transfer

Source Server         : 10.64.11.73
Source Server Version : 50712
Source Host           : 10.64.11.73:3306
Source Database       : new_titancode_test

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2018-09-15 12:30:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for api_base
-- ----------------------------
DROP TABLE IF EXISTS `api_base`;
CREATE TABLE `api_base` (
  `id` varchar(64) NOT NULL,
  `project_id` varchar(64) NOT NULL,
  `version_name` varchar(64) NOT NULL,
  `base_path` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_obj
-- ----------------------------
DROP TABLE IF EXISTS `api_obj`;
CREATE TABLE `api_obj` (
  `id` varchar(64) NOT NULL,
  `api_base_id` varchar(64) NOT NULL,
  `uri` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `prefix_name` varchar(32) DEFAULT NULL COMMENT '类名前缀（默认为空）',
  `request_method` varchar(32) NOT NULL,
  `response_obj_name` varchar(200) NOT NULL,
  `response_obj_generic_type` varchar(64) DEFAULT NULL,
  `response_obj_generic_array_type` varchar(64) DEFAULT NULL,
  `response_obj_generic_format` varchar(200) DEFAULT NULL,
  `produces` varchar(255) DEFAULT NULL COMMENT '生成类型',
  `consumes` varchar(255) DEFAULT NULL COMMENT '消费类型',
  `summary` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tag` varchar(200) DEFAULT NULL,
  `gen_based_table_id` varchar(64) DEFAULT NULL COMMENT '自动生成表id',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `gen_related_table_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_param
-- ----------------------------
DROP TABLE IF EXISTS `api_param`;
CREATE TABLE `api_param` (
  `id` varchar(64) NOT NULL,
  `api_obj_id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `form` varchar(32) NOT NULL,
  `is_required` char(1) DEFAULT NULL,
  `type` varchar(64) NOT NULL,
  `array_type` varchar(64) DEFAULT NULL,
  `format` varchar(200) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_view
-- ----------------------------
DROP TABLE IF EXISTS `api_view`;
CREATE TABLE `api_view` (
  `id` varchar(64) NOT NULL,
  `project_id` varchar(64) NOT NULL COMMENT '项目id',
  `table_config` text COMMENT '表格配置',
  `form_config` text COMMENT '表单配置',
  `save_path` varchar(255) DEFAULT NULL COMMENT '新增修改请求地址',
  `search_path` varchar(255) DEFAULT NULL COMMENT '查询请求地址',
  `delete_path` varchar(255) DEFAULT NULL COMMENT '删除请求地址',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `is_add` char(1) DEFAULT NULL,
  `is_modify` char(1) DEFAULT NULL,
  `is_delete` char(1) DEFAULT NULL,
  `is_page` char(1) DEFAULT NULL,
  `update_path` varchar(255) DEFAULT NULL,
  `table_id` varchar(64) NOT NULL,
  `config_name` varchar(255) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_view_form_config
-- ----------------------------
DROP TABLE IF EXISTS `api_view_form_config`;
CREATE TABLE `api_view_form_config` (
  `id` varchar(64) NOT NULL,
  `columnd_id` varchar(64) NOT NULL COMMENT '字段Id',
  `table_id` varchar(64) NOT NULL COMMENT '表Id',
  `show_flag` char(1) DEFAULT NULL COMMENT '是否显示',
  `order` int(10) DEFAULT NULL COMMENT '显示顺序',
  `is_validate` char(1) DEFAULT NULL,
  `is_nullable` char(1) DEFAULT NULL,
  `read_only` char(1) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `min` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_view_table_config
-- ----------------------------
DROP TABLE IF EXISTS `api_view_table_config`;
CREATE TABLE `api_view_table_config` (
  `id` varchar(64) NOT NULL,
  `columnd_id` varchar(64) NOT NULL COMMENT '字段Id',
  `table_id` varchar(64) NOT NULL COMMENT '表Id',
  `show_flag` char(1) DEFAULT NULL COMMENT '是否显示',
  `search_flag` char(1) DEFAULT NULL COMMENT '是否查询',
  `order` int(10) DEFAULT NULL COMMENT '显示顺序',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for columnd
-- ----------------------------
DROP TABLE IF EXISTS `columnd`;
CREATE TABLE `columnd` (
  `id` varchar(64) NOT NULL,
  `table_id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `jdbc_type` varchar(100) NOT NULL,
  `java_type` varchar(255) NOT NULL,
  `java_field` varchar(200) NOT NULL,
  `is_pk` char(1) DEFAULT NULL,
  `is_nullable` char(1) DEFAULT NULL,
  `read_only` char(1) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `min` int(11) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `dict_type_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for context
-- ----------------------------
DROP TABLE IF EXISTS `context`;
CREATE TABLE `context` (
  `id` varchar(64) NOT NULL,
  `directory_id` varchar(64) DEFAULT NULL COMMENT '目录菜单ID',
  `content` mediumtext COMMENT '文档内容',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(128) DEFAULT NULL COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `updated_by` varchar(128) DEFAULT NULL COMMENT '修改人',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识  0:无标识 显示  1:有标识不显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource` (
  `id` varchar(64) NOT NULL,
  `project_id` varchar(64) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '数据库名称（用于package）',
  `package_name` varchar(32) DEFAULT NULL,
  `dbtype` varchar(32) NOT NULL,
  `dbdriver` varchar(200) NOT NULL,
  `dburl` varchar(200) NOT NULL,
  `dbuser` varchar(100) NOT NULL,
  `dbpassword` varchar(100) NOT NULL,
  `validation_query` varchar(200) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
  `id` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `del_flag` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dict_value
-- ----------------------------
DROP TABLE IF EXISTS `dict_value`;
CREATE TABLE `dict_value` (
  `id` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `dict_code` varchar(64) NOT NULL,
  `sort` int(11) NOT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `del_flag` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for directory
-- ----------------------------
DROP TABLE IF EXISTS `directory`;
CREATE TABLE `directory` (
  `id` varchar(64) NOT NULL,
  `pid` varchar(64) DEFAULT NULL COMMENT '该目录父ID',
  `name` varchar(128) DEFAULT NULL COMMENT '目录名称',
  `sort` varchar(16) DEFAULT NULL COMMENT '排序',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `content_flag` char(1) DEFAULT NULL COMMENT '文档标识  0:该目录无文档 1:该目录有文档',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识  0:未标识删除  1:标识删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单目录';

-- ----------------------------
-- Table structure for equipment_detail
-- ----------------------------
DROP TABLE IF EXISTS `equipment_detail`;
CREATE TABLE `equipment_detail` (
  `id` varchar(64) NOT NULL,
  `intranet_ip` varchar(64) NOT NULL COMMENT '内网ip',
  `mapping_ip` varchar(64) NOT NULL,
  `app_name` varchar(255) NOT NULL COMMENT '应用名称',
  `operating` varchar(200) NOT NULL COMMENT '操作系统',
  `maintenance_person` varchar(200) NOT NULL COMMENT '维护人',
  `user_name_one` varchar(64) NOT NULL COMMENT '帐号',
  `password_one` varchar(64) NOT NULL COMMENT '密码',
  `user_name_two` varchar(64) NOT NULL,
  `password_two` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for file_resources
-- ----------------------------
DROP TABLE IF EXISTS `file_resources`;
CREATE TABLE `file_resources` (
  `id` varchar(64) NOT NULL,
  `path` varchar(255) NOT NULL COMMENT '文件上传的真实路径',
  `small` varchar(255) DEFAULT NULL COMMENT '缩略图的生成路径',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件上传前的真实名字',
  `uploadname` varchar(255) DEFAULT NULL COMMENT '上传到本地服务器的文件名字',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` varchar(255) DEFAULT NULL COMMENT '文件上传的用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for hotpot_function
-- ----------------------------
DROP TABLE IF EXISTS `hotpot_function`;
CREATE TABLE `hotpot_function` (
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
-- Table structure for hotpot_role
-- ----------------------------
DROP TABLE IF EXISTS `hotpot_role`;
CREATE TABLE `hotpot_role` (
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
-- Table structure for hotpot_role_function
-- ----------------------------
DROP TABLE IF EXISTS `hotpot_role_function`;
CREATE TABLE `hotpot_role_function` (
  `function_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`function_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hotpot_role_user
-- ----------------------------
DROP TABLE IF EXISTS `hotpot_role_user`;
CREATE TABLE `hotpot_role_user` (
  `role_id` varchar(36) NOT NULL,
  `party_id` varchar(36) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `type` varchar(3) NOT NULL COMMENT '0.人员 1.组织 2.团队',
  PRIMARY KEY (`role_id`,`party_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for party_project
-- ----------------------------
DROP TABLE IF EXISTS `party_project`;
CREATE TABLE `party_project` (
  `id` varchar(64) NOT NULL,
  `project_id` varchar(64) NOT NULL COMMENT '项目ID',
  `party_id` varchar(64) NOT NULL COMMENT '组织ID或用户ID',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `flag` char(1) DEFAULT NULL COMMENT '权限标识 0:该项目是用户自己创建的  1:该项目权限是用户加入的',
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plugs_info
-- ----------------------------
DROP TABLE IF EXISTS `plugs_info`;
CREATE TABLE `plugs_info` (
  `id` varchar(64) NOT NULL,
  `pic_id` varchar(64) DEFAULT NULL COMMENT '插件对应的图片id',
  `pic_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `download_url` varchar(255) DEFAULT NULL COMMENT '插件下载地址',
  `plugs_name` varchar(255) DEFAULT NULL COMMENT '插件名称',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识 0:未标识 1:标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `packages` varchar(100) NOT NULL,
  `components` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isdictionary` varchar(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `app_id` varchar(64) DEFAULT NULL,
  `app_pub_key` varchar(500) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `modify_id` varchar(64) DEFAULT NULL,
  `modify_name` varchar(100) DEFAULT NULL,
  `modify_ip` varchar(100) DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `flag` char(1) DEFAULT NULL COMMENT '星星标识 0:未标识 1:标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_project_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for table_relation
-- ----------------------------
DROP TABLE IF EXISTS `table_relation`;
CREATE TABLE `table_relation` (
  `id` varchar(64) NOT NULL,
  `master_table_id` varchar(64) NOT NULL COMMENT '主表id',
  `slave_table_id` varchar(64) NOT NULL COMMENT '从表id',
  `relation` varchar(32) DEFAULT NULL COMMENT '关联关系(one_to_one, one_to_many)',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `slave_column_type` varchar(255) NOT NULL,
  `slave_column_name` varchar(255) NOT NULL,
  `master_column_name` varchar(255) NOT NULL,
  `master_column_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for table_senior_column
-- ----------------------------
DROP TABLE IF EXISTS `table_senior_column`;
CREATE TABLE `table_senior_column` (
  `id` varchar(64) NOT NULL,
  `senior_slave_id` varchar(64) NOT NULL,
  `master_column_name` varchar(255) NOT NULL,
  `master_column_type` varchar(255) NOT NULL,
  `slave_column_name` varchar(255) DEFAULT NULL,
  `slave_column_type` varchar(255) DEFAULT NULL,
  `operate` varchar(60) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for table_senior_relation
-- ----------------------------
DROP TABLE IF EXISTS `table_senior_relation`;
CREATE TABLE `table_senior_relation` (
  `id` varchar(64) NOT NULL,
  `master_table_id` varchar(64) NOT NULL,
  `master_table_name` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for table_senior_slave
-- ----------------------------
DROP TABLE IF EXISTS `table_senior_slave`;
CREATE TABLE `table_senior_slave` (
  `id` varchar(64) NOT NULL,
  `senior_id` varchar(64) NOT NULL,
  `slave_table_id` varchar(64) DEFAULT NULL,
  `slave_table_name` varchar(64) DEFAULT NULL,
  `relation` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tabled
-- ----------------------------
DROP TABLE IF EXISTS `tabled`;
CREATE TABLE `tabled` (
  `id` varchar(64) NOT NULL,
  `datasource_id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `class_name` varchar(100) NOT NULL,
  `is_auto_crud` char(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for transfer_obj
-- ----------------------------
DROP TABLE IF EXISTS `transfer_obj`;
CREATE TABLE `transfer_obj` (
  `id` varchar(64) NOT NULL,
  `project_id` varchar(64) NOT NULL COMMENT '项目id',
  `name` varchar(200) NOT NULL COMMENT '实体名称',
  `package_name` varchar(32) DEFAULT NULL COMMENT '包名',
  `comments` varchar(255) DEFAULT NULL COMMENT '注释',
  `is_senior` char(1) DEFAULT NULL,
  `is_generic` char(1) DEFAULT NULL COMMENT '是否泛型',
  `inherit_obj_name` varchar(200) DEFAULT NULL COMMENT '父类实体名',
  `gen_based_table_id` varchar(64) DEFAULT NULL COMMENT '自动生成表id',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for transfer_obj_field
-- ----------------------------
DROP TABLE IF EXISTS `transfer_obj_field`;
CREATE TABLE `transfer_obj_field` (
  `id` varchar(64) NOT NULL,
  `transfer_obj_id` varchar(64) NOT NULL COMMENT '实体id',
  `name` varchar(200) NOT NULL COMMENT '属性名',
  `type` varchar(64) NOT NULL COMMENT '属性类型',
  `array_type` varchar(64) DEFAULT NULL,
  `format` varchar(200) DEFAULT NULL COMMENT '属性格式',
  `description` varchar(255) DEFAULT NULL COMMENT '属性描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `read_only` char(1) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `min` int(11) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `is_nullable` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ui_config
-- ----------------------------
DROP TABLE IF EXISTS `ui_config`;
CREATE TABLE `ui_config` (
  `id` varchar(64) NOT NULL,
  `json_schema_url` varchar(255) DEFAULT NULL,
  `config_name` varchar(255) DEFAULT NULL,
  `pathmag` mediumtext,
  `tab_config_data` mediumtext,
  `form_config_data` mediumtext,
  `table_mapping_name` varchar(255) DEFAULT NULL,
  `table_btn_configs` mediumtext,
  `table_row_btn_configs` mediumtext,
  `form_btn_configs` mediumtext,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` varchar(64) NOT NULL,
  `flag` char(1) NOT NULL,
  `version` varchar(64) NOT NULL,
  `descriptions` varchar(256) DEFAULT NULL COMMENT '属性描述',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
