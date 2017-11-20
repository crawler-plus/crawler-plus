ALTER TABLE `bond_market`
ADD COLUMN `adjunct_url`  varchar(255) NULL COMMENT 'pdf下载url字符串' AFTER `create_time`;