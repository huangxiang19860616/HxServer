package Hx.HxServer.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步工具，可以对一组临界资源进行同步，
 * 仅当对个多个用户访问组中相同的资源时才进行同步
 * 可以用在如下场合：
 * 充值（按卡号同步），注册（按用户名同步）
 *
 * @author Vincent
 *
 * @param <E> 需要同步的资源类型
 */
public class Synchronizer<E>
{
	private Map<E, CountMonitor> lockMap;

	public Synchronizer()
	{
		lockMap = new HashMap<E, CountMonitor>();
	}

	public void lock(E name)
	{
		CountMonitor monitor = lockMap.get(name);
		if (monitor == null)
		{
			// use double check to reduce racing condition
			synchronized (this)
			{
				monitor = lockMap.get(name);
				if (monitor == null)
				{
					monitor = new CountMonitor();
					lockMap.put(name, monitor);
				}
			}
		}
		monitor.reference++;
		monitor.lock.lock();
	}

	public void unlock(E name)
	{
		CountMonitor monitor = lockMap.get(name);
		if (monitor == null)
		{
			throw new RuntimeException("lock " + name + " does not exist");
		}
		monitor.reference--;
		if (monitor.reference < 1)
		{
			lockMap.remove(name);
		}
		monitor.lock.unlock();
	}

	/**
	 * 一个带有计数器的锁
	 *
	 * @author Vincent
	 *
	 */
	private static class CountMonitor
	{
		ReentrantLock lock = new ReentrantLock();

		volatile int reference = 0;
	}
}
