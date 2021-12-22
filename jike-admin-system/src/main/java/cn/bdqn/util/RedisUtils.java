package cn.bdqn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author SwayJike
 * @date 2021/1/18
 */
@Slf4j
@Component
public final class RedisUtils {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ValueOperations<String, String> valueOperations;

    @Resource
    private HashOperations<String, String, String> hashOperations;

    @Resource
    private ListOperations<String, String> listOperations;

    @Resource
    private SetOperations<String, String> setOperations;

    @Resource
    private ZSetOperations<String, String> zSetOperations;

    //
    // RedisOperations
    //

    /**
     * 判断key是否存在
     *
     * @param key 键 不能为null
     * @return boolean true:存在,false:不存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 设置缓存过期时间(秒)
     *
     * @param key     键 不能为null
     * @param timeout 过期时间
     * @return boolean true:成功,false:失败
     */
    public boolean expire(String key, long timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TimeUnit.SECONDS));
    }

    /**
     * 返回键的剩余有效时间(秒)
     *
     * @param key 键 不能为null
     * @return Long 如果键不存在返回-2,如果键存在且没有设置过期时间返回-1
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除一个键
     *
     * @param key 键 不能为null
     * @return boolean true:成功,false:失败
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除多个键,返回删除键的个数
     *
     * @param keys 键 不能为null
     * @return Long 删除键的个数
     */
    public Long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    //
    // ValueOperations
    //

    /**
     * 缓存键值对
     *
     * @param key   键 不能为null
     * @param value 值 不能为null
     * @return boolean true:成功,false:失败
     */
    public boolean set(String key, String value) {
        return this.set(key, value, 0L);
    }

    /**
     * 缓存键值对
     *
     * @param key   键 不能为null
     * @param value 值 不能为null
     * @return boolean true:成功,false:失败
     */
    public boolean set(String key, Object value) {
        return this.set(key, value, 0L);
    }

    /**
     * 缓存键值对,并设置过期时间
     *
     * @param key     键 不能为null
     * @param value   值 不能为null
     * @param timeout 时间(秒),如果time小于等于0,不设置过期时间
     * @return boolean true:成功,false:失败
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            String val;
            if (value instanceof String) {
                val = value.toString();
            } else {
                val = ObjectMapperUtils.objToJson(value);
            }
            valueOperations.set(key, val);
            if (timeout > 0) {
                this.expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            log.error("调用RedisUtils的set方法(带过期时间)异常", e);
            return false;
        }
    }

    /**
     * 递增1
     *
     * @param key 键 不能为null
     * @return Long 递增后的值
     */
    public Long increment(String key) {
        return this.increment(key, 1L);
    }

    /**
     * 递增指定的数值
     *
     * @param key   键 不能为null
     * @param delta 指定的数值
     * @return Long 递增后的值
     */
    public Long increment(String key, long delta) {
        return valueOperations.increment(key, delta);
    }

    /**
     * 递增1
     *
     * @param key 键 不能为null
     * @return Long 递增后的值
     */
    public Long decrement(String key) {
        return this.decrement(key, 1L);
    }

    /**
     * 递减指定的数值
     *
     * @param key   键 不能为null
     * @param delta 指定的数值
     * @return Long 递减后的值
     */
    public Long decrement(String key, long delta) {
        return valueOperations.decrement(key, delta);
    }

    /**
     * 根据键获取对应的值
     *
     * @param key 键 不能为null
     * @return String 值
     */
    public String get(String key) {
        return valueOperations.get(key);
    }

    /**
     * 根据键获取对应的值
     *
     * @param key   键 不能为null
     * @param clazz 类
     * @return <T> T 返回指定类
     */
    public <T> T get(String key, Class<T> clazz) {
        return ObjectMapperUtils.jsonToObj(this.get(key), clazz);
    }

    //
    // HashOperations
    //

    /**
     * 向hash表中存放一个键值对数据,如果不存在将创建
     *
     * @param key     键 不能为null
     * @param hashKey hash键 不能为null
     * @param value   值
     * @return boolean true:成功,false:失败
     */
    public boolean hPut(String key, String hashKey, String value) {
        return this.hPut(key, hashKey, value, 0L);
    }

    /**
     * 向hash表中存放一个键值对数据,如果不存在将创建
     *
     * @param key     键 不能为null
     * @param hashKey hash键 不能为null
     * @param value   值
     * @return boolean true:成功,false:失败
     */
    public boolean hPut(String key, String hashKey, Object value) {
        return this.hPut(key, hashKey, value, 0L);
    }

    /**
     * 向hash表中存放一个键值对数据,如果不存在将创建,带过期时间(秒),过期是整个key都过期
     *
     * @param key     键 不能为null
     * @param hashKey hash键
     * @param value   值
     * @param timeout 过期时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return boolean true:成功,false:失败
     */
    public boolean hPut(String key, String hashKey, Object value, long timeout) {
        try {
            String val;
            if (value instanceof String) {
                val = value.toString();
            } else {
                val = ObjectMapperUtils.objToJson(value);
            }
            hashOperations.put(key, hashKey, val);
            if (timeout > 0) {
                this.expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            log.error("调用RedisUtils的hPut方法(带过期时间)异常", e);
            return false;
        }
    }

    /**
     * 向hash表中存放多个键值对数据
     *
     * @param key 键 不能为null
     * @param map 多个键值对
     * @return boolean true:成功,false:失败
     */
    public boolean hPutAll(String key, Map<String, String> map) {
        return this.hPutAll(key, map, 0L);
    }

    /**
     * 向hash表中存放多个键值对数据,带过期时间(秒),过期是整个key都过期
     *
     * @param key     键 不能为null
     * @param map     对应多个键值
     * @param timeout 过期时间(秒)
     * @return boolean true:成功,false:失败
     */
    public boolean hPutAll(String key, Map<String, String> map, long timeout) {
        try {
            hashOperations.putAll(key, map);
            if (timeout > 0) {
                this.expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            log.error("调用RedisUtils的hPutAll方法(带过期时间)异常", e);
            return false;
        }
    }

    /**
     * 获取hashKey对应的键值
     *
     * @param key     键 不能为null
     * @param hashKey hash键 不能为null
     * @return String hashKey对应的键值
     */
    public String hget(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }


    /**
     * 获取hashKey对应的键值
     *
     * @param key     键 不能为null
     * @param hashKey hash键 不能为null
     * @param clazz   类
     * @return <T> T 返回指定类
     */
    public <T> T hget(String key, String hashKey, Class<T> clazz) {
        return ObjectMapperUtils.jsonToObj(this.hget(key, hashKey), clazz);
    }

    /**
     * 判断hash表中是否存在hashKey对应的值
     *
     * @param key     键 不能为null
     * @param hashKey hash键 不能为null
     * @return boolean true存在 false不存在
     */
    public boolean hHasKey(String key, String hashKey) {
        return Boolean.TRUE.equals(hashOperations.hasKey(key, hashKey));
    }

    /**
     * 获取key对应hash表的所有hashKey列表
     *
     * @param key 键 不能为null
     * @return Set<String> hashKey列表
     */
    public Set<String> hKeys(String key) {
        return hashOperations.keys(key);
    }

    /**
     * 获取key对应的所有键值
     *
     * @param key 键 不能为null
     * @return Map<String, String> key对应的所有键值
     */
    public Map<String, String> hEntries(String key) {
        return hashOperations.entries(key);
    }

    /**
     * 删除hash表中的值
     *
     * @param key     键 不能为null
     * @param hashKey hash键 可以是多个,不能为null
     * @return Long 删除元素的数量
     */
    public Long hDelete(String key, String... hashKey) {
        return hashOperations.delete(key, hashKey);
    }

    /**
     * 一次获取 hash 中多个 hashKey 的值,按 hashKey 顺序返回值
     * 如传入 hashKeys = [f1, f2, f3, f4],其中 f3 在 hash 表中无值
     * 则返回 [f1v, f2v, f3v, null, f4v] v 表示 value
     *
     * @param key  键 不能为null
     * @param keys hash键列表,不能为null
     * @return List<String> hash表中多个键对应的值
     */
    public List<String> hMultiGet(String key, List<String> keys) {
        return hashOperations.multiGet(key, keys);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key     键 不能为null
     * @param hashKey hash键
     * @param delta   递增值
     * @return Double 递增后的值
     */
    public Double hIncrement(String key, String hashKey, double delta) {
        return hashOperations.increment(key, hashKey, delta);
    }

    //
    // ListOperations
    //

    /**
     * 在列表中设置索引index处的元素为value
     *
     * @param key   键 不能为null
     * @param index 索引
     * @param value 值
     * @return boolean true成功,false失败
     */
    public boolean lSet(String key, long index, String value) {
        return this.lSet(key, index, value, 0L);
    }

    /**
     * 在列表中设置索引index处的元素为value,带过期时间(秒),过期是整个key都过期
     * 当索引参数超出范围,或对一个空列表进行 LSET 时,返回错误ERR no such key
     *
     * @param key     键 不能为null
     * @param index   索引
     * @param value   值
     * @param timeout 过期时间(秒)
     * @return boolean true成功,false失败
     */
    public boolean lSet(String key, long index, String value, long timeout) {
        try {
            String val = this.lIndex(key, index);
            if (null == val) {
                this.lRightPush(key, value);
            } else {
                listOperations.set(key, index, value);
            }
            if (timeout > 0) {
                this.expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            log.error("调用RedisUtils的lSet方法(带过期时间)异常", e);
            return false;
        }
    }

    /**
     * 将value放入list缓存
     *
     * @param key   键 不能为null
     * @param value 值
     * @return Long 添加元素后列表元素的总个数
     */
    public Long lRightPush(String key, String value) {
        return this.lRightPush(key, value, 0L);
    }

    /**
     * 将value放入list缓存,带过期时间(秒),过期是整个key都过期
     *
     * @param key     键 不能为null
     * @param value   值
     * @param timeout 过期时间(秒)
     * @return Long 添加元素后列表元素的总个数
     */
    public Long lRightPush(String key, String value, long timeout) {
        Long count = listOperations.rightPush(key, value);
        if (timeout > 0) {
            this.expire(key, timeout);
        }
        return count;
    }

    /**
     * 将value列表放入缓存
     *
     * @param key   键 不能为null
     * @param value 列表
     * @return Long 添加元素后列表元素的总个数
     */
    public Long lRightPushAll(String key, List<String> value) {
        return this.lRightPushAll(key, value, 0L);
    }

    /**
     * 将value列表放入缓存,带过期时间(秒),过期是整个key都过期
     *
     * @param key     键 不能为null
     * @param value   列表
     * @param timeout 过期时间(秒)
     * @return Long 添加元素后列表元素的总个数
     */
    public Long lRightPushAll(String key, List<String> value, long timeout) {
        Long count = listOperations.rightPushAll(key, value);
        if (timeout > 0) {
            this.expire(key, timeout);
        }
        return count;
    }

    /**
     * 从list中移除前count个值为value的元素
     *
     * @param key   键 不能为null
     * @param count 移除多少个
     * @param value 值
     * @return Long 移除元素的个数
     */
    public Long lRemove(String key, long count, String value) {
        return listOperations.remove(key, count, value);
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key   键 不能为null
     * @param index 索引 index>=0时,0表头,1第二个元素,依次类推;index<0时,-1表尾,-2倒数第二个元素,依次类推
     * @return String 值
     */
    public String lIndex(String key, long index) {
        return listOperations.index(key, index);
    }

    /**
     * 获取list中所有元素
     *
     * @param key 键 不能为null
     * @return List<String> list列表
     */
    public List<String> lRange(String key) {
        return listOperations.range(key, 0L, -1L);
    }

    /**
     * 获取list中从start到end的元素
     *
     * @param key   键 不能为null
     * @param start 开始
     * @param end   结束(0 或 -1 代表所有值)
     * @return List<String> list列表
     */
    public List<String> lRange(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    /**
     * 获取list列表的长度
     *
     * @param key 键 不能为null
     * @return Long list缓存的长度
     */
    public Long lSize(String key) {
        return listOperations.size(key);
    }

    //
    // SetOperations
    //

    /**
     * 将数据放入set
     *
     * @param key    键 不能为null
     * @param values 值 可以是多个
     * @return Long 成功个数
     */
    public Long sAdd(String key, String... values) {
        return setOperations.add(key, values);
    }

    /**
     * 将set数据放入缓存,带过期时间(秒),过期是整个key都过期
     *
     * @param key     键 不能为null
     * @param timeout 过期时间(秒)
     * @param values  值 可以是多个
     * @return Long 成功个数
     */
    public Long sAdd(String key, long timeout, String... values) {
        Long count = setOperations.add(key, values);
        if (timeout > 0) {
            this.expire(key, timeout);
        }
        return count;
    }

    /**
     * 移除值为value的值
     *
     * @param key    键 不能为null
     * @param values 值 可以是多个
     * @return Long 移除的个数
     */

    public Long sRemove(String key, String... values) {
        return setOperations.remove(key, values);
    }

    /**
     * set中查询指定value是否存在
     *
     * @param key   键 不能为null
     * @param value 值
     * @return boolean true存在 false不存在
     */
    public boolean sIsMember(String key, String value) {
        return Boolean.TRUE.equals(setOperations.isMember(key, value));
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键 不能为null
     * @return Set<String> Set集合
     */
    public Set<String> sMembers(String key) {
        return setOperations.members(key);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键 不能为null
     * @return Long set缓存的长度
     */
    public Long sSize(String key) {
        return setOperations.size(key);
    }

    //
    // ZSetOperations
    //

    /**
     * 将value添加到zset中,如果已存在则更新其score
     *
     * @param key    键 不能为null
     * @param value  值
     * @param scoure 分数
     * @return boolean true:成功,false:失败,更新时也是返回false
     */
    public boolean zAdd(String key, String value, double scoure) {
        return Boolean.TRUE.equals(zSetOperations.add(key, value, scoure));
    }

    /**
     * 从zset中删除values,返回已删除元素的数量
     *
     * @param key    键 不能为null
     * @param values string可变参数 不能为null
     * @return Long 已删除元素的数量
     */
    public Long zRemove(String key, String... values) {
        return zSetOperations.remove(key, values);
    }

    /**
     * 获取zset中元素的数量
     *
     * @param key 键 不能为null
     * @return Long 元素的数量
     */
    public Long zCard(String key) {
        return zSetOperations.zCard(key);
    }

    /**
     * 从zset的key中获得value元素的分数
     *
     * @param key   键 不能为null
     * @param value 值
     * @return Double 分数
     */
    public Double zScore(String key, String value) {
        return zSetOperations.score(key, value);
    }

    /**
     * 从zset中获取得分在min和max之间的元素
     *
     * @param key 键 不能为null
     * @param min 最小分数
     * @param max 最大分数
     * @return Set<String> 列表
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        return zSetOperations.rangeByScore(key, min, max);
    }

    /**
     * 获取zset中所有的元素
     *
     * @param key 键 不能为null
     * @return Set<String> 列表
     */
    public Set<String> zRange(String key) {
        return this.zRange(key, 0L, -1L);
    }

    /**
     * 获取zset中从start到end的元素
     *
     * @param key   键 不能为null
     * @param start 开始
     * @param end   结束(0 或 -1 代表所有值)
     * @return Set<String> 列表
     */
    public Set<String> zRange(String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

}
