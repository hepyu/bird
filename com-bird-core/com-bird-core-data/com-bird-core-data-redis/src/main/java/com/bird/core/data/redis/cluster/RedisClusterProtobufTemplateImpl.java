package com.bird.core.data.redis.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.bird.core.data.redis.RedisTemplate;
import com.bird.core.data.redis.exception.RedisClientException;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.JedisCluster;

/**
 * redis cluster clients methods
 *
 * @author jiangjun
 * @create 2017/9/15
 */
public class RedisClusterProtobufTemplateImpl implements RedisTemplate {

	private JedisCluster jedisCluster;

	public RedisClusterProtobufTemplateImpl(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public Long setExpires(String key, Integer seconds) throws RedisClientException {
		if (StringUtils.isBlank(key) || seconds <= 0) {
			return 0L;
		}
		Long rows = 0L;
		try {
			rows = jedisCluster.expire(key, seconds);
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setExpires error！error info:" + e.getMessage(), e);
		}
		return rows;
	}

	@Override
	public Boolean isExists(String key) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		Boolean exists = false;
		try {
			exists = jedisCluster.exists(key);
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client isExists error！error info:" + e.getMessage(), e);
		}
		return exists;
	}

	@Override
	public Long delete(String key) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}
		Long rows = 0L;
		try {
			rows = jedisCluster.del(key);
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client delete error！error info:" + e.getMessage(), e);
		}
		return rows;
	}

	@Override
	public Long delete(String key, String... fields) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}
		Long deleteRows = 0L;
		try {
			if (fields == null || fields.length <= 0) {
				deleteRows = jedisCluster.del(key);
			} else {
				List<String> list = Arrays.asList(fields);
				for (String field : list) {
					deleteRows = jedisCluster.hdel(key.getBytes(), field.getBytes());
				}
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client delete error！error info:" + e.getMessage(), e);
		}
		return deleteRows;
	}

	@Override
	public <T> Long setData(String key, T bean, Class<T> clazz, Integer seconds) throws RedisClientException {
		if (StringUtils.isBlank(key) || bean == null || clazz == null) {
			return 0L;
		}
		try {
			Schema schema = RuntimeSchema.createFrom(clazz); // 创建schema
			byte[] bytes = ProtobufIOUtil.toByteArray(bean, schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			// 删除原内容
			String result = jedisCluster.set(key.getBytes(), bytes);
			if (seconds != null && seconds > 0) {
				jedisCluster.expire(key.getBytes(), seconds);
			}
			if (StringUtils.isNoneBlank(result) && result.equalsIgnoreCase("ok")) {
				return 1L;
			} else {
				return 0L;
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setData error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> T getData(String key, Class<T> clazz) throws RedisClientException {
		T bean = null;
		try {
			Schema schema = RuntimeSchema.createFrom(clazz);
			byte[] bytes = jedisCluster.get(key.getBytes());
			if (bytes != null) {
				bean = (T) schema.newMessage();
				ProtobufIOUtil.mergeFrom(bytes, bean, schema);
				return bean;
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client getData error！error info:" + e.getMessage(), e);
		}
		return bean;
	}

	@Override
	public <T> Long setHashData(String key, String filed, T bean, Class<T> clazz) throws RedisClientException {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(filed) || bean == null || clazz == null) {
			return 0L;
		}
		try {
			Schema schema = RuntimeSchema.createFrom(clazz);
			byte[] bytes = ProtobufIOUtil.toByteArray(bean, schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			Long rows = jedisCluster.hset(key.getBytes(), filed.getBytes(), bytes);
			return rows;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setHashData error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> T getHashData(String key, String field, Class<T> clazz) throws RedisClientException {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || clazz == null) {
			return null;
		}
		try {
			T bean = null;
			Schema schema = RuntimeSchema.createFrom(clazz);
			byte[] bytes = jedisCluster.hget(key.getBytes(), field.getBytes());
			if (bytes != null) {
				bean = (T) schema.newMessage();
				ProtobufIOUtil.mergeFrom(bytes, bean, schema);
				return bean;
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client getHashData error！error info:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public <T> Long setHashData(String key, Map<String, T> map, Class<T> clazz, Integer seconds)
			throws RedisClientException {
		if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(map) || clazz == null) {
			return null;
		}
		Schema schema = RuntimeSchema.createFrom(clazz);
		Map<byte[], byte[]> byteMap = new HashMap<>();
		try {
			Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, T> entry = iterator.next();
				String k = entry.getKey();
				T v = entry.getValue();
				byte[] bytes = ProtobufIOUtil.toByteArray(v, schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				byteMap.put(k.getBytes(), bytes);
			}
			String result = jedisCluster.hmset(key.getBytes(), byteMap);
			if (StringUtils.isNoneBlank(result) && result.equalsIgnoreCase("ok")) {
				return 1L;
			} else {
				return 0L;
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setHashData error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> Map<String, T> getHashData(String key, Class<T> clazz) throws RedisClientException {
		if (StringUtils.isBlank(key) || clazz == null) {
			return null;
		}
		Map<String, T> map = new HashMap<>();
		Schema schema = RuntimeSchema.createFrom(clazz);
		try {
			Map<byte[], byte[]> bytes = jedisCluster.hgetAll(key.getBytes());
			Iterator<Map.Entry<byte[], byte[]>> iterator = bytes.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<byte[], byte[]> entry = iterator.next();
				byte[] k = entry.getKey();
				byte[] v = entry.getValue();
				T bean = (T) schema.newMessage();
				ProtobufIOUtil.mergeFrom(v, bean, schema);
				map.put(new String(k, "UTF-8"), bean);
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client getHashData error！error info:" + e.getMessage(), e);
		}
		return map;
	}

	@Override
	public Long removeHashData(String key, Set<String> fieldSet) throws RedisClientException {
		if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(fieldSet)) {
			return 0L;
		}
		byte[] fieldBytes = new byte[fieldSet.size()];
		Set<byte[]> byteSet = new HashSet<>();
		fieldSet.forEach(field -> {
			byteSet.add(field.getBytes());
		});
		List<byte[]> byteList = new ArrayList(byteSet);
		byte[][] bytes = new byte[byteList.size()][];
		for (int i = 0; i < byteList.size(); i++) {
			bytes[i] = byteList.get(i);
		}
		try {
			Long rows = jedisCluster.hdel(key.getBytes(), bytes);
			return rows;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client removeHashData error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> Long setListData(String key, List<T> dataList, Class<T> clazz, Integer seconds)
			throws RedisClientException {
		if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(dataList) || clazz == null) {
			return 0L;
		}
		Schema schema = RuntimeSchema.createFrom(clazz);
		byte[][] bytes = new byte[dataList.size()][];
		try {
			for (int i = 0; i < dataList.size(); i++) {
				byte[] beanBytes = ProtostuffIOUtil.toByteArray(dataList.get(i), schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				bytes[i] = beanBytes;
			}
			Long rows = jedisCluster.rpush(key.getBytes(), bytes);
			return rows;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setListData error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> List<T> getListData(String key, Class<T> clazz) throws RedisClientException {
		if (StringUtils.isBlank(key) || clazz == null) {
			return null;
		}
		List<T> list = new ArrayList<>();
		Schema schema = RuntimeSchema.createFrom(clazz);
		try {
			List<byte[]> bytes = jedisCluster.lrange(key.getBytes(), 0, -1);
			if (!CollectionUtils.isEmpty(bytes)) {
				bytes.forEach(item -> {
					T bean = (T) schema.newMessage();
					ProtobufIOUtil.mergeFrom(item, bean, schema);
					list.add(bean);
				});
				return list;
			}
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client getListData error！error info:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public <T> Long rpush2List(String key, Class<T> clazz, T... beans) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}
		Schema schema = RuntimeSchema.createFrom(clazz);
		try {
			List<T> list = Arrays.asList(beans);
			byte[][] listBytes = new byte[beans.length][];
			for (int i = 0; i < beans.length; i++) {
				byte[] bytes = ProtobufIOUtil.toByteArray(beans[i], schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				listBytes[i] = bytes;
			}
			Long rows = jedisCluster.rpush(key.getBytes(), listBytes);
			return rows;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client rpush2List error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> T rpopFromList(String key, Class<T> clazz) throws RedisClientException {
		if (StringUtils.isBlank(key) || clazz == null) {
			return null;
		}
		Schema schema = RuntimeSchema.createFrom(clazz);
		try {
			byte[] bytes = jedisCluster.rpop(key.getBytes());
			if (bytes != null) {
				T bean = (T) schema.newMessage();
				ProtobufIOUtil.mergeFrom(bytes, bean, schema);
				return bean;
			}
			return null;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client rpopFromList error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> Long lpush2List(String key, Class<T> clazz, T... beans) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}
		Schema schema = RuntimeSchema.createFrom(clazz);
		try {
			List<T> list = Arrays.asList(beans);
			byte[][] listBytes = new byte[beans.length][];
			for (int i = 0; i < beans.length; i++) {
				byte[] bytes = ProtobufIOUtil.toByteArray(beans[i], schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				listBytes[i] = bytes;
			}
			Long rows = jedisCluster.lpush(key.getBytes(), listBytes);
			return rows;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client lpush2List error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public <T> T lpopFromList(String key, Class<T> clazz) throws RedisClientException {
		if (StringUtils.isBlank(key) || clazz == null) {
			return null;
		}
		Schema schema = RuntimeSchema.createFrom(clazz);
		try {
			byte[] bytes = jedisCluster.lpop(key.getBytes());
			if (bytes != null) {
				T bean = (T) schema.newMessage();
				ProtobufIOUtil.mergeFrom(bytes, bean, schema);
				return bean;
			}
			return null;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client lpopFromList error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public Long setCollectionData(String key, HashSet<String> set) throws RedisClientException {
		if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(set)) {
			return 0L;
		}
		String[] arr = new String[set.size()];
		try {
			Long rows = jedisCluster.sadd(key, set.toArray(arr));
			return rows;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setCollectionData error！error info:" + e.getMessage(),
					e);
		}
	}

	@Override
	public Long setCollectionData(String key, String value) throws RedisClientException {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			return 0L;
		}
		try {
			return jedisCluster.sadd(key, value);
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client setCollectionData error！error info:" + e.getMessage(),
					e);

		}
	}

	@Override
	public Set<String> getCollectionData(String key) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		try {
			Set<String> members = jedisCluster.smembers(key);
			return members;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client getCollectionData error！error info:" + e.getMessage(),
					e);
		}
	}

	@Override
	public Long removeFromCollection(String key, Set<String> set) throws RedisClientException {
		if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(set)) {
			return 0L;
		}
		String[] arr = new String[set.size()];
		try {
			Long rows = jedisCluster.srem(key, set.toArray(arr));
			return rows;
		} catch (Exception e) {
			throw new RedisClientException(
					"redisCluster client removeFromCollection error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public Long removeFromCollection(String key, String... values) throws RedisClientException {
		if (StringUtils.isBlank(key) || values.length <= 0) {
			return 0L;
		}
		try {
			Long rows = jedisCluster.srem(key, values);
			return rows;
		} catch (Exception e) {
			throw new RedisClientException(
					"redisCluster client removeFromCollection error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public Boolean isMember(String key, String member) throws RedisClientException {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(member)) {
			return false;
		}
		try {
			Boolean isMember = jedisCluster.sismember(key, member);
			return isMember;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client isMember error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public Boolean isMember(byte[] key, byte[] member) throws RedisClientException {
		if (key == null || member == null) {
			return false;
		}
		try {
			Boolean isMember = jedisCluster.sismember(key, member);
			return isMember;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client isMember error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public Long incr(String key) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}
		try {
			Long incr = jedisCluster.incr(key);
			return incr;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client incr error！error info:" + e.getMessage(), e);
		}
	}

	@Override
	public Long incrBy(String key, long increment) throws RedisClientException {
		if (StringUtils.isBlank(key)) {
			return 0L;
		}
		try {
			Long incr = jedisCluster.incrBy(key, increment);
			return incr;
		} catch (Exception e) {
			throw new RedisClientException("redisCluster client incrBy error！error info:" + e.getMessage(), e);
		}
	}
}
