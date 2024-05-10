import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Dean Miyata-Dawson
 * @version 1.0
 * @userid ddawson42
 * @GTID 903833148
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Error: array or comparator is null.");
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[maxIndex];
            arr[maxIndex] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Error: array or comparator is null.");
        }

        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;
        while (start < end && swapped) {
            swapped = false;
            int lastEndSwap = 0;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    lastEndSwap = i;
                }
            }
            end = lastEndSwap;

            if (swapped) {
                swapped = false;
                int lastFrontSwap = 0;
                for (int j = end; j > start; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        T temp = arr[j - 1];
                        arr[j - 1] = arr[j];
                        arr[j] = temp;
                        swapped = true;
                        lastFrontSwap = j;
                    }
                }
                start = lastFrontSwap;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Error: array or comparator is null.");
        }

        if (arr.length > 1) {
            int middleIndex = (int) (arr.length / 2);
            int leftSize = (int) (arr.length / 2);
            int rightSize = arr.length - leftSize;
            T[] leftArr = (T[]) new Object[leftSize];
            T[] rightArr = (T[]) new Object[rightSize];
            for (int i = 0; i < leftSize; i++) {
                leftArr[i] = arr[i];
            }
            for (int j = leftSize; j < arr.length; j++) {
                rightArr[j - leftSize] = arr[j];
            }

            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);

            int leftIndex = 0;
            int rightIndex = 0;
            int currentIndex = 0;
            while (leftIndex < middleIndex && rightIndex < arr.length - middleIndex) {
                if (comparator.compare(leftArr[leftIndex], rightArr[rightIndex]) <= 0) {
                    arr[currentIndex] = leftArr[leftIndex];
                    leftIndex++;
                } else {
                    arr[currentIndex] = rightArr[rightIndex];
                    rightIndex++;
                }
                currentIndex++;
            }

            while (leftIndex < middleIndex) {
                arr[currentIndex] = leftArr[leftIndex];
                leftIndex++;
                currentIndex++;
            }
            while (rightIndex < arr.length - middleIndex) {
                arr[currentIndex] = rightArr[rightIndex];
                rightIndex++;
                currentIndex++;
            }
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Error: array, comparator, random object is null.");
        }

        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Error: K is not within range.");
        } else {
            return kthSelectHelper(k, arr, comparator, rand, 0, arr.length);
        }
    }

    /**
     * Helper method for kthSelect
     *
     * @param k index to retrive data from + 1
     * @param arr modified array after method is finished
     * @param comparator comparator used to compare data in arr
     * @param rand random object used to select pivots
     * @param left left array
     * @param right right array
     * @param <T> data type to sort
     * @return kth smallest element
     */
    private static <T> T kthSelectHelper(int k, T[] arr, Comparator<T> comparator, Random rand, int left, int right) {
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivot = arr[pivotIndex];
        int j = right - 1;
        T lowest = arr[left];
        arr[pivotIndex] = lowest;
        arr[left] = pivot;
        int i = left + 1;
        while (i <= j) {
            while (i <= j && comparator.compare(pivot, arr[i]) >= 0) {
                i++;
            }
            while (i <= j && comparator.compare(pivot, arr[j]) <= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        arr[left] = arr[j];
        arr[j] = pivot;
        if (j == k - 1) {
            return pivot;
        } else if (j < k - 1) {
            return kthSelectHelper(k, arr, comparator, rand, j + 1, right);
        } else {
            return kthSelectHelper(k, arr, comparator, rand, left, j);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Error: array is null.");
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }

        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIndex = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrIndex++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Error: data is null.");
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            sorted[i] = (int) queue.remove();
        }
        return sorted;
    }
}
