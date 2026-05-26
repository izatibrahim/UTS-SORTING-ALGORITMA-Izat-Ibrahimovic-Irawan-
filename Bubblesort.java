/**
 * =============================================================
 *  BUBBLE SORT — Implementasi Dasar & Versi Teroptimasi (Java)
 * =============================================================
 *
 * Konsep:
 *   Membandingkan dua elemen berdekatan, tukar jika salah urutan.
 *   Ulangi hingga seluruh array terurut.
 *
 * Kompleksitas:
 *   Waktu  (Worst/Average) : O(n²)
 *   Waktu  (Best)          : O(n)  ← hanya versi optimasi
 *   Ruang  (Space)         : O(1)  — in-place sorting
 */
public class BubbleSort {

    // ─────────────────────────────────────────────
    //  VERSI DASAR (tanpa optimasi)
    // ─────────────────────────────────────────────

    /**
     * Bubble Sort dasar.
     * Selalu menjalankan n*(n-1)/2 perbandingan meski array sudah terurut.
     *
     * @param arr Array yang akan diurutkan (dimodifikasi langsung / in-place)
     */
    public static void bubbleSortBasic(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp  = arr[j];
                    arr[j]    = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // ─────────────────────────────────────────────
    //  VERSI OPTIMASI
    // ─────────────────────────────────────────────

    /**
     * Bubble Sort Teroptimasi — dua teknik sekaligus:
     *
     * <p>Optimasi 1 — Early Termination (flag {@code swapped}):
     * Jika dalam satu pass tidak ada swap, array sudah terurut → berhenti.
     * Membuat best-case menjadi O(n) untuk array yang sudah/hampir terurut.
     *
     * <p>Optimasi 2 — Shrinking Boundary:
     * Catat posisi swap terakhir di setiap pass. Elemen di kanan posisi
     * tersebut sudah pasti terurut → batas iterasi dipersempit, mengurangi
     * perbandingan yang tidak perlu.
     *
     * @param arr Array yang akan diurutkan (in-place)
     */
    public static void bubbleSortOptimized(int[] arr) {
        int rightBoundary = arr.length - 1;

        while (rightBoundary > 0) {
            int  lastSwapIndex = 0;
            boolean swapped    = false;

            for (int j = 0; j < rightBoundary; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp  = arr[j];
                    arr[j]    = arr[j + 1];
                    arr[j + 1] = temp;
                    lastSwapIndex = j;
                    swapped = true;
                }
            }

            if (!swapped) break;              // Optimasi 1: tidak ada swap → selesai
            rightBoundary = lastSwapIndex;    // Optimasi 2: perkecil batas
        }
    }

    // ─────────────────────────────────────────────
    //  VERSI DESCENDING (bonus)
    // ─────────────────────────────────────────────

    /**
     * Bubble Sort Teroptimasi — urutan descending (besar → kecil).
     *
     * @param arr Array yang akan diurutkan (in-place)
     */
    public static void bubbleSortDescending(int[] arr) {
        int rightBoundary = arr.length - 1;

        while (rightBoundary > 0) {
            int  lastSwapIndex = 0;
            boolean swapped    = false;

            for (int j = 0; j < rightBoundary; j++) {
                if (arr[j] < arr[j + 1]) {      // ← tanda berbeda
                    int temp  = arr[j];
                    arr[j]    = arr[j + 1];
                    arr[j + 1] = temp;
                    lastSwapIndex = j;
                    swapped = true;
                }
            }

            if (!swapped) break;
            rightBoundary = lastSwapIndex;
        }
    }

    // ─────────────────────────────────────────────
    //  HELPER: cetak array
    // ─────────────────────────────────────────────

    public static String arrayToString(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }

    // ─────────────────────────────────────────────
    //  CONTOH PENGGUNAAN
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=".repeat(55));
        System.out.println("         DEMO BUBBLE SORT (Java)");
        System.out.println("=".repeat(55));

        // --- Contoh 1: Array acak ---
        int[] data1 = {64, 34, 25, 12, 22, 11, 90};
        int[] copy1 = copyArray(data1);
        bubbleSortOptimized(copy1);
        System.out.println("\n[Contoh 1] Array acak");
        System.out.println("  Input  : " + arrayToString(data1));
        System.out.println("  Output : " + arrayToString(copy1));

        // --- Contoh 2: Sudah terurut (best-case) ---
        int[] data2 = {1, 2, 3, 4, 5};
        int[] copy2 = copyArray(data2);
        bubbleSortOptimized(copy2);
        System.out.println("\n[Contoh 2] Array sudah terurut (best-case)");
        System.out.println("  Input  : " + arrayToString(data2));
        System.out.println("  Output : " + arrayToString(copy2));

        // --- Contoh 3: Terbalik (worst-case) ---
        int[] data3 = {5, 4, 3, 2, 1};
        int[] copy3 = copyArray(data3);
        bubbleSortOptimized(copy3);
        System.out.println("\n[Contoh 3] Array terbalik (worst-case)");
        System.out.println("  Input  : " + arrayToString(data3));
        System.out.println("  Output : " + arrayToString(copy3));

        // --- Contoh 4: Descending ---
        int[] data4 = {38, 7, 14, 2, 99, 45};
        int[] copy4 = copyArray(data4);
        bubbleSortDescending(copy4);
        System.out.println("\n[Contoh 4] Descending sort");
        System.out.println("  Input  : " + arrayToString(data4));
        System.out.println("  Output : " + arrayToString(copy4));

        // --- Contoh 5: Benchmark basic vs optimized ---
        int n = 5000;
        int[] big = new int[n];
        java.util.Random rng = new java.util.Random(42);
        for (int i = 0; i < n; i++) big[i] = rng.nextInt(100_000);

        int[] bigBasic = copyArray(big);
        long start = System.nanoTime();
        bubbleSortBasic(bigBasic);
        double tBasic = (System.nanoTime() - start) / 1_000_000.0;

        int[] bigOpt = copyArray(big);
        start = System.nanoTime();
        bubbleSortOptimized(bigOpt);
        double tOpt = (System.nanoTime() - start) / 1_000_000.0;

        System.out.printf("%n[Contoh 5] Benchmark — %,d elemen acak%n", n);
        System.out.printf("  Basic     : %.2f ms%n", tBasic);
        System.out.printf("  Optimized : %.2f ms%n", tOpt);

        // --- Contoh 6: Hampir terurut ---
        int[] almost = new int[200];
        for (int i = 0; i < 200; i++) almost[i] = i;
        almost[198] = 199; almost[199] = 198;   // tukar 2 elemen terakhir

        int[] almostBasic = copyArray(almost);
        start = System.nanoTime();
        bubbleSortBasic(almostBasic);
        double tB2 = (System.nanoTime() - start) / 1_000_000.0;

        int[] almostOpt = copyArray(almost);
        start = System.nanoTime();
        bubbleSortOptimized(almostOpt);
        double tO2 = (System.nanoTime() - start) / 1_000_000.0;

        System.out.printf("%n[Contoh 6] Benchmark — 200 elemen hampir terurut%n");
        System.out.printf("  Basic     : %.4f ms%n", tB2);
        System.out.printf("  Optimized : %.4f ms%n", tO2);

        System.out.println("\n" + "=".repeat(55));
    }
}