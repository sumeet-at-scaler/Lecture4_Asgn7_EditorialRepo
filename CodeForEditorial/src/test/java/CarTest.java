import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Car car;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        car = new Car();
    }

    @Test
    void PriceExists(){
        try {
            Field PriceField = car.getClass().getDeclaredField("Price");
            assertEquals(PriceField.getType().toString(), "int");
        } catch (NoSuchFieldException e) {
            fail("Price not found");
        }
    }

    @Test
    void SpeedExists(){
        try {
            Field SpeedField = car.getClass().getDeclaredField("Speed");
            assertEquals(SpeedField.getType().toString(), "int");
        } catch (NoSuchFieldException e) {
            fail("Speed not found");
        }
    }

    @Test
    void compareToMethodExists(){
        try {
            Method compareToMethod = car.getClass().getDeclaredMethod("compareTo", Car.class);
            assertEquals(compareToMethod.toString(), "public int Car.compareTo(Car)");
        } catch (NoSuchMethodException e) {
            fail("compareToMethod not found");
        }
    }

    @Test
    void comparableOfCarImplemented(){
        List<Class<?>> interfaces = Arrays.asList(car.getClass().getInterfaces());

        assertEquals(1, interfaces.size());
        assertTrue(interfaces.contains(Comparable.class));
    }

    @Test
    void compareToWorksFine() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // car.age = 10
        Field PriceField = car.getClass().getDeclaredField("Price");
        PriceField.setAccessible(true);
        PriceField.set(car, 10);

        Car other = new Car();
        PriceField.set(other, 20);

        // car.compareTo(other)
        Method compareToMethod = car.getClass().getDeclaredMethod("compareTo", Car.class);
        Object retVal = compareToMethod.invoke(car, other);
        int actualRetVal = (int)retVal;

        assertEquals(-10, actualRetVal);
    }

}