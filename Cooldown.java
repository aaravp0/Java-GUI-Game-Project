import javax.swing.Timer;

public class Cooldown {
    private int totalTime;
    private int timeLeft;
    private int delay;

    private Timer timer;

    public Cooldown(int totalTime, int delay)
    {
        this.totalTime = totalTime;
        this.delay = delay;

        timeLeft = totalTime;
        timer = new Timer(delay, e -> updateState());
    }

    public void updateState()
    {
        timeLeft -= delay;
        if (timeLeft < 0)
        {
            timeLeft = totalTime;
            timer.stop();
        }
    }

    public boolean isActive()
    {
        return timer.isRunning();
    }

    public void startTimer()
    {
        timer.start();
    }
}
