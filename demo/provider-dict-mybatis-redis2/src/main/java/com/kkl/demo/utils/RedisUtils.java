package com.kkl.demo.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.kkl.demo.provider.user.config.GsonRedisSerializer;
import com.kkl.demo.provider.user.config.RedisConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Configurable
public class RedisUtils {

    private static Logger log = LoggerFactory.getLogger(RedisUtils.class);
    private static Gson gson = new Gson();
    @Value("${spring.redis.database}")
    private int database;

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public GsonRedisSerializer gsonRedisSerializer;



    // ================================
    // 以下对普通key-value操作
    // ================================

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public Boolean set(final String key, Object value, long expireSeconds) {
        return set(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, value, expireSeconds);
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @param dbType
     * @param expireSeconds 过期时间（单位:秒）
     * @return
     */
    public Boolean set(final RedisConstant.RedisDBType dbType, final String key, Object value, long expireSeconds) {
        if(value == null) return true;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        final byte[] bvalue = gsonRedisSerializer.serialize(value);
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                try {
                    connection.set(bkey, bvalue);
                    if (expireSeconds > 0) {
                        connection.expire(key.getBytes("utf-8"), expireSeconds);
                    }
                    return 1L;
                } catch (UnsupportedEncodingException e) {
                    log.error("redis操作异常", e);
                    return -1L;
                }
            }
        }).equals(1L);
    }

    /**
     * 写入缓存,并设置过期时间（原子性(atomic)操作）
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param key
     * @param value
     * @return
     */
    public Boolean setEX(final String key, Object value, long expireSeconds) {
        return setEX(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, value, expireSeconds);
    }

    /**
     * 写入缓存,并设置过期时间（原子性(atomic)操作）
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param key
     * @param value
     * @param dbType
     * @param expireSeconds 过期时间（单位:秒）<=0,默认设置为30天
     * @return
     */
    public Boolean setEX(final RedisConstant.RedisDBType dbType,final String key, Object value, long expireSeconds) {
        if(key == null || key.length() == 0 || value == null) return true;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        final byte[] bvalue = gsonRedisSerializer.serialize(value);
        Long result =  (Long)redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                try {
                    connection.setEx(bkey,expireSeconds<=0?30*24*3600:expireSeconds, bvalue);
                    return 1L;
                } catch (Exception e) {
                    log.error("redis操作异常", e);
                    return -1L;
                }
            }
        });
        return result.equals(1L);
    }

    /**
     * 写入缓存,是『SET if Not eXists』(如果不存在，则 SET)的简写。
     * key不存在，写入，返回true
     * key存在，不覆盖，返回false
     * @param key
     * @param value
     * @return
     */
    public Boolean setNX(final String key, Object value, long expireSeconds) {
        return setNX(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, value, expireSeconds);
    }

    /**
     * 写入缓存,是『SET if Not eXists』(如果不存在，则 SET)的简写。
     * key不存在，写入，返回true
     * key存在，不覆盖，返回false
     * @param key
     * @param value
     * @param dbType
     * @param expireSeconds 过期时间（单位:秒）
     * @return
     */
    public Boolean setNX(final RedisConstant.RedisDBType dbType,final String key, Object value, long expireSeconds) {
        if(key == null || key.length() == 0 || value == null) return false;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        final byte[] bvalue = gsonRedisSerializer.serialize(value);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                Boolean lock = false;
                try {
                    lock = connection.setNX(bkey, bvalue);

                    if (lock && expireSeconds > 0) {
                        connection.expire(bkey, expireSeconds);
                    }
                    return lock;
                } catch (Exception e) {
                    log.error("redis操作异常", e);
                   
                    //setnx成功，但设置过期时间异常
                    if (lock && bkey != null){
                        connection.del(bkey);
                    }
                    return false;
                }
            }
        });
    }
    /**
     * 读取缓存
     * @param key
     * @return
     * @Sample:
     * Dict dict = (Dict)get("key",Dict.class)
     */
    public Object get(final String key,Class clazz) {
        return get(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, clazz);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     * @Sample:
     * Dict dict = (Dict)get("key",Dict.class)
     */
    public Object get(final RedisConstant.RedisDBType dbType,final String key,Class clazz){
        //System.out.println(redisTemplate.toString());
        if(key == null || key.length() == 0) return null;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = (byte[])redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                //System.out.println(connection.toString());
                try {
                    connection.select(dbType.ordinal());
                    return connection.get(bkey);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return new byte[0];
            }
        });
        if(bytes == null || bytes.length==0) return null;
