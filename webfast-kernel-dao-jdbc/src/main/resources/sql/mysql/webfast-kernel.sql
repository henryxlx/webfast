SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app_block`
-- ----------------------------
DROP TABLE IF EXISTS `app_block`;
CREATE TABLE `app_block`(
                            `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '编辑区ID',
                            `userId`       int(11) NOT NULL COMMENT '编辑区创建人ID',
                            `title`        varchar(255) NOT NULL COMMENT '编辑区名称',
                            `mode`         enum('html','template') NOT NULL DEFAULT 'html' COMMENT '模式',
                            `template`     text COMMENT '模板',
                            `templateData` text COMMENT '模板数据',
                            `content`      text COMMENT '编辑区内容',
                            `code`         varchar(64)  NOT NULL DEFAULT '' COMMENT '编辑区编码',
                            `tips`         text COMMENT '编辑区编辑提示',
                            `updateTime`   bigint unsigned NOT NULL DEFAULT '0' COMMENT '编辑区最后更新时间',
                            `createdTime`  bigint unsigned NOT NULL COMMENT '编辑区创建时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面编辑区表';

-- ----------------------------
-- Table structure for `app_block_history`
-- ----------------------------
DROP TABLE IF EXISTS `app_block_history`;
CREATE TABLE `app_block_history`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '编辑区历史记录ID',
    `blockId`      int(11) NOT NULL COMMENT '编辑区ID',
    `templateData` text COMMENT '模板历史数据',
    `content`      text COMMENT '编辑区历史内容',
    `userId`       int(11) NOT NULL COMMENT '编辑区编辑人ID',
    `createdTime`  bigint unsigned NOT NULL COMMENT '编辑区历史记录创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编辑区历史表';

-- ----------------------------
-- Table structure for `app_file`
-- ----------------------------
DROP TABLE IF EXISTS `app_file`;
CREATE TABLE `app_file` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '上传文件ID',
  `groupId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '上传文件组ID',
  `userId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '上传人ID',
  `uri` varchar(255) NOT NULL COMMENT '文件URI',
  `mime` varchar(255) NOT NULL COMMENT '文件MIME',
  `size` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '文件大小',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '文件状态',
  `createdTime` bigint unsigned NOT NULL DEFAULT '0' COMMENT '文件上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传文件情况表';

-- ----------------------------
-- Table structure for `app_file_group`
-- ----------------------------
DROP TABLE IF EXISTS `app_file_group`;
CREATE TABLE `app_file_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '上传文件组ID',
  `name` varchar(255) NOT NULL COMMENT '上传文件组名称',
  `code` varchar(255) NOT NULL COMMENT '上传文件组编码',
  `public` tinyint(4) NOT NULL DEFAULT '1' COMMENT '文件组文件是否公开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传文件分组表';

-- ----------------------------
-- Table structure for `app_friend`
-- ----------------------------
DROP TABLE IF EXISTS `app_friend`;
CREATE TABLE `app_friend`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '关注ID',
    `fromId`      int(11) unsigned NOT NULL COMMENT '关注人ID',
    `toId`        int(11) unsigned NOT NULL COMMENT '被关注人ID',
    `createdTime` bigint unsigned NOT NULL COMMENT '关注时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注表';

-- ----------------------------
-- Table structure for `app_log`
-- ----------------------------
DROP TABLE IF EXISTS `app_log`;
CREATE TABLE `app_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统日志ID',
  `userId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `module` varchar(32) NOT NULL COMMENT '日志所属模块',
  `action` varchar(32) NOT NULL COMMENT '日志所属操作类型',
  `message` text NOT NULL COMMENT '日志内容',
  `data` text COMMENT '日志数据',
  `ip` varchar(255) NOT NULL COMMENT '日志记录IP',
  `createdTime` bigint unsigned NOT NULL COMMENT '日志发生时间',
  `level` char(10) NOT NULL COMMENT '日志等级',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统服务日志表';

-- ----------------------------
-- Table structure for `app_message`
-- ----------------------------
DROP TABLE IF EXISTS `app_message`;
CREATE TABLE `app_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '私信Id',
  `fromId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '发信人Id',
  `toId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收信人Id',
  `content` text NOT NULL COMMENT '私信内容',
  `createdTime` bigint unsigned NOT NULL DEFAULT '0' COMMENT '私信发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `app_message_conversation`
