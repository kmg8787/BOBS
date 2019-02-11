package memoque.bobs.com.memoque.main.memo;

import android.graphics.Color;

import java.util.Date;

import memoque.bobs.com.memoque.main.MemoQueManager;

public class MemoModel
{
	private int     iIndex;
	private String  sTitle;
	private String  sContent;
	private String  sDate;
	private boolean bPush;
	private int     iMemoBgColor;

	public MemoModel()
	{}

	public MemoModel(int index, String title, String content, String date, boolean push, int bgcolor)
	{
		setAllData( index, title, content, date, push, bgcolor );
	}

	public void setAllData( int index, String title, String content, String date, boolean push, int bgcolor )
	{
		iIndex = index;
		sTitle = title;
		sContent = content;
		sDate = date;
		bPush = push;
		iMemoBgColor = bgcolor;
	}

	public int getIndex()
	{
		return iIndex;
	}

	public String getTitle()
	{
		return sTitle;
	}

	public String getContent()
	{
		return sContent;
	}

	public String getDate()
	{
		return sDate;
	}

	public boolean isPush()
	{
		return bPush;
	}

	public int getMemoBgColor()
	{
		return iMemoBgColor;
	}

	public void setIndex( int iIndex )
	{
		this.iIndex = iIndex;
	}

	public void setTitle( String sTitle )
	{
		this.sTitle = sTitle;
	}

	public void setContent( String sContent )
	{
		this.sContent = sContent;
	}

	public void setDate( String sDate )
	{
		this.sDate = sDate;
	}

	public void setPush( boolean bPush )
	{
		this.bPush = bPush;
	}

	public void setMemoBgColor( int iMemoBgColor )
	{
		this.iMemoBgColor = iMemoBgColor;
	}

	public void setTestData()
	{
		iIndex = MemoQueManager.INDEX;
		sTitle = "테스트 " + MemoQueManager.INDEX;
		sContent = "테스트 메모 " + MemoQueManager.INDEX;
		sDate = new Date(System.currentTimeMillis()).toString();
		bPush = true;
		iMemoBgColor = Color.GRAY;

		MemoQueManager.INDEX++;
	}
}