//        if(clazz == String.class){
//            return new String(bytes);
//        }
        return gson.fromJson(new String(bytes),clazz);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     * @Sample:
     * Dict dict = (Dict)get("key",Dict.class)
     */
    public Object getString(final RedisConstant.RedisDBType dbType,final String key,Class clazz){
        //System.out.println(redisTemplate.toString());
        if(key == null || key.length() == 0) return null;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = (byte[])redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                //System.out.println(connection.toString());
                try {
                    connection.select(dbType.ordinal());
                    return connection.get(bkey);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return new byte[0];
            }
        });
        if(bytes == null || bytes.length==0) return null;
        return new String(bytes);
    }

    /**
     * 在Redis键中设置指定的字符串值，并返回其旧值
     * @param key
     * @return
     * @Sample:
     * Long time = (Long)getSet("key",10000)
     */
    public Object getSet(final String key,Object newValue) {
        return getSet(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, newValue);
    }

    /**
     * 在Redis键中设置指定的字符串值，并返回其旧值
     * @param key
     * @return
     * @Sample:
     */
    public Object getSet(final RedisConstant.RedisDBType dbType,final String key,Object newValue){
        if(key == null || key.length() == 0) return null;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        final byte[] bvalue = gsonRedisSerializer.serialize(newValue);
        byte[] bytes = (byte[]) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    connection.select(dbType.ordinal());
                    return connection.getSet(bkey,bvalue);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return new byte[0];
            }
        });
        if(bytes == null || bytes.length==0) return null;
        if(newValue instanceof String){
            return new String(bytes);
        }
        return gson.fromJson(new String(bytes),newValue.getClass());
    }

    /**
     * 读取缓存,value存储对象为List<T>的json格式（使用google.gson序列化）
     * @param key
     * @param type  反序列化后的数据类型
     * @param <T>
     * @return
     * @Sample
     *  List<Dict> rlist = redisUtils.getList(1,"dicts",Dict[].class);
     */
    public <T> List<T> getList(final String key,Class<T[]> type){
        return getList(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, type);
    }

    /**
     * 读取缓存,value存储对象为List<T>的json格式（使用google.gson序列化）
     * @param dbType
     * @param key
     * @param type  反序列化后的数据类型
     * @param <T>
     * @return
     * @Sample
     *  List<Dict> rlist = redisUtils.getList(1,"dicts",Dict[].class);
     */
    public <T> List<T> getList(final RedisConstant.RedisDBType dbType,final String key,Class<T[]> type){
        if(key == null || key.length() == 0) return Lists.newArrayList();
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = (byte[])redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    connection.select(dbType.ordinal());
                    return connection.get(bkey);
                } catch (Exception e) {
                    log.error("redis操作异常", e);
                    
                }
                return Lists.newArrayList();
            }
        });
        if(bytes==null || bytes.length==0) return  null;
        T[] arr = null;
        try {
            arr = gson.fromJson(new String(bytes, "UTF-8"),type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList(arr));
    }


    /**
     * 删除对应的value
     * @param key
     */
    public Boolean remove(final String key) {
        return remove(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key);
//        if (exists(key)) {
//            redisTemplate.delete(key);
//        }
    }

    /**
     * 删除对应的value
     * @param key
     */
    public Boolean remove(final RedisConstant.RedisDBType dbType, final String key) {
        if(key == null || key.length() == 0) return false;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {

                long cnt = 0;
                try {
                    connection.select(dbType.ordinal());
                    cnt =  connection.del(bkey);
//                    if(cnt == 0){
//                        try {
//                            LogUtils.saveLog("redis操作失败", "remove", key, null, null);
//                        }catch (Exception e1){}
//                    }
                } catch (Exception e) {
                    log.error("redis操作异常", e);
                    
                }
                return cnt>0;
            }
        });
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    public Boolean remove(final String... keys) {
        return remove(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, keys);
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    public Boolean remove(final RedisConstant.RedisDBType dbType,final String... keys) {
        if(keys==null || keys.length==0) return false;
        int vsize = keys.length;
        final byte[][] bkeys = new byte[vsize][];
        for (int i = 0; i < vsize; i++) {
            bkeys[i] = keys[i].getBytes(StandardCharsets.UTF_8);
        }
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                long cnt = 0;
                try {
                    connection.select(dbType.ordinal());
                    cnt =  connection.del(bkeys);
                } catch (Exception e) {
                    log.error("redis操作异常", e);
                    
                }
                return cnt>0;
            }
        });
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public Boolean removePattern(final String pattern) {
        return removePattern(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, pattern);
    }

    /**
     * 批量删除key
     * @param dbType
     * @param pattern
     */
    public Boolean removePattern(final RedisConstant.RedisDBType dbType, final String pattern) {
        if(pattern == null || pattern.length() == 0) return false;
        final byte[] bkey = pattern.getBytes(StandardCharsets.UTF_8);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                long cnt = 0;
                try {
                    Set<byte[]> keys = connection.keys(bkey);
                    if(keys.size()==0){
                        return true;
                    }
                    byte[][] bkeys = keys.toArray(new byte[keys.size()][]);
                    cnt = connection.del(bkeys);

                } catch (Exception e) {
                    log.error("redis操作异常", e);
                    
                }
                return cnt>0;
            }
        });
