/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.apache.xpath.res;

import java.util.ListResourceBundle;

/** A utility class for issuing XPath error messages. */
public class XPATHMessages {
  /** The language specific resource object for XPath messages. */
  private static ListResourceBundle XPATHBundle = null;

  /**
   * Creates a message from the specified key and replacement arguments, localized to the given
   * locale.
   *
   * @param msgKey The key for the message text.
   * @param args The arguments to be used as replacement text in the message created.
   * @return The formatted message string.
   */
  public static String createXPATHMessage(String msgKey, Object[] args) // throws Exception
      {
    if (XPATHBundle == null) XPATHBundle = new XPATHErrorResources();

    if (XPATHBundle != null) {
      return createXPATHMsg(XPATHBundle, msgKey, args);
    }
    return "Could not load any resource bundles.";
  }

  /**
   * Creates a message from the specified key and replacement arguments, localized to the given
   * locale.
   *
   * @param fResourceBundle The resource bundle to use.
   * @param msgKey The message key to use.
   * @param args The arguments to be used as replacement text in the message created.
   * @return The formatted message string.
   */
  private static String createXPATHMsg(
      ListResourceBundle fResourceBundle, String msgKey, Object[] args) {

    String fmsg;
    boolean throwex = false;
    String msg = null;

    if (msgKey != null) msg = fResourceBundle.getString(msgKey);

    if (msg == null) {
      msg = fResourceBundle.getString(XPATHErrorResources.BAD_CODE);
      throwex = true;
    }

    if (args != null) {
      try {
        // Do this to keep format from crying.
        // This is better than making a bunch of conditional
        // code all over the place.
        int n = args.length;

        for (int i = 0; i < n; i++) {
          if (null == args[i]) args[i] = "";
        }

        fmsg = java.text.MessageFormat.format(msg, args);
      } catch (Exception e) {
        fmsg = fResourceBundle.getString(XPATHErrorResources.FORMAT_FAILED);
        fmsg += " " + msg;
      }
    } else {
      fmsg = msg;
    }

    if (throwex) {
      throw new RuntimeException(fmsg);
    }

    return fmsg;
  }
}