-- ----------------------------
DROP TABLE IF EXISTS `app_message_conversation`;
CREATE TABLE `app_message_conversation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '会话Id',
  `fromId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '发信人Id',
  `toId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收信人Id',
  `messageNum` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '此对话的信息条数',
  `latestMessageUserId` int(11) unsigned DEFAULT NULL COMMENT '最后发信人ID',
  `latestMessageTime` bigint unsigned NOT NULL COMMENT '最后发信时间',
  `latestMessageContent` text NOT NULL COMMENT '最后发信内容',
  `unreadNum` int(11) unsigned NOT NULL COMMENT '未读数量',
  `createdTime` bigint unsigned NOT NULL COMMENT '会话创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `app_message_relation`
-- ----------------------------
DROP TABLE IF EXISTS `app_message_relation`;
CREATE TABLE `app_message_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '消息关联ID',
  `conversationId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '关联的会话ID',
  `messageId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '关联的消息ID',
  `isRead` enum('0','1') NOT NULL DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `app_navigation`
-- ----------------------------
DROP TABLE IF EXISTS `app_navigation`;
CREATE TABLE `app_navigation`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '导航ID',
    `name`        varchar(255) NOT NULL COMMENT '导航名称',
    `url`         varchar(300) NOT NULL COMMENT '链接地址',
    `sequence`    tinyint(4) unsigned NOT NULL COMMENT '显示顺序',
    `parentId`    int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父导航ID',
    `type`        varchar(30)  NOT NULL COMMENT '类型',
    `isOpen`      tinyint(2) NOT NULL DEFAULT '1' COMMENT '默认1，为开启',
    `isNewWin`    tinyint(2) NOT NULL DEFAULT '1' COMMENT '默认为1,另开窗口',
    `updateTime`  bigint unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
    `createdTime` bigint unsigned NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站导航表';

-- ----------------------------
-- Table structure for `app_notification`
-- ----------------------------
DROP TABLE IF EXISTS `app_notification`;
CREATE TABLE `app_notification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `userId` int(11) unsigned NOT NULL COMMENT '被通知的用户ID',
  `type` varchar(64) NOT NULL DEFAULT 'default' COMMENT '通知类型',
  `content` text COMMENT '通知内容',
  `createdTime` bigint unsigned NOT NULL COMMENT '通知时间',
  `isRead` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户通知表';

-- ----------------------------
-- Table structure for `app_permission`
-- ----------------------------
DROP TABLE IF EXISTS `app_permission`;
CREATE TABLE `app_permission`
(
    `id`            int(11) unsigned NOT NULL AUTO_INCREMENT,
    `label`         varchar(32) NOT NULL COMMENT '角色文字标签',
    `permissionKey` varchar(32) NOT NULL COMMENT '角色名称',
    `createdUserId` int(11) unsigned NOT NULL COMMENT '创建用户ID',
    `createdTime`   bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updatedTime`   bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限定义表';

-- ----------------------------
-- Records of app_permission
-- ----------------------------
INSERT INTO `app_permission` VALUES ('1', '访问后端管理', 'ACCESS_BACKEND', '1', '1515719362000', '0');

-- ----------------------------
-- Table structure for `app_role`
-- ----------------------------
DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role`
(
    `id`             int(11) unsigned NOT NULL AUTO_INCREMENT,
    `label`          varchar(32) NOT NULL COMMENT '角色文字标签',
    `roleName`       varchar(32) NOT NULL COMMENT '角色名称',
    `permissionData` text COMMENT '角色权限配置',
    `createdUserId`  int(11) unsigned NOT NULL COMMENT '创建用户ID',
    `createdTime`    bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updatedTime`    bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色定义表';

-- ----------------------------
-- Records of app_role
-- ----------------------------
INSERT INTO `app_role` VALUES ('1', '超级管理员', 'ROLE_SUPER_ADMIN', 'ACCESS_BACKEND', '1', '1515719012000', '0');
INSERT INTO `app_role` VALUES ('2', '管理员', 'ROLE_ADMIN', 'ACCESS_BACKEND', '1', '1515719132000', '0');
INSERT INTO `app_role` VALUES ('3', '基本用户', 'ROLE_USER', '', '1', '1515719252000', '0');
INSERT INTO `app_role` VALUES ('4', '教师', 'ROLE_TEACHER', '', '1', '1515719362000', '0');

