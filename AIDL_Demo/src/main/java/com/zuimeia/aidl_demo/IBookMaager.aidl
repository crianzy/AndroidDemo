// IBookMaager.aidl
package com.zuimeia.aidl_demo;


//import com.zuimeia.aidl_demo.Book;
// Declare any non-default types here with import statements

interface IBookMaager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int addBook(int  book);

    int addBook2(Book  book);
}
