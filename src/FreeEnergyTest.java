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
        int n = 26;
        ArrayList<Tiling> tilings = TilingOutput.readTilings("Free Energy_10");
        try {
            PrintWriter energyWriter = new PrintWriter(new FileWriter("energy/" + 10 + "_old"));

            for (Tiling tiling : tilings) {
            /*    FileWriter fileWriter = new FileWriter("correlators/"  + tiling.n + "_" + tiling.T + "_" + "Corelators.dat");
                PrintWriter printWriter = new PrintWriter(fileWriter);
                for (int i = n / 4; i < 3 * n / 4; i++) {
                    printWriter.println(tiling.correlators[i][n]);
                }
                printWriter.close();*/
                energyWriter.println(tiling.T + " " + tiling.averageEnergy + " " + tiling.capacity() + " " + tiling.averageEnergySquared + " " + FreeEnergy.realAverageEnergy(tiling.T, tiling.n));
            }
            energyWriter.close();
        }
        catch (Exception ex) {

        }


    }

    @Test
    public void freeEnergy() throws Exception {
        for (int n = 4; n <= 42; n+=2) {
            CorrectFreeEnergy.generateTilings(n, 2*n, 200);
            System.out.println(n);
        }
        //System.out.println(FreeEnergy.freeEnergy(4, 20, 200));
    }

    @Test
    public void freeEnergyAll() throws Exception {
        FileWriter scalingFileWriter = new FileWriter("scaling.dat");
        PrintWriter scalingPrintWriter = new PrintWriter(scalingFileWriter);
        for (int n = 4; n <= 22; n+=2) {
            ArrayList<CorrectTiling> tilings = CorrectTilingOutput.readTilings("Correct_Results/Free_Energy_"+n + "_correct");
            ArrayList<Double> FreeEnergies = CorrectFreeEnergy.freeEnergies(tilings);
            //System.out.println(n);
            //FileWriter fileWriter = new FileWriter("freeEnergies/" + n +".dat");
            //PrintWriter printWriter = new PrintWriter(fileWriter);
            double dist = 1;
            int x = 0;
            for (int k = 0; k < FreeEnergies.size(); k++) {
                //printWriter.println(tilings.get(k+1).T + " " + FreeEnergies.get(k)/(n*n));
                if (Math.abs(5*tilings.get(k).T/2 - n) < 0.2) {
                    System.out.println(n);
                    if (dist > Math.abs(5*tilings.get(k).T/2 - n)) {
                        dist = Math.abs(5*tilings.get(k).T/2 - n);
                        x = k;
                    }
                }


            }
            scalingPrintWriter.println(1.0/n + " " + FreeEnergies.get(x)/(n*n) + " " + tilings.get(x).T*CorrectFreeEnergy.realEnergy(tilings.get(x).T, n));
            //printWriter.close();
        }
        scalingPrintWriter.close();
    }

    @Test
    public void entropy() throws Exception {
        ArrayList<Tiling> tilings = TilingOutput.readTilings("Free Energy_4");
        FileWriter fileWriter = new FileWriter("energy/entropy_4.dat");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ArrayList<Double> entropy = FreeEnergy.entropy(tilings);
        for (int i = 0; i< tilings.size()-1; i++) {
            printWriter.println(tilings.get(i+1).T + " " + entropy.get(i));
        }
        printWriter.close();

    }

    @Test
    public void generateFreeEnergy() throws Exception {
        for (int n = 4; n <= 4; n+=2) {
            //int n = 40;
            if (n==10) continue;
            ArrayList<Tiling> tilings = TilingOutput.readTilings("Free Energy_" + n);
            FileWriter fileWriter = new FileWriter("energy/Free Energy_" + n + ".dat");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ArrayList<Double> energies = FreeEnergy.freeEnergies(tilings);
            System.out.println(FreeEnergy.realEnergy(0.2, n));
            for (int i = 0; i < tilings.size() - 1; i++) {
                printWriter.println(tilings.get(i + 1).T + " " + tilings.get(i+1).averageEnergy + " " + tilings.get(i+1).averageEnergySquared +" " + tilings.get(i+1).capacity() + " "+ energies.get(i)/(n*n*tilings.get(i+1).T) +  " " + FreeEnergy.realEnergy(tilings.get(i+1).T, n) + " " + (energies.get(i) / (n * n * tilings.get(i + 1).T) - FreeEnergy.realEnergy(tilings.get(i + 1).T, n)));// - 100*FreeEnergy.realEnergy(0.2, 10)/0.2));//0.038));
            }
            printWriter.close();
        }
    }

    @Test
    public void generateCorrectFreeEnergy() throws Exception {
        for (int n = 20; n <= 22; n+=2) {
            //int n = 40;
            //if (n!=12) continue;
            ArrayList<CorrectTiling> tilings = CorrectTilingOutput.readTilings("Correct_Results/Free_Energy_" + n+"_correct");
            FileWriter fileWriter = new FileWriter("Correct_results/energy/Free_Energy_" + n + "_correct.dat");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ArrayList<Double> energies = CorrectFreeEnergy.freeEnergies(tilings);
            System.out.println(FreeEnergy.realEnergy(0.2, n));
            for (int i = 0; i < tilings.size()-1; i++) {
                printWriter.println(tilings.get(i).T + " " + tilings.get(i).averageEnergy + " " + tilings.get(i).averageEnergySquared + " " + tilings.get(i).capacity() + " " + energies.get(i)/(n*n*tilings.get(i).T) + " " + FreeEnergy.realEnergy(tilings.get(i).T, n) + " " + (energies.get(i) / (n * n * tilings.get(i).T) - FreeEnergy.realEnergy(tilings.get(i).T, n)));// - 100*FreeEnergy.realEnergy(0.2, 10)/0.2));//0.038));
            }

            CorrectTiling tiling = tilings.get(tilings.size()-1);
            //LozengePlot.saveImage(tiling.to3dLattice(tiling.getAverageConfiguration()), "Correct_Results/Plots/Plot" + n);
            //LozengePlot.saveImage(tilings.get(tilings.size()-1).getAverageConfiguration(), "Plot" + n);
            printWriter.close();
        }
    }
}