package net.bogdanvalentin.sleepanywhere;

public class SleepAnywhereConfig {
    public Effect hunger = new Effect(120, 1);
    public Effect nausea = new Effect(20, 1);
    public Effect blindness = new Effect(10, 1);
    public Effect darkness = new Effect(30, 1);
    public Effect miningFatigue = new Effect(90, 1);
    public Effect weakness = new Effect(90, 1);
    public Effect slowness = new Effect(60, 1);
    public boolean setSpawnPoint = false;
    public boolean sleepThroughThunderstorms = true;

    public static class Effect {
        public int seconds;
        public int level;

        public Effect(int seconds, int level) {
            this.seconds = seconds;
            this.level = level;
        }
    }
}
