package com.yu;

import android.graphics.*;

public class Plane
{
	Bitmap[] bmpArray;
	int frame;
	float x;
	float y;
	int width;
	int height;
	int skills; //玩家的大技能的个数
	
	//						源图片			 宽         高
	public Plane(Bitmap bmp,int w,int h)
	{
		width = w;
		height = h;
		int n = bmp.getWidth() / w;
		bmpArray = new Bitmap[n];
		skills = 3;
		
		for(int i = 0; i < n; i++)
		{
			bmpArray[i] = Bitmap.createBitmap(bmp, i*w,0, w, h);
		}
	}
	public void change(Bitmap bmp,int w,int h)
	{
		width = w;
		height = h;
		int n = bmp.getWidth() / w;
		for(int i = 0; i < n; i++)
		{
			bmpArray[i] = Bitmap.createBitmap(bmp, i*w,0, w, h);
		}
		
	}
	
	public Plane(Bitmap bmp,int w,int h,float posX,float posY)
	{
		this(bmp, w, h);
		this.setPos(posX, posY);
	}
	
	public void drawPlane(Canvas c,Paint p)
	{
		c.drawBitmap(bmpArray[frame], x, y, p);
	}
	
	public void nextFrame()
	{
		frame = (++frame)%bmpArray.length;
	}
	
	public void setPos(float m, float n)
	{
		x = m;
		y = n;
	}
	
	//碰撞检测
	public boolean isCollies(Plane p)
	{
		if(x < p.x + p.width && x +width > p.x && y < p.y + p.height && y + height > p.y)
		{
			return true;
		}
		return false;
	}
	
	public boolean alwaysReleaseBullet()
	{

		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			return true;
		}
	}
}