-- ----------------------------
-- Table structure for `app_setting`
-- ----------------------------
DROP TABLE IF EXISTS `app_setting`;
CREATE TABLE `app_setting` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统设置ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '系统设置名',
  `value` longblob COMMENT '系统设置值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统设置表';

-- ----------------------------
-- Records of app_setting
-- ----------------------------
INSERT INTO `app_setting` VALUES ('1', 'site', 0x613A31323A7B733A343A226E616D65223B733A32353A2232303231E8AEA1E7AE97E69CBAE8AFBEE7A88BE5ADA6E4B9A0223B733A363A22736C6F67616E223B733A303A22223B733A333A2275726C223B733A303A22223B733A343A226C6F676F223B733A303A22223B733A31323A2273656F5F6B6579776F726473223B733A303A22223B733A31353A2273656F5F6465736372697074696F6E223B733A303A22223B733A31323A226D61737465725F656D61696C223B4E3B733A333A22696370223B733A303A22223B733A393A22616E616C7974696373223B733A303A22223B733A363A22737461747573223B733A343A226F70656E223B733A31313A22636C6F7365645F6E6F7465223B733A303A22223B733A31373A22686F6D65706167655F74656D706C617465223B733A343A226C657373223B7D);
INSERT INTO `app_setting` VALUES ('2', 'auth', 0x613A383A7B733A31333A2272656769737465725F6D6F6465223B733A363A226F70656E6564223B733A32323A22656D61696C5F61637469766174696F6E5F7469746C65223B733A33333A22E8AFB7E6BF80E6B4BBE682A8E79A847B7B736974656E616D657D7DE8B4A6E58FB7223B733A32313A22656D61696C5F61637469766174696F6E5F626F6479223B733A3336363A2248692C207B7B6E69636B6E616D657D7D0A0AE6ACA2E8BF8EE58AA0E585A57B7B736974656E616D657D7D210A0AE8AFB7E782B9E587BBE4B88BE99DA2E79A84E993BEE68EA5E5AE8CE68890E6B3A8E5868CEFBC9A0A0A7B7B76657269667975726C7D7D0A0AE5A682E69E9CE4BBA5E4B88AE993BEE68EA5E697A0E6B395E782B9E587BBEFBC8CE8AFB7E5B086E4B88AE99DA2E79A84E59CB0E59D80E5A48DE588B6E588B0E4BDA0E79A84E6B58FE8A788E599A828E5A682494529E79A84E59CB0E59D80E6A08FE4B8ADE68993E5BC80EFBC8CE8AFA5E993BEE68EA5E59CB0E59D803234E5B08FE697B6E58685E68993E5BC80E69C89E69588E380820A0AE6849FE8B0A2E5AFB97B7B736974656E616D657D7DE79A84E694AFE68C81EFBC810A0A7B7B736974656E616D657D7D207B7B7369746575726C7D7D0A0A28E8BF99E698AFE4B880E5B081E887AAE58AA8E4BAA7E7949FE79A84656D61696CEFBC8CE8AFB7E58BBFE59B9EE5A48DE3808229223B733A31353A2277656C636F6D655F656E61626C6564223B733A363A226F70656E6564223B733A31343A2277656C636F6D655F73656E646572223B733A353A2261646D696E223B733A31353A2277656C636F6D655F6D6574686F6473223B613A303A7B7D733A31333A2277656C636F6D655F7469746C65223B733A32343A22E6ACA2E8BF8EE58AA0E585A57B7B736974656E616D657D7D223B733A31323A2277656C636F6D655F626F6479223B733A3133383A22E682A8E5A5BD7B7B6E69636B6E616D657D7DEFBC8CE68891E698AF7B7B736974656E616D657D7DE79A84E7AEA1E79086E59198EFBC8CE6ACA2E8BF8EE58AA0E585A57B7B736974656E616D657D7DEFBC8CE7A59DE682A8E5ADA6E4B9A0E68489E5BFABE38082E5A682E69C89E997AEE9A298EFBC8CE99A8FE697B6E4B88EE68891E88194E7B3BBE38082223B7D);
INSERT INTO `app_setting` VALUES ('3', 'mailer', 0x613A373A7B733A373A22656E61626C6564223B693A303B733A343A22686F7374223B733A31363A22736D74702E6578616D706C652E636F6D223B733A343A22706F7274223B733A323A223235223B733A383A22757365726E616D65223B733A31363A2275736572406578616D706C652E636F6D223B733A383A2270617373776F7264223B733A303A22223B733A343A2266726F6D223B733A31363A2275736572406578616D706C652E636F6D223B733A343A226E616D65223B733A32353A2232303231E8AEA1E7AE97E69CBAE8AFBEE7A88BE5ADA6E4B9A0223B7D);
INSERT INTO `app_setting` VALUES ('4', 'storage', 0x613A353A7B733A31313A2275706C6F61645F6D6F6465223B733A353A226C6F63616C223B733A31363A22636C6F75645F6163636573735F6B6579223B733A303A22223B733A31363A22636C6F75645F7365637265745F6B6579223B733A303A22223B733A31363A22636C6F75645F6170695F736572766572223B733A32323A22687474703A2F2F6170692E656475736F686F2E6E6574223B733A31323A22636C6F75645F6275636B6574223B733A303A22223B7D);
INSERT INTO `app_setting` VALUES ('5', 'theme', 0x613A313A7B733A333A22757269223B733A373A2264656661756C74223B7D);
INSERT INTO `app_setting` VALUES ('6', 'article', 0x613A323A7B733A343A226E616D65223B733A31323A22E8B584E8AEAFE9A291E98193223B733A383A22706167654E756D73223B693A32303B7D);

