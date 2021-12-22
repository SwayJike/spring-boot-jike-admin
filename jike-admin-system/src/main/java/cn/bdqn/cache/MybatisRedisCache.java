package cn.bdqn.cache;

import cn.bdqn.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用redis实现mybatis二级缓存, 已测试成功...
 */
@Slf4j
public class MybatisRedisCache implements Cache {

     //读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private RedisTemplate<String, Object> redisTemplate;

    private String id;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.info("Redis Cache id " + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null) {
            log.info("向Redis中添加数据，有效时间是2天. key = {}", key.toString());
             //向Redis中添加数据，有效时间是2天
            getRedisTemplate().opsForValue().set(key.toString(), value, 2, TimeUnit.DAYS);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            if (key != null) {
                log.info("从Redis中获取数据. key = {}", key.toString());
                Object obj = getRedisTemplate().opsForValue().get(key.toString());
                return obj;
            }
        } catch (Exception e) {
            log.error("redis ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try {
            if (key != null) {
                log.info("从Redis中获取数据. key = {}", key.toString());
                getRedisTemplate().delete(key.toString());
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void clear() {
        log.info("清空缓存");
        try {
            Set<String> keys = getRedisTemplate().keys("*:" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                getRedisTemplate().delete(keys);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getSize() {
        Long size = (Long) getRedisTemplate().execute((RedisCallback<Long>) connection -> connection.dbSize());
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public RedisTemplate getRedisTemplate(){
        if (redisTemplate == null) {
            log.info("第一次初始化 cache ===> redisTemplate");
            redisTemplate = SpringContextHolder.getBean("redisTemplate");
            SpringContextHolder.cleanApplicationContext();
        }
        return redisTemplate;
    }
}
