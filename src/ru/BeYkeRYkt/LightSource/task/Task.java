package ru.BeYkeRYkt.LightSource.task;

public abstract class Task {

    private boolean started;

    public void start() {
        if (!isStarted()) {
            setStarted(true);
        }
    }

    public void stop() {
        if (isStarted()) {
            setStarted(false);
        }
    }

    public abstract String getId();

    public abstract void doTick();

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean start) {
        this.started = start;
    }
}