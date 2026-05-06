"""
=============================================================
  BINARY INSERTION SORT - Implementasi Dasar & Teroptimasi
=============================================================

Konsep:
  Binary Insertion Sort adalah varian dari Insertion Sort.
  Perbedaannya: posisi insersi dicari menggunakan Binary Search
  (bukan linear scan), sehingga jumlah perbandingan lebih sedikit.

  Cara kerja:
    1. Ambil elemen ke-i dari array (mulai i=1).
    2. Gunakan Binary Search untuk menemukan posisi yang tepat
       dalam bagian kiri yang sudah terurut (indeks 0..i-1).
    3. Geser semua elemen di sebelah kanan posisi itu satu langkah
       ke kanan, lalu sisipkan elemen di posisi tersebut.
    4. Ulangi sampai semua elemen diproses.

Kompleksitas:
  - Perbandingan : O(n log n)   ← lebih baik dari Insertion Sort biasa
  - Pergeseran   : O(n²)        ← masih O(n²) karena shifting
  - Ruang        : O(1)         ← in-place sorting
  - Best-case    : O(n log n)   (bukan O(n) karena binary search tetap jalan)
"""


# ─────────────────────────────────────────────
#  HELPER: Binary Search untuk cari posisi insersi
# ─────────────────────────────────────────────

def _binary_search_position(arr: list, target, low: int, high: int) -> int:
    """
    Mencari posisi yang tepat untuk menyisipkan `target` ke dalam
    subarray arr[low..high] yang sudah terurut (ascending).

    Mengembalikan indeks paling kiri di mana target bisa disisipkan
    agar urutan tetap terjaga (left-biased, stabil).

    Args:
        arr    : List yang sebagian sudah terurut
        target : Nilai yang akan disisipkan
        low    : Batas kiri pencarian (inklusif)
        high   : Batas kanan pencarian (inklusif)

    Returns:
        Indeks posisi insersi
    """
    while low <= high:
        mid = (low + high) // 2
        if arr[mid] < target:
            low = mid + 1
        else:
            high = mid - 1
    return low                  # 'low' sekarang adalah posisi insersi


# ─────────────────────────────────────────────
#  VERSI DASAR
# ─────────────────────────────────────────────

def binary_insertion_sort_basic(arr: list) -> list:
    """
    Binary Insertion Sort dasar.
    Menggunakan Binary Search untuk mengurangi jumlah perbandingan,
    namun pergeseran elemen tetap dilakukan satu per satu.

    Args:
        arr: List yang akan diurutkan (tidak diubah — dikembalikan salinannya)

    Returns:
        List baru yang sudah terurut secara ascending
    """
    data = arr[:]
    n = len(data)

    for i in range(1, n):
        key = data[i]

        # Cari posisi insersi menggunakan binary search di arr[0..i-1]
        pos = _binary_search_position(data, key, 0, i - 1)

        # Geser elemen dari pos hingga i-1 satu langkah ke kanan
        j = i
        while j > pos:
            data[j] = data[j - 1]
            j -= 1

        data[pos] = key         # sisipkan elemen di posisi yang tepat

    return data


# ─────────────────────────────────────────────
#  VERSI OPTIMASI
# ─────────────────────────────────────────────

def binary_insertion_sort_optimized(arr: list) -> list:
    """
    Binary Insertion Sort Teroptimasi — tiga teknik:

    Optimasi 1 — Batch shifting dengan slice assignment:
        Menggantikan loop pergeseran satu-per-satu dengan operasi slice
        Python yang diimplementasikan di level C, jauh lebih cepat
        untuk array berukuran besar di lingkungan CPython.

    Optimasi 2 — Skip elemen yang sudah di posisi benar:
        Jika posisi insersi == i (elemen sudah di tempat), tidak ada
        pergeseran maupun assignment — langsung lanjut ke i berikutnya.

    Optimasi 3 — Sentinel untuk array hampir terurut:
        Sebelum binary search, cek dulu apakah elemen lebih besar dari
        arr[i-1]. Jika ya, elemen sudah di posisi benar → skip seluruhnya.
        Membuat best-case menjadi O(n) ketika array sudah terurut.

    Args:
        arr: List yang akan diurutkan (tidak diubah — dikembalikan salinannya)

    Returns:
        List baru yang sudah terurut secara ascending
    """
    data = arr[:]
    n = len(data)

    for i in range(1, n):
        key = data[i]

        # Optimasi 3: sentinel check — skip jika sudah di urutan benar
        if key >= data[i - 1]:
            continue

        # Cari posisi insersi dengan binary search
        pos = _binary_search_position(data, key, 0, i - 1)

        # Optimasi 2: tidak perlu geser jika sudah di posisi yang benar
        if pos == i:
            continue

        # Optimasi 1: batch shift menggunakan slice assignment (level C)
        data[pos + 1 : i + 1] = data[pos : i]
        data[pos] = key

    return data


