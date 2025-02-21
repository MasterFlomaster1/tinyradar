package dev.mf1.tinyradar.gui;

import dev.mf1.tinyradar.core.util.DummyObjects;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkersTest {

    @Test
    void demo() {
        var a1 = DummyObjects.getList().get(0);
        var a2 = DummyObjects.getList().get(1);
        var a3 = DummyObjects.getList().get(2);

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        var m1 = Markers.get(a1);
        var m2 = Markers.get(a2);
        var m3 = Markers.get(a3);

        System.out.println(m1.hashCode());
        System.out.println(m2.hashCode());
        System.out.println(m3.hashCode());

    }

    @Test
    void demo2() {
        Markers.load();

        var a1 = DummyObjects.getList().get(0);
        var a2 = DummyObjects.getList().get(1);
        var a3 = DummyObjects.getList().get(2);

        a2.setT("A339");
        a3.setT("A339");

        var m1 = Markers.resolve(a1);
        var m2 = Markers.resolve(a2);
        var m3 = Markers.resolve(a3);

        System.out.println(m1.hashCode());
        System.out.println(m2.hashCode());
        System.out.println(m3.hashCode());

    }

}