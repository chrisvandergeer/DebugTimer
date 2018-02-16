public class Timer {

    private String name;
    private Long start;
    private Long end;

    private boolean reported;

    private Timer() {
        super();
    }

    public static Timer start(String name) {
        Timer timer = new Timer();
        timer.name = name;
        timer.start = System.currentTimeMillis();
        return timer;
    }

    public String getName() {
        return name;
    }

    public void end() {
        this.end = System.currentTimeMillis();
    }

    public long getExecutionTime() {
        return end - start;
    }

    public boolean isEnded() {
        return end != null;
    }

    public Timer setReported() {
        this.reported = true;
        return this;
    }

    public boolean isReported() {
        return this.reported;
    }

    @Override
    public String toString() {
        return String.format("%s: start %s, end, %s, total = %s", name, start, end, getExecutionTime());
    }

}
