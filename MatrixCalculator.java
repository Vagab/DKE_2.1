import java.util.ArrayList;

public class MatrixCalculator {


    public ArrayList<double[]> leastSquares(ArrayList<double[]> matrix, ArrayList<double[]> vector) {
        ArrayList<double[]> leastSquaresSolution =matrixMultiplication(matrixMultiplication(inverse(matrixMultiplication(transpose(matrix),matrix)),transpose(matrix)),vector);
        return leastSquaresSolution;
    }

    public ArrayList<double[]> transpose(ArrayList<double[]> matrix) {
        int columns = matrix.size(); //3
        int rows = matrix.get(0).length; //2
        ArrayList<double[]> matrixTranspose = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            matrixTranspose.add(new double[columns]);
        }
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                matrixTranspose.get(i)[j] = matrix.get(j)[i];
            }
        }
        return matrixTranspose;
    }

    public void printMatrix(ArrayList<double[]> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).length; j++) {
                System.out.print(matrix.get(i)[j] + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<double[]>  matrixMultiplication(ArrayList<double[]> matrix1, ArrayList<double[]> matrix2) {
        int matrix1Rows = matrix1.size();
        int matrix1Columns = matrix1.get(0).length;
        int matrix2Rows = matrix2.size();
        int matrix2Columns = matrix2.get(0).length;
        if (matrix1Columns != matrix2Rows) {
            System.out.println("Not possible!");
        }
        ArrayList<double[]> matrixMultiplied = new ArrayList<>();
        for (int i = 0; i < matrix1Rows; i++) {
            matrixMultiplied.add(new double[matrix2Columns]);
        }
        for (int i = 0; i < matrix1Rows; i++) {
            for (int j = 0; j < matrix2Columns; j++) {
                for (int k = 0; k < matrix2.size(); k++) {
                    matrixMultiplied.get(i)[j] += matrix1.get(i)[k]*matrix2.get(k)[j];
                }
            }
        }

        return matrixMultiplied;
    }

    public double determinant(ArrayList<double[]> matrix, int n) //matrix needs to be square
    {
        int D = 0; // Initialize result

        // Base case : if matrix contains single element
        if (n == 1){
            return matrix.get(0)[0];
        }

        ArrayList<double[]> temp = new ArrayList<>(); // To store cofactors
        for (int i = 0; i < n; i++){
            temp.add(new double[n]);
        }

        int sign = 1; // To store sign multiplier

        // Iterate for each element of first row
        for (int i = 0; i < n; i++)
        {
            // Getting Cofactor of A[0][f]
            getCofactor(matrix, temp, 0, i, n);
            D += sign * matrix.get(0)[i] * determinant(temp, n - 1);

            // terms are to be added with alternate sign
            sign = -sign;
        }

        return D;
    }

    // Function to get cofactor of A[p][q] in temp[][]. n is current
// dimension of A[][]
    public void getCofactor(ArrayList<double[]> matrix, ArrayList<double[]> temp, int p, int q, int n)
    {
        int i = 0, j = 0;
        // Looping for each element of the matrix
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                // Copying into temporary matrix only those element
                // which are not in given row and column
                if (row != p && col != q)
                {
                    temp.get(i)[j++] = matrix.get(row)[col];

                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    // Function to get adjoint of A[N][N] in adj[N][N].
    public void adjoint(ArrayList<double[]> matrix, ArrayList<double[]> adj)
    {
        int rows = matrix.size();
        if (rows == 1)
        {
            adj.get(0)[0] = 1;
            return;
        }

        // temp is used to store cofactors of A[][]
        int sign = 1;
        ArrayList<double[]> temp = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            temp.add(new double[rows]);
        }

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                // Get cofactor of A[i][j]
                getCofactor(matrix, temp, i, j, rows);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0)? 1: -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj.get(j)[i] = (sign)*(determinant(temp, rows-1));
            }
        }
    }

    public ArrayList<double[]> inverse(ArrayList<double[]> matrix)
    {
        int rows = matrix.size();
        ArrayList<double[]> inverseMatrix = new ArrayList<>();
        for (int i = 0; i < rows; i++){
            inverseMatrix.add(new double[rows]);
        }
        // Find determinant of A[][]
        double det = determinant(matrix, rows);
        if (det == 0)
        {
            System.out.print("Singular matrix, can't find its inverse");
        }
        // Find adjoint
        ArrayList<double[]> adj = new ArrayList<>();
        for (int i = 0; i < rows; i++){
            adj.add(new double[rows]);
        }
        adjoint(matrix, adj);

        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                inverseMatrix.get(i)[j] = adj.get(i)[j]/((double)det);
            }
        }
        return inverseMatrix;
    }

    public ArrayList<double[]> createVector(ArrayList<Double> list) {
        ArrayList<double[]> vector = new ArrayList<>();
        for (double k : list) {
            vector.add(new double[]{k});
        }
        return vector;
    }

    public double[] normalize(double[] vector) {
        double normalizationConstant = 0;
        double[] normalizedVector = new double[vector.length];
        for (double n : vector) {
            normalizationConstant += n*n;
        }
        normalizationConstant = Math.sqrt(normalizationConstant);
        for (int i = 0; i < vector.length; i++) {
            normalizedVector[i] = vector[i]/normalizationConstant;
        }
        return normalizedVector;
    }

    public double[] listToArray(ArrayList<double[]> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i)[0];
        }
        return array;
    }
}
