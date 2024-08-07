package bg.softuni.parking.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static bg.softuni.parking.Constants.TEST_DATE_TIME_1;
import static bg.softuni.parking.Constants.TEST_DATE_TIME_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {

    @Test
    void testFormatDate() {
        // Arrange
        LocalDateTime dateTime = TEST_DATE_TIME_1;

        // Act
        LocalDateTime result = DateUtil.formatDate(dateTime);

        // Assert
        assertEquals(dateTime, result);
    }

    @Test
    void testFormatDate_WithDifferentDate() {
        // Arrange
        LocalDateTime dateTime = TEST_DATE_TIME_2;

        // Act
        LocalDateTime result = DateUtil.formatDate(dateTime);

        // Assert
        assertEquals(dateTime, result);
    }
}
