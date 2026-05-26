/**
 * =============================================================
 *  BINARY INSERTION SORT — Implementasi Dasar & Teroptimasi (Java)
 * =============================================================
 *
 * Konsep:
 *   Varian Insertion Sort yang menggunakan Binary Search untuk
 *   menemukan posisi insersi, sehingga jumlah perbandingan
 *   berkurang dari O(n) menjadi O(log n) per elemen.
 *
 * Kompleksitas:
 *   Perbandingan : O(n log n)
 *   Pergeseran   : O(n²)
 *   Space        : O(1) — in-place
 *   Best-case    : O(n) ← dengan optimasi sentinel
 */
public class BinaryInsertionSort {

    // ─────────────────────────────────────────────
    //  HELPER: Binary Search untuk posisi insersi
    // ─────────────────────────────────────────────

    /**
     * Mencari posisi paling kiri di mana {@code target} bisa disisipkan
     * ke dalam subarray {@code arr[low..high]} yang sudah terurut ascending,
     * agar urutan tetap terjaga (left-biased → stabil).
     *
     * @param arr    Array (sebagian terurut)
     * @param target Nilai yang akan disisipkan
     * @param low    Batas kiri pencarian (inklusif)
     * @param high   Batas kanan pencarian (inklusif)
     * @return Indeks posisi insersi
     */
    private static int binarySearchPosition(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;   // hindari overflow vs (low+high)/2
            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;   // 'low' adalah posisi insersi
    }

    // ─────────────────────────────────────────────
    //  VERSI DASAR
    // ─────────────────────────────────────────────

    /**
     * Binary Insertion Sort dasar.
     * Menggunakan Binary Search untuk mengurangi perbandingan,
     * namun pergeseran elemen dilakukan satu per satu (loop).
     *
     * @param arr Array yang akan diurutkan (in-place)
     */
    public static void binaryInsertionSortBasic(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];

            // Cari posisi insersi di arr[0..i-1]
            int pos = binarySearchPosition(arr, key, 0, i - 1);

            // Geser elemen dari pos s/d i-1 satu langkah ke kanan
            int j = i;
            while (j > pos) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[pos] = key;
        }
    }

    // ─────────────────────────────────────────────
    //  VERSI OPTIMASI
    // ─────────────────────────────────────────────

    /**
     * Binary Insertion Sort Teroptimasi — tiga teknik:
     *
     * <p>Optimasi 1 — Batch shifting dengan {@code System.arraycopy}:
     * Menggantikan loop pergeseran satu-per-satu dengan panggilan native
     * {@code System.arraycopy} yang berjalan di level JVM/C, jauh lebih
     * cepat untuk blok berukuran besar.
     *
     * <p>Optimasi 2 — Skip jika posisi == i:
     * Jika binary search mengembalikan pos == i, elemen sudah di tempat
     * yang benar → tidak ada shifting maupun assignment.
     *
     * <p>Optimasi 3 — Sentinel check:
     * Sebelum binary search, cek apakah {@code key >= arr[i-1]}.
     * Jika ya, elemen sudah terurut → lewati seluruh proses.
     * Membuat best-case menjadi O(n) untuk array yang sudah terurut.
     *
     * @param arr Array yang akan diurutkan (in-place)
     */
    public static void binaryInsertionSortOptimized(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];

            // Optimasi 3: sentinel — skip jika sudah di urutan benar
            if (key >= arr[i - 1]) continue;

            // Cari posisi insersi
            int pos = binarySearchPosition(arr, key, 0, i - 1);

            // Optimasi 2: tidak perlu apa-apa jika sudah di posisi benar
            if (pos == i) continue;

            // Optimasi 1: batch shift dengan System.arraycopy (native)
            System.arraycopy(arr, pos, arr, pos + 1, i - pos);
            arr[pos] = key;
        }
    }

    // ─────────────────────────────────────────────
    //  VERSI DESCENDING (bonus)
    // ─────────────────────────────────────────────

    private static int binarySearchDescending(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    /**
     * Binary Insertion Sort Teroptimasi — urutan descending (besar → kecil).
     *
     * @param arr Array yang akan diurutkan (in-place)
     */
    public static void binaryInsertionSortDescending(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];

            if (key <= arr[i - 1]) continue;     // sentinel terbalik

            int pos = binarySearchDescending(arr, key, 0, i - 1);

            if (pos == i) continue;

            System.arraycopy(arr, pos, arr, pos + 1, i - pos);
            arr[pos] = key;
        }
    }

    // ─────────────────────────────────────────────
    //  HELPER
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
        System.out.println("    DEMO BINARY INSERTION SORT (Java)");
        System.out.println("=".repeat(55));

        // --- Contoh 1: Array acak ---
        int[] data1 = {64, 34, 25, 12, 22, 11, 90};
        int[] copy1 = copyArray(data1);
        binaryInsertionSortOptimized(copy1);
        System.out.println("\n[Contoh 1] Array acak");
        System.out.println("  Input  : " + arrayToString(data1));
        System.out.println("  Output : " + arrayToString(copy1));

        // --- Contoh 2: Sudah terurut ---
        int[] data2 = {1, 2, 3, 4, 5};
        int[] copy2 = copyArray(data2);
        binaryInsertionSortOptimized(copy2);
        System.out.println("\n[Contoh 2] Array sudah terurut (best-case)");
        System.out.println("  Input  : " + arrayToString(data2));
        System.out.println("  Output : " + arrayToString(copy2));

        // --- Contoh 3: Terbalik ---
        int[] data3 = {5, 4, 3, 2, 1};
        int[] copy3 = copyArray(data3);
        binaryInsertionSortOptimized(copy3);
        System.out.println("\n[Contoh 3] Array terbalik (worst-case)");
        System.out.println("  Input  : " + arrayToString(data3));
        System.out.println("  Output : " + arrayToString(copy3));

        // --- Contoh 4: Descending ---
        int[] data4 = {38, 7, 14, 2, 99, 45};
        int[] copy4 = copyArray(data4);
        binaryInsertionSortDescending(copy4);
        System.out.println("\n[Contoh 4] Descending sort");
        System.out.println("  Input  : " + arrayToString(data4));
        System.out.println("  Output : " + arrayToString(copy4));

        // --- Contoh 5: Benchmark ---
        int n = 5000;
        int[] big = new int[n];
        java.util.Random rng = new java.util.Random(42);
        for (int i = 0; i < n; i++) big[i] = rng.nextInt(100_000);

        int[] bigBasic = copyArray(big);
        long start = System.nanoTime();
        binaryInsertionSortBasic(bigBasic);
        double tBasic = (System.nanoTime() - start) / 1_000_000.0;

        int[] bigOpt = copyArray(big);
        start = System.nanoTime();
        binaryInsertionSortOptimized(bigOpt);
        double tOpt = (System.nanoTime() - start) / 1_000_000.0;

        System.out.printf("%n[Contoh 5] Benchmark — %,d elemen acak%n", n);
        System.out.printf("  Basic     : %.2f ms%n", tBasic);
        System.out.printf("  Optimized : %.2f ms%n", tOpt);

        System.out.println("\n" + "=".repeat(55));
    }
}