# ─────────────────────────────────────────────
#  VERSI DESCENDING (bonus)
# ─────────────────────────────────────────────

def _binary_search_descending(arr: list, target, low: int, high: int) -> int:
    """Binary search untuk urutan descending."""
    while low <= high:
        mid = (low + high) // 2
        if arr[mid] > target:
            low = mid + 1
        else:
            high = mid - 1
    return low


def binary_insertion_sort_descending(arr: list) -> list:
    """Binary Insertion Sort Teroptimasi — urutan descending."""
    data = arr[:]
    n = len(data)

    for i in range(1, n):
        key = data[i]

        if key <= data[i - 1]:     # ← kondisi sentinel terbalik
            continue

        pos = _binary_search_descending(data, key, 0, i - 1)

        if pos == i:
            continue

        data[pos + 1 : i + 1] = data[pos : i]
        data[pos] = key

    return data


# ─────────────────────────────────────────────
#  CONTOH PENGGUNAAN
# ─────────────────────────────────────────────

if __name__ == "__main__":
    import time

    print("=" * 55)
    print("      DEMO BINARY INSERTION SORT")
    print("=" * 55)

    # --- Contoh 1: Array acak ---
    data1 = [64, 34, 25, 12, 22, 11, 90]
    print(f"\n[Contoh 1] Array acak")
    print(f"  Input  : {data1}")
    print(f"  Output : {binary_insertion_sort_optimized(data1)}")

    # --- Contoh 2: Array sudah terurut (best-case dengan sentinel) ---
    data2 = [1, 2, 3, 4, 5]
    print(f"\n[Contoh 2] Array sudah terurut (best-case)")
    print(f"  Input  : {data2}")
    print(f"  Output : {binary_insertion_sort_optimized(data2)}")

    # --- Contoh 3: Array terbalik (worst-case) ---
    data3 = [5, 4, 3, 2, 1]
    print(f"\n[Contoh 3] Array terbalik (worst-case)")
    print(f"  Input  : {data3}")
    print(f"  Output : {binary_insertion_sort_optimized(data3)}")

    # --- Contoh 4: Descending ---
    data4 = [38, 7, 14, 2, 99, 45]
    print(f"\n[Contoh 4] Descending sort")
    print(f"  Input  : {data4}")
    print(f"  Output : {binary_insertion_sort_descending(data4)}")

    # --- Contoh 5: String sorting ---
    data5 = ["banana", "apple", "cherry", "date", "elderberry"]
    print(f"\n[Contoh 5] String sorting")
    print(f"  Input  : {data5}")
    print(f"  Output : {binary_insertion_sort_optimized(data5)}")

    # --- Contoh 6: Benchmark basic vs optimized ---
    import random
    random.seed(42)
    besar = random.sample(range(10_000), 3000)

    start = time.perf_counter()
    binary_insertion_sort_basic(besar)
    t_basic = time.perf_counter() - start

    start = time.perf_counter()
    binary_insertion_sort_optimized(besar)
    t_opt = time.perf_counter() - start

    print(f"\n[Contoh 6] Benchmark — 3.000 elemen acak")
    print(f"  Basic     : {t_basic:.4f} detik")
    print(f"  Optimized : {t_opt:.4f} detik")
    speedup = t_basic / t_opt if t_opt > 0 else float('inf')
    print(f"  Speedup   : {speedup:.2f}x lebih cepat")

    print("\n" + "=" * 55)