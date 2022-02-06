package com.mycompany.computeCSS;

import javafx.application.Application;
import javafx.application.Platform;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TestName;

public class computeCSS_Tests {

    
    @Rule
    public final ExpectedSystemExit exitRule = ExpectedSystemExit.none();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();


    @Rule public TestName name = new TestName();
    
    @Before
    public void setFXStarter() throws Exception {
        // start FX toolkit:
        new Thread(() -> Application.launch(FXStarter.class)).start();
        FXStarter.awaitFXToolkit();
        
    }
    
    //@AfterClass
    public static void setUpAfterClass() throws Exception {
        Platform.exit(); //exit FX toolkit:
    }
    
    
    @Test
    public void html_test() {
        String css = ".hljs{color:#24292e;background:#fff}.hljs-doctag,.hljs-keyword,.hljs-meta .hljs-keyword,.hljs-template-tag,.hljs-template-variable,.hljs-type,.hljs-variable.language_{color:#d73a49}.hljs-title,.hljs-title.class_,.hljs-title.class_.inherited__,.hljs-title.function_{color:#6f42c1}.hljs-attr,.hljs-attribute,.hljs-literal,.hljs-meta,.hljs-number,.hljs-operator,.hljs-selector-attr,.hljs-selector-class,.hljs-selector-id,.hljs-variable{color:#005cc5}.hljs-meta .hljs-string,.hljs-regexp,.hljs-string{color:#032f62}.hljs-built_in,.hljs-symbol{color:#e36209}.hljs-code,.hljs-comment,.hljs-formula{color:#6a737d}.hljs-name,.hljs-quote,.hljs-selector-pseudo,.hljs-selector-tag{color:#22863a}.hljs-subst{color:#24292e}.hljs-section{color:#005cc5;font-weight:700}.hljs-bullet{color:#735c0f}.hljs-emphasis{color:#24292e;font-style:italic}.hljs-strong{color:#24292e;font-weight:700}.hljs-addition{color:#22863a;background-color:#f0fff4}.hljs-deletion{color:#b31d28;background-color:#ffeef0}";
        String body = "<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">xi:include</span> <span class=\"hljs-attr\">href</span>=<span class=\"hljs-string\">\"test1.xml\"</span>/&gt;</span>\n" +
            "<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">xi:include</span> <span class=\"hljs-attr\">href</span>=<span class=\"hljs-string\">\"test2.xml\"</span>/&gt;</span>";
        String resultedHTML = ComputeCSS.computeCSS(css, body).trim();
        assertTrue(resultedHTML.contains("color:rgb(0, 0, 0)"));
        
    }

    
}
