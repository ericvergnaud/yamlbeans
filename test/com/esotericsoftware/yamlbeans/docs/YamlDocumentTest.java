package com.esotericsoftware.yamlbeans.docs;

import java.io.StringWriter;

import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.docs.YamlDocument;
import com.esotericsoftware.yamlbeans.docs.YamlDocumentReader;

import static org.junit.Assert.*;

public class YamlDocumentTest {
	
	@Test
	public void testThatTaggedDocumentIsCopied() throws Exception {
		testEquals("--- !someTag\nscalar: value\n");
	}

	@Test
	public void testThatScalarValueIsCopied() throws Exception {
		testEquals("scalar: value\n");
	}

	@Test
	public void testThatTaggedScalarValueIsCopied() throws Exception {
		testEquals("scalar: value !someTag\n");
	}

	@Test
	public void testThatAnchoredScalarValueIsCopied() throws Exception {
		testEquals("scalar: &anchor value\n");
	}

	@Test
	public void testThatAliasedScalarValueIsCopied() throws Exception {
		testEquals("scalar: *alias\n");
	}

	@Test
	public void testThatSequenceValueIsCopied() throws Exception {
		testEquals("-  scalar1: value\n-  scalar2: value\n");
	}

	@Test
	public void testThatTaggedSequenceValueIsCopied() throws Exception {
		testEquals("-  scalar1: value !someTag1\n-  scalar2: value !someTag2\n");
	}

	@Test
	public void testThatAnchoredSequenceValueIsCopied() throws Exception {
		testEquals("sequence: &anchor\n-  scalar1: value\n-  scalar2: value\n");
	}

	@Test
	public void testThatAliasedSequenceValueIsCopied() throws Exception {
		testEquals("sequence: *alias\n");
	}

	@Test
	public void testThatMappingValueIsCopied() throws Exception {
		testEquals("mapping: \n   scalar1: value\n   scalar2: value\n");
	}

	@Test
	public void testThatTaggedMappingValueIsCopied() throws Exception {
		testEquals("mapping: !someTag1\n   scalar1: value\n   scalar2: value !someTag2\n");
	}


	@Test
	public void testThatAnchoredMappingValueIsCopied() throws Exception {
		testEquals("mapping: &anchor\n   scalar1: value\n   scalar2: value\n");
	}

	@Test
	public void testThatAliasedMappingValueIsCopied() throws Exception {
		testEquals("mapping: *alias\n");
	}

	private void testEquals(String yaml) throws Exception {
		YamlDocumentReader reader = new YamlDocumentReader(yaml);
		YamlDocument document = reader.read();
		StringWriter writer = new StringWriter();
		YamlConfig config = new YamlConfig();
		config.writeConfig.setExplicitFirstDocument(document.getTag()!=null);
		config.writeConfig.setWriteClassname(WriteClassName.NEVER);
		config.writeConfig.setAutoAnchor(false);
		YamlWriter yamlWriter = new YamlWriter(writer, config);
		yamlWriter.write(document);
		yamlWriter.close();
		assertEquals(yaml, writer.toString());
	}
}
