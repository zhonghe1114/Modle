package com.yu;

import android.graphics.Bitmap;

public class ZdType extends Plane
{
	/*Bitmap[] bmpArray;
	int frame;
	float x;
	float y;
	int width;
	int height;
	//						Ô´Í¼Æ¬			 ¿í         ¸ß
	public Plane(Bitmap bmp,int w,int h)
	{
		width = w;
		height = h;
		int n = bmp.getWidth() / w;
		bmpArray = new Bitmap[n];
		
		for(int i = 0; i < n; i++)
		{
			bmpArray[i] = Bitmap.createBitmap(bmp, i*w,0, w, h);
		}
	}*/
	int m;
	public ZdType(Bitmap bmp,int w,int h,float x,float y,int n)
	{
		super(bmp,w,h);
		this.setPos(x,y);
		m=n;
		
	}
	public void move()
	{
		y+=4;
		
	}

}
