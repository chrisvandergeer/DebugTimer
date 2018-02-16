import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TimeMeasurement {

    private static final int MAX_TIMERS = 1000;

    private Logger logger = Logger.getLogger(getClass());

    private List<Timer> timers = new ArrayList<>();

    public Timer start(String name) {
        if (timers.size() >= MAX_TIMERS) {
            logger.info(reportAverage());
            removeEnded();
        }
        Timer timer = Timer.start(name);
        timers.add(timer);
        return timer;
    }

    public Map<String, Double> calculateAverage() {
        return timers.stream()
                .filter(Timer::isEnded)
                .map(Timer::setReported)
                .collect(Collectors.groupingBy(Timer::getName, Collectors.averagingDouble(Timer::getExecutionTime)));
    }

    public String reportAverage() {
        StringBuilder builder = new StringBuilder(String.format("%n"));
        calculateAverage().entrySet()
                .forEach(entry -> builder.append(String.format("%s - %s%n", entry.getKey(), entry.getValue())));
        return builder.toString();
    }

    public long runningProcesses() {
        return timers.stream().filter(timer -> !timer.isEnded()).count();
    }

    public long endedProcesses() {
       return timers.stream().filter(timer -> timer.isEnded()).count();
    }

    public long allProcesses() {
        return timers.stream().count();
    }

    public void removeAll() {
        timers.clear();
    }

    public void removeEnded() {
        timers.removeIf(Timer::isReported);
    }

    public Optional<Timer> findAnyRunningTimer() {
        return timers.stream().filter(t -> !t.isEnded()).findAny();
    }

}
