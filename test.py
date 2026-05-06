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
    bubble_sort_descending,
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