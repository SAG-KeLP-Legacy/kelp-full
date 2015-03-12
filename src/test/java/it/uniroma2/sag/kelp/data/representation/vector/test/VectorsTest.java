package it.uniroma2.sag.kelp.data.representation.vector.test;

import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.example.ExampleFactory;
import it.uniroma2.sag.kelp.data.representation.vector.DenseVector;
import it.uniroma2.sag.kelp.data.representation.vector.SparseVector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VectorsTest {
	private Example a;
	private Example b;
	
	private String denseName = "Dense";
	private String sparseName = "Sparse";
	
	@Before
	/**
	 * This method will be executed before each test method.
	 */
	public void initializeExamples() {
		String reprA = "fakeclass |BDV:"+denseName+"| 1.0,0.0,1.0 |EV| |BV:"+sparseName+"| one:1.0 three:1.0 |EV|";
		String reprB = "fakeclass |BDV:"+denseName+"| 0.0,1.0,1.0 |EV| |BV:"+sparseName+"| two:1.0 three:1.0 |EV|";
		
		try {
			a = ExampleFactory.parseExample(reprA);
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.fail();
		}
		try {
			b = ExampleFactory.parseExample(reprB);
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testDenseSum() {
		DenseVector aVector = (DenseVector) a.getRepresentation(denseName);
		DenseVector bVector = (DenseVector) b.getRepresentation(denseName);
		
		aVector.add(bVector);
		
		Assert.assertEquals(1.0f, aVector.getFeatureValue(0), 0.0001f);
		Assert.assertEquals(1.0f, aVector.getFeatureValue(1), 0.0001f);
		Assert.assertEquals(2.0f, aVector.getFeatureValue(2), 0.0001f);	
	}
	
	@Test
	public void testSparseSum() {
		SparseVector aVector = (SparseVector) a.getRepresentation(sparseName);
		SparseVector bVector = (SparseVector) b.getRepresentation(sparseName);
		
		aVector.add(bVector);
		
		Assert.assertEquals(1.0f, aVector.getActiveFeatures().get("one"), 0.0001f);
		Assert.assertEquals(1.0f, aVector.getActiveFeatures().get("two"), 0.0001f);
		Assert.assertEquals(2.0f, aVector.getActiveFeatures().get("three"), 0.0001f);	
	}
	
	@Test
	public void testDenseSumWithCoefficient() {
		DenseVector aVector = (DenseVector) a.getRepresentation(denseName);
		DenseVector bVector = (DenseVector) b.getRepresentation(denseName);
		
		aVector.add(2.0f, bVector);
		
		Assert.assertEquals(1.0f, aVector.getFeatureValue(0), 0.0001f);
		Assert.assertEquals(2.0f, aVector.getFeatureValue(1), 0.0001f);
		Assert.assertEquals(3.0f, aVector.getFeatureValue(2), 0.0001f);	
	}
	
	@Test
	public void testSparseSumWithCoefficient() {
		SparseVector aVector = (SparseVector) a.getRepresentation(sparseName);
		SparseVector bVector = (SparseVector) b.getRepresentation(sparseName);
		
		aVector.add(2.0f, bVector);
		
		Assert.assertEquals(1.0f, aVector.getActiveFeatures().get("one"), 0.0001f);
		Assert.assertEquals(2.0f, aVector.getActiveFeatures().get("two"), 0.0001f);
		Assert.assertEquals(3.0f, aVector.getActiveFeatures().get("three"), 0.0001f);	
	}
	
	@Test
	public void testDensePointWiseProduct() {
		DenseVector aVector = (DenseVector) a.getRepresentation(denseName);
		DenseVector bVector = (DenseVector) b.getRepresentation(denseName);
		
		aVector.pointWiseProduct(bVector);
		
		Assert.assertEquals(0.0f, aVector.getFeatureValue(0), 0.0001f);
		Assert.assertEquals(0.0f, aVector.getFeatureValue(1), 0.0001f);
		Assert.assertEquals(1.0f, aVector.getFeatureValue(2), 0.0001f);
	}
	
	@Test
	public void testSparsePointWiseProduct() {
		SparseVector aVector = (SparseVector) a.getRepresentation(sparseName);
		SparseVector bVector = (SparseVector) b.getRepresentation(sparseName);
		
		aVector.pointWiseProduct(bVector);
		
		Assert.assertEquals(1, aVector.getActiveFeatures().size());
		
		Assert.assertNull(aVector.getActiveFeatures().get("one"));
		Assert.assertNull(aVector.getActiveFeatures().get("two"));
		Assert.assertEquals(1.0f, aVector.getActiveFeatures().get("three"), 0.0001f);	
	}
	
	@Test
	public void testDenseInnerProduct() {
		DenseVector aVector = (DenseVector) a.getRepresentation(denseName);
		DenseVector bVector = (DenseVector) b.getRepresentation(denseName);
		
		float innerProduct = aVector.innerProduct(bVector);
		
		Assert.assertEquals(1.0f, innerProduct, 0.000001f);
	}
	
	@Test
	public void testSparseInnerProduct() {
		SparseVector aVector = (SparseVector) a.getRepresentation(sparseName);
		SparseVector bVector = (SparseVector) b.getRepresentation(sparseName);
		
		float innerProduct = aVector.innerProduct(bVector);
		
		Assert.assertEquals(1.0f, innerProduct, 0.000001f);
	}

}
