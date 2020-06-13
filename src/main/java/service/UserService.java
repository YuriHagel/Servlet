package service;

import entity.UserEntity;
import org.apache.http.NoHttpResponseException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface UserService {

    @Retryable(value = NoHttpResponseException.class, maxAttempts = 2, backoff = @Backoff(delay = 2))
    UserEntity register(String login, String password);
}
