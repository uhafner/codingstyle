package edu.hm.hafner.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.errorprone.annotations.FormatMethod;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static javax.xml.XMLConstants.*;

/**
 * Factory for XML Parsers that prevent XML External Entity attacks. Those attacks occur when untrusted XML input
 * containing a reference to an external entity is processed by a weakly configured XML parser.
 *
 * @author Ullrich Hafner
 * @see <a href="https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html">XML
 *         External Entity Prevention Cheat Sheet</a>
 * @see <a href="https://rules.sonarsource.com/java/RSPEC-2755">XML parsers should not be vulnerable to XXE
 *         attacks</a>
 */
public class SecureXmlParserFactory {
    /**
     * The following constants are copied from the Xerces distribution 2.12.2. This avoids adding a dependency to
     * Xerces.
     */
    private static final String SAX_FEATURE_PREFIX = "http://xml.org/sax/features/";
    private static final String XERCES_FEATURE_PREFIX = "http://apache.org/xml/features/";
    private static final String EXTERNAL_GENERAL_ENTITIES_FEATURE = "external-general-entities";
    private static final String EXTERNAL_PARAMETER_ENTITIES_FEATURE = "external-parameter-entities";
    private static final String RESOLVE_DTD_URIS_FEATURE = "resolve-dtd-uris";
    private static final String USE_ENTITY_RESOLVER2_FEATURE = "use-entity-resolver2";
    private static final String CREATE_ENTITY_REF_NODES_FEATURE = "dom/create-entity-ref-nodes";
    private static final String LOAD_DTD_GRAMMAR_FEATURE = "nonvalidating/load-dtd-grammar";
    private static final String LOAD_EXTERNAL_DTD_FEATURE = "nonvalidating/load-external-dtd";

    private static final String[] ENABLED_PROPERTIES = {
//            XERCES_FEATURE_PREFIX + DISALLOW_DOCTYPE_DECL_FEATURE,   - If this feature is activated we cannot parse any XML documents that use a DOCTYPE anymore
            FEATURE_SECURE_PROCESSING
    };
    private static final String[] DISABLED_PROPERTIES = {
            SAX_FEATURE_PREFIX + EXTERNAL_GENERAL_ENTITIES_FEATURE,
            SAX_FEATURE_PREFIX + EXTERNAL_PARAMETER_ENTITIES_FEATURE,
            SAX_FEATURE_PREFIX + RESOLVE_DTD_URIS_FEATURE,
            SAX_FEATURE_PREFIX + USE_ENTITY_RESOLVER2_FEATURE,
            XERCES_FEATURE_PREFIX + CREATE_ENTITY_REF_NODES_FEATURE,
            XERCES_FEATURE_PREFIX + LOAD_DTD_GRAMMAR_FEATURE,
            XERCES_FEATURE_PREFIX + LOAD_EXTERNAL_DTD_FEATURE
    };
    private static final String[] DISABLED_ATTRIBUTES = {
            ACCESS_EXTERNAL_DTD,
            ACCESS_EXTERNAL_SCHEMA,
            ACCESS_EXTERNAL_STYLESHEET
    };
    private static final String CLEAR_ATTRIBUTE = "";
    private static final String SUPPORTING_EXTERNAL_ENTITIES = "javax.xml.stream.isSupportingExternalEntities";

    /**
     * Creates a new instance of a {@link DocumentBuilder} that does not resolve external entities.
     *
     * @return a new instance of a {@link DocumentBuilder}
     */
    public DocumentBuilder createDocumentBuilder() {
        try {
            var factory = createDocumentBuilderFactory();
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            factory.setFeature(FEATURE_SECURE_PROCESSING, true);
            setFeatures(factory);
            clearAttributes(factory);

            return factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException exception) {
            throw new IllegalArgumentException("Can't create instance of DocumentBuilder", exception);
        }
    }

    @VisibleForTesting
    DocumentBuilderFactory createDocumentBuilderFactory() {
        return DocumentBuilderFactory.newInstance();
    }

    private void setFeatures(final DocumentBuilderFactory factory) {
        for (String enabledProperty : ENABLED_PROPERTIES) {
            setFeature(factory, enabledProperty, true);
        }
        for (String disabledProperty : DISABLED_PROPERTIES) {
            setFeature(factory, disabledProperty, false);
        }
    }

    private void setFeature(final DocumentBuilderFactory factory, final String enabledProperty, final boolean value) {
        try {
            factory.setFeature(enabledProperty, value);
        }
        catch (ParserConfigurationException ignored) {
            // ignore and continue
        }
    }

