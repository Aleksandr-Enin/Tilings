import org.junit.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TilingTest {
    @org.junit.Test
    public void metropolis() throws Exception {
        int n = 10;
        int m = n;
        CorrectTiling tiling = new CorrectTiling(n);
        tiling.setTemp(10000);
        LozengePlot.saveImage(tiling.to3dLattice(tiling.lattice), "initial");
        System.out.println(tiling);
        tiling.metropolis(100000);
        int[][] t = tiling.to3dLattice(tiling.getAverageConfiguration());
        LozengePlot.saveImage(tiling.to3dLattice(tiling.getAverageConfiguration()), "final");
        System.out.println(tiling);

        double[][] correlators = tiling.correlators;
        for (int i =0 ; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                System.out.print(t[i][j] + " ");
                if (t[i][j] < 10) System.out.print(" ");
            }
            System.out.println();
        }
        FileWriter fileWriter = new FileWriter("correlators/" + tiling.n + "_" + tiling.T + "_" + "Corelators.dat");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (int i = n/4; i < (3*tiling.n)/4; i++) {
                printWriter.println(tiling.correlators[i][m]);
            printWriter.println();
        }
        printWriter.close();


    }

    @org.junit.Test
    public void test() throws Exception {
        ArrayList<Tiling> oldTilings = new ArrayList<>();
        ArrayList<CorrectTiling> tilings = new ArrayList<>();
        for (int t = 20; t<= 40; t+=5) {
            Tiling oldTiling = new Tiling(25);
            oldTiling.setTemp(t);
            CorrectTiling tiling = new CorrectTiling(25);
            tiling.setTemp(t);
            LozengePlot.saveImage(tiling.to3dLattice(tiling.lattice), "correctInitial");
            tiling.metropolis(1000000);
            System.out.println("DONE");
            oldTiling.metropolis(1000000);
            LozengePlot.saveImage(tiling.to3dLattice(tiling.getAverageConfiguration()), "correctFinal");
            LozengePlot.saveImage(oldTiling.to3dLattice(oldTiling.getAverageConfiguration()), "final");
            System.out.println(oldTiling.averageEnergy + " " + oldTiling.averageEnergySquared + " " + oldTiling.capacity()/(25.0*25.0));
            System.out.println(tiling.averageEnergy + " " + tiling.averageEnergySquared + " " + tiling.capacity()/(25.0*25.0));
            oldTilings.add(oldTiling);
            tilings.add(tiling);
        }
        TilingOutput.saveTilings(oldTilings, "oldT");
        TilingOutput.saveTilingsCorrect(tilings, "newT");

    }

    @Test
    public void generateCapacity() throws Exception {
        Tiling tiling = new Tiling(10);
        PrintWriter energyWriter = new PrintWriter(new FileWriter("energy/" + 10));
        for (double t =  0.5; t<10; t+=0.5) {
            tiling.setTemp(t);
            tiling.metropolis(100000);
            energyWriter.println(t + " " + tiling.capacity());
        }
        energyWriter.close();

    }

}