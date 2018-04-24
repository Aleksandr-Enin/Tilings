import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FreeEnergyTest {

    @Test
    public void test() throws Exception {

        FileInputStream fileInput = new FileInputStream("Free Energy");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInput);
        int n = 20;
        ArrayList<Tiling> tilings = (ArrayList<Tiling>) objectInputStream.readObject();
        try {
            PrintWriter energyWriter = new PrintWriter(new FileWriter("energy/" + n));

            for (Tiling tiling : tilings) {
            /*    FileWriter fileWriter = new FileWriter("correlators/"  + tiling.n + "_" + tiling.T + "_" + "Corelators.dat");
                PrintWriter printWriter = new PrintWriter(fileWriter);
                for (int i = n / 4; i < 3 * n / 4; i++) {
                    printWriter.println(tiling.correlators[i][n]);
                }
                printWriter.close();*/
                energyWriter.println(tiling.T + " " + tiling.averageEnergy + " " + tiling.capacity() + " " + tiling.averageEnergySquared);
            }
            energyWriter.close();
        }
        catch (Exception ex) {

        }


    }

    @Test
    public void freeEnergy() throws Exception {
        for (int n = 36; n <= 50; n+=2) {
            FreeEnergy.generateTilings(n, 2*n, 200);
            System.out.println(n);
        }
        //System.out.println(FreeEnergy.freeEnergy(4, 20, 200));
    }

    @Test
    public void freeEnergyAll() throws Exception {
        FileWriter scalingFileWriter = new FileWriter("scaling.dat");
        PrintWriter scalingPrintWriter = new PrintWriter(scalingFileWriter);
        for (int n = 4; n <= 42; n+=2) {
            ArrayList<Tiling> tilings = TilingOutput.readTilings("Free Energy_"+n);
            ArrayList<Double> FreeEnergies = FreeEnergy.freeEnergies(tilings);
            //System.out.println(n);
            FileWriter fileWriter = new FileWriter("freeEnergies/" + n +".dat");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int k = 0; k < FreeEnergies.size(); k++) {
                printWriter.println(tilings.get(k+1).T + " " + FreeEnergies.get(k)/(n*n));
                if (Math.abs(tilings.get(k+1).T - n) < 0.2) {
                    System.out.println(n);
                    scalingPrintWriter.println(1/tilings.get(k+1).T + " " + FreeEnergies.get(k)/(-tilings.get(k+1).T*n*n));
                }
            }
            printWriter.close();
        }
        scalingPrintWriter.close();
    }
}