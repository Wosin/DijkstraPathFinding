package org.dominwos.model;

public class Station {

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Station) obj).name);
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                '}';
    }

    public Station(String name) {
        this.name = name;
    }

    private String name;
}
