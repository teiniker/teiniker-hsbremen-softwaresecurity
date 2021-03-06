package org.se.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class JAXBTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance("org.se.lab");
	}
	
	@Test
	public void testTagInjection()
            throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(
				new File("src/test/resources/xml", "session-tag-injection.xml")));

		// Note that the additional attribute and element will be ignored!
		parseXmlSession(src);
	}


	@Test
	public void testTagOverriding()
            throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(
                new File("src/test/resources/xml", "item-tag-overriding.xml")));

		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<Item> element = unmarshaller.unmarshal(src, Item.class);
		Item item = element.getValue();
		
		Assert.assertEquals("Widget", item.getDescription());
		Assert.assertEquals(BigInteger.valueOf(1000), item.getQuantity());	// should be 1
		Assert.assertEquals(new BigDecimal("1.0"), item.getPrice());	// should be 500.0
	}

	@Test
	public void testExternalEntityAttack() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(
		        new File("src/test/resources/xml", "session-entity-file.xml")));

		parseXmlSession(src);
	}
	
	@Test
	public void testXMLBomb() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(
		        new File("src/test/resources/xml", "session-entity-expansion.xml")));

		parseXmlSession(src);
	}

	
	/*
	 * Utility methods
	 */
	
	private void parseXmlSession(Source src) throws JAXBException
	{
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<SessionRootType> element = unmarshaller.unmarshal(src, SessionRootType.class);
		SessionRootType root = element.getValue();

		Assert.assertEquals(2, root.getSessions().size());		
		SessionType s1 = root.getSessions().get(0);
		Assert.assertEquals("one", s1.getId());
		SessionType s2 = root.getSessions().get(1);
		Assert.assertEquals("two", s2.getId());
	}
}
