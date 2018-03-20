import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
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
        System.out.println(FreeEnergy.freeEnergy(4, 20, 200));
    }

    @Test
    public void freeEnergyAll() throws Exception {
        ArrayList<Tiling> tilings = TilingOutput.readTilings("Free Energy_4");
        ArrayList<Double> freeEnergies = FreeEnergy.freeEnergies(tilings);
        for (double freeEnergy: freeEnergies) {
            System.out.println(freeEnergy);
        }
    }

}