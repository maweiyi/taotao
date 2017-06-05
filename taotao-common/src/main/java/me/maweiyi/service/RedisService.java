package me.maweiyi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by maweiyi on 6/1/17.
 */
@Service
public class RedisService {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    /*public String set(String key, String value) {

        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.set(key, value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }

        return null;
    }*/

     public String set(final String key, final String value) {
         Function function = new Function<String, ShardedJedis>() {
             @Override
             public String callback(ShardedJedis shardedJedis) {
                 return shardedJedis.set(key, value);
             }
         };
         return (String) execute(function);
     }

    /*public String get(String key) {

        ShardedJedis shardedJedis = null;
        try {

            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                shardedJedis.close();
            }
        }
        return null;

    }*/

    public String get(final String key) {
        Function function = new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        };
        return (String) execute(function);
    }

    public Long delete(final String key) {
        Function function = new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        };
        return (Long) execute(function);
    }

    public Long expire(final String key, final Integer seconds) {
        Function function = new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }
        };
        return (Long) execute(function);
    }

    public Long set(final String key, final String value, final Integer seconds) {
        Function function = new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                shardedJedis.set(key, value);
                return shardedJedis.expire(key, seconds);
            }
        };
        return (Long) execute(function);
    }




    private <T>T execute(Function<T, ShardedJedis> function) {
         ShardedJedis shardedJedis = null;
         try {
             shardedJedis = shardedJedisPool.getResource();
             return function.callback(shardedJedis);
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (null != shardedJedis) {
                 shardedJedis.close();
             }
         }

         return null;
    }
}

