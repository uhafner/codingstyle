package edu.hm.hafner.util;

import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.hm.hafner.util.SecureXmlParserFactory.ParsingException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link SecureXmlParserFactory}.
 *
 * @author Ullrich Hafner
 */
class SecureXmlParserFactoryTest {
    private static final String EXPECTED_EXCEPTION = "EXPECTED EXCEPTION";

    @Test
    void shouldCreateDocumentBuilder() throws ParserConfigurationException {
        var factory = spy(new SecureXmlParserFactory());

        assertThat(factory.createDocumentBuilder()).isNotNull();

        var brokenDocumentBuilderFactory = createBrokenDocumentBuilderFactory();
        when(factory.createDocumentBuilderFactory()).thenReturn(brokenDocumentBuilderFactory);

        assertThatIllegalArgumentException().isThrownBy(factory::createDocumentBuilder)
                .withMessage("Can't create instance of DocumentBuilder");
    }

    private DocumentBuilderFactory createBrokenDocumentBuilderFactory() throws ParserConfigurationException {
        var documentBuilderFactory = mock(DocumentBuilderFactory.class);
        when(documentBuilderFactory.newDocumentBuilder()).thenThrow(createConfigurationException());
        return documentBuilderFactory;
    }

    @Test
    void shouldCreateSaxParser() throws ParserConfigurationException, SAXException {
        var factory = spy(new SecureXmlParserFactory());

        assertThat(factory.createSaxParser()).isNotNull();

        var brokenSaxParserFactory = createBrokenSaxParserFactory();
        when(factory.createSaxParserFactory()).thenReturn(brokenSaxParserFactory);

        assertThatIllegalArgumentException().isThrownBy(factory::createSaxParser)
                .withMessage("Can't create instance of SAXParser");
    }

    private SAXParserFactory createBrokenSaxParserFactory() throws ParserConfigurationException, SAXException {
        var saxParserFactory = mock(SAXParserFactory.class);
        when(saxParserFactory.newSAXParser()).thenThrow(createConfigurationException());
        doThrow(createConfigurationException()).when(saxParserFactory).setFeature(anyString(), anyBoolean());
        return saxParserFactory;
    }

    private static ParserConfigurationException createConfigurationException() {
        return new ParserConfigurationException(EXPECTED_EXCEPTION);
    }

    @Test
    void shouldParseEmptyDocument() throws SAXException {
        var factory = new SecureXmlParserFactory();

        DefaultHandler handler = mock(DefaultHandler.class);
        factory.parse(createEmptyXmlReader(), StandardCharsets.UTF_8, handler);

        verify(handler).startDocument();

        Document document = factory.readDocument(createEmptyXmlReader(), StandardCharsets.UTF_8);
        assertThat(document.getChildNodes().getLength()).isOne();
        assertThat(document.getChildNodes().item(0).getNodeName()).isEqualTo("xml");
    }

    @Test
    void shouldFailWithParsingExceptionIfParsingBrokenDocument() throws SAXException {
        var factory = new SecureXmlParserFactory();

        assertThatExceptionOfType(ParsingException.class).isThrownBy(() ->
                factory.parse(createBrokenXmlReader(), StandardCharsets.UTF_8, mock(DefaultHandler.class)));
        assertThatExceptionOfType(ParsingException.class).isThrownBy(() ->
                factory.readDocument(createBrokenXmlReader(), StandardCharsets.UTF_8));
    }

    @Test
    void shouldCreateXmlStreamReader() throws XMLStreamException {
        var factory = spy(new SecureXmlParserFactory());

        assertThat(factory.createXmlStreamReader(createEmptyXmlReader())).isNotNull();

        var brokenXmlInputFactory = createBrokenXmlInputFactory();
        when(factory.createXmlInputFactory()).thenReturn(brokenXmlInputFactory);

        assertThatIllegalArgumentException().isThrownBy(() -> factory.createXmlStreamReader(createEmptyXmlReader()))
                .withMessage("Can't create instance of XMLStreamReader");
    }

    @Test
    void shouldCreateXmlEventReader() throws XMLStreamException {
        var factory = spy(new SecureXmlParserFactory());

        assertThat(factory.createXmlEventReader(createEmptyXmlReader())).isNotNull();

        var brokenXmlInputFactory = createBrokenXmlInputFactory();
        when(factory.createXmlInputFactory()).thenReturn(brokenXmlInputFactory);

        assertThatIllegalArgumentException().isThrownBy(() -> factory.createXmlEventReader(createEmptyXmlReader()))
                .withMessage("Can't create instance of XMLEventReader");
    }

    private XMLInputFactory createBrokenXmlInputFactory() throws XMLStreamException {
        var xmlInputFactory = mock(XMLInputFactory.class);
        when(xmlInputFactory.createXMLStreamReader((Reader) any())).thenThrow(new XMLStreamException(EXPECTED_EXCEPTION));
        when(xmlInputFactory.createXMLEventReader((Reader) any())).thenThrow(new XMLStreamException(EXPECTED_EXCEPTION));
        return xmlInputFactory;
    }

    private StringReader createEmptyXmlReader() {
        return new StringReader("<xml />");
    }

    private StringReader createBrokenXmlReader() {
        return new StringReader("+++ Broken Input +++");
    }

    @Test
    void shouldCreateTransformer() throws TransformerConfigurationException {
        var factory = spy(new SecureXmlParserFactory());

        assertThat(factory.createTransformer()).isNotNull();

        var brokenTransformerFactory = createBrokenTransformerFactory();
        when(factory.createTransformerFactory()).thenReturn(brokenTransformerFactory);

        assertThatIllegalArgumentException().isThrownBy(factory::createTransformer)
                .withMessage("Can't create instance of Transformer");
    }

    private TransformerFactory createBrokenTransformerFactory() throws TransformerConfigurationException {
        var transformerFactory = mock(TransformerFactory.class);
        when(transformerFactory.newTransformer()).thenThrow(new TransformerConfigurationException(EXPECTED_EXCEPTION));
        return transformerFactory;
    }
}
