import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CorrectFreeEnergyTest {

    @Test
    public void test() throws Exception {

        FileInputStream fileInput = new FileInputStream("Free Energy");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInput);
        int n = 26;
        ArrayList<CorrectTiling> tilings = CorrectTilingOutput.readTilings("Free Energy_10_correct");
        try {
            PrintWriter energyWriter = new PrintWriter(new FileWriter("energy/" + 10));

            for (CorrectTiling tiling : tilings) {
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
        for (int n = 4; n <= 4; n+=2) {
            //CorrectFreeEnergy.generateCorrectTilings(n, 15, 50);
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
                if (Math.abs(tilings.get(k+1).T - 0.4*n) < 0.2) {
                    System.out.println(n);
                    scalingPrintWriter.println(1.0/n + " " + FreeEnergies.get(k)/(-tilings.get(k+1).T*n*n));
                }
            }
            printWriter.close();
        }
        scalingPrintWriter.close();
    }

    @Test
    public void entropy() throws Exception {
        ArrayList<Tiling> tilings = TilingOutput.readTilings("Free Energy_4");
        FileWriter fileWriter = new FileWriter("energy/entryopy_4.dat");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ArrayList<Double> entropy = FreeEnergy.entropy(tilings);
        for (int i = 0; i< tilings.size()-1; i++) {
            printWriter.println(tilings.get(i+1).T + " " + entropy.get(i));
        }
        printWriter.close();

    }

    @Test
    public void generateFreeEnergy() throws Exception {
        ArrayList<CorrectTiling> tilings = CorrectTilingOutput.readTilings("Free Energy_10_correct");
        FileWriter fileWriter = new FileWriter("energy/Free Energy_10.dat");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ArrayList<Double> energies = CorrectFreeEnergy.freeEnergies(tilings);
        for (int i = 0;i < tilings.size()-1; i++) {
            printWriter.println(tilings.get(i).T + " " + (energies.get(i)/(100*tilings.get(i).T) - CorrectFreeEnergy.realEnergy(tilings.get(i).T, 10)));// - tilings.get(0).averageEnergy/(100*0.2) + CorrectFreeEnergy.realEnergy(0.2, 10)));
        }
        printWriter.close();
    }
}