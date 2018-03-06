import static org.junit.Assert.*;

public class TilingTest {
    @org.junit.Test
    public void metropolis() throws Exception {
        Tiling tiling = new Tiling(30);
        tiling.setTemp(10000);
        System.out.println(tiling);
        tiling.metropolis(10000000);
        int[][] t = tiling.to3dLattice(tiling.getAverageConfiguration());
        System.out.println(tiling);
        for (int i =0 ; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                System.out.print(t[i][j] + " ");
                if (t[i][j] < 10) System.out.print(" ");
            }
            System.out.println();
        }
    }

}