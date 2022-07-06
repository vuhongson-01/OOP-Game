package main;

import java.util.ArrayList;

import entity.Boss1;
import entity.Boss5;
import entity.Boss4;
import entity.Boss3;
import entity.Boss2;
import entity.Entity;
import entity.Monster;

public class CreateMonster {
	
//	Entity[] listEntity = new Entity()[];
	Entity [][] listEntities = new Entity [5][50];
	
	public CreateMonster(GamePanel gp, int hard) {
		// TODO Auto-generated constructor stub
		listEntities[0][0] = new Boss5(gp, hard, 30*48, 6*48);
		listEntities[0][1] = new Monster(gp, hard, 500, 500);
	}

}
