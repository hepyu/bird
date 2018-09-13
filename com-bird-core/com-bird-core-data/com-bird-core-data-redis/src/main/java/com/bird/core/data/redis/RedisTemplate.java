package com.bird.core.data.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bird.core.data.redis.exception.RedisClientException;

public interface RedisTemplate {

	/**
	 * 设置过期时间
	 *
	 * @param key：设置对应过期时间的key
	 * @param seconds：过期时间。单位：秒
	 * @return
	 * @throws RedisClientException
	 */
	Long setExpires(String key, Integer seconds) throws RedisClientException;

	/**
	 * 检查key是否存在
	 *
	 * @param key
	 * @return
	 * @throws RedisClientException
	 */
	Boolean isExists(String key) throws RedisClientException;

	/**
	 * 删除指定key
	 *
	 * @param key
	 * @return
	 * @throws RedisClientException
	 */
	Long delete(String key) throws RedisClientException;

	/**
	 * 删除指定key
	 *
	 * @param key
	 * @return
	 * @throws RedisClientException
	 */
	Long delete(String key, String... fields) throws RedisClientException;

	/**
	 * 保存key-value数据
	 *
	 * @param key：保存的key
	 * @param bean：保存的对象
	 * @param clazz：保存对象对应的class类型。如果保存字符串，则为String
	 * @param seconds：过期时间。单位：秒；-1：表示永不过期
	 * @return
	 * @throws RedisClientException
	 */
	<T> Long setData(String key, T bean, Class<T> clazz, Integer seconds) throws RedisClientException;

	/**
	 * 根据key获取数据
	 *
	 * @param key：获取数据对应的key
	 * @param clazz：获取数据对应对应的class类型。如果获取类型是字符串，则为String
	 * @param <T>
	 * @return
	 * @throws RedisClientException
	 */
	<T> T getData(String key, Class<T> clazz) throws RedisClientException;

	/**
	 * 保存hash single data
	 *
	 * @param key：保存hash对应对应的key
	 * @param filed：保存对应的field
	 * @param bean：保存的对象
	 * @param clazz：保存对象对应的class类型
	 * @return
	 * @throws RedisClientException
	 */
	<T> Long setHashData(String key, String filed, T bean, Class<T> clazz) throws RedisClientException;

	/**
	 * 根据key获取hash data
	 *
	 * @param key：获取数据对应的key
	 * @param field：获取hash数据的field
	 * @param clazz：获取数据的class类型
	 * @return
	 * @throws RedisClientException
	 */
	<T> T getHashData(String key, String field, Class<T> clazz) throws RedisClientException;

	/**
	 * 保存hash map
	 *
	 * @param key：保存hash对应对应的key
	 * @param map：保存数据对应的map
	 * @param clazz：保存对象对应的class类型
	 * @param seconds：过期时间。-1则表示永不过期
	 * @param <T>
	 * @return
	 * @throws RedisClientException
	 */
	<T> Long setHashData(String key, Map<String, T> map, Class<T> clazz, Integer seconds) throws RedisClientException;

	/**
	 * 获取hash map数据
	 * 
	 * @param key：获取数据对应的key
	 * @param clazz：获取数据对应的class类型
	 * @return
	 * @throws RedisClientException
	 */
	<T> Map<String, T> getHashData(String key, Class<T> clazz) throws RedisClientException;

	/**
	 * 根据field移除hash data
	 *
	 * @param key：所以移除的key
	 * @param fieldSet：要移除的field集合
	 * @return
	 * @throws RedisClientException
	 */
	Long removeHashData(String key, Set<String> fieldSet) throws RedisClientException;

	/**
	 * 设置list data
	 *
	 * @param key：保存list数据对应的key
	 * @param dataList：保存的list数据
	 * @param clazz：list集合中对应的class类型
	 * @param seconds：过期时间。-1则表示永不过期
	 * @throws RedisClientException
	 */
	<T> Long setListData(String key, List<T> dataList, Class<T> clazz, Integer seconds) throws RedisClientException;

	/**
	 * 获取list data
	 *
	 * @param key：获取list数据对应的key
	 * @param clazz：list集合中对应的class类型
	 * @return
	 * @throws RedisClientException
	 */
	<T> List<T> getListData(String key, Class<T> clazz) throws RedisClientException;

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
	 *
	 * @param key
	 * @param beans
	 * @param       <T>
	 * @return
	 * @throws RedisClientException
	 */
	<T> Long rpush2List(String key, Class<T> clazz, T... beans) throws RedisClientException;

	/**
	 * 移除并返回列表 key 的尾元素
	 *
	 * @param key
	 * @param clazz
	 * @param       <T>
	 * @return
	 * @throws RedisClientException
	 */
	<T> T rpopFromList(String key, Class<T> clazz) throws RedisClientException;

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 *
	 * @param key
	 * @param beans
	 * @param       <T>
	 * @return
	 * @throws RedisClientException
	 */
	<T> Long lpush2List(String key, Class<T> clazz, T... beans) throws RedisClientException;

	/**
	 * 移除并返回列表 key 的头元素
	 *
	 * @param key
	 * @param clazz
	 * @param       <T>
	 * @return
	 * @throws RedisClientException
	 */
	<T> T lpopFromList(String key, Class<T> clazz) throws RedisClientException;

	/**
	 * 保存set集合
	 *
	 * @param key
	 * @param set
	 * @return
	 * @throws RedisClientException
	 */
	Long setCollectionData(String key, HashSet<String> set) throws RedisClientException;

	/**
	 * 添加单个元素到集合中
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisClientException
	 */
	Long setCollectionData(String key, String value) throws RedisClientException;

	/**
	 * 获取set集合
	 *
	 * @param key
	 * @return
	 * @throws RedisClientException
	 */
	Set<String> getCollectionData(String key) throws RedisClientException;

	/**
	 * 从set集合中删除数据
	 *
	 * @param key
	 * @param set
	 * @return
	 * @throws RedisClientException
	 */
	Long removeFromCollection(String key, Set<String> set) throws RedisClientException;

	/**
	 * 从set集合中删除数据
	 * 
	 * @param key
	 * @param values
	 * @return
	 * @throws RedisClientException
	 */
	Long removeFromCollection(String key, String... values) throws RedisClientException;

	/**
	 * 判断member是否在key集合中
	 *
	 * @param key
	 * @param member
	 * @return
	 * @throws RedisClientException
	 */
	Boolean isMember(String key, String member) throws RedisClientException;

	Boolean isMember(byte[] key, byte[] member) throws RedisClientException;

	/**
	 * 将 key 中储存的数字值+1，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。只支持整形
	 *
	 * @param key
	 * @return
	 * @throws RedisClientException
	 */
	Long incr(String key) throws RedisClientException;

	/**
	 * 将 key 所储存的值加上增量 increment.如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
	 *
	 * @param key
	 * @param increment
	 * @return
	 */
	Long incrBy(String key, long increment) throws RedisClientException;
}
