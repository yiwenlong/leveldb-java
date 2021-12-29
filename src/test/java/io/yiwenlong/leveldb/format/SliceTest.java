package io.yiwenlong.leveldb.format;

import org.junit.Assert;
import org.junit.Test;

public class SliceTest {

	@Test
	public void testRemovePrefix() {
		final String src = "testRemovePrefix";
		final String expect = "RemovePrefix";
		Slice s = new Slice(src);
		s.removePrefix(4);
		Assert.assertEquals(s.toString(), expect);
	}

	@Test
	public void testStartWith() {
		final String src = "testStartWith";
		Slice s = new Slice(src);
		Assert.assertTrue(s.startWith(new Slice()));
		Assert.assertTrue(s.startWith(new Slice("test")));
		Assert.assertTrue(s.startWith(new Slice("testStart")));
		Assert.assertTrue(s.startWith(new Slice("testStartWith")));

		s.removePrefix(1);
		Assert.assertFalse(s.startWith(new Slice("test")));
	}

	@Test
	public void testCompareTo() {
		final String src = "testCompareTo";
		Slice s = new Slice(src);
		Assert.assertEquals(0, s.compareTo(new Slice(src)));

		Assert.assertTrue(s.compareTo(new Slice("testCompareToaaa")) < 0);
		Assert.assertTrue(s.compareTo(new Slice("u")) < 0);
		Assert.assertTrue(s.compareTo(new Slice("testCompareTp")) < 0);

		Assert.assertTrue(s.compareTo(new Slice()) > 0);
		Assert.assertTrue(s.compareTo(new Slice("testCompareTn")) > 0);
		Assert.assertTrue(s.compareTo(new Slice("testCompareT")) > 0);


		final String src2 = "prefix-testCompareTo";
		Slice s2 = new Slice(src2);
		s2.removePrefix(7);
		Assert.assertEquals(0, s2.compareTo(s));
		Assert.assertEquals(0, s2.compareTo(new Slice(src)));

		Assert.assertTrue(s2.compareTo(new Slice("testCompareToaaa")) < 0);
		Assert.assertTrue(s2.compareTo(new Slice("u")) < 0);
		Assert.assertTrue(s2.compareTo(new Slice("testCompareTp")) < 0);

		Assert.assertTrue(s2.compareTo(new Slice()) > 0);
		Assert.assertTrue(s2.compareTo(new Slice("testCompareTn")) > 0);
		Assert.assertTrue(s2.compareTo(new Slice("testCompareT")) > 0);
	}
}
