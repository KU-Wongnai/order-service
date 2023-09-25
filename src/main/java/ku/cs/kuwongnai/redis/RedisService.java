package ku.cs.kuwongnai.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService<K, V> {

  @Autowired
  private RedisTemplate<K, V> redisTemplate;

  public void saveData(K key, V value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public V getData(K key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void removeData(K key) {
    redisTemplate.delete(key);
  }
}