-- ----------------------------
-- Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user`
(
    `id`                            int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `email`                         varchar(128) NOT NULL COMMENT '用户邮箱',
    `verifiedMobile`                varchar(32)  NOT NULL DEFAULT '',
    `password`                      varchar(64)  NOT NULL COMMENT '用户密码',
    `salt`                          varchar(32)  NOT NULL COMMENT '密码SALT',
    `username`                      varchar(64)  NOT NULL COMMENT '用户名',
    `title`                         varchar(255) NOT NULL DEFAULT '' COMMENT '头衔',
    `tags`                          varchar(255) NOT NULL DEFAULT '' COMMENT '标签',
    `type`                          varchar(32)  NOT NULL COMMENT 'default默认为网站注册, weibo新浪微薄登录',
    `smallAvatar`                   varchar(255) NOT NULL DEFAULT '' COMMENT '小头像',
    `mediumAvatar`                  varchar(255) NOT NULL DEFAULT '' COMMENT '中头像',
    `largeAvatar`                   varchar(255) NOT NULL DEFAULT '' COMMENT '大头像',
    `emailVerified`                 tinyint(1) NOT NULL DEFAULT '0' COMMENT '邮箱是否为已验证',
    `setup`                         tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否初始化设置的，未初始化的可以设置邮箱、昵称。',
    `roles`                         varchar(255) NOT NULL COMMENT '用户角色',
    `permissionData`                text COMMENT '用户权限配置',
    `promoted`                      tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为推荐',
    `promotedTime`                  bigint unsigned NOT NULL DEFAULT '0' COMMENT '推荐时间',
    `locked`                        tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否被禁止',
    `lockDeadline`                  int(11) NOT NULL DEFAULT '0' COMMENT '帐号锁定期限',
    `consecutivePasswordErrorTimes` int(11) NOT NULL DEFAULT '0' COMMENT '帐号密码错误次数',
    `lastPasswordFailTime`          bigint unsigned NOT NULL DEFAULT '0',
    `loginTime`                     bigint unsigned NOT NULL DEFAULT '0' COMMENT '最后登录时间',
    `loginIp`                       varchar(64)  NOT NULL DEFAULT '' COMMENT '最后登录IP',
    `loginSessionId`                varchar(255) NOT NULL DEFAULT '' COMMENT '最后登录会话ID',
    `approvalTime`                  bigint unsigned NOT NULL DEFAULT '0' COMMENT '实名认证时间',
    `approvalStatus`                enum('unapprove','approving','approved','approve_fail') NOT NULL DEFAULT 'unapprove' COMMENT '实名认证状态',
    `newMessageNum`                 int(11) unsigned NOT NULL DEFAULT '0' COMMENT '未读私信数',
    `newNotificationNum`            int(11) unsigned NOT NULL DEFAULT '0' COMMENT '未读消息数',
    `createdIp`                     varchar(64)  NOT NULL DEFAULT '' COMMENT '注册IP',
    `createdTime`                   bigint unsigned NOT NULL DEFAULT '0' COMMENT '注册时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES ('1', 'super@hotmail.com', '', 'FxnzDXLOO8yqOqg64RA5erBujL+iw3z8UvuTr+Y16Ak=', '2pfHj21OUy1rBfVGyCb6qw==', 'super', 'Bookfan', '', 'default', '', '', '', '0', '1', '|ROLE_USER|ROLE_ADMIN|ROLE_SUPER_ADMIN|', '', '0', '0', '0', '0', '0', '0', '1635668402000', '::1', 'l04bkr6d261jsfeg3tn25hltin', '0', 'unapprove', '0', '0', '', '1635668142000');
INSERT INTO `app_user` VALUES ('2', 'test@qq.com', '', 'RIGagyj4RFv7Y4Fk3oxEO0CFboVaOGdEs8Pzt5Sf1HM=', 'FYMMdLVItihBsvfsp+ImFg==', 'test', '', '', 'default', '', '', '', '0', '1', '|ROLE_USER|', '', '0', '0', '0', '0', '0', '0', '0', '', '', '0', 'unapprove', '0', '0', '::1', '1635668723000');

-- ----------------------------
-- Table structure for `app_user_approval`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_approval`;
CREATE TABLE `app_user_approval`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '用户认证ID',
    `userId`      int(11) NOT NULL COMMENT '用户ID',
    `idcard`      varchar(24)  NOT NULL DEFAULT '' COMMENT '身份证号',
    `faceImg`     varchar(500) NOT NULL DEFAULT '' COMMENT '认证正面图',
    `backImg`     varchar(500) NOT NULL DEFAULT '' COMMENT '认证背面图',
    `truename`    varchar(255)          DEFAULT NULL COMMENT '真实姓名',
    `note`        text COMMENT '认证信息',
    `status`      enum('unapprove','approving','approved','approve_fail') NOT NULL COMMENT '是否通过：1是 0否',
    `operatorId`  int(11) unsigned DEFAULT NULL COMMENT '审核人',
    `createdTime` bigint unsigned NOT NULL DEFAULT '0' COMMENT '申请时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证表';

