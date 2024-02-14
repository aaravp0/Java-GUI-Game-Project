import javax.swing.Timer;

public class Cooldown
{
    private int totalTime;
    private int timeLeft;
    private int delay;

    private Timer timer;

    // gets the values for the cooldown
    public Cooldown(int totalTime, int delay)
    {
        this.totalTime = totalTime;
        this.delay = delay;

        timeLeft = totalTime;
        timer = new Timer(delay, e -> updateState());
    }

    // updates the state of the cooldown
    public void updateState()
    {
        timeLeft -= delay;
        if (timeLeft < 0)
        {
            timeLeft = totalTime;
            timer.stop();
        }
    }

    // returns a boolean telling that the cooldown is occuring
    public boolean isActive()
    {
        return timer.isRunning();
    }

    // starts the timer
    public void startTimer()
    {
        timer.start();
    }
}
