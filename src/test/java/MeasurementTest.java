import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeasurementTest {

    @Test
    void test() {
        TimeMeasurement measurement = new TimeMeasurement();
        Timer x1 = measurement.start("proces X");
        sleep(10);
        Timer x2 = measurement.start("proces X");
        sleep(10);
        Timer x3 = measurement.start("proces X");
        Timer y1 = measurement.start("proces Y");
        sleep(10);

        assertEquals(4, measurement.runningProcesses());
        x1.end();
        x2.end();
        x3.end();
        y1.end();
        assertEquals(0, measurement.runningProcesses());
        Timer x4 = measurement.start("proces X");
        assertEquals(1, measurement.runningProcesses());

        System.out.println(measurement.reportAverage());

        measurement.start("proces Y").end();

        System.out.println("Running processes: " + measurement.runningProcesses());
        System.out.println("Ended processes:   " + measurement.endedProcesses());
        System.out.println("All processes:     " + measurement.allProcesses());

        measurement.removeEnded();

        System.out.println("After removing ended: ");
        System.out.println("Running processes: " + measurement.runningProcesses());
        System.out.println("Ended processes:   " + measurement.endedProcesses());
        System.out.println("All processes:     " + measurement.allProcesses());

        measurement.removeAll();
        System.out.println("After removing all: ");
        System.out.println("Running processes: " + measurement.runningProcesses());
        System.out.println("Ended processes:   " + measurement.endedProcesses());
        System.out.println("All processes:     " + measurement.allProcesses());

    }

    @Test
    public void randomProcessName() {
        TimeMeasurement timeMeasurement = new TimeMeasurement();
        new Random(System.currentTimeMillis())
                .ints(1, 8)
                .limit(10000)
                .forEach(i -> {
                    timeMeasurement.start("Proces " + i);
                    timeMeasurement.findAnyRunningTimer().ifPresent(t -> t.end());
                });
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}