package com.mygdx.forkliftaone.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.forkliftaone.entity.BoxBase;
import com.mygdx.forkliftaone.entity.DoorSensor;
import com.mygdx.forkliftaone.entity.ForkliftActorBase;
import com.mygdx.forkliftaone.entity.FuelCan;
import com.mygdx.forkliftaone.entity.Sensor;
import com.mygdx.forkliftaone.maps.CustomTestMap;

public class SensorContactListener implements ContactListener {

    private int salary;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null)
            return;
        if (fa.getUserData() == null || fb.getUserData() == null)
            return;

        if (isSensorContact(fa, fb)){
            Sensor sensor;
            BoxBase rubbishBox;
            if (fa.getUserData() instanceof Sensor){
                sensor = (Sensor) fa.getUserData();
                rubbishBox = (BoxBase) fb.getUserData();
            } else {
                sensor = (Sensor) fb.getUserData();
                rubbishBox = (BoxBase) fa.getUserData();
            }

            sensor.trigger(rubbishBox.getPrice());
            salary += rubbishBox.getPrice();

        }

        if (isForkFuelCollide(fa, fb)){
            ForkliftActorBase forklift;
            FuelCan fuel;
            if (fa.getUserData() instanceof ForkliftActorBase){
                forklift = (ForkliftActorBase) fa.getUserData();
                fuel = (FuelCan) fb.getUserData();
            } else {
                forklift = (ForkliftActorBase) fb.getUserData();
                fuel = (FuelCan) fa.getUserData();
            }

            forklift.setHasFuel(true);
            fuel.setActive(true);

        }

        if (isDoorOpen(fa, fb)){
            BoxBase boxBase;
            DoorSensor doorSensor;
            if (fa.getUserData() instanceof BoxBase){
                boxBase = (BoxBase) fa.getUserData();
                doorSensor = (DoorSensor) fb.getUserData();
            } else {
                boxBase = (BoxBase) fb.getUserData();
                doorSensor = (DoorSensor) fa.getUserData();
            }

            doorSensor.getMap().openDoor();

        }


    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null)
            return;
        if (fa.getUserData() == null || fb.getUserData() == null)
            return;

        if (isSensorContact(fa, fb)){
            Sensor sensor;
            BoxBase rubbishBox;
            if (fa.getUserData() instanceof Sensor){
                sensor = (Sensor) fa.getUserData();
                rubbishBox = (BoxBase) fb.getUserData();
            } else {
                sensor = (Sensor) fb.getUserData();
                rubbishBox = (BoxBase) fa.getUserData();
            }

            sensor.untrigger(rubbishBox.getPrice());
            salary -= rubbishBox.getPrice();

        }

        if (isForkFuelCollide(fa, fb)){
            ForkliftActorBase forklift;
            FuelCan fuel;
            if (fa.getUserData() instanceof ForkliftActorBase){
                forklift = (ForkliftActorBase) fa.getUserData();
                fuel = (FuelCan) fb.getUserData();
            } else {
                forklift = (ForkliftActorBase) fb.getUserData();
                fuel = (FuelCan) fa.getUserData();
            }

            forklift.setHasFuel(false);
            fuel.setActive(false);

        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isSensorContact(Fixture a, Fixture b){
        if (a.getUserData() instanceof BoxBase || b.getUserData() instanceof BoxBase){
            if (a.getUserData() instanceof Sensor || b.getUserData() instanceof Sensor){
                return true;
            }
        }
        return false;

    }

    private boolean isForkFuelCollide(Fixture a, Fixture b){
        if (a.getUserData() instanceof FuelCan || b.getUserData() instanceof FuelCan){
            if (a.getUserData() instanceof ForkliftActorBase || b.getUserData() instanceof ForkliftActorBase){
                return true;
            }
        }
        return false;

    }

    private boolean isDoorOpen(Fixture a, Fixture b){
        if (a.getUserData() instanceof BoxBase || b.getUserData() instanceof BoxBase){
            if (a.getUserData() instanceof DoorSensor || b.getUserData() instanceof DoorSensor){
                return true;
            }
        }
        return false;

    }

    public int getSalary() {
        return salary;
    }
}
