
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MyRunnable implements Runnable {
    CorrectTiling tiling;

    MyRunnable(CorrectTiling tiling) {
        this.tiling = tiling;
    }

    @Override
    public void run() {
        tiling.metropolis(10000000);
    }
}


public class Main {
    public static void main(String[] args) throws Exception{
        final int NTHREADS = 4;
        int iterations = 300;
        for (int n = 4; n <= 42; n+=2) {
            double t = 3*n;
            ArrayList<CorrectTiling> tilings = new ArrayList<>();
            for (int i = 0; i <= iterations; i++) {
                CorrectTiling tiling = new CorrectTiling(n);
                double temperature = 0.01 + i * (t - 0.01) / iterations;
                tiling.setTemp(temperature);
                tilings.add(tiling);
            }

            ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
            for (CorrectTiling tiling: tilings) {
                Runnable worker = new MyRunnable(tiling);
                executor.execute(worker);
            }

            executor.shutdown();

            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println(n + " comlepeted");
            CorrectTilingOutput.saveTilings(tilings, "Free Energy_" + n + "_correct");

        }
    }
}
