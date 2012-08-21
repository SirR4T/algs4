public class Percolation {
    private boolean[][] openness;
    private int size;
    private WeightedQuickUnionUF uf;                
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        size = N;
        openness = new boolean[size][size];
        uf = new WeightedQuickUnionUF(size*size + 2);    //for two extra virtual sites
        for (int i = 1; i <= size; i++)
        {
            for (int j = 1; j <= size; j++)
            {
                openness[i-1][j-1] = false;
            }
        }
    }

    // Assuming this function is used only to generate indices for the uf[] array,
    // uf[0] and uf[N*N+1] are virtual sites.
    // (1,1) -> 1, (size, size) -> size*size
    private int linearize(int i, int j)
    {
        return (i-1)*size+j;
    }

    // To check whether indices i,j are valid coordinates for the NxN matrix of sites
    private boolean isValid(int i, int j)
    {
        if (i <= 0 || i > size) 
            return false;
        if (j <= 0 || j > size) 
            return false;
        return true;
    }

    // open up the (i,j)th site, and also connect it to all its open neighbours.
    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        if (isValid(i, j))
        {
            if (!isOpen(i, j))
            {
                openness[i-1][j-1] = true;
                if (isValid(i, j+1))
                    if (isOpen(i, j+1))
                        uf.union(linearize(i, j), linearize(i, j+1));
                if (isValid(i+1, j))
                    if (isOpen(i+1, j))
                        uf.union(linearize(i, j), linearize(i+1, j));
                if (isValid(i, j-1))
                    if (isOpen(i, j-1))
                        uf.union(linearize(i, j), linearize(i, j-1));
                if (isValid(i-1, j))
                    if (isOpen(i-1, j))
                        uf.union(linearize(i, j), linearize(i-1, j));

                if (i == 1)
                    uf.union(linearize(i, j), 0);
                if ( (i == size) && isFull(i, j) )
                    uf.union(linearize(i, j), size*size+1);
            }
        }
        else
        {
            if (i <= 0 || i > size) 
                throw new IndexOutOfBoundsException("row index i out of bounds");
            if (j <= 0 || j > size)
                throw new IndexOutOfBoundsException("column index j out of bounds");
        }
    }

    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        if (isValid(i, j))
            return openness[i-1][j-1];
        else
            throw new IndexOutOfBoundsException("wrong indices");
    }

    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        if (isValid(i, j))
            return uf.connected(0, linearize(i, j));
        else
            throw new IndexOutOfBoundsException("wrong indices");
    }

    public boolean percolates()            // does the system percolate?
    {
        return uf.connected(0, size*size+1);
    }

    private void show()
    {
        for (int i = 1; i <= size; i++)
        {
            for (int j = 1; j <= size; j++)
            {
                if (isOpen(i, j))
                //if (openness[i-1][j-1])
                    StdOut.print("1 ");
                else
                    StdOut.print("0 ");
            }
            StdOut.println();
        }
        for (int i = 0; i <= size*size+1; i++)
            StdOut.print(uf.connected(0, i) ? "1 " : "0 ");
    }

    public static void main(String[] args)
    {
        Percolation p = new Percolation(1);
        p.show();
        StdOut.println(p.percolates() ? "yes" : "no");
        p.open(1, 1);
        p.show();
        StdOut.println(p.percolates() ? "yes" : "no");
        return;
    }
}
