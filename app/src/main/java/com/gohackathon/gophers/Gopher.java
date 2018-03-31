package com.gohackathon.gophers;

public class Gopher {
    private int lifeTime;
    private final int maxLifeTime;
    private boolean isAlive;
    private final int id;
    private final int line;
    private int holeId = 0;

    public Gopher(int id, int line, int maxLifeTime) {
        this.id = id;
        this.line = line;
        this.maxLifeTime = maxLifeTime;
        lifeTime = maxLifeTime;
        isAlive = true;
    }

    public void setHole(int holeId) {
        this.holeId = holeId;
    }

    public int getHole() {
        return holeId;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void decrLifeTime() {
        lifeTime --;
    }

    public void resetTime() {
        lifeTime = maxLifeTime;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        isAlive = false;
    }

    public void setAlive() {
        isAlive = true;
        resetTime();
    }

    public int getId() {
        return id;
    }

    public int getLine() {
        return line;
    }
}
