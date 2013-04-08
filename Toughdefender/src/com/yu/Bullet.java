package com.yu;

import android.graphics.Bitmap;

public class Bullet extends Plane
{
	int m;
	//boolean b=true;
	public Bullet(Bitmap bmp, int w, int h,float posX,float posY,int n)
	{
		super(bmp, w, h);
		this.setPos(posX, posY);
		m  = n;
	}

	public void move()//根据不同的敌机类型生成不同的子弹
	{
		switch(m)
		{
		case 0:
			this.x -= 4;
			y -= 20;
			break;
		case 1:
			y -= 20;
			break;
		case 2:
			this.x += 4;
			y -= 20;
			break;
		case 3:
		case 12:
		case 13:	
		case 8:
		case 18:
		case 29:
		case 34:
		case 36:
		
		case 79:
			x-=8;
			y+=20;
			break;
		case 28:
			x+=8;
			y+=20;
			break;
		case 4:
		case 5:
		case 14:
		case 15:
		case 26:
		case 27:
		case 35:
			y+=20;
			break;
		case 7:
		case 17:
		case 32:
		case 33:
			
			x+=10;
			y+=20;
			break;
		case 9:
		case 19:
			y+=10;
			if(x<150)
				x+=5;
			if(x>=150)
				x-=5;
			
		case 78:
			y+=20;
			break;
		case 93:
			y+=30;
			break;
		case 6:
		case 10:
		case 11:
		case 16:
		case 20:
		case 21:
		case 30:
		case 31:
			x-=20;
			y+=30;
			break;
		case 53:
		case 62:
		case 63:
			x+=8;
			y+=20;
			break;
		case 25:
		case 77:
		case 91:
		case 90:
			x-=3;
			y+=10;
			break;
		case 75:
		case 76:
		case 40:
		case 92:
			x+=3;
			y+=10;
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
