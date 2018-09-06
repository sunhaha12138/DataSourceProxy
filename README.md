实例初期比较简单，直接运行DateSourceTest的main方法可以运行。

用到的数据表结构如下：

CREATE TABLE `people` (

    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  
    `name` varchar(10) NOT NULL DEFAULT '' COMMENT '姓名',
  
    `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
  
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
    PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
