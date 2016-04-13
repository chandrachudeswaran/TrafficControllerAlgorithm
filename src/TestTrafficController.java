

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestTrafficController{
	
	@Test
	public void normalTestCondition(){
		TrafficController controller = new TrafficController();
		assertEquals("A",controller.doAnalysis(10, 5, 3, 4));
		assertEquals("C",controller.doAnalysis(2, 10, 11, 6));
		assertEquals("B",controller.doAnalysis(4, 12, 3, 8));
		assertEquals("D",controller.doAnalysis(14, 12, 3, 8));
		assertEquals("A",controller.doAnalysis(15, 17, 4, 10));
	}
	
	
	@Test 
	public void zeroTestCondition(){
		// Check for when car is not present in signal
		TrafficController controller = new TrafficController();
		assertEquals("A",controller.doAnalysis(10, 5, 3, 0));
		assertEquals("C",controller.doAnalysis(2, 10, 11, 0));
		assertEquals("B",controller.doAnalysis(4, 12, 3, 8));
		assertEquals("A",controller.doAnalysis(14, 12, 3, 8));
		assertEquals("B",controller.doAnalysis(15, 17, 4, 10));
	}
	
	
	@Test
	public void sameValueCarCountCondition(){
		TrafficController controller = new TrafficController();
		assertEquals("A",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("A",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("A",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("B",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("C",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("D",controller.doAnalysis(5, 5, 5, 5));
	}
	
	@Test
	public void sameValueCarAndZeroCondition(){
		TrafficController controller = new TrafficController();
		assertEquals("A",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("A",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("A",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("B",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("C",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("D",controller.doAnalysis(5, 5, 5, 5));
		assertEquals("A",controller.doAnalysis(5, 5,5,0));
		assertEquals("B",controller.doAnalysis(0, 5, 5, 0));
		assertEquals("C",controller.doAnalysis(0, 0, 5, 0));
		assertEquals("A",controller.doAnalysis(0, 0, 0, 0));
		assertEquals("A",controller.doAnalysis(0, 0, 0, 0));//Try to Check
		assertEquals("A",controller.doAnalysis(0, 0, 0, 0));
		assertEquals("A",controller.doAnalysis(0, 0, 0, 0));
		assertEquals("A",controller.doAnalysis(0, 0, 0, 0));

	}
	
	
	
	
}
