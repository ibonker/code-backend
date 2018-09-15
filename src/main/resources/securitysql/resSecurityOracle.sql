CREATE TABLE hotpot_function (
  id varchar2(36) NOT NULL,
  function_name varchar2(255) DEFAULT NULL,
  code varchar2(255) DEFAULT NULL,
  url clob DEFAULT NULL,
  is_menu varchar2(3) DEFAULT NULL,
  description varchar2(255) DEFAULT NULL,
  enabled varchar2(3) DEFAULT NULL,
  parent_id varchar2(36) DEFAULT NULL,
  image1_id varchar2(255) DEFAULT NULL,
  image2_id varchar2(255) DEFAULT NULL,
  created_at DATE DEFAULT NULL,
  updated_at DATE DEFAULT NULL,
  created_by varchar2(255) DEFAULT NULL,
  updated_by varchar2(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO hotpot_function VALUES ('1', '高级管理', '1', '', 'Y', null, 'Y', '0', null, null, null, null, null, null);
INSERT INTO hotpot_function VALUES ('11', '菜单管理', '11', '{"path":"/menuConfig", "component": "common/menuConfig/menuConfig.vue", "type": "div", "openMethod": "default"}', 'Y', null, 'Y', '1', null, null, null, null, null, null);
INSERT INTO hotpot_function VALUES ('111', '接口列表配置', '111', '{"path":"/menuConfig/restConfig", "component": "common/menuConfig/restConfig.vue", "type": "div", "openMethod": "default"}', 'N', null, 'Y', '11', null, null, null, null, null, null);
INSERT INTO hotpot_function VALUES ('112', '高级查询配置', '112', '{"path":"/menuConfig/tabConfig", "component": "common/menuConfig/tabConfig.vue", "type": "div", "openMethod": "default"}', 'N', null, 'Y', '11', null, null, null, null, null, null);
INSERT INTO hotpot_function VALUES ('113', '模板配置', '113', '{"path":"/menuConfig/framework", "component": "common/menuConfig/frameworkConfig.vue", "type": "div", "openMethod": "default"}', 'N', null, 'Y', '11', null, null, null, null, null, null);
INSERT INTO hotpot_function VALUES ('12', '字典管理', '12', '{"path":"/dictionary", "component": "common/dictionary/dictionary.vue", "type": "div", "openMethod": "default"}', 'Y', null, 'Y', '1', null, null, null, null, null, null);
INSERT INTO hotpot_function VALUES ('13', '角色管理', '13', '{"component":"common/roleEdit/roleConfig.vue","openMethod":"default","path":"/roleConfig","type":"div","url":""}', 'Y', null, 'Y', '1', null, '', null, null, null, null);
INSERT INTO hotpot_function VALUES ('131', '角色配置', '131', '{"path":"/roleConfig/editRole", "component": "common/roleEdit/addRole.vue", "type": "div", "openMethod": "default"}', 'N', null, 'Y', '13', null, null, null, null, null, null);
INSERT  INTO hotpot_function VALUES ('16', '文件管理', '16', '{"component":"common/fileMag.vue","openMethod":"default","path":"/fileMag","type":"div","url":""}', 'Y', null, 'Y', '1', null, '', null, null, null, null);
INSERT  INTO hotpot_function VALUES ('17', '应用监控', '17', '{"component":"common/sba.vue","openMethod":"default","path":"/bootAdmin","type":"div","url":""}', 'Y', null, 'Y', '1', null, '', null, null, null, null);


CREATE TABLE hotpot_role (
  id varchar2(36) NOT NULL,
  role_name varchar2(255) DEFAULT NULL,
  description varchar2(255) DEFAULT NULL,
  created_at DATE DEFAULT NULL,
  updated_at DATE DEFAULT NULL,
  created_by varchar2(255) DEFAULT NULL,
  updated_by varchar2(255) DEFAULT NULL,
  del_flag varchar2(3) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

INSERT  INTO hotpot_role VALUES ('1', 'test', 'test', null, null, null, null, '0');
INSERT  INTO hotpot_role VALUES ('9999', 'administrator', 'admin', null, null, null, null, '0');


CREATE TABLE hotpot_role_function (
  function_id varchar2(36) NOT NULL,
  role_id varchar2(36) NOT NULL,
  created_at DATE DEFAULT NULL,
  created_by varchar2(255) DEFAULT NULL,
  PRIMARY KEY (function_id,role_id)
) ;


INSERT  INTO hotpot_role_function VALUES ('1', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('11', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('111', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('112', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('113', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('12', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('13', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('131', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('14', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('15', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('16', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('17', '9999', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('2', '1', null, 'admin');
INSERT  INTO hotpot_role_function VALUES ('2', '9999', null, 'admin');


CREATE TABLE hotpot_role_user (
  role_id varchar2(36) NOT NULL,
  party_id varchar2(36) NOT NULL,
  created_at DATE DEFAULT NULL,
  created_by varchar2(255) DEFAULT NULL,
  type varchar2(3) NOT NULL,
  PRIMARY KEY (role_id,party_id)
) ;

comment on column hotpot_role_user.type is '0.人员 1.组织 2.团队';
