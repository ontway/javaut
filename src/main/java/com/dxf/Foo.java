package com.dxf;

public class Foo {
    private static A a = new A();
    private B b = new B();

    public static A getA() {
        return a;
    }

    public static void setA(A a) {
        Foo.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public String fooMethod1() {
        A a = new A();
        a.setName("fooMethod1");
        return a.getName();
    }

    public String fooMethod2() {
        return fooPrivateMethod();
    }

    public static String fooMethodStatic() {
        return "fooMethodStatic";
    }

    private String fooPrivateMethod() {
        return "fooPrivateMethod";
    }

}
