public class NonCubeTiling extends CorrectTiling {
    public NonCubeTiling(int n) {
        super(n);
    }

    @Override
    public boolean isOnBorder(int i, int j) {
        if (i == 0 || j == 0 || i - j == n || j - i == n-2 || i == 2*n || j == 2*n) return true;
        return false;
    }

    @Override
    public boolean isOutOfBorder(int i, int j) {
        if (i <= 0 || j <= 0 || i - j >= n || j - i >= n || i >= 2*n || j >= 2*n || (j-i >= n/2 && i >= n)) return true;
        return false;
    }

    @Override
    void initializeLattice() {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                lattice[i][j] = 0;
            }
        }
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                lattice[n + i][j + i] = i;
                lattice[j + i][n + i] = i;
            }
        }
    }

}