//        Set<Serializable> keys = redisTemplate.keys(pattern);
//        if (keys.size() > 0)
//            redisTemplate.delete(keys);
    }

    /**
     * 返回缓存所有符合条件的key集合
     * @param dbType
     * @param pattern 筛选条件
     * @return
     */
    public Set<byte[]> keys(final RedisConstant.RedisDBType dbType, final String pattern){
        if(pattern == null || pattern.length() == 0) return Sets.newHashSet();
        final byte[] bkey = pattern.getBytes(StandardCharsets.UTF_8);
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    connection.select(dbType.ordinal());
                    return connection.keys(bkey);
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                    return null;
                }
            }
        });
        if(result == null){
            return Sets.newHashSet();
        }else{
            return (Set<byte[]>)result;
        }
    }

    /**
     * 获取过期时间(秒)
     *
     * @param key
     * @return
     */
    public long ttl(final String key){
        return ttl(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key);
    }

    /**
     * 获取过期时间(秒)
     *
     * @param key
     * @return
     */
    public long ttl(final RedisConstant.RedisDBType dbType,final String key){
        if(key == null || key.length() == 0) return 0l;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (long) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                return connection.ttl(bkey);
            }
        });
    }

    /**
     * 获取过期时间(毫秒)
     *
     * @param key
     * @return
     */
    public long pTtl(final String key){
        return pTtl(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key);
    }

    /**
     * 获取过期时间(毫秒)
     *
     * @param key
     * @return
     */
    public long pTtl(final RedisConstant.RedisDBType dbType,final String key){
        if(key == null || key.length() == 0) return 0l;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (long) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                return connection.pTtl(bkey);
            }
        });
    }

    /**
     * 设置key过期时间
     *
     * @param key
     * @param seconds  单位:秒
     * @return 
     */
    public Boolean expire(final String key,final long seconds) {
        return expire(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, seconds);
    }

    /**
     * 设置key过期时间
     *
     * @param key
     * @param seconds  单位:秒
     * @return 
     */
    public Boolean expire(final RedisConstant.RedisDBType dbType, final String key, final long seconds) {
        if(key == null || key.length() == 0 || seconds<=0) return false;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                return connection.expire(bkey,seconds);
            }
        });
    }

    /**
     * 设置key在某时间过期
     *
     * @param key
     * @param timeStamp  所在时间(TIME_IN_UNIX_TIMESTAMP)
     * @return 
     */
    public Boolean expireAt(final String key,final long timeStamp) {
        return expireAt(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, timeStamp);
    }

    /**
     * 设置key在某时间过期
     * @param dbType database
     * @param key
     * @param timeStamp  所在时间(TIME_IN_UNIX_TIMESTAMP)
     * @return 
     */
    public Boolean expireAt(final RedisConstant.RedisDBType dbType, final String key, final long timeStamp) {
        if(key == null || key.length() == 0 || timeStamp <=0) return false;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                return connection.expireAt(bkey,timeStamp);
            }
        });
    }

    /**
     * 判断缓存中是否有对应的key
     * @param key
     * @return
     */
    public Boolean exists(final String key) {
        return exists(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key);
    }
    /**
     * 判断缓存中是否有对应的key
     * @param dbType
     * @param key
     * @return
     */
    public Boolean exists(final RedisConstant.RedisDBType dbType, final String key) {
        if(key == null || key.length() == 0) return false;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                return connection.exists(bkey);
            }
        });
