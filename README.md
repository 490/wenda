# 基于Spring Boot的问答网站搭建

## 功能：
登陆注册，增删改查，提问，评论，敏感词过滤，站内信，赞踩，新鲜事，邮件通知，solr搜索等<br>

## 技术：
  1. 基于Redis的缓存 <br>
  2. 实现前缀树算法的敏感词过滤 <br>
  3. 基于RabbitMQ实现异步框架的设计，使用异步队列实现站内信提醒等功能 <br>
  4. 利用Solr结合IKAnalyzer自定义中文分词，实现搜索引擎 <br>
  5. 实现推拉模式结合的时间轴，渲染新鲜事，进行内容的推送<br>
  6. 使用Guava RateLimiter通过AOP方式进行接口限流，防止恶意攻击<br>
  7. 使用Druid作为数据库连接池，防SQL注入攻击，隐藏配置文件密码等<br>
  8. 使用JMeter作为压测工具，辅助系统性能的调优<br>
  9. 使用PySpider爬虫工具爬取知乎热门榜，填充系统内容<br>
  
## 环境：
  1. 框架：SpringBoot+ Mybatis<br>
  2. 存储：MySQL<br>
  3. 缓存：Redis<br>
  4. 模板引擎：Freemarker<br>
  5. 搜索引擎：Solr<br>
  6. 消息队列：RabbitMQ<br>
  7. 数据库连接池：Druid<br>
  8. 压测工具：JMeter
  