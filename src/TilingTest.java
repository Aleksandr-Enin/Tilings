import static org.junit.Assert.*;

public class TilingTest {
    @org.junit.Test
    public void metropolis() throws Exception {
        Tiling tiling = new Tiling(2);
        tiling.setTemp(10);
        tiling.metropolis(100);
        System.out.println(tiling);
        int[][] t = tiling.to3dLattice();
        for (int i =0 ; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                System.out.print(t[i][j] + " ");
            }
            System.out.println();
        }
    }

}