    private void clearAttributes(final DocumentBuilderFactory factory) {
        for (String securityAttribute : DISABLED_ATTRIBUTES) {
            try {
                factory.setAttribute(securityAttribute, CLEAR_ATTRIBUTE);
            }
            catch (IllegalArgumentException e) {
                // ignore and continue
            }
        }
    }

    private void clearAttributes(final TransformerFactory transformerFactory) {
        for (String securityAttribute : DISABLED_ATTRIBUTES) {
            try {
                transformerFactory.setAttribute(securityAttribute, CLEAR_ATTRIBUTE);
            }
            catch (IllegalArgumentException e) {
                // ignore and continue
            }
        }
    }

    /**
     * Creates a new instance of a {@link SAXParser} that does not resolve external entities.
     *
     * @return a new instance of a {@link SAXParser}
     */
    public SAXParser createSaxParser() {
        try {
            var factory = createSaxParserFactory();
            configureSaxParserFactory(factory);

            SAXParser parser = factory.newSAXParser();
            secureParser(parser);
            return parser;
        }
        catch (ParserConfigurationException | SAXException exception) {
            throw new IllegalArgumentException("Can't create instance of SAXParser", exception);
        }
    }

    @VisibleForTesting
    SAXParserFactory createSaxParserFactory() {
        return SAXParserFactory.newInstance();
    }

    /**
     * Secure the {@link SAXParser} so that it does not resolve external entities.
     *
     * @param parser
     *         the parser to secure
     */
    private void secureParser(final SAXParser parser) {
        for (String securityAttribute : DISABLED_ATTRIBUTES) {
            try {
                parser.setProperty(securityAttribute, CLEAR_ATTRIBUTE);
            }
            catch (SAXNotRecognizedException | SAXNotSupportedException e) {
                // ignore and continue
            }
        }
    }

    /**
     * Configures a {@link SAXParserFactory} so that it does not resolve external entities.
     *
     * @param factory
     *         the facotry to configure
     */
    public void configureSaxParserFactory(final SAXParserFactory factory) {
        factory.setValidating(false);
        factory.setXIncludeAware(false);

        for (String enabledProperty : ENABLED_PROPERTIES) {
            try {
                factory.setFeature(enabledProperty, true);
            }
            catch (ParserConfigurationException | SAXException ignored) {
                // ignore and continue
            }
        }
        for (String disabledProperty : DISABLED_PROPERTIES) {
            try {
                factory.setFeature(disabledProperty, false);
            }
            catch (ParserConfigurationException | SAXException ignored) {
                // ignore and continue
            }
        }
    }

    /**
     * Creates a new instance of a {@link XMLStreamReader} that does not resolve external entities.
     *
     * @param reader
     *         the reader to wrap
     *
     * @return a new instance of a {@link XMLStreamReader}
     */
    @SuppressFBWarnings(value = "XXE_XMLSTREAMREADER", justification = "The reader is secured in the called method")
    public XMLStreamReader createXmlStreamReader(final Reader reader) {
        try {
            return createSecureInputFactory().createXMLStreamReader(reader);
        }
        catch (XMLStreamException exception) {
            throw new IllegalArgumentException("Can't create instance of XMLStreamReader", exception);
        }
    }

    /**
     * Creates a new instance of a {@link XMLStreamReader} that does not resolve external entities.
     *
     * @param reader
     *         the reader to wrap
     *
     * @return a new instance of a {@link XMLStreamReader}
     */
    @SuppressFBWarnings(value = "XXE_XMLSTREAMREADER", justification = "The reader is secured in the called method")
    public XMLEventReader createXmlEventReader(final Reader reader) {
        try {
            return createSecureInputFactory().createXMLEventReader(reader);
        }
        catch (XMLStreamException exception) {
            throw new IllegalArgumentException("Can't create instance of XMLEventReader", exception);
        }
    }

    private XMLInputFactory createSecureInputFactory() {
        var factory = createXmlInputFactory();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(SUPPORTING_EXTERNAL_ENTITIES, false);
        return factory;
    }

    @VisibleForTesting
    XMLInputFactory createXmlInputFactory() {
        return XMLInputFactory.newInstance();
    }

