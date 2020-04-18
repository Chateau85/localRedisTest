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

		// simple string
		jedis.set("NAME", "Cheolwoo Nam");
		System.out.println(jedis.get("NAME"));

		// set
		jedis.sadd("myset", "element1", "element2", "element3");
		System.out.println("element2 is a member : " + jedis.sismember("myset", "element2"));

		// list
		jedis.rpush("mylist", "element1", "element2", "element3");
		System.out.println("element at index 1 : " + jedis.lindex("mylist", 1));

		// hash
		jedis.hset("myhash", "word1", Integer.toString(2));
		jedis.hincrBy("myhash", "word2", 1);
		System.out.println("frequency or word1 : " + jedis.hget("myhash", "word1"));
		System.out.println("frequency or word2 : " + jedis.hget("myhash", "word2"));

		if (jedis != null) {
			jedis.close();
			jedis = null;
		}
		pool.close();
	}

}
