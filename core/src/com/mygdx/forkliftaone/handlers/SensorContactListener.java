package com.mygdx.forkliftaone.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.forkliftaone.entity.RubbishBox;
import com.mygdx.forkliftaone.entity.Sensor;

public class SensorContactListener implements ContactListener {
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
            RubbishBox rubbishBox;
            if (fa.getUserData() instanceof Sensor){
                sensor = (Sensor) fa.getUserData();
                rubbishBox = (RubbishBox) fb.getUserData();
            } else {
                sensor = (Sensor) fb.getUserData();
                rubbishBox = (RubbishBox) fa.getUserData();
            }

            sensor.trigger();

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
            RubbishBox rubbishBox;
            if (fa.getUserData() instanceof Sensor){
                sensor = (Sensor) fa.getUserData();
                rubbishBox = (RubbishBox) fb.getUserData();
            } else {
                sensor = (Sensor) fb.getUserData();
                rubbishBox = (RubbishBox) fa.getUserData();
            }

            sensor.untrigger();

        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isSensorContact(Fixture a, Fixture b){
        if (a.getUserData() instanceof RubbishBox || b.getUserData() instanceof RubbishBox){
            if (a.getUserData() instanceof Sensor || b.getUserData() instanceof Sensor){
                return true;
            }
        }
        return false;

    }
}
