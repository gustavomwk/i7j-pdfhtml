/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css.w3c;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @see <a href="https://github.com/w3c/csswg-test">https://github.com/w3c/csswg-test</a>
 */
@Category(IntegrationTest.class)
public abstract class W3CCssTest extends ExtendedITextTest {

    private static final String baseSourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/w3c/";
    private static final String baseDestinationFolder = "./target/test/com/itextpdf/html2pdf/css/w3c/";

    protected abstract String getHtmlFileName();

    @BeforeClass
    public static void beforeClass() {
    }

    @Before
    public void before() {
        createDestinationFolder(getDestinationFolder());
    }

    @Test
    public void test() throws IOException, InterruptedException {
        String sourceFolder = getSourceFolder();
        String destinationFolder = getDestinationFolder();
        String htmlFilePath = sourceFolder + getHtmlFileName();
        String outFilePath = destinationFolder + getOutPdfFileName();
        String cmpFilePath = sourceFolder + getOutPdfFileName();
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlFilePath).getPath() + "\n");
        HtmlConverter.convertToPdf(new File(htmlFilePath), new File(outFilePath));
        Assert.assertNull(new CompareTool().compareByContent(outFilePath, cmpFilePath, destinationFolder, "diff_"));
    }

    private String getDestinationFolder() {
        String localPackage = getLocalPackage();
        return baseDestinationFolder + localPackage + File.separatorChar + getTestClassName() + File.separatorChar;
    }

    private String getSourceFolder() {
        String localPackage = getLocalPackage();
        return baseSourceFolder + localPackage + File.separatorChar;
    }

    private String getTestClassName() {
        return getClass().getSimpleName();
    }

    private String getLocalPackage() {
        String packageName = getClass().getPackage().getName();
        String basePackageName = W3CCssTest.class.getPackage().getName();
        return packageName.substring(basePackageName.length()).replace('.', File.separatorChar);
    }

    private String getOutPdfFileName() {
        String htmlFileName = getHtmlFileName();
        return htmlFileName.replaceAll("\\.[a-zA-Z]+?$", ".pdf");
    }

}
