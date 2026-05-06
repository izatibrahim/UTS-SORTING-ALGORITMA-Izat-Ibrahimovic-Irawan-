"""
=============================================================
    DEMO & PERBANDINGAN: Bubble Sort vs Binary Insertion Sort
=============================================================
Jalankan file ini untuk melihat perbandingan langsung
antara kedua algoritma sorting.

    python main.py
"""

import random
import time

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


# ─────────────────────────────────────────────
#  HELPER UTILS
# ─────────────────────────────────────────────

def header(title: str) -> None:
    print("\n" + "=" * 60)
    print(f"  {title}")
    print("=" * 60)


def bench(func, data: list) -> tuple:
    """Jalankan fungsi dan kembalikan (hasil, waktu_detik)."""
    start = time.perf_counter()
    result = func(data[:])
    elapsed = time.perf_counter() - start
    return result, elapsed


# ─────────────────────────────────────────────
#  BAGIAN 1 — DEMO FUNGSIONAL
# ─────────────────────────────────────────────

header("DEMO FUNGSIONAL")

samples = {
    "Acak"          : [64, 34, 25, 12, 22, 11, 90],
    "Hampir terurut": [1, 2, 3, 5, 4, 6, 7],
    "Sudah terurut" : [1, 2, 3, 4, 5, 6, 7],
    "Terbalik"      : [7, 6, 5, 4, 3, 2, 1],
    "Duplikat"      : [3, 1, 4, 1, 5, 9, 2, 6, 5, 3],
}

for label, arr in samples.items():
    bs  = bubble_sort_optimized(arr)
    bis = binary_insertion_sort_optimized(arr)
    match = "✓" if bs == bis else "✗"
    print(f"\n  [{label}]")
    print(f"    Input          : {arr}")
    print(f"    Bubble Sort    : {bs}")
    print(f"    BinaryIns Sort : {bis}  {match} sama")


# ─────────────────────────────────────────────
#  BAGIAN 2 — BENCHMARK
# ─────────────────────────────────────────────

header("BENCHMARK PERFORMA")

scenarios = [
    ("Acak (500 el)",          lambda n: random.sample(range(n * 10), n),  500),
    ("Acak (2.000 el)",        lambda n: random.sample(range(n * 10), n), 2000),
    ("Sudah terurut (1.000)",  lambda n: list(range(n)),                  1000),
    ("Hampir terurut (1.000)", lambda n: list(range(n - 5)) + random.sample(range(n), 5), 1000),
    ("Terbalik (500 el)",      lambda n: list(range(n, 0, -1)),            500),
]

random.seed(2024)

print(f"\n  {'Skenario':<30} {'BS-Basic':>10} {'BS-Opt':>10} {'BIS-Basic':>11} {'BIS-Opt':>10}")
print("  " + "-" * 75)

for label, gen, n in scenarios:
    data = gen(n)

    _, t_bs_b  = bench(bubble_sort_basic,                  data)
    _, t_bs_o  = bench(bubble_sort_optimized,              data)
    _, t_bi_b  = bench(binary_insertion_sort_basic,        data)
    _, t_bi_o  = bench(binary_insertion_sort_optimized,    data)

    print(f"  {label:<30} {t_bs_b*1000:>8.2f}ms {t_bs_o*1000:>8.2f}ms "
            f"{t_bi_b*1000:>9.2f}ms {t_bi_o*1000:>8.2f}ms")


# ─────────────────────────────────────────────
#  BAGIAN 3 — DESCENDING DEMO
# ─────────────────────────────────────────────

header("DEMO DESCENDING SORT")

arr_desc = [38, 7, 14, 2, 99, 45, 31]
print(f"\n  Input             : {arr_desc}")
print(f"  Bubble (desc)     : {bubble_sort_descending(arr_desc)}")
print(f"  BinaryIns (desc)  : {binary_insertion_sort_descending(arr_desc)}")


# ─────────────────────────────────────────────
#  BAGIAN 4 — RINGKASAN
# ─────────────────────────────────────────────

header("RINGKASAN PERBANDINGAN")

summary = """
    ┌─────────────────────┬──────────────────────┬──────────────────────┐
    │ Aspek               │ Bubble Sort          │ Binary Ins. Sort     │
    ├─────────────────────┼──────────────────────┼──────────────────────┤
    │ Perbandingan        │ O(n²)                │ O(n log n)           │
    │ Pergeseran/Swap     │ O(n²)  banyak swap   │ O(n²)  geser blok    │
    │ Best-case           │ O(n)  *dgn optimasi  │ O(n)  *dgn sentinel  │
    │ Worst-case          │ O(n²)                │ O(n²)                │
    │ Space               │ O(1)  in-place       │ O(1)  in-place       │
    │ Stabilitas          │ Stabil               │ Stabil               │
    │ Cocok untuk         │ Array kecil/hampir   │ Array kecil-menengah │
    │                     │ terurut              │ dgn banyak insertion  │
    └─────────────────────┴──────────────────────┴──────────────────────┘
"""
print(summary)