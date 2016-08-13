package com.yfd.appTest.softupdate;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParsingXMLElements extends DefaultHandler 
{
	private Map<String, String> element;	//�洢XMLԪ�ؼ�ֵ
    private String elementName;				//ǰһ��Ԫ������
    
    public Map<String, String> getElement()
    {
    	return element;
    }
    
    public void startDocument() throws SAXException
    {
    	System.out.println("*******��ʼ�����ĵ�*******");
    	element = new HashMap<String, String>();
    }

    public void endDocument() throws SAXException
    { 
    	System.out.println("*******�ĵ���������*******");
    }

    public void startPrefixMapping( String prefix, String uri )
    {
    	System.out.println(" ǰ׺ӳ��: " + prefix +" ��ʼ!"+ " ����URI��:" + uri);
    }

    public void endPrefixMapping( String prefix )
    {
    	System.out.println(" ǰ׺ӳ��: " + prefix + " ����!");
    }

    // public void processingInstruction( String target, String instruction )throwsSAXException{}

    // public void ignorableWhitespace( char[] chars, int start, int length ) throwsSAXException {}

    // public void skippedEntity( String name ) throws SAXException {}

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
    {
    	System.out.println("*******��ʼ����" + localName + "Ԫ��*******"); 
    	elementName = localName;
    }

    public void endElement(String namespaceURI,String localName,String fullName )throws SAXException
    {
	    System.out.println("******" + localName + "Ԫ�ؽ�������********");
    }

    public void characters( char[] chars, int start, int length )throws SAXException
    {
    	//��Ԫ�����ݼӵ�Map��
    	if(null != elementName)
    	{
    		element.put(elementName, String.valueOf(chars, start, length).replace("\\r", "\r").replace("\\n", "\n"));
    		elementName = null;
    		System.out.println("Ԫ��ֵ��" + element.get(elementName));
    	}
    }
}