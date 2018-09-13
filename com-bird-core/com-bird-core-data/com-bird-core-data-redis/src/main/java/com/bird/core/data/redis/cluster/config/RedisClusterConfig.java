package com.bird.core.data.redis.cluster.config;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bird.core.common.util.IntrospectorUtil;
import com.bird.core.data.redis.cluster.RedisClusterProtobufTemplateImpl;
import com.bird.core.data.redis.pool.config.RedisPoolProperties;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnClass(JedisCluster.class)
@EnableConfigurationProperties({ RedisClusterProperty.class, RedisPoolProperties.class })
public class RedisClusterConfig {

	@Bean
	public JedisCluster jedisCluster(RedisClusterProperty clusterProperties, RedisPoolProperties poolProperties)
			throws IllegalAccessException, IntrospectionException, InvocationTargetException {
		// 初始化clusterProperties
		if (clusterProperties == null || IntrospectorUtil.entityIsNull(clusterProperties)) {
			throw new RuntimeException("init jedis cluster failed! redis cluster nodes is null");
		}
		Set<HostAndPort> nodes = new HashSet<HostAndPort>(clusterProperties.getNodes().size());
		clusterProperties.getNodes().forEach(node -> {
			String[] ipPortPair = node.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		});
		// 初始化poolProperties
		if (poolProperties == null || IntrospectorUtil.entityIsNull(poolProperties)) {
			throw new RuntimeException("init jedis pool failed! redis pool config is null");
		}
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(poolProperties.getMaxTotal());
		jedisPoolConfig.setMaxIdle(poolProperties.getMaxIdle());
		jedisPoolConfig.setMaxWaitMillis(poolProperties.getMaxWaitMillis());
		jedisPoolConfig.setTestOnBorrow(poolProperties.getTestOnBorrow());
		jedisPoolConfig.setTestOnReturn(poolProperties.getTestOnReturn());
		return new JedisCluster(nodes, clusterProperties.getConnectionTimeout(), clusterProperties.getSoTimeout(),
				clusterProperties.getMaxAttempts(), jedisPoolConfig);
	}

	@Bean
	public RedisClusterProtobufTemplateImpl redisClusterTemplate(RedisClusterProperty clusterProperties,
			RedisPoolProperties poolProperties)
			throws IllegalAccessException, IntrospectionException, InvocationTargetException {
		JedisCluster jedisCluster = jedisCluster(clusterProperties, poolProperties);
		return new RedisClusterProtobufTemplateImpl(jedisCluster);
	}
}
