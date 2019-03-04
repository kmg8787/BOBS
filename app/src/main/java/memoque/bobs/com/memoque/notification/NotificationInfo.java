package memoque.bobs.com.memoque.notification;

import memoque.bobs.com.memoque.main.memo.BSMemo;

public class NotificationInfo
{
	private BSMemo notiMemo = null;
	private long   delay = 0L;

	public NotificationInfo()
	{}

	public void setInfoData( BSMemo memo, long delay )
	{
		notiMemo = memo;
		this.delay = delay;
	}

	public boolean isEmpty()
	{
		return notiMemo == null;
	}

	public BSMemo getNotiMemo()
	{
		return notiMemo;
	}

	public long getDelay()
	{
		return delay;
	}
}
