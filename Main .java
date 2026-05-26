/**
 * =============================================================
 *  DEMO & BENCHMARK — Bubble Sort vs Binary Insertion Sort
 * =============================================================
 * Kompilasi & jalankan:
 *   javac *.java
 *   java Main
 */
public class Main {

    // ─────────────────────────────────────────────
    //  HELPER
    // ─────────────────────────────────────────────

    static void header(String title) {
        System.out.println("\n" + "=".repeat(62));
        System.out.println("  " + title);
        System.out.println("=".repeat(62));
    }

    static int[] copy(int[] arr) {
        int[] c = new int[arr.length];
        System.arraycopy(arr, 0, c, 0, arr.length);
        return c;
    }

    static String str(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    static boolean isEqual(int[] a, int[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) if (a[i] != b[i]) return false;
        return true;
    }

    /** Insertion sort manual sebagai expected value (tanpa library). */
    static int[] expectedSorted(int[] arr) {
        int[] d = copy(arr);
        for (int i = 1; i < d.length; i++) {
            int key = d[i], j = i - 1;
            while (j >= 0 && d[j] > key) { d[j + 1] = d[j]; j--; }
            d[j + 1] = key;
        }
        return d;
    }

    // ─────────────────────────────────────────────
    //  BAGIAN 1 — DEMO FUNGSIONAL
    // ─────────────────────────────────────────────

    static void demoFungsional() {
        header("DEMO FUNGSIONAL");

        int[][] samples = {
            {64, 34, 25, 12, 22, 11, 90},
            {1, 2, 3, 5, 4, 6, 7},
            {1, 2, 3, 4, 5, 6, 7},
            {7, 6, 5, 4, 3, 2, 1},
            {3, 1, 4, 1, 5, 9, 2, 6, 5, 3}
        };
        String[] labels = {"Acak", "Hampir terurut", "Sudah terurut", "Terbalik", "Duplikat"};

        for (int s = 0; s < samples.length; s++) {
            int[] arr = samples[s];
            int[] bs  = copy(arr); BubbleSort.bubbleSortOptimized(bs);
            int[] bi  = copy(arr); BinaryInsertionSort.binaryInsertionSortOptimized(bi);
            String match = isEqual(bs, bi) ? "✓ sama" : "✗ beda";

            System.out.println("\n  [" + labels[s] + "]");
            System.out.println("    Input          : " + str(arr));
            System.out.println("    Bubble Sort    : " + str(bs));
            System.out.println("    BinaryIns Sort : " + str(bi) + "  " + match);
        }
    }

    // ─────────────────────────────────────────────
    //  BAGIAN 2 — BENCHMARK
    // ─────────────────────────────────────────────

    static double bench(Runnable fn) {
        long start = System.nanoTime();
        fn.run();
        return (System.nanoTime() - start) / 1_000_000.0;
    }

    static void benchmark() {
        header("BENCHMARK PERFORMA");

        java.util.Random rng = new java.util.Random(2024);
        int[] sizes = {500, 2000, 1000, 1000, 500};
        String[] labels = {
            "Acak (500 el)", "Acak (2.000 el)",
            "Sudah terurut (1.000)", "Hampir terurut (1.000)", "Terbalik (500 el)"
        };

        System.out.printf("%n  %-30s %10s %10s %11s %10s%n",
            "Skenario", "BS-Basic", "BS-Opt", "BIS-Basic", "BIS-Opt");
        System.out.println("  " + "-".repeat(75));

        for (int s = 0; s < sizes.length; s++) {
            int n = sizes[s];
            int[] data;

            if (s == 0 || s == 1) {              // acak
                data = new int[n];
                for (int i = 0; i < n; i++) data[i] = rng.nextInt(n * 10);
            } else if (s == 2) {                  // sudah terurut
                data = new int[n];
                for (int i = 0; i < n; i++) data[i] = i;
            } else if (s == 3) {                  // hampir terurut
                data = new int[n];
                for (int i = 0; i < n; i++) data[i] = i;
                for (int k = 0; k < 5; k++) {
                    int a = rng.nextInt(n), b = rng.nextInt(n);
                    int tmp = data[a]; data[a] = data[b]; data[b] = tmp;
                }
            } else {                              // terbalik
                data = new int[n];
                for (int i = 0; i < n; i++) data[i] = n - i;
            }

            final int[] d = data;
            double tBSB = bench(() -> { int[] x = copy(d); BubbleSort.bubbleSortBasic(x); });
            double tBSO = bench(() -> { int[] x = copy(d); BubbleSort.bubbleSortOptimized(x); });
            double tBIB = bench(() -> { int[] x = copy(d); BinaryInsertionSort.binaryInsertionSortBasic(x); });
            double tBIO = bench(() -> { int[] x = copy(d); BinaryInsertionSort.binaryInsertionSortOptimized(x); });

            System.out.printf("  %-30s %8.2fms %8.2fms %9.2fms %8.2fms%n",
                labels[s], tBSB, tBSO, tBIB, tBIO);
        }
    }

    // ─────────────────────────────────────────────
    //  BAGIAN 3 — DESCENDING DEMO
    // ─────────────────────────────────────────────

    static void demoDescending() {
        header("DEMO DESCENDING SORT");

        int[] arr = {38, 7, 14, 2, 99, 45, 31};
        int[] bs  = copy(arr); BubbleSort.bubbleSortDescending(bs);
        int[] bi  = copy(arr); BinaryInsertionSort.binaryInsertionSortDescending(bi);

        System.out.println("\n  Input             : " + str(arr));
        System.out.println("  Bubble (desc)     : " + str(bs));
        System.out.println("  BinaryIns (desc)  : " + str(bi));
    }

    // ─────────────────────────────────────────────
    //  BAGIAN 4 — RINGKASAN
    // ─────────────────────────────────────────────

    static void ringkasan() {
        header("RINGKASAN PERBANDINGAN");
        System.out.println("""
  ┌─────────────────────┬──────────────────────┬──────────────────────┐
  │ Aspek               │ Bubble Sort          │ Binary Ins. Sort     │
  ├─────────────────────┼──────────────────────┼──────────────────────┤
  │ Perbandingan        │ O(n²)                │ O(n log n)           │
  │ Pergeseran/Swap     │ O(n²) banyak swap    │ O(n²) geser blok     │
  │ Best-case           │ O(n) *dgn optimasi   │ O(n) *dgn sentinel   │
  │ Worst-case          │ O(n²)                │ O(n²)                │
  │ Space               │ O(1) in-place        │ O(1) in-place        │
  │ Stabilitas          │ Stabil               │ Stabil               │
  │ Cocok untuk         │ Array kecil/hampir   │ Array kecil-menengah │
  │                     │ terurut              │ dgn banyak insertion  │
  └─────────────────────┴──────────────────────┴──────────────────────┘
""");
    }

    // ─────────────────────────────────────────────
    //  MAIN
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        demoFungsional();
        benchmark();
        demoDescending();
        ringkasan();
    }
}