import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class App {

	public static void main(String[] args) {
		String ip = "127.0.0.1";
		int port = 6379;
		int timeout = 3000;

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool pool = new JedisPool(jedisPoolConfig, ip, port, timeout, null);

		Jedis jedis = pool.getResource();
		jedis.set("NAME", "Cheolwoo Nam");
		System.out.println(jedis.get("NAME"));

		if (jedis != null) {
			jedis.close();
			jedis = null;
		}
		pool.close();
	}

}
