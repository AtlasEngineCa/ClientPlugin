package ca.atlasengine.cutscenes;

public final class CutsceneSubFrame {
    private String yaw;
    private String pitch;
    private String transitionTime;
    private String waitTime;

    public CutsceneSubFrame(float yaw, float pitch, int transitionTime, int waitTime) {
        this.yaw = String.valueOf(yaw);
        this.pitch = String.valueOf(pitch);
        this.transitionTime = String.valueOf(transitionTime);
        this.waitTime = String.valueOf(waitTime);
    }

    public String yaw() {
        return yaw;
    }

    public String pitch() {
        return pitch;
    }

    public String transitionTime() {
        return transitionTime;
    }

    public String waitTime() {
        return waitTime;
    }

    public void setYaw(String yaw) {
        this.yaw = yaw;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public void setTransitionTime(String transitionTime) {
        this.transitionTime = transitionTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
}
