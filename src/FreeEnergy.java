import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FreeEnergy {
    public static void generateTilings(int n, double t, int iterations) {
        double entropy = 0;
        ArrayList<Tiling> tilings = new ArrayList<>();
        for (int i = 0; i <= iterations; i++) {
            Tiling tiling = new Tiling(n);
            double temperature = 0.2 + i * (t - 0.2) / iterations;
            tiling.setTemp(temperature);
            //System.out.println("T = " + temperature);
            //tiling.metropolis(100000);
            tilings.add(tiling);
            //System.out.println(tiling.averageEnergy);
            //System.out.println(tiling.capacity());
        }
        System.out.println("initialized");
        tilings.parallelStream().forEach(tiling -> tiling.metropolis(100000));

        TilingOutput.saveOutput(tilings, "");
        TilingOutput.saveTilings(tilings, "Free Energy_" + n);
    }

    public static ArrayList<Double> entropy(ArrayList<Tiling> tilings) {
        double e;
        ArrayList<Double> entropy = new ArrayList<>();
        e = tilings.get(0).capacity()/(2*tilings.get(0).T);
        for (int i = 1; i < tilings.size(); i++) {
            Tiling tiling = tilings.get(i);
            entropy.add((tiling.T * e + tiling.capacity() / 2) / (i + 1));
            e += tiling.capacity() / tiling.T;
        }
        return entropy;
    }

    public static ArrayList<Double> freeEnergies(ArrayList<Tiling> tilings) {
        double entropy;
        ArrayList<Double> freeEnergies = new ArrayList<>();
        entropy = tilings.get(0).capacity()/(2*tilings.get(0).T);
        for (int i = 1; i < tilings.size(); i++) {
            Tiling tiling = tilings.get(i);
            freeEnergies.add(tiling.averageEnergy - (tiling.T * entropy + tiling.capacity() / 2)*tiling.T / (i + 1));
            entropy += tiling.capacity() / tiling.T;
        }
        return freeEnergies;
    }
}
