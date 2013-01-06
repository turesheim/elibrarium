/*******************************************************************************
 * Copyright (c) 2012 Torkild U. Resheim and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Torkild U. Resheim - initial API and implementation
 *******************************************************************************/

package no.resheim.elibrarium.epub.ui.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.browser.Browser;

/**
 * This type's methods should be used instead of calling browser JavaScript
 * directly.
 * 
 * @author Torkild U. Resheim
 */
public final class EpubUiUtility {

	public boolean injectJavaScript(Browser browser) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("desiredWidth = " + browser.getSize().x + ";");
		sb.append("desiredHeight = " + (browser.getSize().y - 20) + ";");
		readJS("jquery-1.7.1.js", sb);
		readJS("rangy-core.js", sb);
		readJS("rangy-serializer.js", sb);
		readJS("rangy-cssclassapplier.js", sb);
		readJS("injected.js", sb);
		return browser.execute(sb.toString());
	}

	/**
	 * Reads the (JavaScript) file and appends the content to the given buffer.
	 * 
	 * @param filename
	 *            file to read
	 * @param sb
	 *            buffer to add to
	 * @throws IOException
	 */
	private void readJS(String filename, StringBuilder sb) throws IOException {
		String in;
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
		while ((in = br.readLine()) != null) {
			sb.append(in);
			sb.append('\n');
		}
	}

	/**
	 * Navigates to the given chapter page.
	 * 
	 * @param browser
	 *            the browser widget
	 * @param page
	 *            the page number
	 */
	public static void navigateToPage(Browser browser, int page) {
		browser.evaluate("navigateToPage(" + page + ");");
	}

	public static int getPageWidth(Browser browser) {
		return (int) Math.round((Double) browser.evaluate("return desiredWidth"));
	}

}
