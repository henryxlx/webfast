SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app_block`
-- ----------------------------
DROP TABLE IF EXISTS `app_block`;
CREATE TABLE `app_block` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编辑区ID',
  `userId` int(11) NOT NULL COMMENT '编辑区创建人ID',
  `title` varchar(255) NOT NULL COMMENT '编辑区名称',
  `mode` enum('html','template') NOT NULL DEFAULT 'html' COMMENT '模式',
  `template` text COMMENT '模板',
  `templateData` text COMMENT '模板数据',
  `content` text COMMENT '编辑区内容',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编辑区编码',
  `tips` text COMMENT '编辑区编辑提示',
  `updateTime` bigint NOT NULL DEFAULT '0' COMMENT '编辑区最后更新时间',
  `createdTime` bigint NOT NULL COMMENT '编辑区创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面编辑区表';

-- ----------------------------
-- Table structure for `app_block_history`
-- ----------------------------
DROP TABLE IF EXISTS `app_block_history`;
CREATE TABLE `app_block_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编辑区历史记录ID',
  `blockId` int(11) NOT NULL COMMENT '编辑区ID',
  `templateData` text COMMENT '模板历史数据',
  `content` text COMMENT '编辑区历史内容',
  `userId` int(11) NOT NULL COMMENT '编辑区编辑人ID',
  `createdTime` bigint NOT NULL COMMENT '编辑区历史记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编辑区历史表';

-- ----------------------------
-- Table structure for `app_category`
-- ----------------------------
DROP TABLE IF EXISTS `app_category`;
CREATE TABLE `app_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '分类编码',
  `name` varchar(255) NOT NULL COMMENT '分类名称',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '图标',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '分类完整路径',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '分类权重',
  `groupId` int(10) unsigned NOT NULL COMMENT '分类组ID',
  `parentId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父分类ID',
  `description` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uri` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Table structure for `app_category_group`
-- ----------------------------
DROP TABLE IF EXISTS `app_category_group`;
CREATE TABLE `app_category_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类组ID',
  `code` varchar(64) NOT NULL COMMENT '分类组编码',
  `name` varchar(255) NOT NULL COMMENT '分类组名称',
  `depth` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '该组下分类允许的最大层级数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类分组表';

