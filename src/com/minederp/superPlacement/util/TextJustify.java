package com.minederp.superPlacement.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;



/*
 * This code is NOT perfect. Its safe to say the constants are perfect, but the rest.... not so much. 
 * Needs tinkering.
 * 
 */
public class TextJustify {
	private static final int[] characterWidths = new int[] { 1, 9, 9, 8, 8, 8,
			8, 7, 9, 8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8,
			8, 9, 9, 9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6,
			6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4,
			6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6,
			4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
			6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6,
			8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
			9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7,
			7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8,
			7, 6, 6, 9, 7, 6, 7, 1 };
	private static final char COLOR_CHAR = '\u00A7';
	private static final int CHAT_WINDOW_WIDTH = 320;
	private static final int CHAT_STRING_LENGTH = 119;
	static {
	
		allowedChars = " !\"#$%&'()*+,-./" + "0123456789:;<=>?"
				+ "@ABCDEFGHIJKLMNO" + "PQRSTUVWXYZ[\\]^_" + "'abcdefghijklmno"
				+ "pqrstuvwxyz{|}~⌂" + "ÇüéâäàåçêëèïîìÄÅ" + "ÉæÆôöòûùÿÖÜø£Ø×ƒ"
				+ "áíóúñÑªº¿®¬½¼¡«»";
	}
	private static final String allowedChars;

	public static String rightJustify(String txt) {

		int totalWidth = 0;

		for (char ch : txt.toCharArray()) {

			if (ch == COLOR_CHAR) {

				continue;
			}

			// Figure out if it's allowed
			int index = allowedChars.indexOf(ch);
			
			if (index == -1) {
				// Invalid character .. skip it.
				continue;
			} else {
				// Sadly needed as the allowedChars string misses the first
				index += 32;
			}

			// Find the width
			final int width = characterWidths[index];
			totalWidth += width;

		}

		int index = allowedChars.indexOf(' ');
		index += 32;

		final int spacewidth = characterWidths[index];

		if (CHAT_WINDOW_WIDTH > totalWidth) {
			while (CHAT_WINDOW_WIDTH > totalWidth) {
				totalWidth += spacewidth;
				txt = ' ' + txt;
			}
		}
		return txt;
	}
}
