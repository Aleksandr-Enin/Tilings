import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FreeEnergy {
    public static double freeEnergy(int n, double t, int iterations) {
        double entropy = 0;
        ArrayList<Tiling> tilings = new ArrayList<>();
        for (int i = 0; i <= iterations; i++) {
            Tiling tiling = new Tiling(n);
            double temperature = 0.2 + i*(t - 0.2)/iterations;
            tiling.setTemp(temperature);
            System.out.println("T = " + temperature);
            tiling.metropolis(100000);
            tilings.add(tiling);
            System.out.println(tiling.averageEnergy);
            System.out.println(tiling.capacity());
        }
        entropy = tilings.get(0).capacity()/(2*tilings.get(0).T);
        for (int i = 1; i < iterations; i++)
            entropy += tilings.get(i).capacity()/tilings.get(i).T;
        entropy += tilings.get(iterations).capacity()/t/2;

        try {
            FileOutputStream fos = new FileOutputStream("Free Energy");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tilings);
            oos.close();
            fos.close();
        }
        catch(Exception ex) {

        }

        return tilings.get(iterations).averageEnergy - t*t*entropy/(iterations+1);
    }
}
