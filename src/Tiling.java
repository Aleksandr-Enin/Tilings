import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Tiling implements Serializable{
    int n = 30;
    int[][] lattice;
    double[][] averageConfiguration;
    double[][] correlators;
    double T = 10;
    double energy = 0;
    double averageEnergy = 0;
    double averageEnergySquared = 0;

    Random random;

    public int[][] getAverageConfiguration() {
        return arrayToInt(averageConfiguration);
    }

    public int[][] to3dLattice(int lattice[][]) {
        int [][] result = new int[n][n];
        for (int i = 0; i<2*n; i++) {
            for (int j = 0; j<2*n; j++) {
                if (isOutOfBorder(i,j)) continue;
                if (lattice[i][j] == lattice[i+1][j+1]) {
                    result[i-lattice[i][j]][j-lattice[i][j]] = lattice[i][j];
                }
            }

        }
        return result;
    }

    private int[][] arrayToInt(double [][] array) {
        int [][] t = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            t[i] = new int[array[i].length];
            for (int j = 0; j < t.length; j++) {
                t[i][j] = (int) array[i][j];
            }
        }
        return t;
    }

    public Tiling(int n) {
        this.n = n;
        lattice = new int[2*n+1][2*n+1];
        random = new Random();
        initializeLattice();
    }

    public void setTemp(double T)
    {
        this.T = T;
    }

    public boolean isOnBorder(int i, int j) {
        if (i == 0 || j == 0 || i - j == n || j - i == n || i == 2*n || j == 2*n) return true;
        return false;
    }

    public boolean isOutOfBorder(int i, int j) {
        if (i <= 0 || j <= 0 || i - j >= n || j - i >= n || i >= 2*n || j >= 2*n) return true;
        return false;
    }

    boolean isCorrectChange(int i, int j, int heightDifference) {
        if (isOutOfBorder(i, j)) return false;
        if (lattice[i+1][j+1] - lattice[i-1][j-1] > 1) return false;
        if (lattice[i][j] + heightDifference > lattice[i+1][j] || lattice[i][j] + heightDifference > lattice[i][j+1]
                || lattice[i][j] + heightDifference > lattice[i+1][j+1]) return false;
        else if (lattice[i][j] + heightDifference < lattice[i-1][j] || lattice[i][j] + heightDifference < lattice[i][j-1]
                || lattice[i][j] + heightDifference < lattice[i-1][j-1]) return false;
        return true;
    }

    void initializeLattice()
    {
        for (int i = 0; i <= n; i++) {
            for (int j =0; j<= n; j++) {
                lattice[i][j] = 0;
            }
        }
        for (int i = 0; i<=n; i++) {
            for (int j = 0; j<=n; j++) {
                lattice[n + i][j + i] = i;
                lattice[j + i][n + i] = i;
            }
        }
    }

    void changeConfiguration()
    {
        int heightDifference = 0;
        int i = 0;
        int j = 0;
        do {
            heightDifference = (random.nextBoolean() ? 1 : -1);
            i = random.nextInt(2*n+1);
            j = random.nextInt(2*n+1);
        } while (!isCorrectChange(i,j,heightDifference));
        if (random.nextDouble() < Math.exp(-heightDifference/T)) {
            lattice[i][j]+= heightDifference;
            energy += heightDifference;
        }
    }

    public void metropolis(int iterations)
    {
        int i, j;
        initializeSample();
        for (int t =0; t < n*n*n*10000; t++) {
            changeConfiguration();
        }
        System.out.println("thermalization done") ;
        for (int k = 0; k < iterations; k++)
        {
            //for (int t =0; t < 100; t++) {
                changeConfiguration();
            //}
            sample();
        }
        finalizeSample(iterations);
    }

    private void initializeSample() {
        energy = 0;
        averageConfiguration = new double[2*n+1][2*n+1];
        correlators = new double[2*n+1][2*n+1];
    }

    private void sample() {
        int height = 0;
        int heightSquared = 0;
        for (int i = 0; i < 2*n+1; i++) {
            for (int j = 0; j < 2*n+1; j++) {
                averageConfiguration[i][j] += lattice[i][j];
                correlators[i][j] += lattice[n][n] * lattice[i][j];
            }
        }
        averageEnergy += energy;
        averageEnergySquared += energy*energy;
    }

    private void finalizeSample(int iterations) {
        for (int i = 0; i < 2*n+1; i++) {
            for (int j = 0; j < 2*n+1; j++) {
                averageConfiguration[i][j] /= iterations;
            }
        }

        for (int i = 0; i < 2*n +1; i++) {
            for (int j = 0; j < 2*n +1; j++) {
                correlators[i][j] /= iterations;
                correlators[i][j] -= averageConfiguration[i][j] * averageConfiguration[n][n];
            }
        }
        averageEnergy /= iterations;
        averageEnergySquared /= iterations;
    }

    public double capacity() {
        return (this.averageEnergySquared - this.averageEnergy*this.averageEnergy)/(T*T);
    }

    public String toString() {
        String result = "";
        for (int i=0;i<2*n+1;i++) {
            for (int j =0; j<2*n+1;j++) {
                result += lattice[i][j] + " ";
                if (lattice[i][j] < 10) result+= " ";
            }
            result += "\n";
        }
        return result;
    }
}
