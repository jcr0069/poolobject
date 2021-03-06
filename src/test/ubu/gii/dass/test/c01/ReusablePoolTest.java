/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {

	private ReusablePool pool1;
	private ReusablePool pool2;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool1 = ReusablePool.getInstance();
		pool2 = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool1 = null;
		pool2 = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertTrue(pool1 instanceof ReusablePool);
		assertTrue(pool2 instanceof ReusablePool);
		assertEquals(pool1,pool2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException 
	 */
	@Test
	public void testAcquireReusable(){
		Reusable r1 = null, r2 = null;
		try{
			r1 = pool1.acquireReusable();
			assertTrue(r1 instanceof Reusable);
			r2 = pool1.acquireReusable();
			assertTrue(r2 instanceof Reusable);
		}catch(NotFreeInstanceException e){
			fail("Aun deberia haber reusables libres");
		}
		
		try{
			pool1.acquireReusable();
			fail("No deberia haber reusables libres");
		}catch(NotFreeInstanceException e){
			assertTrue(true);
			try {
				pool1.releaseReusable(r1);
				pool1.releaseReusable(r2);
			} catch (DuplicatedInstanceException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * @throws NotFreeInstanceException 
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {
		Reusable r = pool1.acquireReusable();
		System.out.println(r.util());
		Reusable r2 = pool1.acquireReusable();
		System.out.println(r2.util());
		
		try {
			pool1.releaseReusable(r);
		} catch (DuplicatedInstanceException e) {
			e.printStackTrace();
			fail("Deberia dejar liberar el objeto reusable.");
		}
		
		try {
			pool1.releaseReusable(r);
			fail("No deberia dejar liberar el objeto reusable");
		} catch (DuplicatedInstanceException e) {
			assertTrue(pool1.getReusables().contains(r));
		}
		
		try {
			pool1.releaseReusable(r2);
		} catch (DuplicatedInstanceException e) {
			e.printStackTrace();
			fail("Deberia dejar liberar el objeto reusable.");
		}
		
		try {
			pool1.releaseReusable(r2);
			fail("No deberia dejar liberar el objeto reusable");
		} catch (DuplicatedInstanceException e) {
			assertTrue(pool1.getReusables().contains(r2));
		}
		
	}

}
