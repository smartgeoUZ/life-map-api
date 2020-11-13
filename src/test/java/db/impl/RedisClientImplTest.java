package db.impl;

import org.junit.Before;
import org.junit.Test;
import org.rarefiedredis.redis.RedisMock;
import org.rarefiedredis.redis.RedisStringCache;

import static org.junit.Assert.assertEquals;

public class RedisClientImplTest {
    private RedisStringCache cacheStringType;

    private String key1   = "key1";
    private String value1 = "Hello";
    private String key2   = "key2";
    private String value2 = "World";

    @Before
    public void setUp() throws Exception {
        cacheStringType = new RedisStringCache();
    }

    @Test
    public void exists() {
        RedisStringCache cacheString = new RedisStringCache();

        assertEquals(false, cacheString.exists(key1));
        cacheString.set(key1, value1);
        assertEquals(true, cacheString.exists(key1));
        assertEquals(false, cacheString.exists(key2));

    }

    @Test
    public void getUserById() throws Exception {
        RedisStringCache cacheString = new RedisStringCache();

        cacheString.set(key1, value1);
        assertEquals(false, cacheString.exists(key2));
        assertEquals(true, cacheString.exists(key1));
    }

    @Test
    public void getAllUsers() throws Exception {
        RedisMock cacheMock     = new RedisMock();
        String[] expectedVal    = {"Hello", "World"};

        cacheMock.mset(key1, value1, key2, value2);
        String[] values = cacheMock.mget(key1, key2);

        //assertEquals(expectedVal, values);
    }

    @Test
    public void deleteUserById() throws Exception {
        RedisStringCache cacheString = new RedisStringCache();

        cacheString.set(key1, value1);
        assertEquals(true, cacheString.exists(key1));

        cacheString.remove(key1);
        assertEquals(false, cacheString.exists(key1));

    }

    @Test
    public void insertUser() throws Exception {
        RedisStringCache cacheString = new RedisStringCache();

        cacheString.set(key1, value1);
        assertEquals(value1, cacheString.get(key1));

        assertEquals(null, cacheString.get(key2));
    }

    @Test public void testType() {
        assertEquals("string", cacheStringType.type());
    }

}