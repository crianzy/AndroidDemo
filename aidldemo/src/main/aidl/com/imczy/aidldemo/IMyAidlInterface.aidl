// IMyAidlInterface.aidl
package com.imczy.aidldemo;

// Declare any non-default types here with import statements
import com.imczy.aidldemo.Book;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int getPid();

    void addBook(in Book book);

    void waitToReady();
}
