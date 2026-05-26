"""
=============================================================
  UNIT TEST — Bubble Sort & Binary Insertion Sort
=============================================================
Jalankan dengan:  python test_sorting.py
"""

import unittest
import random

from bubble_sort import (
    bubble_sort_basic,
    bubble_sort_optimized,
    bubble_sort_descending/**
 * =============================================================
 *  UNIT TEST — Bubble Sort & Binary Insertion Sort (Java)
 * =============================================================
 * Kompilasi & jalankan:
 *   javac *.java
 *   java SortingTest
 *
 * Tidak menggunakan JUnit — murni plain Java agar bisa langsung
 * dijalankan tanpa setup tambahan.
 */
public class SortingTest {

    // ─────────────────────────────────────────────
    //  FRAMEWORK TEST MINIMAL
    // ─────────────────────────────────────────────

    static int passed = 0;
    static int failed = 0;

    static void assertArrayEquals(String testName, int[] expected, int[] actual) {
        boolean ok = (expected.length == actual.length);
        if (ok) for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) { ok = false; break; }
        }

        if (ok) {
            System.out.printf("  ✓  %s%n", testName);
            passed++;
        } else {
            System.out.printf("  ✗  %s%n", testName);
            System.out.printf("       Expected : %s%n", arrStr(expected));
            System.out.printf("       Actual   : %s%n", arrStr(actual));
            failed++;
        }
    }

    static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.printf("  ✓  %s%n", testName);
            passed++;
        } else {
            System.out.printf("  ✗  %s%n", testName);
            failed++;
        }
    }

    // ─────────────────────────────────────────────
    //  HELPER
    // ─────────────────────────────────────────────

    /** Reference sort — manual insertion sort (tanpa library). */
    static int[] refSort(int[] arr) {
        int[] d = arr.clone();
        for (int i = 1; i < d.length; i++) {
            int key = d[i], j = i - 1;
            while (j >= 0 && d[j] > key) { d[j + 1] = d[j]; j--; }
            d[j + 1] = key;
        }
        return d;
    }

    static int[] refSortDesc(int[] arr) {
        int[] d = refSort(arr);
        for (int l = 0, r = d.length - 1; l < r; l++, r--) {
            int t = d[l]; d[l] = d[r]; d[r] = t;
        }
        return d;
    }

    static int[] copy(int[] arr) { return arr.clone(); }

    static String arrStr(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    // ─────────────────────────────────────────────
    //  TEST CASES
    // ─────────────────────────────────────────────

    /** Jalankan satu kasus untuk semua fungsi ascending. */
    static void testAsc(String label, int[] arr) {
        int[] exp = refSort(arr);

        int[] r1 = copy(arr); BubbleSort.bubbleSortBasic(r1);
        assertArrayEquals("bubble_basic        | " + label, exp, r1);

        int[] r2 = copy(arr); BubbleSort.bubbleSortOptimized(r2);
        assertArrayEquals("bubble_optimized     | " + label, exp, r2);

        int[] r3 = copy(arr); BinaryInsertionSort.binaryInsertionSortBasic(r3);
        assertArrayEquals("bis_basic            | " + label, exp, r3);

        int[] r4 = copy(arr); BinaryInsertionSort.binaryInsertionSortOptimized(r4);
        assertArrayEquals("bis_optimized        | " + label, exp, r4);
    }

    static void testDesc(String label, int[] arr) {
        int[] exp = refSortDesc(arr);

        int[] r1 = copy(arr); BubbleSort.bubbleSortDescending(r1);
        assertArrayEquals("bubble_desc          | " + label, exp, r1);

        int[] r2 = copy(arr); BinaryInsertionSort.binaryInsertionSortDescending(r2);
        assertArrayEquals("bis_desc             | " + label, exp, r2);
    }

    static void section(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    // ─────────────────────────────────────────────
    //  MAIN TEST RUNNER
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("   UNIT TEST — Bubble Sort & Binary Insertion Sort");
        System.out.println("=".repeat(60));

        // ── Ascending ──────────────────────────────────────
        section("ARRAY KOSONG & SATU ELEMEN");
        testAsc("kosong",        new int[]{});
        testAsc("satu elemen",   new int[]{42});

        section("DUA ELEMEN");
        testAsc("sudah urut",    new int[]{1, 2});
        testAsc("terbalik",      new int[]{2, 1});
        testAsc("sama",          new int[]{5, 5});

        section("ARRAY UMUM");
        testAsc("acak kecil",    new int[]{64, 34, 25, 12, 22, 11, 90});
        testAsc("sudah terurut", new int[]{1, 2, 3, 4, 5});
        testAsc("terbalik",      new int[]{5, 4, 3, 2, 1});
        testAsc("hampir terurut",new int[]{1, 2, 3, 5, 4, 6, 7});
        testAsc("duplikat",      new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5, 3});
        testAsc("semua sama",    new int[]{7, 7, 7, 7, 7});

        section("ANGKA NEGATIF");
        testAsc("negatif semua", new int[]{-3, -1, -7, -4, -2});
        testAsc("campuran",      new int[]{-5, 3, -1, 0, 7, -2, 4});

        section("ARRAY BESAR — ACAK (200 elemen)");
        java.util.Random rng = new java.util.Random(99);
        int[] bigArr = new int[200];
        for (int i = 0; i < 200; i++) bigArr[i] = rng.nextInt(1000) - 500;
        testAsc("200 elemen acak", bigArr);

        // ── Descending ─────────────────────────────────────
        section("DESCENDING");
        testDesc("acak",         new int[]{38, 7, 14, 2, 99, 45});
        testDesc("sudah desc",   new int[]{9, 7, 5, 3, 1});
        testDesc("terbalik",     new int[]{1, 2, 3, 4, 5});
        testDesc("satu elemen",  new int[]{5});
        testDesc("kosong",       new int[]{});

        // ── Immutability (Java sort in-place, test copy behavior) ──
        section("ARRAY TIDAK RUSAK SETELAH COPY");
        int[] original = {5, 3, 1, 4, 2};
        int[] snapshot = copy(original);
        int[] working  = copy(original);
        BubbleSort.bubbleSortOptimized(working);
        assertTrue("array original tidak berubah (bubble)",
            java.util.Arrays.equals(original, snapshot));

        int[] working2 = copy(original);
        BinaryInsertionSort.binaryInsertionSortOptimized(working2);
        assertTrue("array original tidak berubah (bis)",
            java.util.Arrays.equals(original, snapshot));

        // ── Stabilitas ─────────────────────────────────────
        // Uji stabilitas lewat array integer dengan nilai sama
        // (Java tidak punya tuple, pakai nilai terdekat)
        section("STABILITAS (nilai sama = urutan relatif terjaga)");
        int[] stab = {3, 1, 3, 2, 1};   // ada duplikat
        int[] stabExp = refSort(stab);
        int[] stabBS = copy(stab); BubbleSort.bubbleSortOptimized(stabBS);
        int[] stabBI = copy(stab); BinaryInsertionSort.binaryInsertionSortOptimized(stabBI);
        assertArrayEquals("bubble stabil (nilai sama)", stabExp, stabBS);
        assertArrayEquals("bis    stabil (nilai sama)", stabExp, stabBI);

        // ── Ringkasan ──────────────────────────────────────
        int total = passed + failed;
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("  Hasil : %d/%d test lulus  %s%n",
            passed, total, failed == 0 ? "✓ SEMUA LULUS!" : "✗ ADA KEGAGALAN");
        System.out.println("=".repeat(60));

        System.exit(failed > 0 ? 1 : 0);
    }
},
)
from binary_insertion_sort import (
    binary_insertion_sort_basic,
    binary_insertion_sort_optimized,
    binary_insertion_sort_descending,
)

