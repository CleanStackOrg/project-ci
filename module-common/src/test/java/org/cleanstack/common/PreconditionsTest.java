package org.cleanstack.common;

import static org.cleanstack.common.Preconditions.isBlank;
import static org.cleanstack.common.Preconditions.isEmpty;
import static org.cleanstack.common.Preconditions.isNumeric;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PreconditionsTest {

    @Test
    public void test_isBlank() {
	assertTrue(isBlank(null) == true);
	assertTrue(isBlank("") == true);
	assertTrue(isBlank(" ") == true);
	assertTrue(isBlank("bob") == false);
	assertTrue(isBlank("  bob") == false);
    }

    @Test
    public void test_isEmpty() {
	assertTrue(isEmpty(null) == true);
	assertTrue(isEmpty("") == true);
	assertTrue(isEmpty(" ") == false);
	assertTrue(isEmpty("bob") == false);
	assertTrue(isEmpty("  bob") == false);
    }

    @Test
    public void test_isNumeric() {
	assertTrue(isNumeric(null) == false);
	assertTrue(isNumeric("") == true);
	assertTrue(isNumeric("  ") == false);
	assertTrue(isNumeric("123") == true);
	assertTrue(isNumeric("12 3") == false);
	assertTrue(isNumeric("ab2c") == false);
	assertTrue(isNumeric("12-3") == false);
	assertTrue(isNumeric("12.3") == false);
    }

}
