/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : tijian

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 31/07/2023 15:30:10
*/
CREATE DATABASE tijian;
use tijian;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alreadytijianinfo
-- ----------------------------
DROP TABLE IF EXISTS `alreadytijianinfo`;
CREATE TABLE `alreadytijianinfo`  (
  `aid` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `gid` int(0) NOT NULL,
  `did` int(0) NOT NULL,
  `prenumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  `sdegree` enum('不满意','满意','很满意') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`aid`) USING BTREE,
  INDEX `prenumber`(`prenumber`) USING BTREE,
  INDEX `time`(`time`) USING BTREE,
  INDEX `did`(`did`) USING BTREE,
  INDEX `alreadytijianinfo_ibfk_5`(`gid`) USING BTREE,
  INDEX `alreadytijianinfo_ibfk_1`(`uid`) USING BTREE,
  CONSTRAINT `alreadytijianinfo_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `userinfo` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `alreadytijianinfo_ibfk_2` FOREIGN KEY (`prenumber`) REFERENCES `prepayinfo` (`prenumber`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `alreadytijianinfo_ibfk_3` FOREIGN KEY (`time`) REFERENCES `prepayinfo` (`endtime`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `alreadytijianinfo_ibfk_4` FOREIGN KEY (`did`) REFERENCES `doctorinfo` (`did`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `alreadytijianinfo_ibfk_5` FOREIGN KEY (`gid`) REFERENCES `pregoodsinfo` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alreadytijianinfo
-- ----------------------------
INSERT INTO `alreadytijianinfo` VALUES (1, 2, 6, 1, '95279527', '2023-03-24 20:33:56', '很满意', 1);
INSERT INTO `alreadytijianinfo` VALUES (2, 19, 11, 3, '95259525', '2023-04-03 21:18:19', '满意', 1);
INSERT INTO `alreadytijianinfo` VALUES (3, 2, 26, 6, '95239523', '2023-04-15 11:15:08', '不满意', 1);
INSERT INTO `alreadytijianinfo` VALUES (4, 2, 1, 1, '75287974', '2023-05-03 00:12:43', '不满意', 1);
INSERT INTO `alreadytijianinfo` VALUES (5, 2, 2, 1, '73087558', '2023-05-03 16:47:11', '满意', 1);
INSERT INTO `alreadytijianinfo` VALUES (6, 2, 10, 1, '78999825', '2023-05-03 21:52:41', NULL, 0);

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `role` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (3, '体检前注意事项', '1．体检前三天，请注意清淡饮食，不吃动物血制品、动物内脏等高脂、高蛋白食物，不饮酒，不要吃对肝、肾功能有影响的药物。2．建议体检前一天不吃或少吃水果，尤其是葡萄或含维生素C成分较高的水果。体检前一天晚上8时起，避免进食和剧烈运动，晚上10时以后请勿吃任何食物。保持充足睡眠，休息不好会影响血糖、血脂、血压。 3．体检当日起床后，禁食、禁水。如果口干，可以喝一小杯白开水。当日早上禁止饮用蜂蜜水等含糖量饮品及嚼口香糖，也不能喝别的饮料。否则会对胆囊、胰腺超声、血脂有影响。 4．高血压、心脏病、高脂血症等需要长期服药的慢性病患者，一般可以按常规服药，请少量清水送服。糖尿病患者请随身携带降血糖药，空腹项目结束后，吃早餐并按常规服药。 5．需做泌尿及生殖系统（即肾、输尿管、膀胱、前列腺、子宫及其附件）B超检查时，则须憋尿，使膀胱充盈才能后进行。 6．女士请勿化浓妆，装饰会影响医生对疾病的判断。为了避免干扰眼科检查，体检当日请勿佩戴隐形眼镜。体检前应对口腔、鼻腔、外耳道等处自我进行清洁，不清洁容易使一些疾病漏诊。 7．不要穿过于复杂的服装，以方便穿、脱。女士最好不要穿连衣裙、高筒靴、连裤袜，男士最好不要穿高领套头衫、紧袖上衣、紧腿裤子等，以防造成不必要的麻烦。8．体检当日请勿携带贵重物品或饰品。请不要佩戴金属饰物，随身物品请妥善保管。为进一步了解您的健康状况，敬请预先全面填写健康问卷及既往史，带上有效身份证件及相关病历资料。 9．女性受检者如果要做妇科检查及超声，注意请避开经期，最好在月经结束后 3～7 天，检查前 2 天请避免性生活、阴道灌洗。 怀孕、可能怀孕、或准备怀孕的女性，受检时不要做x线检查（包括PET/CT和其他放射性同位素等检查）。', '2023-04-06 20:49:04', 'user');
INSERT INTO `announcement` VALUES (4, '会议', '这周末召开紧急会议，时间：周三上午9：00，地点：院长办公室，人员：所有主任。', '2023-04-06 20:52:43', 'doc');
INSERT INTO `announcement` VALUES (8, '五一放假通知', '经研究，“五一”劳动节放假事宜安排如下：\r\n\r\n　　一、20xx年4月30日至5月2日放假，共3天。5月3日(星期二)照常上班。\r\n\r\n　　二、放假期间，急诊、病房正常开放。门诊4月30日、5月1日按常规星期六和星期日上班;5月2日门诊实行轮休。知名专家门诊、退休老中医门诊4月30日至5月2日停诊;5月3日起恢复正常门诊，相关科室据此安排好工作。放假期间调课事宜按学校统一安排进行。\r\n\r\n　　三、请各科做好药品、器械、物资准备，加强工作责任心，切实做好医疗安全、消防安全和稳定工作，严防各种差错事故的发生。\r\n\r\n　　四、各科室节日值班表请于4月28日前交医院办公室汇总。', '2023-05-01 16:12:43', 'doc');
INSERT INTO `announcement` VALUES (9, '五一通知', '患者朋友您好!\r\n\r\n我院2022年“五·一”假期门、急诊工作安排如下:\r\n\r\n一、门诊安排:\r\n\r\n4月30日(星期六)至5月2日(星期一)门诊关闭不开诊。5月3日(星期二)和5月4日(星期三)安排节假日门诊。开诊科室按节假日值班出诊。开诊时间内,开放预住院患者、无症状、健康宝弹窗人员核酸检测。采血室抽血时间同开诊时间。\r\n\r\n因调休,4月24日(星期日)和5月7日(星期六)上班,分别安排星期三和星期一的门诊。\r\n\r\n二、节假日门诊挂号方式:\r\n\r\n采取预约挂号形式挂号,提前7天开放号源。当天只挂当日剩余号。\r\n\r\n节日期间,急诊科、发热门诊24小时接诊。', '2023-05-01 16:13:23', 'user');
INSERT INTO `announcement` VALUES (10, '通知111', 'test', '2023-05-03 16:54:50', 'user');

-- ----------------------------
-- Table structure for doctorinfo
-- ----------------------------
DROP TABLE IF EXISTS `doctorinfo`;
CREATE TABLE `doctorinfo`  (
  `did` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `gender` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `saveage` int(0) NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`did`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  CONSTRAINT `doctorinfo_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctorinfo
-- ----------------------------
INSERT INTO `doctorinfo` VALUES (1, 3, '张三风', 41, '男', 22, '13167983331', '主任医师');
INSERT INTO `doctorinfo` VALUES (2, 23, '赵翠花', 31, '女', 11, '19216812231', '主治医师');
INSERT INTO `doctorinfo` VALUES (3, 24, '朱重八', 50, '男', 30, '13216611231', '副主任医师');
INSERT INTO `doctorinfo` VALUES (4, 25, '李世民', 24, '男', 2, '13167063411', '医师');
INSERT INTO `doctorinfo` VALUES (5, 26, '张茂', 20, '男', 0, '13399232850', '医士');
INSERT INTO `doctorinfo` VALUES (6, 27, '七七', 22, '女', 1, '13919731529', '医士');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pregoodsinfo
-- ----------------------------
DROP TABLE IF EXISTS `pregoodsinfo`;
CREATE TABLE `pregoodsinfo`  (
  `gid` int(0) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NOT NULL,
  `goodsinfo` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attentioninfo` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `forage` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `forpeople` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classes` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `projectinfo` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `count` int(0) NOT NULL DEFAULT 0,
  `ifshelf` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pregoodsinfo
-- ----------------------------
INSERT INTO `pregoodsinfo` VALUES (1, '血液检查套餐', '../../static/picture/血液检查套餐.jpg', 299.00, '包含血常规、肝功能、肾功能、心肌酶谱等等', '检查前12小时禁食，前一天禁止熬夜', '18-61岁', '一般人群', '检查套餐', '血常规、肝功能、肾功能、心肌酶谱等', 6, 1);
INSERT INTO `pregoodsinfo` VALUES (2, '心电图检查', '../../static/picture/心电图检查.jpg', 58.00, '检查心脏的基本情况，包含心率、节律等', '饭后2小时后检查', '12-60岁', '一般人群', '单项检查', '心脏基本情况、心率、节律等', 12, 1);
INSERT INTO `pregoodsinfo` VALUES (3, '肺功能检查', '../../static/picture/肺功能检查.jpg', 198.00, '	评估肺功能情况，包含肺活量、呼吸功能、强度等', '禁烟4小时后检查，最好避免剧烈运动', '18-60岁', '一般人群', '单项检查', '肺活量、呼吸功能、强度等', 9, 1);
INSERT INTO `pregoodsinfo` VALUES (4, '腹部超声检查', '../../static/picture/腹部超声检查.jpg', 488.00, '检查腹部脏器情况，包含肝脏、胆囊、脾脏、胰腺等', '饭后4小时后检查，饮食不得过多，不饮酒', '18-60岁', '一般人群', '检查套餐', '肝脏、胆囊、脾脏、胰腺等', 8, 1);
INSERT INTO `pregoodsinfo` VALUES (5, '妇科检查套餐', '../../static/picture/妇科检查套餐.jpg', 768.00, '检查妇科情况，包含妇科常规、子宫肌瘤、卵巢囊肿等', '检查前一天禁食，检查前不得进行性生活', '18-50岁', '女性', '检查套餐', '妇科常规、子宫肌瘤、卵巢囊肿等', 8, 1);
INSERT INTO `pregoodsinfo` VALUES (6, '糖尿病筛查', '../../static/picture/糖尿病筛查.jpg', 298.00, '包括血糖、胰岛素、糖化血红蛋白等项目', '请在空腹状态下进行体检，注意多喝水', '不限', '	一般人群', '单项检查', '血糖、胰岛素、糖化血红蛋白等', 7, 1);
INSERT INTO `pregoodsinfo` VALUES (7, '肝功能检测', '../../static/picture/肝功能检测.jpg', 198.00, '包括肝功能指标、乙肝、丙肝等项目', '请在空腹状态下进行体检，注意多喝水', '不限', '一般人群', '检查套餐', '肝功能指标、乙肝、丙肝等', 1, 1);
INSERT INTO `pregoodsinfo` VALUES (8, '妇科检查', '../../static/picture/妇科检查.jpg', 688.00, '包括妇科常规、宫颈涂片、乳腺B超等项目', '请在月经期间外进行检查，避免在经期或临近经期进行检查', '不限', '女性', '检查套餐', '妇科常规、宫颈涂片、乳腺B超等', 0, 1);
INSERT INTO `pregoodsinfo` VALUES (9, '骨密度检查', '../../static/picture/骨密度检查.jpg', 298.00, '包括骨密度、骨代谢等项目', '请勿在前一天进行剧烈运动，不要服用钙、维生素D等药物', '不限', '一般人群', '单项检查', '骨密度、骨代谢等', 2, 1);
INSERT INTO `pregoodsinfo` VALUES (10, '眼科检查套餐', '../../static/picture/眼科检查套餐.jpg', 188.00, '包含眼科常规检查，如视力、眼压、角膜等。', '在检查前请勿佩戴隐形眼镜。', '18-60岁', '一般人群', '单项检查', '视力、眼压、角膜等', 1, 1);
INSERT INTO `pregoodsinfo` VALUES (11, '冠状动脉CT检查套餐', '../../static/picture/冠状动脉CT检查套餐.jpg', 1580.00, '针对冠心病、心肌梗死等疾病的检查。', '检查前请勿进食、饮水，穿上舒适的宽松衣服。', '35-65岁', '高危人群', '单项检查', '冠状动脉CT检查', 1, 1);
INSERT INTO `pregoodsinfo` VALUES (12, '肝功能五项套餐', '../../static/picture/肝功能五项套餐.jpg', 199.00, '肝功五项包括五个检测指标', '空腹，饮酒和用药会影响检测结果，建议提前两天避免。', '不限', '一般人群', '单项检查', '谷丙转氨酶、谷草转氨酶、总胆红素、直接胆红素、白蛋白', 1, 1);
INSERT INTO `pregoodsinfo` VALUES (13, '血脂全套检查', '../../static/picture/血脂全套检查.jpg', 229.00, '血脂全套检查包括六个指标', '饮食不宜过于油腻，建议提前3-4小时不要进食，但可以喝水。', '不限', '	一般人群', '体检套餐', '总胆固醇、甘油三酯、高密度脂蛋白胆固醇、低密度脂蛋白胆固醇、胆固醇/高密度脂蛋白胆固醇比值', 2, 1);
INSERT INTO `pregoodsinfo` VALUES (26, 'Test1', '/static/picture/Test1.jpg', 232.00, 'test1', 'test1', '10~21', '高危人群', '体检套餐', 'tes', 0, 0);
INSERT INTO `pregoodsinfo` VALUES (31, 'yuanguanqing', '../../static/picture/yuanguanqing.jpg', 232.00, '11', '22', '10~21', '女性', '单项检查', 'ewew', 0, 0);

-- ----------------------------
-- Table structure for prepayinfo
-- ----------------------------
DROP TABLE IF EXISTS `prepayinfo`;
CREATE TABLE `prepayinfo`  (
  `pid` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `gid` int(0) NOT NULL,
  `did` int(0) NULL DEFAULT NULL,
  `prenumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ifok` int(0) NOT NULL DEFAULT 0,
  `pretime` date NOT NULL,
  `starttime` datetime(0) NULL DEFAULT NULL,
  `endtime` datetime(0) NULL DEFAULT NULL,
  `iftijian` int(0) NOT NULL DEFAULT 0,
  `jilu` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `ifbreak` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `prepayinfo_ibfk_2`(`gid`) USING BTREE,
  INDEX `prenumber`(`prenumber`) USING BTREE,
  INDEX `endtime`(`endtime`) USING BTREE,
  INDEX `prepayinfo_ibfk_1`(`uid`) USING BTREE,
  INDEX `prepayinfo_ibfk_3`(`did`) USING BTREE,
  CONSTRAINT `prepayinfo_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `userinfo` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `prepayinfo_ibfk_2` FOREIGN KEY (`gid`) REFERENCES `pregoodsinfo` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `prepayinfo_ibfk_3` FOREIGN KEY (`did`) REFERENCES `doctorinfo` (`did`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prepayinfo
-- ----------------------------
INSERT INTO `prepayinfo` VALUES (1, 2, 6, 1, '95279527', 1, '2023-03-16', '2023-03-16 20:31:42', '2023-03-24 20:33:56', 1, '诊断：1、糖尿病 （1）空腹血糖≥7.0毫摩尔/升 （2）或者餐后2小时血糖≥11.1毫摩尔/升 2、糖耐量减低 （1）空腹血糖＜7.0毫摩尔/升 （2）且餐后2小时血糖≥7.8毫摩尔/升，并＜11.1毫摩尔/升 3、空腹血糖受损 （1）空腹血糖6.1毫摩尔/升~6.9毫摩尔/升 （2。）且餐后2小时血糖＜7.8毫摩尔/升。', '没事问题，多喝热水', 0);
INSERT INTO `prepayinfo` VALUES (2, 20, 11, 1, '95289528', 1, '2023-05-07', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (3, 13, 3, NULL, '95269526', 1, '2023-05-07', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (4, 19, 11, 3, '95259525', 1, '2023-04-03', '2023-04-03 07:18:04', '2023-04-03 21:18:19', 1, '、查体：体温（腋温）36.7℃，脉搏101次/分，呼吸20次/分，血压170', '诊断为冠心病，请立即办理住院手续', 0);
INSERT INTO `prepayinfo` VALUES (5, 2, 26, 6, '95239523', 1, '2023-04-15', '2023-04-15 11:15:04', '2023-04-15 11:15:08', 1, 'test', 'test', 0);
INSERT INTO `prepayinfo` VALUES (12, 2, 1, 1, '53478409', 1, '2023-05-05', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (14, 11, 6, NULL, '81973906', 1, '2023-05-05', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (15, 13, 11, NULL, '10936576', 0, '2023-05-04', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (16, 19, 13, NULL, '36683646', 0, '2023-05-05', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (17, 20, 9, NULL, '74995487', 0, '2023-05-05', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (19, 2, 1, 1, '75287974', 1, '2023-05-03', '2023-05-03 00:12:02', '2023-05-03 00:12:43', 1, '1234', '1234', 0);
INSERT INTO `prepayinfo` VALUES (20, 2, 2, 1, '73087558', 1, '2023-05-03', '2023-05-03 16:46:54', '2023-05-03 16:47:11', 1, '11', '11', 0);
INSERT INTO `prepayinfo` VALUES (21, 2, 7, 1, '55554118', 1, '2023-05-06', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `prepayinfo` VALUES (25, 2, 10, 1, '78999825', 1, '2023-05-03', '2023-05-03 21:23:56', '2023-05-03 21:52:41', 1, '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` enum('admin','doc','user') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '$2a$10$oSq4U/o2Lk5DCageJCBae.wLKu4hXqwCutLJvZI1Qt5AVnU5r5jgq');
INSERT INTO `user` VALUES (2, 'zhangsan', 'user', '$2a$10$WTC.g2T6ccDLmotr9Cn8sehxdZeXBCzyZ9nL5B7Z1bmq5uuFFV/2K');
INSERT INTO `user` VALUES (3, 'zhangsanfeng', 'doc', '$2a$10$oSq4U/o2Lk5DCageJCBae.wLKu4hXqwCutLJvZI1Qt5AVnU5r5jgq');
INSERT INTO `user` VALUES (5, 'root', 'admin', '$2a$10$oSq4U/o2Lk5DCageJCBae.wLKu4hXqwCutLJvZI1Qt5AVnU5r5jgq');
INSERT INTO `user` VALUES (11, 'zhangwuji', 'user', '$2a$10$kDyfFwO6abdVwLUtOq5Qu.slLOmZ3.dGnN95PcgDpJkDUnpDz7nMW');
INSERT INTO `user` VALUES (13, 'wanglaowu', 'user', '$2a$10$Iqhop8Zrtd43mdvcB3PvhOgDR/JXzIguOqYEaPKfGGhFfRl5nf/Rm');
INSERT INTO `user` VALUES (19, 'mike', 'user', '$2a$10$Iqhop8Zrtd43mdvcB3PvhOgDR/JXzIguOqYEaPKfGGhFfRl5nf/Rm');
INSERT INTO `user` VALUES (20, 'yangguo', 'user', '$2a$10$Iqhop8Zrtd43mdvcB3PvhOgDR/JXzIguOqYEaPKfGGhFfRl5nf/Rm');
INSERT INTO `user` VALUES (23, 'zhaoliu', 'doc', '$2a$10$r9x/HAYT/2rlqcddc59y3e9Od7gcxO1b5W4inyXAx7JtGrBbVymR6');
INSERT INTO `user` VALUES (24, 'zhubaba', 'doc', '$2a$10$cUdsh83HQ9pgwGFcrHyzAOkGZHvd4D.PCT.2HQiQSjWHoDW4wzGie');
INSERT INTO `user` VALUES (25, 'lisiming', 'doc', '$2a$10$XOI1C0evtJeJPVQriPEnOebtxAjxdHxum1xXNhzEBOElRNbhBJ82u');
INSERT INTO `user` VALUES (26, 'zhangmao', 'doc', '$2a$10$.3oHSs6MarWMEQOgckt/iuPazM.Q/YeCWm52OgsvdqpuFnS3qvz76');
INSERT INTO `user` VALUES (27, 'qiqi', 'doc', '$2a$10$0OMea/43ou8gMXEDDzmlJ.gL9tN20Rl2EO2kGOuBtXF.no80ODd6a');
INSERT INTO `user` VALUES (29, '123123', 'user', '$2a$10$Iqhop8Zrtd43mdvcB3PvhOgDR/JXzIguOqYEaPKfGGhFfRl5nf/Rm');
INSERT INTO `user` VALUES (30, 'zhang123', 'user', '$2a$10$DP2oFBEGQ0dnkpWr.iLN6O3ogZADwibgWvvBj8ALzVyeF8D6RADOy');
INSERT INTO `user` VALUES (31, 'zhang1234', 'user', '$2a$10$cUX.tT6jE8C36Oo43cLr8.lKUkFfNnEAd76glgaX61zxQoLGm0cG2');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `oid` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NOT NULL,
  `gender` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nocome` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`oid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  CONSTRAINT `userinfo_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES (1, 2, '张无', '3232312@qq.com', '2001-05-04', '男', '13167983332', 0);
INSERT INTO `userinfo` VALUES (2, 11, '张无忌', 'qq2@qq.com', '1976-10-30', '男', '13179072223', 0);
INSERT INTO `userinfo` VALUES (3, 13, '王老五', 'qdsajdksa@qq.com', '1957-02-23', '男', '13179072223', 0);
INSERT INTO `userinfo` VALUES (7, 19, '迈克', 'vossdad@osdoa.com', '1983-12-21', '男', '13399232850', 0);
INSERT INTO `userinfo` VALUES (8, 20, '杨过', 'anranxiaohun@qq.com', '1955-10-25', '男', '15179182381', 0);
INSERT INTO `userinfo` VALUES (9, 29, '你好', 'anranxiaohun@qq.com', '2023-04-22', '女', '13169085551', 0);
INSERT INTO `userinfo` VALUES (10, 30, '张张三', 'anranxiaohun@qq.com', '1999-10-20', '男', '13169085521', 0);
INSERT INTO `userinfo` VALUES (11, 31, '张三三', 'anranxiaohun@qq.com', '2006-06-21', '男', '13169085521', 0);

SET FOREIGN_KEY_CHECKS = 1;