-- ----------------------------
-- Table structure for `app_content`
-- ----------------------------
DROP TABLE IF EXISTS `app_content`;
CREATE TABLE `app_content` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `title` varchar(255) NOT NULL COMMENT '内容标题',
  `editor` enum('richeditor','none') NOT NULL DEFAULT 'richeditor' COMMENT '编辑器选择类型字段',
  `type` varchar(255) NOT NULL COMMENT '内容类型',
  `alias` varchar(255) NOT NULL DEFAULT '' COMMENT '内容别名',
  `summary` text COMMENT '内容摘要',
  `body` text COMMENT '内容正文',
  `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '内容头图',
  `template` varchar(255) NOT NULL DEFAULT '' COMMENT '内容模板',
  `status` enum('published','unpublished','trash') NOT NULL COMMENT '内容状态',
  `categoryId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '内容分类ID',
  `tagIds` tinytext COMMENT '内容标签ID',
  `hits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '内容点击量',
  `featured` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '是否头条',
  `promoted` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否推荐',
  `sticky` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否置顶',
  `userId` int(10) unsigned NOT NULL COMMENT '发布人ID',
  `extraField` text COMMENT '扩展字段',
  `publishedTime` bigint NOT NULL DEFAULT '0' COMMENT '发布时间',
  `createdTime` bigint  NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面内容表';

-- ----------------------------
-- Table structure for `app_navigation`
-- ----------------------------
DROP TABLE IF EXISTS `app_navigation`;
CREATE TABLE `app_navigation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '导航ID',
  `name` varchar(255) NOT NULL COMMENT '导航名称',
  `url` varchar(300) NOT NULL COMMENT '链接地址',
  `sequence` tinyint(4) unsigned NOT NULL COMMENT '显示顺序',
  `parentId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父导航ID',
  `type` varchar(30) NOT NULL COMMENT '类型',
  `isOpen` tinyint(2) NOT NULL DEFAULT '1' COMMENT '默认1，为开启',
  `isNewWin` tinyint(2) NOT NULL DEFAULT '1' COMMENT '默认为1,另开窗口',
  `updateTime` bigint NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `createdTime` bigint NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站导航表';

-- ----------------------------
-- Table structure for `app_permission`
-- ----------------------------
DROP TABLE IF EXISTS `app_permission`;
CREATE TABLE `app_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(32) NOT NULL COMMENT '角色文字标签',
  `permissionKey` varchar(32) NOT NULL COMMENT '角色名称',
  `createdUserId` int(10) unsigned NOT NULL COMMENT '创建用户ID',
  `createdTime` bigint NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updatedTime` bigint NOT NULL DEFAULT '0' COMMENT '更新时间',
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
CREATE TABLE `app_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(32) NOT NULL COMMENT '角色文字标签',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `data` text COMMENT '权限配置',
  `data_v2` text COMMENT 'admin_v2权限配置',
  `createdUserId` int(10) unsigned NOT NULL COMMENT '创建用户ID',
  `createdTime` bigint NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updatedTime` bigint NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色定义表';

-- ----------------------------
-- Records of app_role
-- ----------------------------
INSERT INTO `app_role` VALUES ('1', '学员', 'ROLE_USER', '', '', '1', '1515719362000', '0');
INSERT INTO `app_role` VALUES ('2', '教师', 'ROLE_TEACHER', '[\"web\",\"course_manage\",\"course_manage_info\",\"course_manage_base\",\"course_manage_detail\",\"course_manage_picture\",\"course_manage_lesson\",\"live_course_manage_replay\",\"course_manage_files\",\"course_manage_setting\",\"course_manage_price\",\"course_manage_teachers\",\"course_manage_students\",\"course_manage_student_create\",\"course_manage_questions\",\"course_manage_question\",\"course_manage_testpaper\",\"course_manange_operate\",\"course_manage_data\",\"course_manage_order\",\"classroom_manage\",\"classroom_manage_settings\",\"classroom_manage_set_info\",\"classroom_manage_set_price\",\"classroom_manage_set_picture\",\"classroom_manage_service\",\"classroom_manage_headteacher\",\"classroom_manage_teachers\",\"classroom_manage_assistants\",\"classroom_manage_content\",\"classroom_manage_courses\",\"classroom_manage_students\",\"classroom_manage_testpaper\"]', '[\"web\",\"course_manage\",\"course_manage_info\",\"course_manage_base\",\"course_manage_detail\",\"course_manage_picture\",\"course_manage_lesson\",\"live_course_manage_replay\",\"course_manage_files\",\"course_manage_setting\",\"course_manage_price\",\"course_manage_teachers\",\"course_manage_students\",\"course_manage_student_create\",\"course_manage_questions\",\"course_manage_question\",\"course_manage_testpaper\",\"course_manange_operate\",\"course_manage_data\",\"course_manage_order\",\"classroom_manage\",\"classroom_manage_settings\",\"classroom_manage_set_info\",\"classroom_manage_set_price\",\"classroom_manage_set_picture\",\"classroom_manage_service\",\"classroom_manage_headteacher\",\"classroom_manage_teachers\",\"classroom_manage_assistants\",\"classroom_manage_content\",\"classroom_manage_courses\",\"classroom_manage_students\",\"classroom_manage_testpaper\"]', '1', '1515719362000', '0');
INSERT INTO `app_role` VALUES ('3', '管理员', 'ROLE_ADMIN', '[\"admin\",\"admin_user\",\"admin_user_show\",\"admin_user_manage\",\"admin_user_create\",\"admin_user_edit\",\"admin_user_roles\",\"admin_user_send_passwordreset_email\",\"admin_user_send_emailverify_email\",\"admin_user_lock\",\"admin_user_unlock\",\"admin_user_org_update\",\"admin_online\",\"admin_login_record\",\"admin_user_learn_statistics_manage\",\"admin_user_learn_statistics\",\"admin_teacher\",\"admin_teacher_manage\",\"admin_teacher_promote\",\"admin_teacher_promote_cancel\",\"admin_teacher_promote_list\",\"admin_approval_manage\",\"admin_approval_approvals\",\"admin_approval_cancel\",\"admin_message_manage\",\"admin_message\",\"admin_course\",\"admin_course_show\",\"admin_course_manage\",\"admin_course_content_manage\",\"admin_course_add\",\"admin_course_set_recommend\",\"admin_course_set_cancel_recommend\",\"admin_course_guest_member_preview\",\"admin_course_set_close\",\"admin_course_sms_prepare\",\"admin_course_set_clone\",\"admin_course_set_publish\",\"admin_course_set_delete\",\"admin_course_set_recommend_list\",\"admin_course_set_data\",\"admin_classroom\",\"admin_classroom_manage\",\"admin_classroom_content_manage\",\"admin_classroom_create\",\"admin_classroom_cancel_recommend\",\"admin_classroom_set_recommend\",\"admin_classroom_close\",\"admin_sms_prepare\",\"admin_classroom_open\",\"admin_classroom_delete\",\"admin_classroom_recommend\",\"admin_open_course_manage\",\"admin_open_course\",\"admin_open_course_add\",\"admin_open_course_recommend_list\",\"admin_opencourse_analysis\",\"admin_live_course\",\"admin_live_course_manage\",\"admin_live_course_add\",\"admin_question_bank\",\"admin_question_bank_manage\",\"admin_question_bank_add\",\"admin_question_bank_category_manage\",\"admin_question_bank_category_add\",\"admin_course_thread\",\"admin_course_thread_manage\",\"admin_classroom_thread_manage\",\"admin_course_question\",\"admin_course_question_manage\",\"admin_course_note\",\"admin_course_note_manage\",\"admin_course_review\",\"admin_course_review_tab\",\"admin_classroom_review_tab\",\"admin_course_category\",\"admin_course_category_manage\",\"admin_category_create\",\"admin_classroom_category_manage\",\"admin_classroom_category_create\",\"admin_course_tag\",\"admin_course_tag_manage\",\"admin_course_tag_add\",\"admin_course_tag_group_manage\",\"admin_course_tag_group_add\",\"admin_operation\",\"admin_operation_article\",\"admin_operation_article_manage\",\"admin_operation_article_create\",\"admin_operation_article_category\",\"admin_operation_category_create\",\"admin_operation_group\",\"admin_operation_group_manage\",\"admin_operation_group_create\",\"admin_operation_group_thread\",\"admin_operation_invite\",\"admin_operation_invite_record\",\"admin_operation_invite_user\",\"admin_operation_invite_coupon\",\"admin_announcement\",\"admin_announcement_manage\",\"admin_announcement_create\",\"admin_operation_notification\",\"admin_batch_notification\",\"admin_batch_notification_create\",\"admin_block_manage\",\"admin_block\",\"admin_block_visual_edit\",\"admin_operation_content\",\"admin_content\",\"admin_operation_mobile\",\"admin_operation_mobile_banner_manage\",\"admin_discovery_column_index\",\"admin_discovery_column_create\",\"admin_operation_wechat_notification\",\"admin_operation_wechat_fans_list\",\"admin_operation_wechat_notification_record\",\"admin_operation_wechat_notification_manage\",\"admin_operation_analysis_register\",\"admin_operation_analysis\",\"admin_operation_coupon\",\"admin_operation_coupon_manage\",\"admin_coupon_generate\",\"admin_operation_coupon_query\",\"admin_coupon_setting\",\"admin_operation_keyword\",\"admin_keyword\",\"admin_keyword_create\",\"admin_keyword_banlogs\",\"admin_finance\",\"admin_orders\",\"admin_order_manage\",\"admin_order_refunds\",\"admin_order_refunds_manage\",\"admin_bills\",\"admin_bill_cash\",\"admin_bill_coin\",\"admin_coin_user\",\"admin_coin_user_records\",\"admin_app\",\"admin_cloud_edulive_setting\",\"admin_cloud_edulive_overview\",\"admin_setting_cloud_edulive\",\"admin_edu_cloud_email\",\"admin_edu_cloud_email_overview\",\"admin_edu_cloud_email_setting\",\"admin_app_im\",\"admin_app_im_setting\",\"admin_cloud_file_manage\",\"admin_cloud_file\",\"admin_app_center_show\",\"admin_app_center\",\"admin_app_installed\",\"admin_app_upgrades\",\"admin_app_logs\",\"admin_cloud_attachment_manage\",\"admin_cloud_attachment\",\"admin_cloud_consult\",\"admin_cloud_consult_setting\",\"admin_cloud_data_lab_manage\",\"admin_cloud_data_lab\",\"admin_cloud_data_lab_setting\",\"admin_cloud_face_identify_manage\",\"admin_cloud_face_identify\",\"admin_cloud_feature_lab_manage\",\"admin_cloud_feature_lab\",\"admin_marketing_top\",\"_admin_marketing_marketing_menu\",\"_marketing_marketing_page\",\"admin_distributor_top\",\"_admin_distributor_menu\",\"_distributor_page\",\"admin_mobile\",\"admin_wechat_app_manage\",\"admin_wechat_app\",\"admin_setting_mobile\",\"admin_setting_mobile_discoveries_settings\",\"admin_setting_mobile_settings\",\"admin_h5_set\",\"admin_wap_set\",\"web\",\"course_manage\",\"course_manage_info\",\"course_manage_base\",\"course_manage_detail\",\"course_manage_picture\",\"course_manage_lesson\",\"live_course_manage_replay\",\"course_manage_files\",\"course_manage_setting\",\"course_manage_price\",\"course_manage_teachers\",\"course_manage_students\",\"course_manage_student_create\",\"course_manage_questions\",\"course_manage_question\",\"course_manage_testpaper\",\"course_manange_operate\",\"course_manage_data\",\"course_manage_order\",\"classroom_manage\",\"classroom_manage_settings\",\"classroom_manage_set_info\",\"classroom_manage_set_price\",\"classroom_manage_set_picture\",\"classroom_manage_service\",\"classroom_manage_headteacher\",\"classroom_manage_teachers\",\"classroom_manage_assistants\",\"classroom_manage_content\",\"classroom_manage_courses\",\"classroom_manage_students\",\"classroom_manage_testpaper\"]', '[\"admin_v2\",\"admin_v2_teach\",\"admin_v2_course_group\",\"admin_v2_course_show\",\"admin_v2_course_manage\",\"admin_v2_course_content_manage\",\"admin_v2_go_to_choose\",\"admin_v2_course_add\",\"admin_v2_course_set_recommend\",\"admin_v2_course_set_cancel_recommend\",\"admin_v2_course_guest_member_preview\",\"admin_v2_course_set_close\",\"admin_v2_course_sms_prepare\",\"admin_v2_course_set_clone\",\"admin_v2_course_set_publish\",\"admin_v2_course_set_delete\",\"admin_v2_course_set_remove\",\"admin_v2_course_set_recommend_list\",\"admin_v2_course_set_data\",\"admin_v2_classroom\",\"admin_v2_classroom_manage\",\"admin_v2_classroom_content_manage\",\"admin_v2_classroom_create\",\"admin_v2_classroom_cancel_recommend\",\"admin_v2_classroom_set_recommend\",\"admin_v2_classroom_close\",\"admin_v2_classroom_open\",\"admin_v2_classroom_delete\",\"admin_v2_sms_prepare\",\"admin_v2_classroom_recommend\",\"admin_v2_live_course\",\"admin_v2_live_course_coming\",\"admin_v2_live_course_add\",\"admin_v2_live_course_underway\",\"admin_v2_live_course_end\",\"admin_v2_open_course_manage\",\"admin_v2_open_course\",\"admin_v2_open_course_add\",\"admin_v2_open_course_recommend_list\",\"admin_v2_open_course_analysis\",\"admin_v2_course_category_tag\",\"admin_v2_course_category\",\"admin_v2_course_category_manage\",\"admin_v2_category_create\",\"admin_v2_classroom_category_manage\",\"admin_v2_classroom_category_create\",\"admin_v2_tag\",\"admin_v2_course_tag_manage\",\"admin_v2_course_tag_add\",\"admin_v2_course_tag_group_manage\",\"admin_v2_course_tag_group_add\",\"admin_v2_tool_group\",\"admin_v2_course_note\",\"admin_v2_course_note_manage\",\"admin_v2_course_question\",\"admin_v2_course_question_manage\",\"admin_v2_course_thread\",\"admin_v2_course_thread_manage\",\"admin_v2_classroom_thread_manage\",\"admin_v2_review\",\"admin_v2_course_review_tab\",\"admin_v2_classroom_review_tab\",\"admin_v2_cloud_resource_group\",\"admin_v2_cloud_resource\",\"admin_v2_cloud_file\",\"admin_v2_cloud_attachment\",\"admin_v2_question_bank\",\"admin_v2_question_bank_basic\",\"admin_v2_question_bank_add\",\"admin_v2_question_bank_category\",\"admin_v2_question_bank_category_add\",\"admin_v2_marketing\",\"admin_v2_marketing_group\",\"admin_v2_operation_invite\",\"admin_v2_operation_invite_record\",\"admin_v2_operation_invite_user\",\"admin_v2_operation_invite_coupon\",\"admin_v2_marketing_coupon\",\"admin_v2_marketing_coupon_manage\",\"admin_v2_coupon_generate\",\"admin_v2_marketing_coupon_query\",\"admin_v2_coupon_setting\",\"admin_v2_micromarketing\",\"admin_v2_micromarketing_page\",\"admin_v2_micromarketing_bootpage\",\"admin_v2_channel_group\",\"admin_v2_distributor\",\"admin_v2_distributor_page\",\"admin_v2_distributor_bootpage\",\"admin_v2_operating\",\"admin_v2_site_decoration_group\",\"admin_v2_h5_set\",\"admin_v2_wap_set\",\"admin_v2_wechat_app_manage\",\"admin_v2_wechat_app\",\"admin_v2_setting_mobile\",\"admin_v2_mobile_discoveries_settings\",\"admin_v2_mobile_settings\",\"admin_v2_block_manage\",\"admin_v2_block_theme\",\"admin_v2_block_all\",\"admin_v2_block_system\",\"admin_v2_setting_theme\",\"admin_v2_site_setting\",\"admin_v2_top_navigation\",\"admin_v2_foot_navigation\",\"admin_v2_setting_consult_setting\",\"admin_v2_setting_es_bar\",\"admin_v2_homepage_live_notify\",\"admin_v2_content\",\"admin_v2_setting_share\",\"admin_v2_friendlyLink_navigation\",\"admin_v2_content_operation_group\",\"admin_v2_operation_notification\",\"admin_v2_batch_notification\",\"admin_v2_batch_notification_create\",\"admin_v2_operation_announcement\",\"admin_v2_operation_announcement_manage\",\"admin_v2_announcement_create\",\"admin_v2_operation_article\",\"admin_v2_operation_article_manage\",\"admin_v2_operation_article_create\",\"admin_v2_operation_article_category\",\"admin_v2_operation_category_create\",\"admin_v2_operation_group_manage\",\"admin_v2_operation_group\",\"admin_v2_operation_group_create\",\"admin_v2_operation_group_thread\",\"admin_v2_operation_wechat_notification\",\"admin_v2_operation_wechat_notification_record\",\"admin_v2_operation_wechat_notification_manage\",\"admin_v2_message_manage\",\"admin_v2_message\",\"admin_v2_operation_sensitive_words\",\"admin_v2_keyword\",\"admin_v2_keyword_create\",\"admin_v2_keyword_banlogs\",\"admin_v2_user\",\"admin_v2_user_group\",\"admin_v2_user_show\",\"admin_v2_user_manage\",\"admin_v2_user_create\",\"admin_v2_user_edit\",\"admin_v2_user_roles\",\"admin_v2_user_send_passwordreset_email\",\"admin_v2_user_send_emailverify_email\",\"admin_v2_user_lock\",\"admin_v2_user_unlock\",\"admin_v2_user_org_update\",\"admin_v2_fans\",\"admin_v2_online\",\"admin_v2_learn_statistics\",\"admin_v2_login_record\",\"admin_v2_approval_manage\",\"admin_v2_approval_approving\",\"admin_v2_approval_approved\",\"admin_v2_approval_cancel\",\"admin_v2_teacher\",\"admin_v2_teacher_manage\",\"admin_v2_teacher_promote\",\"admin_v2_teacher_promote_cancel\",\"admin_v2_teacher_promote_list\",\"admin_v2_destroy_account\",\"admin_v2_destroy_account_record_manage\",\"admin_v2_destroyed_account_manage\",\"admin_v2_trade\",\"admin_v2_basic_order_manage_group\",\"admin_v2_goods_order\",\"admin_v2_goods_order_list\",\"admin_v2_order_refunds\",\"admin_v2_order_refunds_manage\",\"admin_v2_basic_asset_manage_group\",\"admin_v2_bills\",\"admin_v2_bill_cash\",\"admin_v2_bill_coin\",\"admin_v2_user_coin\",\"admin_v2_coin_user_records\",\"admin_v2_basic_invoice_manage_group\",\"admin_v2_statistics\",\"admin_v2_data_statictics_analysis_basic_group\",\"admin_v2_data_statistics_overview\",\"admin_v2_data_statistics_overview_basic_tab\",\"admin_v2_statistics_data_statistics_menu\",\"admin_v2_data_statistics_basic_tab\",\"admin_v2_cloud_center\",\"admin_v2_basic_content_resource_group\",\"admin_v2_purchase_market_menu\",\"admin_v2_purchase_market_tab\",\"admin_v2_products_version_tab\",\"admin_v2_product_update_setting_tab\",\"admin_v2_app_resource_settlement\",\"admin_v2_resource_settlement_balance\",\"admin_v2_resource_settlement_order\",\"admin_v2_resource_settlement_product\",\"admin_v2_basic_app_center_group\",\"admin_v2_app_center_show\",\"admin_v2_app_center\",\"admin_v2_app_installed\",\"admin_v2_app_upgrades\",\"admin_v2_app_logs\",\"admin_v2_basic_cloud_group\",\"admin_v2_cloud_service\",\"admin_v2_cloud_edulive\",\"admin_v2_edu_cloud_edulive_overview\",\"admin_v2_edu_cloud_edulive_setting\",\"admin_v2_cloud_email\",\"admin_v2_edu_cloud_email_overview\",\"admin_v2_edu_cloud_email_setting\",\"admin_v2_cloud_consult\",\"admin_v2_edu_cloud_consult_setting\",\"admin_v2_app_im\",\"admin_v2_app_im_setting\",\"admin_v2is_s2b2c_enabled_cloud_data_lab_manage\",\"admin_v2_cloud_data_lab\",\"admin_v2_cloud_data_lab_setting\",\"admin_v2_cloud_face_identify\",\"admin_v2_cloud_face_identify_setting\",\"admin_v2_cloud_facein\",\"admin_v2_edu_cloud_facein_overview\",\"admin_v2_edu_cloud_facein_generate_link\",\"admin_v2_edu_cloud_facein_setting\",\"admin_v2_cloud_feature_lab\",\"admin_v2_cloud_feature_lab_setting\",\"admin_v2_developer_module\",\"admin_v2_developer_group\",\"admin_v2_xapi_manage\",\"admin_v2_xapi_setting\",\"admin_v2_xapi_statement_list\",\"admin_v2_cdn_manage\",\"admin_v2_setting_cdn\",\"admin_v2_developer_debug_manage\",\"admin_v2_develop_debug_setting\",\"admin_v2_setting_developer_magic\",\"admin_v2_setting_developer_version\",\"admin_v2_mocked_manage\",\"admin_v2_mocked\",\"admin_v2_page_static_manage\",\"admin_v2_page_static_setting\",\"admin_v2_org_manage\",\"admin_v2_org\",\"admin_v2_jobs_manage\",\"admin_v2_jobs\",\"admin_v2_job_logs\",\"admin_v2_crontab\",\"admin_v2_plumber_manage\",\"admin_v2_plumber\",\"admin_v2_plumber_queue\",\"admin_v2_apple_manage\",\"admin_v2_setting_apple\",\"admin_v2_setting_mobile_iap_product\",\"admin_v2_setting_mobile_iap_product_list\",\"admin_v2_queue_manage\",\"admin_v2_queue_failed_logs\",\"web\",\"course_manage\",\"course_manage_info\",\"course_manage_base\",\"course_manage_detail\",\"course_manage_picture\",\"course_manage_lesson\",\"live_course_manage_replay\",\"course_manage_files\",\"course_manage_setting\",\"course_manage_price\",\"course_manage_teachers\",\"course_manage_students\",\"course_manage_student_create\",\"course_manage_questions\",\"course_manage_question\",\"course_manage_testpaper\",\"course_manange_operate\",\"course_manage_data\",\"course_manage_order\",\"classroom_manage\",\"classroom_manage_settings\",\"classroom_manage_set_info\",\"classroom_manage_set_price\",\"classroom_manage_set_picture\",\"classroom_manage_service\",\"classroom_manage_headteacher\",\"classroom_manage_teachers\",\"classroom_manage_assistants\",\"classroom_manage_content\",\"classroom_manage_courses\",\"classroom_manage_students\",\"classroom_manage_testpaper\"]', '1', '1515719362000', '0');
INSERT INTO `app_role` VALUES ('4', '超级管理员', 'ROLE_SUPER_ADMIN', '[\"admin\",\"admin_user\",\"admin_user_show\",\"admin_user_manage\",\"admin_user_create\",\"admin_user_edit\",\"admin_user_roles\",\"admin_user_avatar\",\"admin_user_change_password\",\"admin_user_send_passwordreset_email\",\"admin_user_send_emailverify_email\",\"admin_user_lock\",\"admin_user_unlock\",\"admin_user_org_update\",\"admin_online\",\"admin_login_record\",\"admin_user_learn_statistics_manage\",\"admin_user_learn_statistics\",\"admin_teacher\",\"admin_teacher_manage\",\"admin_teacher_promote\",\"admin_teacher_promote_cancel\",\"admin_teacher_promote_list\",\"admin_approval_manage\",\"admin_approval_approvals\",\"admin_approval_cancel\",\"admin_message_manage\",\"admin_message\",\"admin_course\",\"admin_course_show\",\"admin_course_manage\",\"admin_course_content_manage\",\"admin_course_add\",\"admin_course_set_recommend\",\"admin_course_set_cancel_recommend\",\"admin_course_guest_member_preview\",\"admin_course_set_close\",\"admin_course_sms_prepare\",\"admin_course_set_clone\",\"admin_course_set_publish\",\"admin_course_set_delete\",\"admin_course_set_recommend_list\",\"admin_course_set_data\",\"admin_classroom\",\"admin_classroom_manage\",\"admin_classroom_content_manage\",\"admin_classroom_create\",\"admin_classroom_cancel_recommend\",\"admin_classroom_set_recommend\",\"admin_classroom_close\",\"admin_sms_prepare\",\"admin_classroom_open\",\"admin_classroom_delete\",\"admin_classroom_recommend\",\"admin_open_course_manage\",\"admin_open_course\",\"admin_open_course_add\",\"admin_open_course_recommend_list\",\"admin_opencourse_analysis\",\"admin_live_course\",\"admin_live_course_manage\",\"admin_live_course_add\",\"admin_question_bank\",\"admin_question_bank_manage\",\"admin_question_bank_add\",\"admin_question_bank_category_manage\",\"admin_question_bank_category_add\",\"admin_course_thread\",\"admin_course_thread_manage\",\"admin_classroom_thread_manage\",\"admin_course_question\",\"admin_course_question_manage\",\"admin_course_note\",\"admin_course_note_manage\",\"admin_course_review\",\"admin_course_review_tab\",\"admin_classroom_review_tab\",\"admin_course_category\",\"admin_course_category_manage\",\"admin_category_create\",\"admin_classroom_category_manage\",\"admin_classroom_category_create\",\"admin_course_tag\",\"admin_course_tag_manage\",\"admin_course_tag_add\",\"admin_course_tag_group_manage\",\"admin_course_tag_group_add\",\"admin_operation\",\"admin_operation_article\",\"admin_operation_article_manage\",\"admin_operation_article_create\",\"admin_operation_article_category\",\"admin_operation_category_create\",\"admin_operation_group\",\"admin_operation_group_manage\",\"admin_operation_group_create\",\"admin_operation_group_thread\",\"admin_operation_invite\",\"admin_operation_invite_record\",\"admin_operation_invite_user\",\"admin_operation_invite_coupon\",\"admin_announcement\",\"admin_announcement_manage\",\"admin_announcement_create\",\"admin_operation_notification\",\"admin_batch_notification\",\"admin_batch_notification_create\",\"admin_block_manage\",\"admin_block\",\"admin_block_visual_edit\",\"admin_operation_content\",\"admin_content\",\"admin_operation_mobile\",\"admin_operation_mobile_banner_manage\",\"admin_discovery_column_index\",\"admin_discovery_column_create\",\"admin_operation_wechat_notification\",\"admin_operation_wechat_fans_list\",\"admin_operation_wechat_notification_record\",\"admin_operation_wechat_notification_manage\",\"admin_operation_analysis_register\",\"admin_operation_analysis\",\"admin_operation_coupon\",\"admin_operation_coupon_manage\",\"admin_coupon_generate\",\"admin_operation_coupon_query\",\"admin_coupon_setting\",\"admin_operation_keyword\",\"admin_keyword\",\"admin_keyword_create\",\"admin_keyword_banlogs\",\"admin_finance\",\"admin_orders\",\"admin_order_manage\",\"admin_order_refunds\",\"admin_order_refunds_manage\",\"admin_bills\",\"admin_bill_cash\",\"admin_bill_coin\",\"admin_coin_user\",\"admin_coin_user_records\",\"admin_app\",\"admin_my_cloud\",\"admin_my_cloud_overview\",\"admin_cloud_video_setting\",\"admin_cloud_video_overview\",\"admin_cloud_setting_video\",\"admin_cloud_edulive_setting\",\"admin_cloud_edulive_overview\",\"admin_setting_cloud_edulive\",\"admin_edu_cloud_sms\",\"admin_edu_cloud_sms_overview\",\"admin_edu_cloud_setting_sms\",\"admin_edu_cloud_email\",\"admin_edu_cloud_email_overview\",\"admin_edu_cloud_email_setting\",\"admin_edu_cloud_search_setting\",\"admin_edu_cloud_search_overview\",\"admin_edu_cloud_setting_search\",\"admin_app_im\",\"admin_app_im_setting\",\"admin_cloud_file_manage\",\"admin_cloud_file\",\"admin_setting_cloud_attachment\",\"admin_edu_cloud_attachment\",\"admin_app_center_show\",\"admin_app_center\",\"admin_app_installed\",\"admin_app_upgrades\",\"admin_app_logs\",\"admin_cloud_attachment_manage\",\"admin_cloud_attachment\",\"admin_cloud_consult\",\"admin_cloud_consult_setting\",\"admin_cloud_data_lab_manage\",\"admin_cloud_data_lab\",\"admin_cloud_data_lab_setting\",\"admin_cloud_face_identify_manage\",\"admin_cloud_face_identify\",\"admin_cloud_feature_lab_manage\",\"admin_cloud_feature_lab\",\"admin_setting_cloud\",\"admin_setting_my_cloud\",\"admin_marketing_top\",\"_admin_marketing_marketing_menu\",\"_marketing_marketing_page\",\"admin_distributor_top\",\"_admin_distributor_menu\",\"_distributor_page\",\"admin_mobile\",\"admin_wechat_app_manage\",\"admin_wechat_app\",\"admin_setting_mobile\",\"admin_setting_mobile_discoveries_settings\",\"admin_setting_mobile_settings\",\"admin_h5_set\",\"admin_wap_set\",\"admin_system\",\"admin_setting\",\"admin_setting_message\",\"admin_setting_theme\",\"admin_setting_mailer\",\"admin_top_navigation\",\"admin_foot_navigation\",\"admin_friendlyLink_navigation\",\"admin_setting_consult_setting\",\"admin_setting_es_bar\",\"admin_setting_share\",\"admin_setting_security\",\"admin_setting_user\",\"admin_user_auth\",\"admin_setting_login_bind\",\"admin_setting_user_center\",\"admin_setting_user_fields\",\"admin_setting_avatar\",\"admin_setting_user_message\",\"admin_roles\",\"admin_role_manage\",\"admin_role_create\",\"admin_role_edit\",\"admin_role_delete\",\"admin_setting_course_setting\",\"admin_setting_course\",\"admin_setting_questions_setting\",\"admin_setting_course_avatar\",\"admin_classroom_setting\",\"admin_open_course_setting\",\"admin_setting_operation\",\"admin_article_setting\",\"admin_group_set\",\"admin_invite_set\",\"admin_message_setting\",\"admin_setting_finance\",\"admin_payment\",\"admin_coin_settings\",\"admin_setting_refund\",\"admin_setting_wechat\",\"admin_setting_wechat_auth\",\"admin_setting_mobile_iap_product\",\"admin_setting_mobile_iap_product_list\",\"admin_optimize\",\"admin_optimize_settings\",\"admin_setting_ip_blacklist\",\"admin_setting_ip_blacklist_manage\",\"admin_setting_post_num_rules\",\"admin_setting_post_num_rules_settings\",\"admin_report_status\",\"admin_report_status_list\",\"admin_logs\",\"admin_logs_query\",\"admin_logs_prod\",\"admin_org_manage\",\"admin_org\",\"admin_jobs_manage\",\"admin_jobs\",\"admin_job_logs\",\"admin_crontab\",\"admin_xapi_manage\",\"admin_xapi_setting\",\"admin_xapi_statement_list\",\"admin_queue_menege\",\"admin_queue_failed_logs\",\"web\",\"course_manage\",\"course_manage_info\",\"course_manage_base\",\"course_manage_detail\",\"course_manage_picture\",\"course_manage_lesson\",\"live_course_manage_replay\",\"course_manage_files\",\"course_manage_setting\",\"course_manage_price\",\"course_manage_teachers\",\"course_manage_students\",\"course_manage_student_create\",\"course_manage_questions\",\"course_manage_question\",\"course_manage_testpaper\",\"course_manange_operate\",\"course_manage_data\",\"course_manage_order\",\"classroom_manage\",\"classroom_manage_settings\",\"classroom_manage_set_info\",\"classroom_manage_set_price\",\"classroom_manage_set_picture\",\"classroom_manage_service\",\"classroom_manage_headteacher\",\"classroom_manage_teachers\",\"classroom_manage_assistants\",\"classroom_manage_content\",\"classroom_manage_courses\",\"classroom_manage_students\",\"classroom_manage_testpaper\"]', '[\"admin_v2\",\"admin_v2_teach\",\"admin_v2_course_group\",\"admin_v2_course_show\",\"admin_v2_course_manage\",\"admin_v2_course_content_manage\",\"admin_v2_go_to_choose\",\"admin_v2_course_add\",\"admin_v2_course_set_recommend\",\"admin_v2_course_set_cancel_recommend\",\"admin_v2_course_guest_member_preview\",\"admin_v2_course_set_close\",\"admin_v2_course_sms_prepare\",\"admin_v2_course_set_clone\",\"admin_v2_course_set_publish\",\"admin_v2_course_set_delete\",\"admin_v2_course_set_remove\",\"admin_v2_course_set_recommend_list\",\"admin_v2_course_set_data\",\"admin_v2_classroom\",\"admin_v2_classroom_manage\",\"admin_v2_classroom_content_manage\",\"admin_v2_classroom_create\",\"admin_v2_classroom_cancel_recommend\",\"admin_v2_classroom_set_recommend\",\"admin_v2_classroom_close\",\"admin_v2_classroom_open\",\"admin_v2_classroom_delete\",\"admin_v2_sms_prepare\",\"admin_v2_classroom_recommend\",\"admin_v2_live_course\",\"admin_v2_live_course_coming\",\"admin_v2_live_course_add\",\"admin_v2_live_course_underway\",\"admin_v2_live_course_end\",\"admin_v2_open_course_manage\",\"admin_v2_open_course\",\"admin_v2_open_course_add\",\"admin_v2_open_course_recommend_list\",\"admin_v2_open_course_analysis\",\"admin_v2_course_category_tag\",\"admin_v2_course_category\",\"admin_v2_course_category_manage\",\"admin_v2_category_create\",\"admin_v2_classroom_category_manage\",\"admin_v2_classroom_category_create\",\"admin_v2_tag\",\"admin_v2_course_tag_manage\",\"admin_v2_course_tag_add\",\"admin_v2_course_tag_group_manage\",\"admin_v2_course_tag_group_add\",\"admin_v2_tool_group\",\"admin_v2_course_note\",\"admin_v2_course_note_manage\",\"admin_v2_course_question\",\"admin_v2_course_question_manage\",\"admin_v2_course_thread\",\"admin_v2_course_thread_manage\",\"admin_v2_classroom_thread_manage\",\"admin_v2_review\",\"admin_v2_course_review_tab\",\"admin_v2_classroom_review_tab\",\"admin_v2_cloud_resource_group\",\"admin_v2_cloud_resource\",\"admin_v2_cloud_file\",\"admin_v2_cloud_attachment\",\"admin_v2_cloud_attachment_setting\",\"admin_v2_question_bank\",\"admin_v2_question_bank_basic\",\"admin_v2_question_bank_add\",\"admin_v2_question_bank_category\",\"admin_v2_question_bank_category_add\",\"admin_v2_marketing\",\"admin_v2_marketing_group\",\"admin_v2_operation_invite\",\"admin_v2_operation_invite_record\",\"admin_v2_operation_invite_user\",\"admin_v2_operation_invite_coupon\",\"admin_v2_marketing_coupon\",\"admin_v2_marketing_coupon_manage\",\"admin_v2_coupon_generate\",\"admin_v2_marketing_coupon_query\",\"admin_v2_coupon_setting\",\"admin_v2_micromarketing\",\"admin_v2_micromarketing_page\",\"admin_v2_micromarketing_bootpage\",\"admin_v2_channel_group\",\"admin_v2_distributor\",\"admin_v2_distributor_page\",\"admin_v2_distributor_bootpage\",\"admin_v2_operating\",\"admin_v2_site_decoration_group\",\"admin_v2_h5_set\",\"admin_v2_wap_set\",\"admin_v2_wechat_app_manage\",\"admin_v2_wechat_app\",\"admin_v2_setting_mobile\",\"admin_v2_mobile_discoveries_settings\",\"admin_v2_mobile_settings\",\"admin_v2_block_manage\",\"admin_v2_block_theme\",\"admin_v2_block_all\",\"admin_v2_block_system\",\"admin_v2_setting_theme\",\"admin_v2_site_setting\",\"admin_v2_top_navigation\",\"admin_v2_foot_navigation\",\"admin_v2_setting_consult_setting\",\"admin_v2_setting_es_bar\",\"admin_v2_homepage_live_notify\",\"admin_v2_content\",\"admin_v2_setting_share\",\"admin_v2_friendlyLink_navigation\",\"admin_v2_content_operation_group\",\"admin_v2_operation_notification\",\"admin_v2_batch_notification\",\"admin_v2_batch_notification_create\",\"admin_v2_operation_announcement\",\"admin_v2_operation_announcement_manage\",\"admin_v2_announcement_create\",\"admin_v2_operation_article\",\"admin_v2_operation_article_manage\",\"admin_v2_operation_article_create\",\"admin_v2_operation_article_category\",\"admin_v2_operation_category_create\",\"admin_v2_operation_group_manage\",\"admin_v2_operation_group\",\"admin_v2_operation_group_create\",\"admin_v2_operation_group_thread\",\"admin_v2_operation_wechat_notification\",\"admin_v2_operation_wechat_notification_record\",\"admin_v2_operation_wechat_notification_manage\",\"admin_v2_message_manage\",\"admin_v2_message\",\"admin_v2_operation_sensitive_words\",\"admin_v2_keyword\",\"admin_v2_keyword_create\",\"admin_v2_keyword_banlogs\",\"admin_v2_user\",\"admin_v2_user_group\",\"admin_v2_user_show\",\"admin_v2_user_manage\",\"admin_v2_user_create\",\"admin_v2_user_edit\",\"admin_v2_user_roles\",\"admin_v2_user_change_nickname\",\"admin_v2_user_avatar\",\"admin_v2_user_change_password\",\"admin_v2_user_send_passwordreset_email\",\"admin_v2_user_send_emailverify_email\",\"admin_v2_user_lock\",\"admin_v2_user_unlock\",\"admin_v2_user_org_update\",\"admin_v2_fans\",\"admin_v2_online\",\"admin_v2_learn_statistics\",\"admin_v2_login_record\",\"admin_v2_approval_manage\",\"admin_v2_approval_approving\",\"admin_v2_approval_approved\",\"admin_v2_approval_cancel\",\"admin_v2_teacher\",\"admin_v2_teacher_manage\",\"admin_v2_teacher_promote\",\"admin_v2_teacher_promote_cancel\",\"admin_v2_teacher_promote_list\",\"admin_v2_destroy_account\",\"admin_v2_destroy_account_record_manage\",\"admin_v2_destroyed_account_manage\",\"admin_v2_trade\",\"admin_v2_basic_order_manage_group\",\"admin_v2_goods_order\",\"admin_v2_goods_order_list\",\"admin_v2_order_refunds\",\"admin_v2_order_refunds_manage\",\"admin_v2_basic_asset_manage_group\",\"admin_v2_bills\",\"admin_v2_bill_cash\",\"admin_v2_bill_coin\",\"admin_v2_user_coin\",\"admin_v2_coin_user_records\",\"admin_v2_basic_invoice_manage_group\",\"admin_v2_statistics\",\"admin_v2_data_statictics_analysis_basic_group\",\"admin_v2_data_statistics_overview\",\"admin_v2_data_statistics_overview_basic_tab\",\"admin_v2_statistics_data_statistics_menu\",\"admin_v2_data_statistics_basic_tab\",\"admin_v2_system\",\"admin_v2_general_group\",\"admin_v2_school_information_setting\",\"admin_v2_school_information\",\"admin_v2_setting_user\",\"admin_v2_user_auth\",\"admin_v2_setting_login_bind\",\"admin_v2_setting_user_center\",\"admin_v2_setting_user_fields\",\"admin_v2_setting_avatar\",\"admin_v2_setting_wechat\",\"admin_v2_setting_wechat_auth\",\"admin_v2_setting_course_setting\",\"admin_v2_setting_course\",\"admin_v2_setting_questions_setting\",\"admin_v2_setting_course_avatar\",\"admin_v2_classroom_setting\",\"admin_v2_open_course_setting\",\"admin_v2_setting_operation\",\"admin_v2_setting_mailer\",\"admin_v2_article_set\",\"admin_v2_group_set\",\"admin_v2_invite_set\",\"admin_v2_message_send_set\",\"admin_v2_message_open_set\",\"admin_v2_transaction_group\",\"admin_v2_asset_setting\",\"admin_v2_payment\",\"admin_v2_coin_settings\",\"admin_v2_setting_refund\",\"admin_v2_system_group\",\"admin_v2_optimize\",\"admin_v2_optimize_settings\",\"admin_v2_roles\",\"admin_v2_role_manage\",\"admin_v2_role_create\",\"admin_v2_role_edit\",\"admin_v2_role_delete\",\"admin_v2_report_status\",\"admin_v2_report_status_list\",\"admin_v2_logs\",\"admin_v2_logs_query\",\"admin_v2_logs_prod\",\"admin_v2_security\",\"admin_v2_ip_blacklist_setting\",\"admin_v2_security_setting\",\"admin_v2_post_num_rules_setting\",\"admin_v2_cloud_center\",\"admin_v2_basic_content_resource_group\",\"admin_v2_purchase_market_menu\",\"admin_v2_purchase_market_tab\",\"admin_v2_products_version_tab\",\"admin_v2_product_update_setting_tab\",\"admin_v2_app_resource_settlement\",\"admin_v2_resource_settlement_balance\",\"admin_v2_resource_settlement_order\",\"admin_v2_resource_settlement_product\",\"admin_v2_basic_app_center_group\",\"admin_v2_app_center_show\",\"admin_v2_app_center\",\"admin_v2_app_installed\",\"admin_v2_app_upgrades\",\"admin_v2_app_logs\",\"admin_v2_basic_cloud_group\",\"admin_v2_my_cloud\",\"admin_v2_my_cloud_overview\",\"admin_v2_cloud_service\",\"admin_v2_cloud_video\",\"admin_v2_edu_cloud_video_overview\",\"admin_v2_edu_cloud_video_setting\",\"admin_v2_cloud_edulive\",\"admin_v2_edu_cloud_edulive_overview\",\"admin_v2_edu_cloud_edulive_setting\",\"admin_v2_cloud_sms\",\"admin_v2_edu_cloud_sms_overview\",\"admin_v2_edu_cloud_sms_setting\",\"admin_v2_cloud_email\",\"admin_v2_edu_cloud_email_overview\",\"admin_v2_edu_cloud_email_setting\",\"admin_v2_cloud_search\",\"admin_v2_edu_cloud_search_overview\",\"admin_v2_edu_cloud_search_setting\",\"admin_v2_cloud_consult\",\"admin_v2_edu_cloud_consult_setting\",\"admin_v2_app_im\",\"admin_v2_app_im_setting\",\"admin_v2is_s2b2c_enabled_cloud_data_lab_manage\",\"admin_v2_cloud_data_lab\",\"admin_v2_cloud_data_lab_setting\",\"admin_v2_cloud_face_identify\",\"admin_v2_cloud_face_identify_setting\",\"admin_v2_cloud_facein\",\"admin_v2_edu_cloud_facein_overview\",\"admin_v2_edu_cloud_facein_generate_link\",\"admin_v2_edu_cloud_facein_setting\",\"admin_v2_cloud_feature_lab\",\"admin_v2_cloud_feature_lab_setting\",\"admin_v2_setting_cloud\",\"admin_v2_setting_my_cloud\",\"admin_v2_developer_module\",\"admin_v2_developer_group\",\"admin_v2_xapi_manage\",\"admin_v2_xapi_setting\",\"admin_v2_xapi_statement_list\",\"admin_v2_cdn_manage\",\"admin_v2_setting_cdn\",\"admin_v2_developer_debug_manage\",\"admin_v2_develop_debug_setting\",\"admin_v2_setting_developer_magic\",\"admin_v2_setting_developer_version\",\"admin_v2_mocked_manage\",\"admin_v2_mocked\",\"admin_v2_page_static_manage\",\"admin_v2_page_static_setting\",\"admin_v2_org_manage\",\"admin_v2_org\",\"admin_v2_jobs_manage\",\"admin_v2_jobs\",\"admin_v2_job_logs\",\"admin_v2_crontab\",\"admin_v2_plumber_manage\",\"admin_v2_plumber\",\"admin_v2_plumber_queue\",\"admin_v2_apple_manage\",\"admin_v2_setting_apple\",\"admin_v2_setting_mobile_iap_product\",\"admin_v2_setting_mobile_iap_product_list\",\"admin_v2_queue_manage\",\"admin_v2_queue_failed_logs\",\"web\",\"course_manage\",\"course_manage_info\",\"course_manage_base\",\"course_manage_detail\",\"course_manage_picture\",\"course_manage_lesson\",\"live_course_manage_replay\",\"course_manage_files\",\"course_manage_setting\",\"course_manage_price\",\"course_manage_teachers\",\"course_manage_students\",\"course_manage_student_create\",\"course_manage_questions\",\"course_manage_question\",\"course_manage_testpaper\",\"course_manange_operate\",\"course_manage_data\",\"course_manage_order\",\"classroom_manage\",\"classroom_manage_settings\",\"classroom_manage_set_info\",\"classroom_manage_set_price\",\"classroom_manage_set_picture\",\"classroom_manage_service\",\"classroom_manage_headteacher\",\"classroom_manage_teachers\",\"classroom_manage_assistants\",\"classroom_manage_content\",\"classroom_manage_courses\",\"classroom_manage_students\",\"classroom_manage_testpaper\"]', '1', '1515719362000', '0');

-- ----------------------------
-- Table structure for `app_setting`
-- ----------------------------
DROP TABLE IF EXISTS `app_setting`;
CREATE TABLE `app_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统设置ID',
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
CREATE TABLE `app_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(128) NOT NULL COMMENT '用户邮箱',
  `verifiedMobile` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `salt` varchar(32) NOT NULL COMMENT '密码SALT',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '头衔',
  `tags` varchar(255) NOT NULL DEFAULT '' COMMENT '标签',
  `type` varchar(32) NOT NULL COMMENT 'default默认为网站注册, weibo新浪微薄登录',
  `smallAvatar` varchar(255) NOT NULL DEFAULT '' COMMENT '小头像',
  `mediumAvatar` varchar(255) NOT NULL DEFAULT '' COMMENT '中头像',
  `largeAvatar` varchar(255) NOT NULL DEFAULT '' COMMENT '大头像',
  `emailVerified` tinyint(1) NOT NULL DEFAULT '0' COMMENT '邮箱是否为已验证',
  `setup` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否初始化设置的，未初始化的可以设置邮箱、昵称。',
  `roles` varchar(255) NOT NULL COMMENT '用户角色',
  `promoted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为推荐',
  `promotedTime` bigint NOT NULL DEFAULT '0' COMMENT '推荐时间',
  `locked` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否被禁止',
  `lockDeadline` int(10) NOT NULL DEFAULT '0' COMMENT '帐号锁定期限',
  `consecutivePasswordErrorTimes` int(11) NOT NULL DEFAULT '0' COMMENT '帐号密码错误次数',
  `lastPasswordFailTime` bigint NOT NULL DEFAULT '0',
  `loginTime` bigint NOT NULL DEFAULT '0' COMMENT '最后登录时间',
  `loginIp` varchar(64) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `loginSessionId` varchar(255) NOT NULL DEFAULT '' COMMENT '最后登录会话ID',
  `approvalTime` bigint NOT NULL DEFAULT '0' COMMENT '实名认证时间',
  `approvalStatus` enum('unapprove','approving','approved','approve_fail') NOT NULL DEFAULT 'unapprove' COMMENT '实名认证状态',
  `newMessageNum` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '未读私信数',
  `newNotificationNum` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '未读消息数',
  `createdIp` varchar(64) NOT NULL DEFAULT '' COMMENT '注册IP',
  `createdTime` bigint NOT NULL DEFAULT '0' COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES ('1', 'super@hotmail.com', '', 'FxnzDXLOO8yqOqg64RA5erBujL+iw3z8UvuTr+Y16Ak=', '2pfHj21OUy1rBfVGyCb6qw==', 'super', 'Bookfan', '', 'default', 'public://user/2021/10-31/162139305458301713.jpg', 'public://user/2021/10-31/16213930449e898200.jpg', 'public://user/2021/10-31/162139302286665315.jpg', '0', '1', '|ROLE_USER|ROLE_TEACHER|ROLE_SUPER_ADMIN|', '0', '0', '0', '0', '0', '0', '1635668402000', '::1', 'l04bkr6d261jsfeg3tn25hltin', '0', 'unapprove', '0', '0', '', '1635668142000');
