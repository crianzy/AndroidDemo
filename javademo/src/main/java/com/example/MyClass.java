package com.example;

public class MyClass {

    public static void main(String args[]) {

        Bucket bucket = new Bucket();
        System.out.println("1 : bucket = " + bucket);

        bucket.set(2);
        System.out.println("2 : bucket = " + bucket);

        bucket.set(6);
        System.out.println("3 : bucket = " + bucket);

        bucket.clear(3);
        System.out.println("4 : bucket = " + bucket);

        bucket.set(4);
        System.out.println("5 : bucket.get(6) = " + bucket.get(6));
        System.out.println("6 : bucket.get(3) = " + bucket.get(3));


        bucket.insert(12, true);
        System.out.println("7 : bucket = " + bucket);

        bucket.insert(11, true);
        System.out.println("8 : bucket = " + bucket);

        System.out.println("8 : bucket = " + bucket.countOnesBefore(14));

        System.out.println("8 : getOffset = " + getOffset(bucket, 15));

        System.out.println("8 : bucket = " + bucket);


    }


    public static int getOffset(Bucket bucket, int index) {
        if (index < 0) {
            return -1; //anything below 0 won't work as diff will be undefined.
        }
        final int limit = 30;
        int offset = index;
        while (offset < limit) {
            final int removedBefore = bucket.countOnesBefore(offset);
            final int diff = index - (offset - removedBefore);
            if (diff == 0) {
                while (bucket.get(offset)) { // ensure this offset is not hidden
                    offset++;
                }
                return offset;
            } else {
                offset += diff;
            }
        }
        return -1;
    }


    /**
     * Bitset implementation that provides methods to offset indices.
     * <p/>
     * 目录  链表结构
     */
    static class Bucket {

        final static int BITS_PER_WORD = Long.SIZE;//64 移动 6位

        final static long LAST_BIT = 1L << (Long.SIZE - 1);// -64

        long mData = 0;// 代表 那位上 有数据

        Bucket next;

        // 这个位置 插入一个极点
        void set(int index) {
            if (index >= BITS_PER_WORD) {// 如果大于 64
                ensureNext();
                next.set(index - BITS_PER_WORD);
            } else {
                mData |= 1L << index;
            }
        }

        // 获取下一个节点
        private void ensureNext() {
            if (next == null) {
                next = new Bucket();
            }
        }

        /**
         * 清除这个位置的界定啊
         * 且遍历清除 这个极点 后面的节点
         *
         * @param index
         */
        void clear(int index) {
            if (index >= BITS_PER_WORD) {// 如果大于64
                if (next != null) {
                    next.clear(index - BITS_PER_WORD);
                }
            } else {
                mData &= ~(1L << index);
            }

        }

        /**
         * 查看这个位置 是否 有节点
         *
         * @param index
         * @return
         */
        boolean get(int index) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                return next.get(index - BITS_PER_WORD);
            } else {
                return (mData & (1L << index)) != 0;
            }
        }

        /**
         * 递归重置数据
         */
        void reset() {
            mData = 0;
            if (next != null) {
                next.reset();
            }
        }

        /**
         * 插入一位
         *
         * @param index
         * @param value 是否设置值
         *              <p/>
         *              计算不设置值 也会插入
         */
        void insert(int index, boolean value) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                next.insert(index - BITS_PER_WORD, value);
            } else {
                final boolean lastBit = (mData & LAST_BIT) != 0;
                long mask = (1L << index) - 1;
                final long before = mData & mask;
                final long after = ((mData & ~mask)) << 1;
                mData = before | after;
                if (value) {
                    set(index);
                } else {
                    clear(index);
                }
                if (lastBit || next != null) {
                    ensureNext();
                    next.insert(0, lastBit);
                }
            }
        }

        // 直接 移除 1位
        boolean remove(int index) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                return next.remove(index - BITS_PER_WORD);
            } else {
                long mask = (1L << index);
                final boolean value = (mData & mask) != 0;
                mData &= ~mask;
                mask = mask - 1;
                final long before = mData & mask;
                // cannot use >> because it adds one.
                final long after = Long.rotateRight(mData & ~mask, 1);
                mData = before | after;
                if (next != null) {
                    if (next.get(0)) {
                        set(BITS_PER_WORD - 1);
                    }
                    next.remove(0);
                }
                return value;
            }
        }

        int countOnesBefore(int index) {
            if (next == null) {
                if (index >= BITS_PER_WORD) {
                    return Long.bitCount(mData);
                }
                return Long.bitCount(mData & ((1L << index) - 1));
            }
            if (index < BITS_PER_WORD) {
                return Long.bitCount(mData & ((1L << index) - 1));
            } else {
                return next.countOnesBefore(index - BITS_PER_WORD) + Long.bitCount(mData);
            }
        }

        @Override
        public String toString() {
            return next == null ? Long.toBinaryString(mData)
                    : next.toString() + "xx" + Long.toBinaryString(mData);
        }
    }

}
