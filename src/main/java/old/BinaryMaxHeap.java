package old;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: classicalalgorithms
 * @description: 堆
 * @author: WANGYE
 * @create: 2019-02-26 21:42
 **/
public class BinaryMaxHeap<T extends Comparable> {
    private Object[] array;
    private int size;
    private T MIN;

    public Object[] getArray() {
        return array;
    }

    public void setArray(T[] array) {
        this.array = array;
        size = array.length;
    }

    public T getMIN() {
        return MIN;
    }

    public void setMIN(T MIN) {
        this.MIN = MIN;
    }

    //    左右树为最大堆
    public void maxify(int idx) {
        int lidx = getLeftIdx(idx);
        if (lidx > size - 1) return;
        int largestIdx = idx;
        T largest = (T) array[idx];
        if (largest.compareTo(array[lidx]) < 0) {
            largestIdx = lidx;
            largest = (T) array[lidx];
        }
        int ridx = getRightIdx(idx);
        if (ridx <= size - 1) {
            if (largest.compareTo(array[ridx]) < 0) {
                largestIdx = ridx;
            }
        }
        if (largestIdx != idx) {
            swap(idx, largestIdx);
            maxify(largestIdx);
        }
    }

    public void createHeap() {
//        最后一个有子节点
        int idx = getParentIdx(size - 1);
        for (int i = idx; i >= 0; i--)
            maxify(i);
    }

    public void delMax() {
        array[0] = array[size - 1];
        maxify(0);
        size--;
        if (size > 0)
            System.arraycopy(array, 0, array, 0, size);
    }

    public void insertN(T t) {
        size++;
        enSureCapcity();
        array[size - 1] = MIN;
        changeKey(size - 1, t);
    }


    private void changeKey(int idx, T t) {
        if (idx == 0) return;
        int pidx = getParentIdx(idx);
        array[idx] = t;
        if (t.compareTo(array[pidx]) > 0) {
            swap(pidx, idx);
            changeKey(pidx, t);
        } else {
            //未交换就是原有idx
            return;
        }
    }

    public List heapSort() {
        BinaryMaxHeap tmp = clone();
        List<T> list = new ArrayList<>();
        while (tmp.size >= 1) {
            list.add((T) tmp.array[0]);
            tmp.delMax();
        }
        return list;
    }

    protected BinaryMaxHeap clone() {
        BinaryMaxHeap tmp = new BinaryMaxHeap();
        tmp.array = array.clone();
        tmp.size = size;
        tmp.MIN = MIN;
        return tmp;
    }

    private int getLeftIdx(int idx) {
        return 2 * idx + 1;
    }

    private int getRightIdx(int idx) {
        return 2 * idx + 2;
    }

    private int getParentIdx(int idx) {
        if (idx % 2 == 0) return idx / 2 - 1;
        else return (idx - 1) / 2;
    }

    private void swap(int idx1, int idx2) {
        T tmp = (T) array[idx1];
        array[idx1] = array[idx2];
        array[idx2] = tmp;
    }

    private void enSureCapcity() {
        if (size > array.length) grow();
    }

    private void grow() {
        long newCapacity = (long) (1.5 * size);
        if (newCapacity > Integer.MAX_VALUE) throw new OutOfMemoryError();
        array = Arrays.copyOf(array, (int) newCapacity);
    }
}