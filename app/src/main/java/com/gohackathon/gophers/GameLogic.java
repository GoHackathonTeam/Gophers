package com.gohackathon.gophers;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private final List<Gopher> gophers;
    private final List<Integer> freeTopHoles;
    private final List<Integer> freeBottomHoles;
    private int score;
    private int lives = 3;
    private final Random random;
    private boolean gopherMissed = false;

    /**
     * @param gophers takes a gopher's ID and his line (1-top, 2-bottom)
     */

    public GameLogic(boolean difficultyHard, List<Pair<Integer, Integer>> gophers,
                     List<Integer> freeTopHoles, List<Integer> freeBottomHoles) {
        int maxLifeTime = difficultyHard? 2: 7;

        this.gophers = new ArrayList<>();
        for (int i = 0; i < gophers.size(); i++) {
            this.gophers.add(new Gopher(gophers.get(i).first, gophers.get(i).second, maxLifeTime));
        }

        this.freeTopHoles = freeTopHoles;
        this.freeBottomHoles = freeBottomHoles;

        random = new Random();
    }

    public void setGophersHoles(List<Integer> holesIds) {
        if (holesIds.size() != gophers.size()) {
            throw new IllegalArgumentException(
                    "Amount of holes should be the same as amount of gophers.");
        }

        for (int i = 0; i < holesIds.size(); i++) {
            gophers.get(i).setHole(holesIds.get(i));
        }
    }

    public void onGopherClick(int id) {
        score ++;
        for (Gopher gopher: gophers) {
            if (gopher.getId() == id) {
                gopher.kill();
                moveGopher(gopher);
                break;
            }
        }
    }

    public List<Gopher> getGophers() {
        return gophers;
    }

    public String getScore() {
        return ((Integer)score).toString();
    }

    public int getLives() {
        return lives;
    }

    public boolean isGopherMissed() {
        return gopherMissed;
    }

    public void gopherUpdate() {
        gopherMissed = false;
        for (Gopher gopher: gophers) {
            gopher.decrLifeTime();
            if (gopher.getLifeTime() == 0 && gopher.isAlive()) {
                lives --;
                gopherMissed = true;
            }

            if (gopher.getLifeTime() <= 0) {
                if (gopher.isAlive()) {
                    gopher.kill();
                    moveGopher(gopher);
                } else {
                    boolean ready = random.nextBoolean();
                    if (ready) {
                        gopher.setAlive();
                    }
                }
            }
        }
    }

    private void moveGopher(Gopher gopher){
        if (gopher.getLine() == 2) {
            int newHole = freeBottomHoles.get(random.nextInt(freeBottomHoles.size()));
            freeBottomHoles.remove(freeBottomHoles.indexOf(newHole));
            freeBottomHoles.add(gopher.getHole());
            gopher.setHole(newHole);
        } else {
            int newHole = freeTopHoles.get(random.nextInt(freeTopHoles.size()));
            freeTopHoles.remove(freeTopHoles.indexOf(newHole));
            freeTopHoles.add(gopher.getHole());
            gopher.setHole(newHole);
        }
    }
}