INSERT INTO `app_user` VALUES ('2', 'test@qq.com', '', 'RIGagyj4RFv7Y4Fk3oxEO0CFboVaOGdEs8Pzt5Sf1HM=', 'FYMMdLVItihBsvfsp+ImFg==', 'test', '', '', 'default', '', '', '', '0', '1', '|ROLE_USER|', '0', '0', '0', '0', '0', '0', '0', '', '', '0', 'unapprove', '0', '0', '::1', '1635668723000');

-- ----------------------------
-- Table structure for `app_user_profile`
-- ----------------------------
DROP TABLE IF EXISTS `app_user_profile`;
CREATE TABLE `app_user_profile` (
  `id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `truename` varchar(255) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `idcard` varchar(24) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `gender` enum('male','female','secret') NOT NULL DEFAULT 'secret' COMMENT '性别',
  `whoami` varchar(255) NOT NULL DEFAULT '' COMMENT '我是谁',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) NOT NULL DEFAULT '' COMMENT '城市',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
  `qq` varchar(32) NOT NULL DEFAULT '' COMMENT 'QQ',
  `signature` text COMMENT '签名',
  `aboutme` text COMMENT '自我介绍',
  `mycompany` varchar(255) NOT NULL DEFAULT '' COMMENT '公司',
  `myjob` varchar(255) NOT NULL DEFAULT '' COMMENT '工作',
  `myschool` varchar(255) NOT NULL DEFAULT '' COMMENT '学校',
  `myclass` varchar(255) NOT NULL DEFAULT '' COMMENT '班级',
  `myweibo` varchar(255) NOT NULL DEFAULT '' COMMENT '微博',
  `myweixin` varchar(255) NOT NULL DEFAULT '' COMMENT '微信',
  `mysite` varchar(255) NOT NULL DEFAULT '' COMMENT '网站',
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