    /**
     * Creates a {@link SAXParser} that does not resolve external entities and parses the provided content with the
     * given SAX {@link DefaultHandler}.
     *
     * @param reader
     *         the content that should be parsed
     * @param charset
     *         the charset to use when reading the content
     * @param handler
     *         the SAX handler to parse the file
     *
     * @throws ParsingException
     *         if the file could not be parsed
     */
    @SuppressFBWarnings(value = "XXE_SAXPARSER", justification = "The parser is secured in the called method")
    public void parse(final Reader reader, final Charset charset, final DefaultHandler handler) {
        try {
            createSaxParser().parse(createInputSource(reader, charset), handler);
        }
        catch (SAXException | IOException exception) {
            throw new ParsingException(exception);
        }
    }

    /**
     * Parses the provided content into a {@link Document}.
     *
     * @param reader
     *         the content that should be parsed
     * @param charset
     *         the charset to use when reading the content
     *
     * @return the file content as document
     * @throws ParsingException
     *         if the file could not be parsed
     */
    @SuppressFBWarnings(value = "XXE_DOCUMENT", justification = "The parser is secured in the called method")
    public Document readDocument(final Reader reader, final Charset charset) {
        try {
            return createDocumentBuilder().parse(createInputSource(reader, charset));
        }
        catch (SAXException | IOException exception) {
            throw new ParsingException(exception);
        }
    }

    private InputSource createInputSource(final Reader reader, final Charset charset) {
        return new InputSource(new ReaderInputStream(reader, charset));
    }

    /**
     * Creates a {@link Transformer} that does not resolve external entities and stylesheets.
     *
     * @return the created {@link Transformer}
     */
    @SuppressFBWarnings(value = {"XXE_DTD_TRANSFORM_FACTORY", "XXE_XSLT_TRANSFORM_FACTORY"}, justification = "The transformer is secured in the called method")
    public Transformer createTransformer() {
        try {
            var transformerFactory = createTransformerFactory();

            clearAttributes(transformerFactory);

            return transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException exception) {
            throw new IllegalArgumentException("Can't create instance of Transformer", exception);
        }
    }

    @VisibleForTesting
    @SuppressFBWarnings(value = {"XXE_DTD_TRANSFORM_FACTORY", "XXE_XSLT_TRANSFORM_FACTORY"}, justification = "The transformer is secured in the called method")
    TransformerFactory createTransformerFactory() {
        return TransformerFactory.newInstance();
    }

    /**
     * Indicates that during parsing a non-recoverable error has been occurred.
     */
    public static class ParsingException extends RuntimeException {
        private static final long serialVersionUID = -9016364685084958944L;

        /**
         * Constructs a new {@link ParsingException} with the specified cause.
         *
         * @param cause
         *         the cause (which is saved for later retrieval by the {@link #getCause()} method).
         */
        public ParsingException(final Throwable cause) {
            super(createMessage(cause, "Exception occurred during parsing"), cause);
        }

        /**
         * Constructs a new {@link ParsingException} with the specified message.
         *
         * @param messageFormat
         *         the message as a format string as described in <a href="../util/Formatter.html#syntax">Format string
         *         syntax</a>
         * @param args
         *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
         *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be zero.
         *         The maximum number of arguments is limited by the maximum dimension of a Java array as defined by
         *         <cite>The Java&trade; Virtual Machine Specification</cite>. The behaviour on a {@code null} argument
         *         depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
         */
        @FormatMethod
        public ParsingException(final String messageFormat, final Object... args) {
            super(String.format(messageFormat, args));
        }

        /**
         * Constructs a new {@link ParsingException} with the specified cause and message.
         *
         * @param cause
         *         the cause (which is saved for later retrieval by the {@link #getCause()} method).
         * @param messageFormat
         *         the message as a format string as described in <a href="../util/Formatter.html#syntax">Format string
         *         syntax</a>
         * @param args
         *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
         *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be zero.
         *         The maximum number of arguments is limited by the maximum dimension of a Java array as defined by
         *         <cite>The Java&trade; Virtual Machine Specification</cite>. The behaviour on a {@code null} argument
         *         depends on the <a href="../util/Formatter.html#syntax">conversion</a>.
         */
        @FormatMethod
        public ParsingException(final Throwable cause, final String messageFormat, final Object... args) {
            super(createMessage(cause, String.format(messageFormat, args)), cause);
        }

        private static String createMessage(final Throwable cause, final String message) {
            return String.format("%s%n%s%n%s", message,
                    ExceptionUtils.getMessage(cause), ExceptionUtils.getStackTrace(cause));
        }
    }
}
