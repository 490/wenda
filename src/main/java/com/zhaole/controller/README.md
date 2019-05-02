# 注册

@RequestMapping(path={"/reg"},method = {RequestMethod.POST})

Map<String ,Object> map = userService.register(username,password);
register:
```java
LoginTicket ticket = new LoginTicket();
然后设置创建时间、超时什么的，然后插入数据库，返回ticket

map.put("ticket",ticket);

```

map的key包含ticket的话，设置cookie

Cookie cookie = new Cookie("ticket",map.get("ticket").toString());


如果是从别的页面过来的，可以跳回去：
```java
if(StringUtils.isNotBlank(next))
{
    return "redirect:"+next;
}
```
否则跳到主页


map不含ticket（注册失败：用户名密码为空、用户名已经注册）则跳到登录页面。


# 登录

@RequestMapping(path = {"/login/"},method = {RequestMethod.POST})

Map<String ,Object> map = userService.login(username,password);

login：
```java
先判断name pass为不为空
然后User user = userDAO.selectByName(username);
再判断user！=null
判断密码
WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())

String ticket = addLoginTicket(user.getId());
map.put("ticket",ticket);
return map
```
map的key包含ticket的话（登陆成功），设置cookie

校验在passwordInterceptor






# 点赞

@RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
like返回的是redis的scard命令。<like:entitytype:entityid , userid>这个key的数量
long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);

like()里面：
以like为key的list，加东西
同时以dislike为key的list，删东西，（这次怎么不事务了呢？
```java
 public long like(int userId, int entityType, int entityId)
    {
        //like:entitytype:entityid
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //<like:entitytype:entityid , userid>
        //System.out.println("likeKey::"+likeKey+",,,userid:::"+userId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }
```



# 关注

@RequestMapping(path = {"/followUser"},method = {RequestMethod.POST})

followService.follow

用RedisKeyUtil生成key，字符串形式的：`FOLLOWER:entityId:entityType`

设置时间

用Redis事务，关注与被关注是一起的
```java
Transaction tx = jedisAdapter.multi(jedis);
tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
List<Object> ret = jedisAdapter.exec(tx, jedis);
```
multi开启事务执行之后在zsort中返回的是list
(事务块内所有命令的返回值，按命令执行的先后顺序排列。 当操作被打断时，返回空值 nil )



放到消息队列生产者里
eventProducer.fireEvent



# 添加评论

@RequestMapping(path = {"/addComment"})

Comment comment = new Comment();
添加user、date、content等
commentService.addComment(comment);
更新一下这个问题的评论数量
int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
向comment表里插入，此处service有一步字符过滤
```java
public int addComment(Comment comment)
    {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDAO.addComment(comment) > 0 ? comment.getId():0;
    }
```
    
questionService.updateCommentCount(comment.getEntityId(),count);

放到消息队列生产者里
eventProducer.fireEvent









