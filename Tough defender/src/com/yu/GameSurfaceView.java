package com.yu;
import java.io.InputStream;
import android.media.MediaPlayer;

import java.util.Random;
import java.util.Vector;

import android.content.*;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable
{
	SurfaceHolder sHolder;
	//一定要使用二个参数的

	Bitmap bmpRole;
	Bitmap bmpBg3;
    Bitmap bmpmap2;
	Bitmap bmpBult1;
    
	Bitmap bmpBomb;
	Bitmap RoleBomb;//设置玩家爆炸时的效果

	Bitmap bmpnpc1;//设置4种子弹lh
	Bitmap bmpnpc2;
	Bitmap bmpnpc3;
	Bitmap bmpnpc4;

	Bitmap planex;//第一架飞机
	Bitmap planey;//第二架飞机
	Bitmap planez;//第三架飞机

	Bitmap bmpblood;//玩家血量图
	Bitmap bmplife;//玩家生命图
	Bitmap bmpbossbult1;//设置boss三种子弹
	Bitmap bmpbossbult2;
	Bitmap bmpbossbult3;
	Bitmap bmpbossbult4;
	Bitmap bmpbossbult5;

	//道具时所出现的图片
	Bitmap zd1;//15
	Bitmap zd2;//16
	Bitmap zd3;//17
	Bitmap zd4;//18

	Bitmap blood;//21
	Bitmap bomb;//20
	Bitmap plane;//19
	boolean bombmeet;
	int boss1blood;//boss1血量
	int boss2blood;
	int zdtype;//设置道具获得时子弹的类型




	//	Bitmap[] bmpArray;

	Plane role;

	boolean up,down,left,right;

	Vector<Bullet> vctBulletRole;

	Vector<Monster> vctMonster;
	Vector<Plane> vctBomb;
	Vector<Bullet> vctBulletMonster;//存储敌机的子弹对象lh
	Vector<ZdType> vctZdType;   //用于存储获得的道具类型

	//被廖同学定义的变量
	int firstPicTime;
	int iconPosition;
	int iconPosition2;
	int iconPosition5;
	int chapter2=1;

	int lggFlag;  //语言标志
	int planeFlag;//飞机标志	
	int levelFlag;//难度标志	
	int voiceFlag;//声音标志
	boolean aboutPic;//关于标志
	boolean chapter3 = false;
	String over,over1,toughDefender,toughDefender1;//游戏结束界面会出现的两个字符串
	int skillEchoTimes = 0;
	int alpha = 100;
	int i=20;//控制alpha的递减

	Vector<String> stringList;
	Vector<String> stringList1;
	Vector<String> stringList2;
	Vector<String> stringList2_;

	public Music sounds;//声音集合对象

	boolean bl=true;	//有生命才有血
	boolean dead=true;//玩家有生命才继续战斗
	int rolelife=5;//玩家的初始血量
	int rolenum=3;//玩家生命
	int gamelevel=1;//
	public GameSurfaceView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		sHolder = this.getHolder();
		sHolder.addCallback(this);

		this.setFocusable(true);

		//载入图片对象
		bmpRole = this.getBitmapById(R.drawable.plane1);//默认的飞机
		planex=this.getBitmapById(R.drawable.plane1);
		planey=this.getBitmapById(R.drawable.plane2);
		planez=this.getBitmapById(R.drawable.plane3);

		bmpBg3 = this.getBitmapById(R.drawable.bg3);
        bmpmap2=this.getBitmapById(R.drawable.map2);
		bmpBult1 = this.getBitmapById(R.drawable.zd2);//飞机子弹

		bmpBomb = this.getBitmapById(R.drawable.explode);
		RoleBomb=this.getBitmapById(R.drawable.explosion1);//载入玩家爆炸时的图片
		bmpnpc1=this.getBitmapById(R.drawable.zd);//取得4种子弹位图lh
		bmpnpc2=this.getBitmapById(R.drawable.nzd1);
		bmpnpc3=this.getBitmapById(R.drawable.nzd3);
		bmpnpc4=this.getBitmapById(R.drawable.nzd4);
		bmpbossbult1=this.getBitmapById(R.drawable.zb1);//获取boss1的子弹类型xzm
		bmpbossbult2=this.getBitmapById(R.drawable.zb2);
		bmpbossbult3=this.getBitmapById(R.drawable.zb3);
		bmpbossbult4=this.getBitmapById(R.drawable.zb4);
		bmpbossbult5=this.getBitmapById(R.drawable.zb5);
		bmpblood=this.getBitmapById(R.drawable.rolelife);//玩家血量
		bmplife=this.getBitmapById(R.drawable.life);//玩家生命

		role = new Plane(bmpRole,30,30);//创造默认飞机
		role.setPos(150f, 440f);
		role.frame = 1;
		rolelife=5;  //玩家每架飞机血量
		zdtype=3;    //默认的子弹类型

		//加载生成道具时的图片
		zd1=this.getBitmapById(R.drawable.zd1);
		zd2=this.getBitmapById(R.drawable.zd2);
		zd3=this.getBitmapById(R.drawable.zd3);
		zd4=this.getBitmapById(R.drawable.zd4);

		blood=this.getBitmapById(R.drawable.blood);//加血的图片
		bomb=this.getBitmapById(R.drawable.bomb);  //爆炸的图片
		plane=this.getBitmapById(R.drawable.plane);  //加飞机的图片



		vctBulletRole = new Vector<Bullet>();
		vctMonster = new Vector<Monster>();
		vctBomb = new Vector<Plane>();
		vctBulletMonster=new Vector<Bullet>();
		vctZdType=new Vector<ZdType>();
		//		bmpArray = new Bitmap[3];
		//		
		//		for(int i = 0; i < 3; i++)
		//		{ 
		//			bmpArray[i] = Bitmap.createBitmap(bmpRole, i*32,0, 32, 32);
		//		}

		//		bmpArray[0] = Bitmap.createBitmap(bmpRole, 0,0, 32, 32);
		//		bmpArray[1] = Bitmap.createBitmap(bmpRole, 32,0, 32, 32);
		//		bmpArray[2] = Bitmap.createBitmap(bmpRole, 64,0, 32, 32);

		//被廖同学定义的变量的初始化

		firstPicTime = 1;
		iconPosition = 0;
		iconPosition2 = 0;
		iconPosition5 = 1;
		lggFlag = 1;
		planeFlag = 1;
		levelFlag =1;
		voiceFlag =1;
		sounds = new Music(getContext());
		bombmeet=false;
		aboutPic = false;

		over = this.getStringById(R.string.over);
		over1 = this.getStringById(R.string.over1);
		toughDefender = this.getStringById(R.string.defender);
		toughDefender1 = this.getStringById(R.string.defender);

		stringList = new Vector<String>();
		int[] textid = {R.string.start,R.string.option,R.string.about_author,R.string.English,R.string.exit};
		for(int i= 0;i<5;i++)
		{
			stringList.add(this.getStringById(textid[i]));
		}

		stringList1 = new Vector<String>();
		int[] textid1 = {R.string.start1,R.string.option1,R.string.about_author1,R.string.langguage1,R.string.exit1};
		for(int i= 0;i<5;i++)
		{
			stringList1.add(this.getStringById(textid1[i]));
		}

		stringList2 = new Vector<String>();
		int[] textid2 = {R.string.level,R.string.audio_on,R.string.audio_off,R.string.go_back};
		for(int i= 0;i<4;i++)
		{
			stringList2.add(this.getStringById(textid2[i]));
		}

		stringList2_ = new Vector<String>();
		int[] textid3 = {R.string.level1,R.string.audio_on1,R.string.audio_off1,R.string.go_back1};
		for(int i= 0;i<4;i++)
		{
			stringList2_.add(this.getStringById(textid3[i]));
		}
	}

	int[][] monsterTable = 
	{
			//创建时间	         x	y 种类   敌机出来的位置 xzm
			{50,		40,-50,4,		200,-50,4},
			{80,       -10,50,7,       -10,100,7},
			{100,       320,50,8,      320,100,8},
			{120,       150,-10,16},//zd2
			{150,		30,-50,3,		90,-50,3,      210,-50,3,   270,-50,3},
			{200,		20,-50,6,		80,-50,6,      220,-50,6,   280,-50,6},
			{270,		20,-50,10,		50,-50,10,      220,-50,11,   250,-50,11},
			{300,       150,-10,17},//zd3
			{340,		10,-50,5,       180,-50,5},
			{400,       150,-50,14},
			{450,		10,-50,4,    	110,-20,4,	   210,-50,4},

			{475,       50,-10,15},//zd1
			{476,       150,-10,19},//plane
			{550,		70,-50,3,		210,-50,3},
			{600,       -10,50,7,       350,50,8},
			{605,       -10,50,7,       350,50,8},
			{610,       -10,50,7,       350,50,8},

			{615,       -10,50,7,       350,50,8},
			{640,       150,-10,16},//zd2
			{650,		30,-50,12		},
			{655,		90,-50,12		},
			{660,		510,-50,12		},
			{700,       50,-10,15},//zd1
			{750,       -10,50,7,       350,50,8},
			{755,       -10,50,7,       350,50,8},
			{757,       150,-10,17},//zd3
			{760,       -10,50,7,       350,50,8},
			{765,       -10,50,7,       350,50,8},
			{770,       150,-50,4,      160,-50,4},
			{775,		10,-50,5,       180,-50,5},
			{800,		20,-50,6,		80,-50,6,      200,-50,6,   260,-50,6},
			{815,       150,-10,20,     180,-50,13},//bomb
			{820,       150,-10,21,     50,-50,9},//blood
			{825,       50,-50,9,       250,-50,9 },
			{830,       50,-50,9,       250,-50,9 },
			{835,       50,-50,9,       250,-50,9 },
			{837,       150,-10,18},//zd4
			{840,       50,-50,9,       250,-50,9 },
			{845,       50,-50,9,       250,-50,9 },
			{850,       50,-50,9,       250,-50,9 },
			{855,       50,-50,9,       250,-50,9 },
			{900,       150,-10,18},//zd4
			{950,		10,-50,13,      250,-50,13},
			{955,        10,-50,13,     250,-50,13},
			{960,        20,-50,13,     240,-50,13},
			{963,       150,-10,21},//blood
			{964,       150,-10,21},//blood
			{965,        30,-50,13,     230,-50,13},
			{970,        40,-50,13,     220,-50,13},
			{975,        50,-50,13,     210,-50,13},
			{1090,       140,-50,25}

	};
	int [][] monsterTable1=
	{
			//创建时间	         x	y 种类   敌机出来的位置 xzm
			//{50,		40,-50,4,		200,-50,4},
			{50,      40,-50,26,      200,-50,26},//直线行走4号z3
			{80,      110,-50,26,     170,-50,26},//直线行走4号
			{110,     10,-50,27,      110,-50,27,    210,-50,27},//摇摆运动5号npc5
			{150,      140,-50,28,    150,-50,28},//直线行走7号npc4
			{155,      130,-50,28,    160,-50,28},//直线行走z0
			{160,      120,-50,28,    170,-50,28},//直线行走
			{162,      120,-50,15,    170,-50,16},//直线行走

			{165,      110,-50,28,    180,-50,28},//直线行走
			{170,      100,-50,28,    190,-50,28},//直线行走
			{175,      90,-50,28,    200,-50,28},//直线行走
			{180,      80,-50,28,    210,-50,28},//直线行走
			{240,		20,-50,30,		80,-50,30,      200,-50,31,   260,-50,31},//左右直走z2
			{290,     10,-50,35,      110,-20,35,    210,-50,35},//直线行走NPC5
			{295,		20,-50,6,		80,-50,6,      200,-50,11,   260,-50,11},
			{300,      80,-50,17},//直线行走
			{340,		30,-50,28		},//直线行走
			{345,		90,-50,28		},
			{350,		150,-50,28	},
			{355,     210,-50,28},
			{390,       -10,50,36,       350,50,32},//左右冲下来
			{395,       -10,50,36,       350,50,32},//z0
			{400,       -10,50,36,       350,50,32},
			{401,       -10,50,20},//左右冲下来
			{405,       -10,50,36,       350,50,32},
			{460,       20,-50,26,       100,-50,26,   170,-50,26,    260,-50,26},//直线行走
			{500,       10,-50,27,      110,-20,27,    210,-50,27},//摇摆运动5号npc5
			{530,       150,-50,27},
			{550,       150,-50,21},
			{570,       120,-50,27,     180,-50,27},
			{600,       90,-50,27,     210,-50,27},
			{650,       -10,50,36,       350,50,32},//左右冲下来
			{652,       -10,50,36,       350,50,19},//左右冲下来
			{655,       -10,50,36,       350,50,32},//z0
			{660,       -10,50,36,       350,50,32},
			{665,       -10,50,36,       350,50,32},
			{680,       -10,50,18},
			{720,       -10,450,33,      350,450,34},//左右冲上去
			{725,       -10,450,33,      350,450,34},//z0
			{730,       -10,450,33,      350,450,34},
			{735,       -10,450,33,      350,450,34},
			{740,       -10,450,33,      350,450,34},
			{745,       -10,450,33,      350,450,34},
			{747,       -10,450,20},
			{750,     10,-50,27,      110,-50,27,    210,-50,27},//摇摆运动5号npc5
			{790,      40,-50,26,      200,-50,26},//直线行走4号z3
			{800,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{810,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{820,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{825,      120,-50,18,    150,-50,18},//直线行走7号npc4
			{830,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{840,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{850,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{860,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{870,      120,-50,28,    150,-50,29},//直线行走7号npc4
			{880,      120,-50,16,    150,-50,16},//直线行走7号npc4
			{920,      40,-50,26,      200,-50,26},//直线行走4号z3
			{950,     10,-50,27,      110,-20,27},//
			{980,     110,-20,27,    210,-50,27},
			{1090,       140,-50,40}//
	};
	int time;
	int tableRow;
	int leveltime=0;//敌机出现批次
	int boss1pos;
	int boss2pos;//boss出现
	boolean noboss=true;//判断boss是否出现
	int bosschangeb;
	//按时间轴生成敌机
	public void createNpc()
	{
		if(gamelevel==1)
		{
			if(noboss==true)
			{
				time++;

				if(time == monsterTable[tableRow][0])
				{
					for(int i = 1; i <monsterTable[tableRow].length; i+=3)
					{
						Monster m = this.createMonter(monsterTable[tableRow][i+2]);
						m.setPos(monsterTable[tableRow][i], monsterTable[tableRow][i+1]);
						vctMonster.addElement(m);
						if(m.m==3||m.m==12||m.m==13)
						{
							vctBulletMonster.addElement(new Bullet(bmpnpc1,18,16,m.x+m.width/2-5,m.y+m.height+1,m.m));
							vctBulletMonster.addElement(new Bullet(bmpnpc1,18,16,m.x+m.width/2-5,m.y+m.height+1,m.m+50));
						}
						if(m.m==4||m.m==5||m.m==14||m.m==15)
							vctBulletMonster.addElement(new Bullet(bmpnpc2,12,12,m.x+m.width/2-5,m.y+m.height+1,m.m));
						if(m.m==7||m.m==8||m.m==9||m.m==17||m.m==18||m.m==19)
							vctBulletMonster.addElement(new Bullet(bmpnpc3,12,12,m.x+m.width/2-5,m.y+m.height+1,m.m));
						if(m.m==6||m.m==10||m.m==11||m.m==16||m.m==20||m.m==21)
							vctBulletMonster.addElement(new Bullet(bmpnpc4,23,24,m.x+m.width/2-5,m.y+m.height+1,m.m));
					}
					//			tableRow=(++tableRow)%monsterTable.length;
					tableRow++;
					if(tableRow == monsterTable.length-1)
					{
						boss1pos=tableRow;
						tableRow = 0;
						time = 0;
						leveltime=leveltime+1;
						if(leveltime==1)
						{
							time=monsterTable[boss1pos][0];
							noboss=false;

						}

					}

				}
			}
			if(noboss==false)
			{
				time++;
				if(time>=1150)
				{  
					for(int i = 1; i <monsterTable[boss1pos].length; i+=3)
					{
						Monster m=this.createMonter(monsterTable[boss1pos][i+2]);
						m.setPos(monsterTable[boss1pos][i], monsterTable[boss1pos][i+1]);
						vctMonster.addElement(m);
						bosschangeb++;
						if(bosschangeb<=20)
						{
							vctBulletMonster.addElement(new Bullet(bmpbossbult1,23,24,m.x+20,m.y+100,m.m));
							vctBulletMonster.addElement(new Bullet(bmpbossbult1,23,24,m.x+80,m.y+100,m.m+50));
							vctBulletMonster.addElement(new Bullet(bmpbossbult2,9,14,m.x+20,m.y+100,m.m+51));
							vctBulletMonster.addElement(new Bullet(bmpbossbult2,9,14,m.x+80,m.y+100,m.m+52));
						}
						if(bosschangeb>=30)
						{
							for(int k=0;k<4;k++)
							{
								vctBulletMonster.addElement(new Bullet(bmpbossbult3,12,11,m.x+53,m.y+110,m.m+53));
							}

							if(bosschangeb==40)
							{
								bosschangeb=0;
							}

						}
					}
					noboss=true;
				}
			}
		}
		if(gamelevel==2)
		{
			if(noboss==true)
			{
				time++;

				if(time == monsterTable1[tableRow][0])
				{
					for(int i = 1; i <monsterTable1[tableRow].length; i+=3)
					{
						Monster m = this.createMonter(monsterTable1[tableRow][i+2]);
						m.setPos(monsterTable1[tableRow][i], monsterTable1[tableRow][i+1]);
						vctMonster.addElement(m);
						if(m.m==28||m.m==29||m.m==15||m.m==17)
						{
							vctBulletMonster.addElement(new Bullet(bmpnpc1,18,16,m.x+m.width/2-5,m.y+m.height+1,m.m));
							vctBulletMonster.addElement(new Bullet(bmpnpc1,18,16,m.x+m.width/2-5,m.y+m.height+1,m.m+50));
						}
						if(m.m==26||m.m==27||m.m==35||m.m==16||m.m==18)
							vctBulletMonster.addElement(new Bullet(bmpnpc2,12,12,m.x+m.width/2-5,m.y+m.height+1,m.m));
						if(m.m==32||m.m==33||m.m==34||m.m==36||m.m==21)
							vctBulletMonster.addElement(new Bullet(bmpnpc3,12,12,m.x+m.width/2-5,m.y+m.height+1,m.m));
						if(m.m==30||m.m==31||m.m==19||m.m==20)
							vctBulletMonster.addElement(new Bullet(bmpnpc4,23,24,m.x+m.width/2-5,m.y+m.height+1,m.m));
					}
					//			tableRow=(++tableRow)%monsterTable.length;
					tableRow++;
					if(tableRow == monsterTable1.length-1)
					{
						boss2pos=tableRow;
						tableRow = 0;
						time = 0;
						leveltime=leveltime+1;
						if(leveltime==1)
						{
							time=monsterTable1[boss2pos][0];
							noboss=false;

						}

					}

				}
			}
			if(noboss==false)
			{
				time++;
				if(time>=1150)
				{  
					for(int i = 1; i <monsterTable1[boss2pos].length; i+=3)
					{
						Monster m=this.createMonter(monsterTable1[boss2pos][i+2]);
						m.setPos(monsterTable1[boss2pos][i], monsterTable1[boss2pos][i+1]);
						vctMonster.addElement(m);
						bosschangeb++;
						if(bosschangeb<=20)
						{
							vctBulletMonster.addElement(new Bullet(bmpbossbult4,21,18,m.x,m.y+40,m.m));
	  						vctBulletMonster.addElement(new Bullet(bmpbossbult4,21,18,m.x+76,m.y+40,m.m+50));
	  						vctBulletMonster.addElement(new Bullet(bmpbossbult5,21,18,m.x+20,m.y+60,m.m+51));
	  						vctBulletMonster.addElement(new Bullet(bmpbossbult5,21,18,m.x+58,m.y+60,m.m+52));
						}
						if(bosschangeb>=30)
						{
							for(int k=0;k<4;k++)
							{
								vctBulletMonster.addElement(new Bullet(bmpbossbult3,12,11,m.x+39,m.y+85,m.m+53));
							}

							if(bosschangeb==40)
							{
								bosschangeb=0;
							}

						}
					}
					noboss=true;
				}
			}

		}
	}

	//根据各类生成相应的敌机对象
	public  Monster createMonter(int n)
	{

		Monster m = null;
		Bitmap bmpMonster = null;

		switch(n)
		{
		case 4:
		case 14:
		case 26:
			bmpMonster = this.getBitmapById(R.drawable.z3);
			m = new Monster(bmpMonster,57,46,n);
			break;
		case 5:
		case 15:
		case 27:
		case 35:
			bmpMonster = this.getBitmapById(R.drawable.npc5);
			m = new Monster(bmpMonster,78,40,n);
			break;

		case 3:
		case 13:
		case 12:
		case 28:
		case 29:
			bmpMonster = this.getBitmapById(R.drawable.npc4);
			m = new Monster(bmpMonster,24,32,n);
			break;
		case 6:
		case 10:
		case 11:
		case 16:
		case 20:
		case 21:
		case 30:
		case 31:
			bmpMonster = this.getBitmapById(R.drawable.z2);
			m = new Monster(bmpMonster,20,21,n);
			break;
		case 7:
		case 8:
		case 9:
		case 17:
		case 18:
		case 19:
		case 32:
		case 36:
		case 33:
		case 34:
			bmpMonster = this.getBitmapById(R.drawable.z0);
			m = new Monster(bmpMonster,19,22,n);
			break;
		case 25:
			bmpMonster=this.getBitmapById(R.drawable.bossnum1);
			m = new Monster(bmpMonster,107,110,n);
			break;
		case 40:
			bmpMonster=this.getBitmapById(R.drawable.bossnum2);
			m = new Monster(bmpMonster,76,85,n);
			break;
		}

		return m;
	}

	public void roleBulletAttackMonster()
	{
		Bullet blt = null;
		Monster m = null;
		for(int i = 0 ; i < vctBulletRole.size(); i++)
		{
			blt = vctBulletRole.elementAt(i);
			for(int j  = 0 ; j < vctMonster.size(); j++)
			{
				m = vctMonster.elementAt(j);
				//打中了
				if(blt.isCollies(m))
				{
					//
					sounds.setMusicWillBePlay(2);
					vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y));
					//清除子弹
					vctBulletRole.elementAt(i).alwaysReleaseBullet();
					vctBulletRole.removeElement(blt);
					//清除monster
					if(m.m!=25&&m.m!=40)
					{
						//vctZdType.addElement(new ZdType(zd1,25,43,m.x,m.y,m.m));
						//判断消灭的敌机类型，根据设定的类型来产生相应的道具
						if(m.m==15)//子弹1道具
						{
							vctZdType.addElement(new ZdType(zd1,13,22,m.x,m.y,m.m));

						}
						if(m.m==16)//子弹2道具
						{

							vctZdType.addElement(new ZdType(zd2,31,16,m.x,m.y,m.m));

						}
						if(m.m==17)//子弹3道 具
						{
							vctZdType.addElement(new ZdType(zd3,29,21,m.x,m.y,m.m));

						}
						if(m.m==18)//子弹4道 具
						{

							vctZdType.addElement(new ZdType(zd4,18,22,m.x,m.y,m.m));

						}
						if(m.m==19)//飞机道具
						{
							vctZdType.addElement(new ZdType(plane,38,30,m.x,m.y,m.m));

						}
						if(m.m==20)//炸弹道具
						{
							vctZdType.addElement(new ZdType(bomb,41,36,m.x,m.y,m.m));

						}
						if(m.m==21)//加血道具
						{
							vctZdType.addElement(new ZdType(blood,27,25,m.x,m.y,m.m));

						}
						vctMonster.elementAt(j).alwaysReleaseBullet();
						vctMonster.removeElement(m);
					}
					if(m.m==25)
					{
						boss1blood++;
						if(boss1blood>=1)
						{
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x+m.width-56,m.y));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y+m.height-39));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x+m.width-56,m.y+m.height-39));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x+m.width/2-28,m.y+m.height-20));
						}
						if(boss1blood==20)
						{
							//ClearAll();
							vctMonster.elementAt(j).alwaysReleaseBullet();
							vctMonster.removeElement(m);
//							gamelevel=2;
//							boss1blood=0;
							time=-50;
							leveltime=0;
							//ClearAll();
						}
						
					}
					if(m.m==40)
					{
						boss2blood++;
						if(boss2blood>=1)
						{
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x+m.width-56,m.y));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y+m.height-39));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x+m.width-56,m.y+m.height-39));
							vctBomb.addElement(new Plane(bmpBomb,56,39,m.x+m.width/2-28,m.y+m.height-20));
						}
						
						if(boss2blood==20)
						{
							
							chapter2 = 3;
							vctMonster.elementAt(j).alwaysReleaseBullet();
							vctMonster.removeElement(m);
//							gamelevel=2;
//							boss1blood=0;
							time=-50;
							leveltime=0;
						}
					}
					//					vctMonster.elementAt(j).alwaysReleaseBullet();
					//					vctMonster.removeElement(m);
					break;
				}
			}

		}
	}
	public void drawZdType(Canvas c,Paint p)//画生成的相应的道具
	{
		ZdType daoju;
		for(int i=0;i<vctZdType.size();i++)
		{
			daoju=vctZdType.elementAt(i);
			daoju.drawPlane(c, p);
			daoju.move();
			if(daoju.x<0||daoju.x>this.getWidth()||daoju.y<0||daoju.y>this.getHeight())
			{
				vctZdType.removeElement(daoju);
			}

		}
	}
	public void DaojuMeetRole()//产生的道具与玩家相碰时使相应的道具生效
	{
		ZdType daoju=null;
		for(int i=0;i<vctZdType.size();i++)
		{
			daoju=vctZdType.elementAt(i);
			if(daoju.isCollies(role))
			{
				if(daoju.m==19)//加飞机
				{
					rolenum++;

				}
				if(daoju.m==20)//炸弹
				{
					//炸弹
					bombmeet=true;

				}
				if(daoju.m==21)//加血
				{
					rolelife+=1;
					if(rolelife>=5)
						rolelife=5;

				}
				if(daoju.m==15)//子弹1
				{
					zdtype=1;


				}
				if(daoju.m==16)//子弹2
				{
					zdtype=2;

				}
				if(daoju.m==17)//子弹3
				{
					zdtype=3;

				}
				if(daoju.m==18)//子弹4
				{
					zdtype=4;

				}
				//vctZdType

				sounds.setMusicWillBePlay(1);
				daoju.alwaysReleaseBullet();
				vctZdType.removeElement(daoju);
			}

		}
	}
	public void MonsterBulletAttackRole()
	{
		Bullet blt = null;
		//Monster m = null;
		for(int k=0;k<vctBulletMonster.size();k++)//敌机攻击玩家
		{
			blt=vctBulletMonster.elementAt(k);
			if(blt.isCollies(role))
			{
				rolelife-=1;
				if(role.skills>0&&rolelife<=0)
				{
					this.skillEchoTimes+=50;
					this.rolelife = 2;
					role.skills -=1;
					alpha = 100;
				}
				//			vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y));
				//			//清除子弹
				//			vctBulletRole.elementAt(i).alwaysReleaseBullet();
				//			vctBulletRole.removeElement(blt);
				//			//清除monster
				//			vctMonster.elementAt(j).alwaysReleaseBullet();
				//			vctMonster.removeElement(m);
				//			




				//vctBulletMonster.removeElement(blt);
				vctBomb.addElement(new Plane(bmpBomb,56,39,role.x,role.y));
				//清除子弹
				sounds.setMusicWillBePlay(2);
				vctBulletMonster.elementAt(k).alwaysReleaseBullet();
				vctBulletMonster.removeElement(blt);
				if(rolelife==0)
				{
					zdtype=3;
					vctBomb.addElement(new Plane(RoleBomb,98,98,role.x-49,role.y-49));//玩家爆炸
					vctBomb.addElement(new Plane(RoleBomb,98,98,role.x-49,role.y-49));//玩家爆炸
					//vctBomb.addElement(new Plane(RoleBomb,98,98,role.x-49,role.y-49));//玩家爆炸
					rolenum-=1;
					role.x=150;
					role.y=445;
					if(rolenum==0)
						dead=false;
					rolelife=5;
					if(rolenum==0)
						bl=false;

				}
			}
		}

	}
	public void MonsterMeetRole()///敌机与玩家相撞
	{
		Monster m=null;
		for(int i=0;i<vctMonster.size();i++)
		{
			m=vctMonster.elementAt(i);
			if(m.isCollies(role))
			{
				zdtype=3;
				if(m.m!=25&&m.m!=40)
				{

					sounds.setMusicWillBePlay(2);
					vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y));
					//vctBomb.addElement(new Plane(bmpBomb,56,39,role.x,role.y));
					vctBomb.addElement(new Plane(RoleBomb,98,98,role.x-49,role.y-49));//玩家爆炸
					vctBomb.addElement(new Plane(RoleBomb,98,98,role.x-49,role.y-49));//玩家爆炸
					//vctBomb.addElement(new Plane(RoleBomb,98,98,role.x-49,role.y-49));//玩家爆炸
					vctMonster.elementAt(i).alwaysReleaseBullet();
					vctMonster.removeElement(m);
					role.x=150;
					role.y=445;
				}
				dead=true;
				if(role.skills>0){rolelife = 3;role.skills-=1;this.skillEchoTimes+=50;alpha = 100;}
				else
					{
					rolenum-=1;
					}
				if(rolenum==0)
					dead=false;
				rolelife=5;
				if(rolenum==0)
					bl=false;

			}

		}
	}


	int bgY1 = 0;
	int bgY2 = -480;
	//	
	public int draw()
	{
		Canvas c = null;
		try
		{
			c = sHolder.lockCanvas();
			Paint p = new Paint();
			//			firstPicTime = 3;
			//游戏画面，也是用的最多的画面

			if(chapter2==3)
			{
				ClearAll();
				drawEnd(c,p);
				
				return 1;
			}
			if(boss1blood>=20)
			{
				drawChapterChange(c,p);
				ClearAll();
				chapter2 =2;
				System.out.println("chapter2");
				System.out.println("chapter2");
				System.out.println("chapter2");
				return 1;
			}
			if(firstPicTime == 3)
			{
				int w =  this.getWidth();
				int h = this.getHeight();

				if(!chapter3||gamelevel==1)
				{
				c.drawBitmap(bmpBg3, 0, bgY1, p);
				c.drawBitmap(bmpBg3, 0, bgY2, p);
				}
				if(chapter3||gamelevel==2)
				{
					
					c.drawBitmap(bmpmap2, 0, bgY1, p);
					c.drawBitmap(bmpmap2, 0, bgY2, p);
				}

				role.drawPlane(c, p);
				DaojuMeetRole();//道具与玩家相碰
				roleBulletLogic(c,p);//玩家子弹逻辑
				MonsterBulletAttackRole();//敌机子弹攻击玩家
				MonsterMeetRole();//敌机与玩家相碰
				monsterLogic(c,p);//敌机逻辑
				MonsterBulletLogic(c,p);//敌机子弹发射 
				drawZdType(c,p);//画生成的道 具

				bombLogic(c,p);//炸弹逻辑
				if(bombmeet)
				{
					Bomb();
					bombmeet=false;
				}
				drawblood(c,p);//画玩家飞机的血
				drawlife(c,p);//画玩家飞机的数量
			}
			//第一个画面
			if(firstPicTime == 1)
			{
				drawFirstPic(c,p);
			}

			if(firstPicTime == 5)
			{
				this.drawFifthPic(c, p);
			}

			if(aboutPic)
			{
				drawAboutPic(c,p);
			}

			//绘制第二个画面
			if(firstPicTime == 2)
			{
				drawSecPic(c,p);
			}

			if(firstPicTime == 4)
			{
				ClearAll();
				drawExitPic(c,p);
				
			}

			if(this.skillEchoTimes>0)
			{
				System.out.println("xxxxxxxxxxx");
				this.drawSkillsNotify(c, p);
				skillEchoTimes -=1;
			}

		}
		finally
		{
			sHolder.unlockCanvasAndPost(c);
		}
		return 1;



	}

	public void drawSkillsNotify(Canvas c,Paint p)
	{
		p.setTextSize(18);
		p.setColor(0xffff0000);
		p.setAlpha(alpha);
		if(i==5)
			alpha -= 1;
		else
			i=(i+1)%1280;
		if(alpha==50)alpha=20;
		if(lggFlag==1)
			c.drawText(toughDefender,10,450,p);
		else
			c.drawText(toughDefender1,10,450,p);
		p.setTextSize(25);
	}

	public void drawblood(Canvas c,Paint p)
	{
		if(bl)
		{
			for(int i=0;i<rolelife;i++)
				c.drawBitmap(bmpblood, 20*i, 10, p);
		}
		//Bitmap.createBitmap(bmpblood, 23, 0, bgY1, bgY1)

	}

	public void drawlife(Canvas c,Paint p)
	{
		if(dead)
		{
			for(int i=0;i<rolenum;i++)
				c.drawBitmap(bmplife, this.getWidth()-30-30*i, 10, p);
		}
		//Bitmap.createBitmap(bmpblood, 23, 0, bgY1, bgY1)
	}
	public void Bomb()
	{
		Monster m=null;
		Bullet b=null;
		for(int i=0;i<vctMonster.size();i++)
		{
			m=vctMonster.elementAt(i);
			vctMonster.removeElement(m);
			vctBomb.addElement(new Plane(bmpBomb,56,39,m.x,m.y));


		}
		for(int j=0;j<vctBulletMonster.size();j++)
		{
			b=vctBulletMonster.elementAt(j);
			b.alwaysReleaseBullet();
			vctBulletMonster.removeElement(b);
		}
	}
	public void model()
	{
		bgY1 += 10;
		bgY2 += 10;

		if(bgY1 >= 480)
		{
			bgY1 = -480;
		}

		if(bgY2 >= 480)
		{
			bgY2 = -480;
		}

		createNpc();
		roleBulletAttackMonster();
	}

	public boolean onKeyDown5(int k)
	{
		//		System.out.println(k);
		if(iconPosition5 == 0 && k==19)
		{
			iconPosition5 = 2;
			return true;
		}

		if(iconPosition5 == 0 && k==20)
		{
			iconPosition5 = 1;
			return true;
		}

		if(iconPosition5 == 1 && k==19)
		{
			iconPosition5 = 0;
			return true;
		}

		if(iconPosition5 == 1 && k==20)
		{
			iconPosition5 = 2;
			return true;
		}

		if(iconPosition5 == 2 && k==19)
		{
			iconPosition5 = 1;
			return true;
		}

		if(iconPosition5 == 2 && k==20)
		{
			iconPosition5 = 0;
			return true;
		}

		if(iconPosition5 == 0 && k == 23)
		{
			firstPicTime = 3;
		}

		if(iconPosition5 == 1)
		{
			if(k == 19)
			{
				iconPosition5 = 0;
			}

			if(k == 20)
			{
				iconPosition5 = 0;
			}

			if(k == 21 || k == 22)
			{
				voiceFlag = -voiceFlag;
				sounds.voiceFlag = voiceFlag;
				if(voiceFlag==-1)sounds.removeMusicWillBePlay(0);
				else sounds.setMusicWillBePlay(0);
			}
		}

		if(iconPosition5 ==2 && k == 23)
		{
			if(voiceFlag == 1)sounds.removeMusicWillBePlay(0);
			System.exit(0);
		}

		if(k == 4)
		{
			firstPicTime = 3;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int k, KeyEvent event) {

		if(this.boss1blood>=20&&k==23&&chapter2==2)
		{
			boss1blood =1;
			System.out.println("chapter2");
			System.out.println("chapter2");
			System.out.println("chapter2");
			chapter2=1;
			gamelevel=2;
			chapter3 = true;
			return true;

		}
		
		if(chapter2==3&&23==k)
		{
			System.out.println("55555555555555555");
			System.out.println("55555555555555555");
			System.out.println("55555555555555555");
			chapter2=1;
			firstPicTime =1;
			iconPosition = 3;
		}
		if(firstPicTime == 4&&k==23)
		{
			firstPicTime = 1;
			return true;
		}
		if(firstPicTime == 5)
		{
			return onKeyDown5(k);
		}

		if(aboutPic)
		{
			aboutPic = false;
			return true;
		}

		if(firstPicTime == 1 &&iconPosition==0&&23 == k)
		{
			firstPicTime = 3;
			time=0;
			rolelife=7;
			rolenum=9;//********************************************888*888
			zdtype=3;
			//planeFlag=2;
			boss1blood=0;
			noboss=true;
			gamelevel= levelFlag;
			tableRow=0;
			firstPicTime = 3;
			if(planeFlag==1)//根据所选的飞机，生成相应的飞机
			{
				//					role = new Plane(planex,32,32);
				return true;

			}
			if(planeFlag==2)
			{
				role = new Plane(planey,36,38);

			}
			if(planeFlag==3)
			{
				role = new Plane(planez,32,32);


			}
			role.setPos(150f, 440f);
			role.frame = 1;
			return true;
		}

		if(k==62&&role.skills>0){this.Bomb();role.skills--;}
		if(firstPicTime == 1 &&iconPosition==2&&23 == k)
		{
			aboutPic = true;
			return true;
		}

		if(k == 19)		// 上
		{
			if(firstPicTime == 3)
			{
				up = true;
			}
			if(firstPicTime ==1)
			{
				if(iconPosition==0)
				{
					iconPosition = 4;
					return true;
				}
				iconPosition = (iconPosition-1)%5;
				return true;
			}

			if(firstPicTime == 2)
			{
				if(iconPosition2==0)
				{
					iconPosition2 = 3;
					return true;
				}
				iconPosition2 = (iconPosition2-1)%4;
				return true;
			}
		}
		if(20 == k)		// 下
		{
			if(firstPicTime == 3)
			{
				down = true;
			}
			if(firstPicTime ==1)
			{
				iconPosition = (iconPosition+1)%5;
				return true;
			}

			if(firstPicTime == 2)
			{
				iconPosition2 = (iconPosition2+1)%4;
				return true;
			}
		}

		if(21 == k)		// 左
		{
			if(firstPicTime == 3)
			{
				left = true;
				role.frame = 2;
			}
			if(firstPicTime ==1)
			{
				if(iconPosition == 0)
				{
					firstPicTime = 3;
				}

				if(iconPosition == 3)
				{
					lggFlag = lggFlag*(-1);
				}
				return true;
			}

			if(firstPicTime ==2)
			{
				if(iconPosition2 == 0)
				{
					if(planeFlag == 1){planeFlag = 3;return true;};
					planeFlag = planeFlag-1;

				}
				if(iconPosition2 == 1)
				{
					if(levelFlag == 1){levelFlag=3;return true;}
					levelFlag= levelFlag-1;
					return true;
				}
				if(iconPosition2 == 2)
				{
					voiceFlag = voiceFlag*(-1);

					sounds.setVoiceFlag(voiceFlag);
					if(voiceFlag == 1)sounds.setMusicWillBePlay(0);
					else sounds.removeMusicWillBePlay(0);

					
					return true;
				}
				return true;
			}
		}

		if(22 == k)		// 右
		{
			if(firstPicTime == 3)
			{
				right = true;
				role.frame = 0;
			}
			if(firstPicTime ==1)
			{
				if(iconPosition == 3)
				{
					lggFlag = lggFlag*(-1);

				}
				return true;
			}

			if(firstPicTime ==2)
			{
				if(iconPosition2 == 0)
				{
					planeFlag = (planeFlag+1)%3 +1;
				}

				if(iconPosition2 == 1)
				{
					if(levelFlag == 3){levelFlag=1;return true;}
					levelFlag=levelFlag+1;
					return true;
				}

				if(iconPosition2 == 2)
				{
					voiceFlag = voiceFlag*(-1);
					sounds.setVoiceFlag(voiceFlag);
					if(voiceFlag == 1)
						sounds.setMusicWillBePlay(0);
					else sounds.removeMusicWillBePlay(0);
					
					return true;
				}

				return true;
			}
		}
		if(23==k)
		{
			if(firstPicTime == 3)
			{
				if(zdtype==1)//根据子弹类型，生成子弹1
				{
					vctBulletRole.addElement(new Bullet(zd1,13,22,role.x+role.width/2-6,role.y-5,0));
					vctBulletRole.addElement(new Bullet(zd1,13,22,role.x+role.width/2-6,role.y-5,2));


				}
				if(zdtype==2)//根据子弹类型，生成子弹2
				{

					for(int i = 0; i < 3; i++)
					{
						vctBulletRole.addElement(new Bullet(zd2,31,16,role.x+role.width/2-15,role.y-5,i));

					}


				}
				if(zdtype==3)//根据子弹类型，生成子弹3
				{
					vctBulletRole.addElement(new Bullet(zd3,29,21,role.x+role.width/2-14,role.y-5,1));

				}
				if(zdtype==4)//根据子弹类型，生成子弹4
				{
					vctBulletRole.addElement(new Bullet(zd4,18,22,role.x+role.width/2-9,role.y-5,0));
					vctBulletRole.addElement(new Bullet(zd4,18,22,role.x+role.width/2-9,role.y-5,1));
					vctBulletRole.addElement(new Bullet(zd4,18,22,role.x+role.width/2-9,role.y-5,2));

				}
			}
			if(firstPicTime == 1)
			{
				if(iconPosition == 0)
				{
					firstPicTime = 3;
					if(planeFlag==1)//根据所选的飞机，生成相应的飞机
					{
						//					role = new Plane(planex,32,32);
						return true;

					}
					if(planeFlag==2)
					{
						role = new Plane(planey,36,38);

					}
					if(planeFlag==3)
					{
						role = new Plane(planez,32,32);


					}
					role.setPos(150f, 440f);
					role.frame = 1;
					return true;
				}

				if(iconPosition == 1)
				{
					firstPicTime = 2;
					return true;
				}

				if(iconPosition == 4)
				{
					sounds.removeMusicWillBePlay(0);
					System.exit(0);
				}
			}

			if(firstPicTime ==2)
			{
				if(iconPosition2 == 3)
				{
					firstPicTime = 1;
					return true;
				}
			}
		}


		if(4 == k)
		{
			firstPicTime = 5;
		}

		return true;
	}

	@Override
	public boolean onKeyUp(int k, KeyEvent event)
	{

		if(k == 19)		// 上
		{
			up = false;
		}
		if(20 == k)		// 下
		{
			down = false;
		}

		if(21 == k)		// 左
		{
			left = false;
		}

		if(22 == k)		// 右
		{
			right = false;
		}


		role.frame = 1;
		return true;
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		float fx,fy,x,y;
		fx = (int)event.getRawX();
		fy = (int)event.getRawY();
		x = role.x;
		y = role.y;

		float x1;
		x1 = 8*(x-fx)/(y-fy);
		if(x1<0)x1 = -x1;
		if(x1>14)x1=14;


		if(x1<0)x1 = -x1;


		if(fx>x)role.x = x + x1;
		else
			role.x = x - x1;
		if(fy>y)
		{
			role.y = y+8;
		}

		if(fy<y)
		{
			role.y = y-8;
		}
		if(role.x<0)role.x=0;
		if(role.x>288)role.x=288;
		if(role.y<0)role.y=0;
		if(role.y>448)role.y=448;

		return super.onTouchEvent(event);
	}

	public void control()
	{
		int width;
		int heigh;
		int pwidth;
		int pheigh;
		width=this.getWidth();//取得屏幕宽度
		heigh=this.getHeight();//取得屏幕高度
		pwidth=bmpRole.getWidth()/3;//计算飞机的宽度
		pheigh=bmpRole.getHeight();//计算飞机的高度
		if(up)
		{
			role.y -= 12;
			if(role.y<=0)//若飞机出上界，则使飞机固定于上界处
				role.y=0;
		}

		if(down)
		{
			role.y += 12;
			if(role.y>=heigh-pheigh)//若飞机出下界，则使飞机固定于下界处

				role.y=heigh-pheigh;
			//System.out.println(role.y);

		}

		if(left)
		{
			role.x -= 12;
			if(role.x<=0)//若飞机出左界，则使飞机固定于左界处
				role.x=0;
		}

		if(right)
		{
			role.x += 12;
			if(role.x>=width-pwidth)//若飞机出右界，则使飞机固定于右界处
				role.x=width-pwidth;
		}
	}

	public void bombLogic(Canvas c,Paint p)
	{
		Plane b = null;
		for(int i = 0; i < vctBomb.size(); i++)
		{
			b = vctBomb.elementAt(i);
			b.drawPlane(c, p);
			b.nextFrame();
			if(b.frame == 0)//播放完一轮
			{
				b.alwaysReleaseBullet();
				vctBomb.removeElement(b);
			}
		}
	}

	public void roleBulletLogic(Canvas c,Paint p)
	{
		for(int i = 0; i < vctBulletRole.size(); i++)
		{
			Bullet b = vctBulletRole.elementAt(i);
			b.drawPlane(c, p);
			b.move();
			if(b.x<0||b.x>this.getWidth()||b.y<0||b.y>this.getHeight())
			{
				b.alwaysReleaseBullet();
				vctBulletRole.removeElement(b);
			}
			/*if(b.releaseBullet())
			{
				vctBulletRole.remove(i);
				i = i-1;
			}*/
		}

		//		System.out.println("vctBulletRole.size():"+vctBulletRole.size());
	}

	public void monsterLogic(Canvas c,Paint p)
	{
		int n;
		int b;
		Random rand=new Random();
		for(int i = 0; i < vctMonster.size(); i++)
		{

			n=rand.nextInt(250);//生成随机数，满足敌机子弹随机发射

			b=rand.nextInt(100);
			Monster m = vctMonster.elementAt(i);
			//vctBulletMonster.addElement(new Bullet(bmpBult1,18,16,m.x,m.y,m.m));
			m.drawPlane(c, p);
			m.move();
			if(m.y>=n-10&&m.y<=n+10&&m.m!=25&&m.m!=40)//当m.y满足一定时，生成子弹
			{
				if(m.m==3||m.m==12||m.m==13||m.m==28||m.m==29)
				{
					vctBulletMonster.addElement(new Bullet(bmpnpc1,18,16,m.x+m.width/2-5,m.y+m.height+1,m.m));
					vctBulletMonster.addElement(new Bullet(bmpnpc1,18,16,m.x+m.width/2-5,m.y+m.height+1,m.m+50));
				}
				if(m.m==4||m.m==5||m.m==14||m.m==26||m.m==27||m.m==35)
					vctBulletMonster.addElement(new Bullet(bmpnpc2,12,12,m.x+m.width/2-5,m.y+m.height+1,m.m));
				if(m.m==7||m.m==8||m.m==9||m.m==32||m.m==33||m.m==34||m.m==36)
					vctBulletMonster.addElement(new Bullet(bmpnpc3,12,12,m.x+m.width/2-5,m.y+m.height+1,m.m));
				if(m.m==6||m.m==10||m.m==11||m.m==30||m.m==31)
					vctBulletMonster.addElement(new Bullet(bmpnpc4,23,24,m.x+m.width/2-5,m.y+m.height+1,m.m));

			}
			if(m.y>=b+30&&m.y<=60)
			{
				if(m.m==25)
				{bosschangeb++;
				if(bosschangeb<=20)
				{
					vctBulletMonster.addElement(new Bullet(bmpbossbult1,23,24,m.x+20,m.y+100,m.m));
					vctBulletMonster.addElement(new Bullet(bmpbossbult1,23,24,m.x+80,m.y+100,m.m+50));
					vctBulletMonster.addElement(new Bullet(bmpbossbult2,9,14,m.x+20,m.y+100,m.m+51));
					vctBulletMonster.addElement(new Bullet(bmpbossbult2,9,14,m.x+80,m.y+100,m.m+52));
				}
				if(bosschangeb>=30)
				{
					for(int k=0;k<4;k++)
					{
						vctBulletMonster.addElement(new Bullet(bmpbossbult3,12,11,m.x+53,m.y+110,m.m+53));
					}


					if(bosschangeb==34)
						bosschangeb=0;

				}
				}
				if(m.m==40)
				{
					bosschangeb++;
					if(bosschangeb<=20)
					{
						vctBulletMonster.addElement(new Bullet(bmpbossbult4,21,18,m.x,m.y+40,m.m));
  						vctBulletMonster.addElement(new Bullet(bmpbossbult4,21,18,m.x+76,m.y+40,m.m+50));
  						vctBulletMonster.addElement(new Bullet(bmpbossbult5,21,18,m.x+20,m.y+60,m.m+51));
  						vctBulletMonster.addElement(new Bullet(bmpbossbult5,21,18,m.x+58,m.y+60,m.m+52));
					}
					if(bosschangeb>=30)
					{
						for(int k=0;k<4;k++)
						{
							vctBulletMonster.addElement(new Bullet(bmpbossbult3,12,11,m.x+39,m.y+85,m.m+53));
						}


						if(bosschangeb==34)
							bosschangeb=0;

					}
				}

			}
			if(m.x<-50||m.x>400||m.y<-100||m.y>500)//判断敌机越界就清除xzm
				vctMonster.removeElement(m);
			//vctBulletMonster.addElement(new Bullet(bmpnpc,18,16,m.x,m.y,m.m));
			//			if(m.x<0||m.x>this.getWidth()||m.y<0||m.y>this.getHeight())
			//			{
			//				vctMonster.removeElement(m);
			//			}
		}

	}
	public void MonsterBulletLogic(Canvas c,Paint p)
	{
		for(int i=0;i<vctBulletMonster.size();i++)
		{
			Bullet b=vctBulletMonster.elementAt(i);
			b.drawPlane(c, p);
			b.move();
			if(b.x<0||b.x>this.getWidth()||b.y<0||b.y>this.getHeight())
			{
				b.alwaysReleaseBullet();
				vctBulletMonster.removeElement(b);
			}
		}

	}
	boolean running;
	//线程体
	public void run()
	{
		sounds.playMusic(0);
//		chapter2 = 3;
		while(running)
		{
			

			if(!(bl&&dead))firstPicTime = 4;
			control();
			model();
			long l = System.currentTimeMillis();
			//			sounds.MusicManagement();
			draw();//drawFirstPic()
			//			System.out.println(System.currentTimeMillis() - l);
					try
						{
							Thread.sleep(10);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
		}
	}

	public Bitmap getBitmapById(int id)
	{
		Bitmap image = null;

		//得到当前的设备环境
		Context currentCtx = this.getContext();

		//得到资源管理器
		Resources res = currentCtx.getResources();
		InputStream is = res.openRawResource(id);
		image = BitmapFactory.decodeStream(is);


		return image;
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height)
	{

	}

	//启动线程
	public void surfaceCreated(SurfaceHolder holder)
	{
		running = true;
		Thread th = new Thread(this);
		th.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		running = false;
	}
	
	public void drawEnd(Canvas c,Paint p)
	{
		Bitmap bmp = this.getBitmapById(R.drawable.exit);
		Bitmap bmp1= getBitmapById(R.drawable.firstbackground);
		bmp1 = Bitmap.createBitmap(bmp1, 0, 0, 320, 480);

		p.setTextSize(31);
		p.setColor(0xff030309);
		//		p.setStyle(Style.FILL);

		c.drawBitmap(bmp1, 0, 0, p);
		if(lggFlag == 1)
			{c.drawText("you win!", 75, 210, p);
		    c.drawText("The end!", 75, 265, p);}
		else
			{c.drawText("赢得胜利", 75, 210, p);
			c.drawText("结束！", 75, 265, p);
			}
	}


	//廖同学定义和实现的函数，画第一个界面
	//廖同学定义和实现的函数，画第一个界面
	public void drawFirstPic(Canvas c,Paint p)
	{

		Bitmap bmp= getBitmapById(R.drawable.firstbackground);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 320, 480);
		c.drawBitmap(bmp, 0, 0, p);


		bmp = getBitmapById(R.drawable.chooseicon);
		c.drawBitmap(bmp, 80, iconPosition*55+130, p);


		p.setTextSize(25);
		p.setColor(0xff090909);


		if(lggFlag == 1)
		{
			c.drawText(stringList.get(0), 100, 143, p);
			c.drawText(stringList.get(1), 100, 198, p);
			c.drawText(stringList.get(2), 100, 253, p);
			c.drawText(stringList.get(3), 100, 308, p);
			c.drawText(stringList.get(4), 100, 363, p);
		}
		else 
		{
			c.drawText(stringList1.get(0), 100, 143, p);
			c.drawText(stringList1.get(1), 100, 198, p);
			c.drawText(stringList1.get(2), 100, 253, p);
			c.drawText(stringList1.get(3), 100, 308, p);
			c.drawText(stringList1.get(4), 100, 363, p);
		}

	}

	//廖同学定义和实现的函数，画第二个界面
	public void drawSecPic(Canvas c,Paint p)
	{
		p.setTextSize(25);
		p.setColor(0xff090909);

		//		String st1 = new String("level: ");
		//		String st2 = new String("audio on");
		//		String st3 = new String("audion off");
		//		String st4 = new String("go back");

		Bitmap bmp= getBitmapById(R.drawable.firstbackground);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 320, 480);
		c.drawBitmap(bmp, 0, 0, p);

		Bitmap plane1 = getBitmapById(R.drawable.plane1);
		Bitmap plane2 = getBitmapById(R.drawable.plane2);
		Bitmap plane3 = getBitmapById(R.drawable.plane3);

		plane1 = Bitmap.createBitmap(plane1, 30, 0, 30, 30);
		plane2 = Bitmap.createBitmap(plane2, 36, 0, 36, 38);
		plane3 = Bitmap.createBitmap(plane3, 32, 0, 32, 32);


		bmp = getBitmapById(R.drawable.chooseicon);
		c.drawBitmap(bmp, 80, iconPosition2*55+110, p);

		if(planeFlag == 1)
			c.drawBitmap(plane1, 100, 100, p);

		if(planeFlag == 2)
			c.drawBitmap(plane2, 100, 100, p);
		if(planeFlag == 3)
			c.drawBitmap(plane3, 100, 100, p);

		//		c.drawText(st1+levelFlag, 100, 183, p);
		//		if(voiceFlag == 1)
		//			c.drawText(st2, 100, 238, p);
		//		else
		//			c.drawText(st3, 100, 238, p);
		//		c.drawText(st4, 100, 293, p);

		if(lggFlag == 1)
		{
			c.drawText(stringList2.get(0)+levelFlag, 100, 183, p);
			if(voiceFlag == 1)
				c.drawText(stringList2.get(1), 100, 238, p);
			else
				c.drawText(stringList2.get(2), 100, 238, p);
			c.drawText(stringList2.get(3), 100, 293, p);
		}
		else
		{
			c.drawText(stringList2_.get(0)+levelFlag, 100, 183, p);
			if(voiceFlag == 1)
				c.drawText(stringList2_.get(1), 100, 238, p);
			else
				c.drawText(stringList2_.get(2), 100, 238, p);
			c.drawText(stringList2_.get(3), 100, 293, p);
		}
	}


	//获得string从strings.xml里
	public  String getStringById(int id)
	{
		Context currentCtx = this.getContext();
		Resources res = currentCtx.getResources();
		return res.getString(id);
	}

	public void drawChapterChange(Canvas c,Paint p)
	{
		Bitmap bmp= getBitmapById(R.drawable.firstbackground);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 320, 480);
		c.drawBitmap(bmp, 0, 0, p);
		p.setTextSize(20);
		if(lggFlag==1)
		{
			c.drawText("congratulations，good boy！", 30, 210, p);
			c.drawText("enter chapter 2", 30, 270, p);
		}
		else 
		{
			c.drawText("恭喜您，过关！", 30, 210, p);
			c.drawText("进入第二关", 30, 270, p);
		}
	}

	public void drawAboutPic(Canvas c,Paint p)
	{
		Bitmap bmp = this.getBitmapById(R.drawable.aboutpic);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 320, 480);
		//		System.out.println("------------");
		//		System.out.println("------------");
		c.drawBitmap(bmp, 0, 0, p);
	}

	public void drawExitPic(Canvas c,Paint p)
	{
		Bitmap bmp = this.getBitmapById(R.drawable.exit);
		Bitmap bmp1= getBitmapById(R.drawable.firstbackground);
		bmp1 = Bitmap.createBitmap(bmp1, 0, 0, 320, 480);

		bl = true ;
		dead = true;
		p.setTextSize(31);
		p.setColor(0xff030309);
		//		p.setStyle(Style.FILL);

		c.drawBitmap(bmp1, 0, 0, p);
		c.drawBitmap(bmp, 20, 120, p);
		if(lggFlag == 1)
			c.drawText(over, 130, 210, p);
		else
			c.drawText(over1, 130, 210, p);

	}

	public void drawFifthPic(Canvas c,Paint p)
	{

		Bitmap bmp= getBitmapById(R.drawable.firstbackground);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 320, 480);
		c.drawBitmap(bmp, 0, 0, p);


		bmp = getBitmapById(R.drawable.chooseicon);
		System.out.println(iconPosition5);
		System.out.println(iconPosition5);
		System.out.println(iconPosition5);
		c.drawBitmap(bmp, 80, iconPosition5*55+130, p);

		p.setTextSize(25);
		p.setColor(0xff090909);


		if(lggFlag == 1)
		{
			c.drawText("continue", 100, 143, p);
			if(voiceFlag == 1)
				c.drawText(stringList2.get(1), 100, 198, p);
			else
				c.drawText(stringList2.get(2), 100, 198, p);
			c.drawText(stringList.get(4), 100, 248, p);
		}
		else 
		{
			//			c.drawText(stringList1.get(0), 100, 143, p);
			//			c.drawText(stringList1.get(1), 100, 198, p);
			//			c.drawText(stringList1.get(2), 100, 253, p);
			//			c.drawText(stringList1.get(3), 100, 308, p);
			//			c.drawText(stringList1.get(4), 100, 363, p);
			c.drawText("继续游戏", 100, 143, p);
			if(voiceFlag == 1)
				c.drawText(stringList2_.get(1), 100, 198, p);
			else
				c.drawText(stringList2_.get(2), 100, 198, p);
			c.drawText(stringList1.get(4), 100, 248, p);
		}

	}

	public void ClearAll()
	{
		Monster m=null;
		Bullet bRole=null;
		Bullet bMonster=null;
		ZdType daoju;
		Plane p=null;
		for(int i=0;i<vctMonster.size();i++)
		{
			m=vctMonster.elementAt(i);
			vctMonster.removeElement(m);
		}
		for(int i=0;i<vctBulletMonster.size();i++)
		{
			bMonster=vctBulletMonster.elementAt(i);
			vctBulletMonster.removeElement(bMonster);
		}
		for(int i=0;i<vctBulletRole.size();i++)
		{
			bRole=vctBulletRole.elementAt(i);
			vctBulletRole.removeElement(bRole);
		}
		for(int i=0;i<vctZdType.size();i++)
		{
			daoju=vctZdType.elementAt(i);
			vctZdType.removeElement(daoju);
		}
		for(int i=0;i<vctBomb.size();i++)
		{
			p=vctBomb.elementAt(i);
			vctBomb.removeElement(p);
		}
	}
}
