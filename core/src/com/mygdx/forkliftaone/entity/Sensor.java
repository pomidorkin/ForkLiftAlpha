package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.forkliftaone.config.GameConfig;

public class Sensor {

    private int salary;

    public Sensor(World world, float x, float y, float width, float height){
        Body box;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(x, y); // Should be obtained from the map

        box = world.createBody(bodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width, height);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = ps;
        fixDef.isSensor = true;
        box.createFixture(fixDef).setUserData(this); // required for collision

        ps.dispose();
    }

    // (To Do) Implement saving logic

    public void trigger(){
        salary += 100;
        System.out.println("Balance: " + salary);
    }

    public void untrigger(){
        salary -= 100;
        System.out.println("Balance: " + salary);
    }

    public int getSalary() {
        return salary;
    }
}