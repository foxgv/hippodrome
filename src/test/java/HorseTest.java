import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    public void ifFirstParamNull_ThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1.5, 2.5));
    }

    @Test
    public void ifFirstParamNull_ThenExceptionAndControlText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1.5, 2.5));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"","  ", "\t\t"})
    public void ifFirstParamIsEmptyOrSpecialCharacters_ThenException(String name){
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1.5, 2.5));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","  ", "\t\t"})
    public void ifFirstParamIsEmptyOrSpecialCharacters_ThenExceptionControlText(String name){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1.5, 2.5));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void ifSecondParamNegativeNumber_ThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Вишня", -2.5, 3.5));
    }

    @Test
    public void ifSecondParamNegativeNumber_ThenExceptionControlText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Вишня", -2.5, 3.5));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void ifThirdParamNegativeNumber_ThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Вишня", 0.5, -2.5));
    }

    @Test
    public void ifThirdParamNegativeNumber_ThenExceptionControlText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Вишня", 0.5, -2.5));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void firstParamGetNameReturnString() {
        String horseNameExpected = "Вишня";
        Horse horse = new Horse(horseNameExpected, 1.4, 35.5);
        String horseNameActual = horse.getName();
        assertEquals(horseNameExpected, horseNameActual);
    }

    @Test
    public void secondParamGetSpeedReturnNumber() {
        double horseSpeedExpected = 3.8;
        Horse horse = new Horse("Вишня", horseSpeedExpected, 35.5);
        double horseSpeedActual = horse.getSpeed();
        assertEquals(horseSpeedExpected, horseSpeedActual);
    }

    @Test
    public void thirdParamGetDistanceReturnNumber() {
        double horseDistanceExpected = 35.5;
        Horse horse = new Horse("Вишня", 3.8, horseDistanceExpected);
        double horseDistanceActual = horse.getDistance();
        assertEquals(horseDistanceExpected, horseDistanceActual);
    }

    @Test
    public void thirdParamGetDistanceReturnZero() {
        double horseDistanceExpected = 0;
        Horse horse = new Horse("Вишня", 3.8);
        double horseDistanceActual = horse.getDistance();
        assertEquals(horseDistanceExpected, horseDistanceActual);
    }

    @Test
    public void callInnerMethod_GetRandomDoubleWithParameters() {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Вишня", 0.8, 25.5).move();

            mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9, 1.5, 2.8, 5.5})
    public void checkingTheDistanceCalculationFormula(double random) {
        double speed = 0.8;
        double distance = 25.5;
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Вишня", speed, distance);
            mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            double expectValue = distance + speed * random;
            horse.move();
            double actualValue = horse.getDistance();
            assertEquals(expectValue, actualValue);
        }
    }
}