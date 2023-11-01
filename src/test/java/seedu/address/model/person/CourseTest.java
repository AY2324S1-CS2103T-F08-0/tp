package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void constructor_invalidCourse_throwsIllegalArgumentException() {
        String invalidCourse = "InvalidCourse";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourse));
    }
    @Test
    public void isValidCourseName() {
        // Test invalid course names
        assertFalse(Course.isValidCourseName("InvalidCourse")); // letters only
        assertFalse(Course.isValidCourseName("AB1")); // one digit only
        assertFalse(Course.isValidCourseName("ABC12345")); // Extra digits
        assertFalse(Course.isValidCourseName("ABCD2345")); // Extra letters
        assertFalse(Course.isValidCourseName("ABC1234AA")); // Extra letters at the end

        // Test valid course names
        assertTrue(Course.isValidCourseName("AB1234")); // two letters and 4 digits - valid format
        assertTrue(Course.isValidCourseName("ABC1234")); // three letters and 4 digits - valid format
        assertTrue(Course.isValidCourseName("ABC5678X")); // Valid format that ends with a letter
    }

    @Test
    public void getCourseName() {
        // Test getting the course name from a valid course string
        Course twoLettersCourse = new Course("AB1234/T01");
        assertEquals("AB1234", twoLettersCourse.getCourseName());

        Course threeLetterCourse = new Course("XYZ9999/T02");
        assertEquals("XYZ9999", threeLetterCourse.getCourseName());

        Course courseEndWithLetter = new Course("ABC5678X/T03");
        assertEquals("ABC5678X", courseEndWithLetter.getCourseName());
    }

    @Test
    public void equals() {
        Course course1 = new Course("AB1234");
        Course course2 = new Course("AB1234");
        Course course3 = new Course("ABC1234");
        Course course4 = new Course("ABC1234X");

        // Same course -> return true
        assertTrue(course1.equals(course1));

        // course 1 equals course 2 then course 2 equals course 1 -> return true
        assertEquals(course1.equals(course2), course2.equals(course1));

        // Same course -> return true
        assertEquals(course1, course2);

        // Different course -> return false
        assertFalse(course1.equals(course3));

        // Test additional valid contacts for equality
        assertTrue(course3.equals(new Course("ABC1234")));
        assertTrue(course4.equals(new Course("ABC1234X")));
    }

    @Test
    public void hashCodeMethod() {
        Course course1 = new Course("AB1234");
        Course course2 = new Course("AB1234");
        Course course3 = new Course("ABC1234");
        Course course4 = new Course("ABC1234X");

        // Test that the hash codes of equivalent courses are equal
        assertEquals(course1.hashCode(), course2.hashCode());
        // same course sme hashCOde -> return true
        assertEquals(course1.hashCode(), course2.hashCode());

        // Test that the hash codes of different courses are not equal
        assertFalse(course1.hashCode() == course3.hashCode());
    }

    @Test
    public void toString_validContact_returnsFormattedString() {
        Contact contact = new Contact("ABC1234");

        // Define the expected formatted string
        String expectedString = "[ABC1234]";

        // Call the toString method and compare with the expected string
        assertEquals(expectedString, contact.toString());
    }
}
