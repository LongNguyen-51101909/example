// IAdditionService.aidl
package com.example.nqlong.example;

// Declare any non-default types here with import statements

interface IAdditionService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     //You can pass values in, out, or inout.
     // Primitive datatypes (such as int, boolean, ect.) can only be passed in.
     int add(int value1, int value2);
}

