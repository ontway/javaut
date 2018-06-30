package com.dxf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Foo.class, A.class, B.class})
public class PowerMockTest {

    /**
     * 不能用@RunWith(PowerMockRunner.class)时
     */
//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

    /**
     * mock 成员变量
     *
     * @throw
     */
    @Test
    public void staticVarTest() throws Exception {
        A a = mock(A.class);
        B b = mock(B.class);
        Foo foo = new Foo();

        PowerMockito.when(a.getName()).thenReturn("mockA");
        PowerMockito.when(b.getName()).thenReturn("mockB");

        assertEquals("A", Foo.getA().getName());
        assertEquals("B", foo.getB().getName());

        //mock 静态变量(1)
        Field field = PowerMockito.field(Foo.class, "a");
        field.set(Foo.class, a);
        assertEquals("mockA", Foo.getA().getName());

        //mock 静态变量(2)
        MemberModifier.field(Foo.class, "a").set(Foo.class, a);
        assertEquals("mockA", Foo.getA().getName());

        //mock 普通变量(1)
        Field field1 = PowerMockito.field(Foo.class, "b");
        field1.set(foo, b);
        assertEquals("mockB", foo.getB().getName());

        //mock 普通变量(2)
        MemberModifier.field(Foo.class, "b").set(foo, b);
        assertEquals("mockB", foo.getB().getName());
    }

    /**
     * 改变成员变量，改变方法返回值，或者忽略方法
     * MemberModifier
     */
    @Test
    public void testMemberModifier() {
        A a = mock(A.class);
        B b = mock(B.class);
        Foo foo = new Foo();

        PowerMockito.when(a.getName()).thenReturn("mockA");
        PowerMockito.when(b.getName()).thenReturn("mockB");
        assertEquals("fooMethod1", foo.fooMethod1());

        //忽略方法
        MemberModifier.suppress(MemberMatcher.method(A.class, "setName"));
        assertEquals("A", foo.fooMethod1());

        //改变方法返回值
        MemberModifier.stub(MemberMatcher.method(A.class, "getName"))
                .toReturn("mockgetName");
        assertEquals("mockgetName", foo.fooMethod1());

    }

    /**
     * mock 静态方法
     */
    @Test
    public void testStaticMethod() {
        assertEquals("fooMethodStatic", Foo.fooMethodStatic());
        //mock静态方法
        PowerMockito.mockStatic(Foo.class);
        PowerMockito.when(Foo.fooMethodStatic()).thenReturn("mockStatic");
        assertEquals("mockStatic", Foo.fooMethodStatic());

    }

    /**
     * mock私有方法
     * 如果是想调用私有方法，可用反射
     */
    @Test
    public void testPrivateMethod() throws Exception {
        Foo foo = PowerMockito.mock(Foo.class);
        PowerMockito.when(foo.fooMethod2()).thenCallRealMethod();
        PowerMockito.when(foo, "fooPrivateMethod").thenCallRealMethod();
        assertEquals("fooPrivateMethod", foo.fooMethod2());

        PowerMockito.when(foo, "fooPrivateMethod").thenReturn("mockfooPrivateMethod");
        assertEquals("mockfooPrivateMethod", foo.fooMethod2());
    }

    @Test
    public void testOther() throws IllegalAccessException {
        A a = new A();
        Foo foo = new Foo();

        PowerMockito.when(a.getName()).thenReturn("mockA");

        MemberModifier.field(Foo.class, "a").set(Foo.class, a);
        assertEquals("mockA", Foo.getA().getName());


    }
}
