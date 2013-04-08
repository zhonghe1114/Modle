package com.yu;

import java.util.Random;

import android.graphics.Bitmap;

public class Monster extends Plane
{
	float xp2=5;
	float xp1=8;
	boolean b=true;
	boolean bb=true;
	int x0;
	int m;
	int xb=5;//boss横向移动
	int yb;//boss冲下来的时间
	public Monster(Bitmap bmp, int w, int h,int n)
	{
		super(bmp, w, h); 
		m=n;

	}
	public void movex(float xp,int xq,int yp)//一种移动轨迹
	{

		y += yp;
		if(b==true)
		{
			x +=xp;
			x0++;
			if(x0==xq)
			{
				x0=0;
				b=false;
			}			
		}
		if(b==false)
		{
			x -=xp;
			x0++;
			if(x0==xq)
			{
				x0=0;
				b=true;
			}

		}
	}
	Random r=new Random();	

	public void move()//控制敌机的飞行路线xzm

	{

		switch(m)
		{
		case 4:  

		case 3:
		case 27:
			movex(xp2,8,5);
			break;
		case 5:
		case 15:
			movex(10,15,3);
			break;
		case 12:
		case 13:
		case 14:
		case 26:
		case 28:
		case 29:
		case 30:
		case 31:
		case 35:
			y+=3;
			break;
		case 10:
		case 20:
		case 6:
		case 16:
			y+=2;
			if(y>=100)
			{
				x+=20;
				y+=20;
			}
			break;
		case 11:
		case 21:
			y+=2;
			if(y>=100)
			{
				x-=20;
				y+=20;
			}
			break;
		case 7:
		case 17:
		case 36:
			x+=xp1;
			y+=5;
			break;
		case 8:
		case 18:
		case 32:
			x -=xp1;
			y+=5;
			break;
		case 9:
		case 19:
			y+=3;
			break;
		case 33:
			  x+=10;
			  y-=10;
			  break;
		  case 34:
			  x-=10;
			  y-=10;
			  break;
		case 25://BOSS 的移动轨迹
		case 40:
			if(y<50)
				y += 3;
			if(b)
			  {
				x+=xb;
				yb++;
				if(x>200)
				{
					xb=-xb;
				}			
				else if(x<10)
				{
					xb=-xb;
					
				}
			   
			  }
			if(yb>=r.nextInt(13)+150)
			{
				yb=0;
				b=false;
			}
			
			if(!b)
			{
				if(y<380&&bb)
				   {
					y+=50;
					if(y>=380)
					  bb=false;
				   }
				else if(y>=50&&!b)
					y-=10;
				if(y<50)
					{b=true;bb=true;}
			}
			break;
			
		}
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
