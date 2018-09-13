package com.bird.core.data.redis.exception;

public class RedisClientException extends RuntimeException {

	private static final long serialVersionUID = 5181343791884146714L;

	public RedisClientException(String message) {
		super(message);
	}

	public RedisClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
