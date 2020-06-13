package tests;

import entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.http.NoHttpResponseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@Slf4j
public class RetryableTest {

    static String login = RandomString.make();
    static String password = RandomString.make();

    @Autowired
    private UserService userService;
    @Autowired
    RetryConfig retryConfig;

    @Test
    public void testRetry() {
        final int expectedAttempts = 1;
        this.userService.register(login, password);
//        log.info("response " + response);
        verify(userService, times(2)).register(login, password);
//        assertThat(response.getBalance(), is("100.00"));
        assertEquals(expectedAttempts, retryConfig.getCount());
    }

    @Configuration
    @EnableRetry
    public static class RetryConfig {
        private int count = 0;

        @Bean
        public UserService remoteCallService() {

            UserService userService = mock(UserService.class);
            doAnswer(invocation -> {
                throw new NoHttpResponseException("Remote Exception");
            }).doAnswer(invocation -> new UserEntity())
                    .when(userService).register(login, password);
            return userService;
        }

        @Bean
        public List<RetryListener> retryListeners() {
            return Collections.singletonList(new RetryListenerSupport() {

                @Override
                public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                    log.warn("Response: {}, count={}", throwable.toString(), context.getRetryCount());
                    count++;
                }

                @Override
                public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                    log.info("open stack: " + Thread.currentThread().getName());
                    return true;
                }

                @Override
                public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                    log.info("close stack: " + Thread.currentThread().getName());
                }
            });
        }

        public int getCount() {
            return count;
        }
    }
}




