/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.uritemplate.parse;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateParseException;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public final class ParsingExceptionsTest
{
    private static final MessageBundle BUNDLE
        = MessageBundles.getBundle(URITemplateMessageBundle.class);

    @DataProvider
    public Iterator<Object[]> invalidInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        String message;
        int offset;

        input = "foo%";
        message = BUNDLE.getMessage("paser.percentShortRead");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo%r";
        message = BUNDLE.getMessage("paser.percentShortRead");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo%ra";
        message = BUNDLE.getMessage("parse.percentIllegal");
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = "foo%ar";
        message = BUNDLE.getMessage("parse.percentIllegal");
        offset = 5;
        list.add(new Object[]{input, message, offset});

        input = "foo<";
        message = BUNDLE.getMessage("parse.noParser");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        input = "foo{";
        message = BUNDLE.getMessage("parse.unexpectedEOF");
        offset = 3;
        list.add(new Object[]{input, message, offset});

        return list.iterator();
    }

    @Test(dataProvider = "invalidInputs")
    public void invalidInputsRaiseAppropriateExceptions(final String input,
        final String message, final int offset)
    {
        try {
            URITemplateParser.parse(input);
            fail("No exception thrown!!");
        } catch (URITemplateParseException e) {
            assertEquals(e.getOriginalMessage(), message);
            assertEquals(e.getOffset(), offset);
        }
    }
}