//        return redisTemplate.hasKey(key);
    }

    /**
     *
     * 插入时取得某张表的自增id值
     *
     * @param key
     *            示例:db.{tableName}.id
     * @return 大于0时表示正常 -1 表示key必须填写
     */
    public long incr(final String key) {
        return incr(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key);
    }

    /**
     *
     * 插入时取得某张表的自增id值
     *
     * @param dbType 数据库下标
     * @param key 示例:db.{tableName}.id
     * @return 大于0时表示正常 -1 表示key必须填写
     */
    @SuppressWarnings("unchecked")
    public long incr(final RedisConstant.RedisDBType dbType, final String key) {
        if (key == null || key.length() == 0) {
            return -1l;
        }
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        return (Long) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.select(dbType.ordinal());
                long id = 0;
                try {
                    id = connection.incr(bkey);
                } catch (Exception e) {
                    log.error("redis操作异常", e);
                   
                }
                return id;
            }
        });
    }


    // ---------- //
    //  哈希
    // ---------- //
    /**
     * 判断哈希中是否有指定的字段
     * @param key
     * @param field
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean hexist(final String key, final String field) {
        return hexist(RedisConstant.RedisDBType.REDIS_CONSTANTS_DB, key, field);
    }

    /**
     * 判断哈希中是否有指定的字段
     * @param key
     * @param field
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean hexist(final RedisConstant.RedisDBType dbType,final String key, final String field) {
        if(key == null || key.length() == 0) return false;
        final byte[] bkey = key.getBytes(StandardCharsets.UTF_8);
        final byte[] bfield = field.getBytes(StandardCharsets.UTF_8);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    connection.select(dbType.ordinal());
                    return connection.hExists(bkey, bfield);
                }catch (Exception e) {
                    log.error("redis操作异常", e);
                    
                    return false;
                }
            }
        });
    }

    /**
     * 将byte数组反序列化为对象
     */
    public Function<byte[], Object> byteDeserialObject = new Function<byte[], Object>() {
        public Object apply(byte[] input) {
            return (Object)gsonRedisSerializer.deserialize(input,Object.class);
        }
    };

    public Function<Object,byte[]> serialObjectToByte = new Function<Object,byte[]>(){
        public byte[] apply(Object input) {
            return gsonRedisSerializer.serialize(input);
        }
    };

}