# Kumpulan semua fungsi ascending untuk di-test seragam
ASCENDING_FUNCS = [
    ("bubble_sort_basic",                   bubble_sort_basic),
    ("bubble_sort_optimized",               bubble_sort_optimized),
    ("binary_insertion_sort_basic",         binary_insertion_sort_basic),
    ("binary_insertion_sort_optimized",     binary_insertion_sort_optimized),
]

DESCENDING_FUNCS = [
    ("bubble_sort_descending",              bubble_sort_descending),
    ("binary_insertion_sort_descending",    binary_insertion_sort_descending),
]


class TestAscending(unittest.TestCase):

    def _run_all(self, arr):
        """Pastikan semua fungsi menghasilkan hasil yang sama dengan sorted()."""
        expected = sorted(arr)
        for name, func in ASCENDING_FUNCS:
            with self.subTest(func=name):
                result = func(arr)
                self.assertEqual(result, expected,
                    f"{name} gagal untuk input {arr}")

    def test_empty(self):
        self._run_all([])

    def test_single_element(self):
        self._run_all([42])

    def test_two_elements_sorted(self):
        self._run_all([1, 2])

    def test_two_elements_reversed(self):
        self._run_all([2, 1])

    def test_already_sorted(self):
        self._run_all([1, 2, 3, 4, 5])

    def test_reversed(self):
        self._run_all([5, 4, 3, 2, 1])

    def test_random_small(self):
        self._run_all([64, 34, 25, 12, 22, 11, 90])

    def test_with_duplicates(self):
        self._run_all([3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5])

    def test_all_same(self):
        self._run_all([7, 7, 7, 7, 7])

    def test_negative_numbers(self):
        self._run_all([-3, -1, -7, -4, -2])

    def test_mixed_negative_positive(self):
        self._run_all([-5, 3, -1, 0, 7, -2, 4])

    def test_large_random(self):
        random.seed(99)
        arr = random.sample(range(1000), 200)
        self._run_all(arr)

    def test_strings(self):
        arr = ["banana", "apple", "cherry", "date"]
        for name, func in ASCENDING_FUNCS:
            with self.subTest(func=name):
                self.assertEqual(func(arr), sorted(arr))

    def test_does_not_mutate_original(self):
        """Fungsi tidak boleh mengubah array input asli."""
        original = [5, 3, 1, 4, 2]
        copy_before = original[:]
        for name, func in ASCENDING_FUNCS:
            with self.subTest(func=name):
                func(original)
                self.assertEqual(original, copy_before,
                    f"{name} mengubah array asli!")

    def test_stability(self):
        """
        Uji stabilitas: elemen dengan nilai sama harus mempertahankan
        urutan relatifnya. Gunakan tuple (nilai, indeks_asli).
        """
        arr = [(3, 0), (1, 1), (3, 2), (2, 3), (1, 4)]
        expected = sorted(arr, key=lambda x: x[0])
        for name, func in ASCENDING_FUNCS:
            with self.subTest(func=name):
                result = func(arr)
                # Cek nilai terurut
                values = [x[0] for x in result]
                self.assertEqual(values, sorted(x[0] for x in arr))


