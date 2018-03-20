import java.io.*;
import java.util.ArrayList;

public class TilingOutput {
    public static void saveOutput(ArrayList<Tiling> tilings, String prefix) {
        int n = tilings.get(0).n;
        try {
            PrintWriter energyWriter = new PrintWriter(new FileWriter("energy/" +prefix + n));

            for (Tiling tiling : tilings) {
                FileWriter fileWriter = new FileWriter("correlators/" + prefix + tiling.n + "_" + tiling.T + "_" + "Corelators.dat");
                PrintWriter printWriter = new PrintWriter(fileWriter);
                for (int i = n / 4; i < 3 * n / 4; i++) {
                    printWriter.println(tiling.correlators[i][n]);
                }
                printWriter.close();
                energyWriter.println(tiling.T + " " + tiling.averageEnergy + " " + tiling.capacity() + " " + tiling.averageEnergySquared);
            }
            energyWriter.close();
        }
        catch (Exception ex) {

        }
    }

    public static void saveTilings(ArrayList<Tiling> tilings, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tilings);
            oos.close();
            fos.close();
        }
        catch(Exception ex) {

        }
    }

    public static ArrayList<Tiling> readTilings(String filename) throws Exception {
        FileInputStream fileInput = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInput);
        ArrayList<Tiling> tilings = (ArrayList<Tiling>) objectInputStream.readObject();
        return tilings;
    }
}
