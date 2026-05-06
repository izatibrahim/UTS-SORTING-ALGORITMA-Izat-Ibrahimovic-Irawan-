"""
=============================================================
  BUBBLE SORT - Implementasi Dasar & Versi Teroptimasi
=============================================================

Konsep:
  Bubble Sort bekerja dengan cara membandingkan dua elemen
  yang berdekatan, lalu menukarnya jika urutan salah.
  Proses ini diulang hingga seluruh array terurut.

Kompleksitas:
  - Waktu (Worst/Average) : O(n²)
  - Waktu (Best / Sudah terurut) : O(n)  ← hanya versi optimasi
  - Ruang (Space)          : O(1)  — in-place sorting
"""


# ─────────────────────────────────────────────
#  VERSI DASAR (tanpa optimasi)
# ─────────────────────────────────────────────

def bubble_sort_basic(arr: list) -> list:
    """
    Bubble Sort dasar.
    Selalu menjalankan n*(n-1)/2 perbandingan meski array sudah terurut.

    Args:
        arr: List yang akan diurutkan (tidak diubah — dikembalikan salinannya)

    Returns:
        List baru yang sudah terurut secara ascending
    """
    data = arr[:]          # salin agar array asli tidak berubah
    n = len(data)

    for i in range(n):
        for j in range(0, n - i - 1):
            if data[j] > data[j + 1]:
                data[j], data[j + 1] = data[j + 1], data[j]   # swap

    return data


# ─────────────────────────────────────────────
#  VERSI OPTIMASI
# ─────────────────────────────────────────────

def bubble_sort_optimized(arr: list) -> list:
    """
    Bubble Sort Teroptimasi — dua teknik sekaligus:

    Optimasi 1 — Early Termination (Flag 'swapped'):
        Jika dalam satu pass tidak ada swap, array sudah terurut → berhenti.
        Membuat best-case menjadi O(n) untuk array yang sudah/hampir terurut.

    Optimasi 2 — Shrinking Boundary:
        Catat posisi swap terakhir di setiap pass.  Elemen di sebelah kanan
        posisi tersebut sudah pasti terurut → batas iterasi dipersempit,
        mengurangi perbandingan yang tidak perlu.

    Args:
        arr: List yang akan diurutkan (tidak diubah — dikembalikan salinannya)

    Returns:
        List baru yang sudah terurut secara ascending
    """
    data = arr[:]
    n = len(data)
    right_boundary = n - 1          # batas kanan yang masih perlu diperiksa

    while right_boundary > 0:
        last_swap_index = 0         # posisi swap terakhir dalam pass ini
        swapped = False             # flag: apakah ada swap?

        for j in range(right_boundary):
            if data[j] > data[j + 1]:
                data[j], data[j + 1] = data[j + 1], data[j]
                last_swap_index = j
                swapped = True

        if not swapped:             # Optimasi 1: tidak ada swap → selesai
            break

        right_boundary = last_swap_index   # Optimasi 2: perkecil batas

    return data


# ─────────────────────────────────────────────
#  VERSI DESCENDING (bonus)
# ─────────────────────────────────────────────

def bubble_sort_descending(arr: list) -> list:
    """
    Bubble Sort Teroptimasi — urutan descending (besar ke kecil).
    Hanya mengubah kondisi perbandingan dari > menjadi <.
    """
    data = arr[:]
    n = len(data)
    right_boundary = n - 1

    while right_boundary > 0:
        last_swap_index = 0
        swapped = False

        for j in range(right_boundary):
            if data[j] < data[j + 1]:          # ← tanda beda dari ascending
                data[j], data[j + 1] = data[j + 1], data[j]
                last_swap_index = j
                swapped = True

        if not swapped:
            break
        right_boundary = last_swap_index

    return data


# ─────────────────────────────────────────────
#  CONTOH PENGGUNAAN
# ─────────────────────────────────────────────

if __name__ == "__main__":
    import time

    print("=" * 55)
    print("         DEMO BUBBLE SORT")
    print("=" * 55)

    # --- Contoh 1: Array acak ---
    data1 = [64, 34, 25, 12, 22, 11, 90]
    print(f"\n[Contoh 1] Array acak")
    print(f"  Input  : {data1}")
    print(f"  Output : {bubble_sort_optimized(data1)}")

    # --- Contoh 2: Array sudah terurut (best-case) ---
    data2 = [1, 2, 3, 4, 5]
    print(f"\n[Contoh 2] Array sudah terurut (best-case)")
    print(f"  Input  : {data2}")
    print(f"  Output : {bubble_sort_optimized(data2)}")

    # --- Contoh 3: Array terbalik (worst-case) ---
    data3 = [5, 4, 3, 2, 1]
    print(f"\n[Contoh 3] Array terbalik (worst-case)")
    print(f"  Input  : {data3}")
    print(f"  Output : {bubble_sort_optimized(data3)}")

    # --- Contoh 4: Descending ---
    data4 = [38, 7, 14, 2, 99, 45]
    print(f"\n[Contoh 4] Descending sort")
    print(f"  Input  : {data4}")
    print(f"  Output : {bubble_sort_descending(data4)}")

    # --- Contoh 5: Benchmark basic vs optimized ---
    import random
    random.seed(42)
    besar = random.sample(range(10_000), 3000)   # 3000 elemen acak

    start = time.perf_counter()
    bubble_sort_basic(besar)
    t_basic = time.perf_counter() - start

    start = time.perf_counter()
    bubble_sort_optimized(besar)
    t_opt = time.perf_counter() - start

    print(f"\n[Contoh 5] Benchmark — 3.000 elemen acak")
    print(f"  Basic     : {t_basic:.4f} detik")
    print(f"  Optimized : {t_opt:.4f} detik")
    speedup = t_basic / t_opt if t_opt > 0 else float('inf')
    print(f"  Speedup   : {speedup:.2f}x lebih cepat")

    # --- Contoh 6: Hampir terurut (early-termination bersinar) ---
    almost = list(range(1, 101))   # [1..100]
    almost[98], almost[99] = almost[99], almost[98]   # swap 2 elemen terakhir

    start = time.perf_counter()
    bubble_sort_basic(almost[:])
    t_basic2 = time.perf_counter() - start

    start = time.perf_counter()
    bubble_sort_optimized(almost[:])
    t_opt2 = time.perf_counter() - start

    print(f"\n[Contoh 6] Benchmark — 100 elemen hampir terurut")
    print(f"  Basic     : {t_basic2:.6f} detik")
    print(f"  Optimized : {t_opt2:.6f} detik")

    print("\n" + "=" * 55)