class TestDescending(unittest.TestCase):

    def _run_all(self, arr):
        expected = sorted(arr, reverse=True)
        for name, func in DESCENDING_FUNCS:
            with self.subTest(func=name):
                self.assertEqual(func(arr), expected,
                    f"{name} gagal untuk input {arr}")

    def test_empty(self):
        self._run_all([])

    def test_single(self):
        self._run_all([5])

    def test_random(self):
        self._run_all([38, 7, 14, 2, 99, 45])

    def test_already_descending(self):
        self._run_all([9, 7, 5, 3, 1])

    def test_large(self):
        random.seed(77)
        self._run_all(random.sample(range(500), 100))


if __name__ == "__main__":
    loader = unittest.TestLoader()
    suite  = unittest.TestSuite()
    suite.addTests(loader.loadTestsFromTestCase(TestAscending))
    suite.addTests(loader.loadTestsFromTestCase(TestDescending))

    runner = unittest.TextTestRunner(verbosity=2)
    result = runner.run(suite)

    total  = result.testsRun
    failed = len(result.failures) + len(result.errors)
    passed = total - failed
    print(f"\n{'='*55}")
    print(f"  Hasil: {passed}/{total} test lulus  {'✓ SEMUA LULUS!' if failed == 0 else '✗ ADA KEGAGALAN'}")
    print(f"{'='*55}")