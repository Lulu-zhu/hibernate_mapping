hibernate基本映射
实体类---表
实体中的普通属性----表字段
采用 <class>标签映射成数据库表，通过<properties>标签将普通属性映射成表字段
所谓普通属性不包括自定义类，集合和数组

实体类设计的四个原则
无参数构造器
标识属性
使用非final类
为持久化字段声明访问器 accessors


many2one多对一关联映射
关联映射本质
将关联关系映射到数据库，所谓关联关系是对象模型中的一个或多个引用

在多的一段加入一个外键指向一的一端，这个外键是由many-to-one中的column属性定义的
如果忽略了这个属性，那么这个属性是与实体中的属性一致的 

加字段

理解级联
是对象的连锁操作

一对一主键关联映射
让两个实体对象的id保持相同，避免创建多余的字段
具体映射
<id name="id"> 
<!--共享主键-->
<generator class="foreign">
	<param name ="property">idCard</param>
	</generator>
	</id>
	<property name="name"/>
<one-to-one name="idCard" constrained="true"/>
idCard。hbm。xml

<one-to-one name="Person" constrained="true"/>
Person.hbm.xml
双向关联映射 默认根据主键加载
抓取策略 fetch

一对一单向实际上是多对一的unique特例
一对一双向
<one-to-one name ="person" property-ref="idCard"/>


session flush测试
在commit之前执行session flush方法
主要做了两件事
清理缓存 
执行sql

sesison在什么情况下执行flush
默认在事务提交时
显示调用flush
在执行查询前，如iterate committed

hibernate中是按照insert update delete的顺序执行sql
显示用flush调整sql语句执行顺序