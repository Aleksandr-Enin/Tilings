import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FreeEnergyTest {
    @Test
    public void freeEnergy() throws Exception {
        System.out.println(FreeEnergy.freeEnergy(20, 40, 200));
    }

    @Test
    public void test() throws Exception {

        FileInputStream fileInput = new FileInputStream("Free Energy");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInput);
        ArrayList<Tiling> tilings = (ArrayList<Tiling>) objectInputStream.readObject();
        System.out.println(tilings.get(0).n);

    }

}