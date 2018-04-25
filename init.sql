-- 导出  表 new_titancode_test.api_base 结构
CREATE TABLE IF NOT EXISTS `api_base` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.api_obj 结构
CREATE TABLE IF NOT EXISTS `api_obj` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.api_param 结构
CREATE TABLE IF NOT EXISTS `api_param` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.api_view 结构
CREATE TABLE IF NOT EXISTS `api_view` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.api_view_form_config 结构
CREATE TABLE IF NOT EXISTS `api_view_form_config` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.api_view_table_config 结构
CREATE TABLE IF NOT EXISTS `api_view_table_config` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.columnd 结构
CREATE TABLE IF NOT EXISTS `columnd` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.datasource 结构
CREATE TABLE IF NOT EXISTS `datasource` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.dict_type 结构
CREATE TABLE IF NOT EXISTS `dict_type` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.dict_value 结构
CREATE TABLE IF NOT EXISTS `dict_value` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.equipment_detail 结构
CREATE TABLE IF NOT EXISTS `equipment_detail` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.project 结构
CREATE TABLE IF NOT EXISTS `project` (
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
  `user_id` varchar(64) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `modify_id` varchar(64) DEFAULT NULL,
  `modify_name` varchar(100) DEFAULT NULL,
  `modify_ip` varchar(100) DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_project_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.tabled 结构
CREATE TABLE IF NOT EXISTS `tabled` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.table_relation 结构
CREATE TABLE IF NOT EXISTS `table_relation` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.table_senior_column 结构
CREATE TABLE IF NOT EXISTS `table_senior_column` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.table_senior_relation 结构
CREATE TABLE IF NOT EXISTS `table_senior_relation` (
  `id` varchar(64) NOT NULL,
  `master_table_id` varchar(64) NOT NULL,
  `master_table_name` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.table_senior_slave 结构
CREATE TABLE IF NOT EXISTS `table_senior_slave` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.transfer_obj 结构
CREATE TABLE IF NOT EXISTS `transfer_obj` (
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

-- 数据导出被取消选择。
-- 导出  表 new_titancode_test.transfer_obj_field 结构
CREATE TABLE IF NOT EXISTS `transfer_obj_field` (
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

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