-- ----------------------------
-- Table structure for `app_user_field`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_field`;
CREATE TABLE `app_user_field`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT,
    `fieldName`   varchar(100)  NOT NULL DEFAULT '',
    `title`       varchar(1024) NOT NULL DEFAULT '',
    `seq`         int(11) unsigned NOT NULL,
    `enabled`     int(11) unsigned NOT NULL DEFAULT '0',
    `createdTime` bigint unsigned NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `app_user_profile`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_profile`;
CREATE TABLE `app_user_profile` (
  `id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `truename` varchar(255) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `idcard` varchar(24) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `gender` enum('male','female','secret') NOT NULL DEFAULT 'secret' COMMENT '性别',
  `iam` varchar(255) NOT NULL DEFAULT '' COMMENT '我是谁',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) NOT NULL DEFAULT '' COMMENT '城市',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
  `qq` varchar(32) NOT NULL DEFAULT '' COMMENT 'QQ',
  `signature` text COMMENT '签名',
  `aboutme` text COMMENT '自我介绍',
  `company` varchar(255) NOT NULL DEFAULT '' COMMENT '公司',
  `job` varchar(255) NOT NULL DEFAULT '' COMMENT '工作',
  `myschool` varchar(255) NOT NULL DEFAULT '' COMMENT '学校',
  `myclass` varchar(255) NOT NULL DEFAULT '' COMMENT '班级',
  `weibo` varchar(255) NOT NULL DEFAULT '' COMMENT '微博',
  `weixin` varchar(255) NOT NULL DEFAULT '' COMMENT '微信',
  `site` varchar(255) NOT NULL DEFAULT '' COMMENT '网站',
  `intField1` int(11) DEFAULT NULL,
  `intField2` int(11) DEFAULT NULL,
  `intField3` int(11) DEFAULT NULL,
  `intField4` int(11) DEFAULT NULL,
  `intField5` int(11) DEFAULT NULL,
  `dateField1` date DEFAULT NULL,
  `dateField2` date DEFAULT NULL,
  `dateField3` date DEFAULT NULL,
  `dateField4` date DEFAULT NULL,
  `dateField5` date DEFAULT NULL,
  `floatField1` float(10,2) DEFAULT NULL,
  `floatField2` float(10,2) DEFAULT NULL,
  `floatField3` float(10,2) DEFAULT NULL,
  `floatField4` float(10,2) DEFAULT NULL,
  `floatField5` float(10,2) DEFAULT NULL,
  `varcharField1` varchar(1024) DEFAULT NULL,
  `varcharField2` varchar(1024) DEFAULT NULL,
  `varcharField3` varchar(1024) DEFAULT NULL,
  `varcharField4` varchar(1024) DEFAULT NULL,
  `varcharField5` varchar(1024) DEFAULT NULL,
  `varcharField6` varchar(1024) DEFAULT NULL,
  `varcharField7` varchar(1024) DEFAULT NULL,
  `varcharField8` varchar(1024) DEFAULT NULL,
  `varcharField9` varchar(1024) DEFAULT NULL,
  `varcharField10` varchar(1024) DEFAULT NULL,
  `textField1` text,
  `textField2` text,
  `textField3` text,
  `textField4` text,
  `textField5` text,
  `textField6` text,
  `textField7` text,
  `textField8` text,
  `textField9` text,
  `textField10` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

-- ----------------------------
-- Table structure for `app_user_secure_question`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_secure_question`;
CREATE TABLE `app_user_secure_question`
(
    `id`                   int(10) unsigned NOT NULL AUTO_INCREMENT,
    `userId`               int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
    `securityQuestionCode` varchar(64) NOT NULL DEFAULT '' COMMENT '问题的code',
    `securityAnswer`       varchar(64) NOT NULL DEFAULT '' COMMENT '安全问题的答案',
    `securityAnswerSalt`   varchar(64) NOT NULL DEFAULT '' COMMENT '安全问题的答案Salt',
    `createdTime`          bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户安全设置安全问题表';

-- ----------------------------
-- Table structure for `app_user_status`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_status`;
CREATE TABLE `app_user_status`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `userId`      int(10) unsigned NOT NULL COMMENT '动态发布的人',
    `type`        varchar(64) NOT NULL COMMENT '动态类型',
    `objectType`  varchar(64) NOT NULL DEFAULT '' COMMENT '动态对象的类型',
    `objectId`    int(10) unsigned NOT NULL DEFAULT '0' COMMENT '动态对象ID',
    `message`     text        NOT NULL COMMENT '动态的消息体',
    `properties`  text        NOT NULL COMMENT '动态的属性',
    `commentNum`  int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
    `likeNum`     int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被赞的数量',
    `private`     tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏',
    `createdTime` bigint unsigned NOT NULL DEFAULT '0' COMMENT '动态发布时间',
    PRIMARY KEY (`id`),
    KEY           `userId` (`userId`),
    KEY           `createdTime` (`createdTime`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `app_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_token`;
CREATE TABLE `app_user_token`
(
    `id`            int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'TOKEN编号',
    `token`         varchar(64)  NOT NULL COMMENT 'TOKEN值',
    `userId`        int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'TOKEN关联的用户ID',
    `type`          varchar(255) NOT NULL COMMENT 'TOKEN类型',
    `data`          text         NOT NULL COMMENT 'TOKEN数据',
    `times`         int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'TOKEN的校验次数限制(0表示不限制)',
    `remainedTimes` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'TOKEN剩余校验次数',
    `expiredTime`   bigint unsigned NOT NULL DEFAULT '0' COMMENT 'TOKEN过期时间',
    `createdTime`   bigint unsigned NOT NULL COMMENT 'TOKEN创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `token` (`token`(60))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;