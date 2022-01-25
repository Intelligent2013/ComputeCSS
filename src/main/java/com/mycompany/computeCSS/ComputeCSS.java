package com.mycompany.computecss;

/*
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import com.sun.webkit.dom.DOMWindowImpl;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.html.HTMLBodyElement;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
*/
import com.sun.javafx.application.PlatformImpl;
import javafx.scene.web.WebEngine;
import org.w3c.dom.*;
import org.w3c.dom.css.*;
import org.w3c.dom.events.*;
import org.w3c.dom.html.*;
import org.w3c.dom.stylesheets.*;
import org.w3c.dom.views.*;
import com.sun.webkit.dom.*;
import java.io.File;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class ComputeCSS {

    private static WebView view;
    
    public static void main(String[] args)  { //throws SAXException, ParserConfigurationException, IOException 

        String htmltest = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "body {background-color:blue}\n" +
            ".header {color:red}\n" +
            ".par {color:green}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1 class='header'>Header</h1>\n" +
            "<p class='par'>Text</p>\n" +
            "</body>\n" +
            "</html>";
        
/*        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(htmltest)));
  */      

        
        //final Document doc = getDocumentFor("src/test/resources/test/html/dom.html");
        final Document doc = getDocumentFor(htmltest);
        
        
        
        
        HTMLDocument htmlDoc = (HTMLDocument)doc;
        final HTMLBodyElement body = (HTMLBodyElement)htmlDoc.getBody();
        
        // https://stackoverflow.com/questions/11737034/how-to-access-an-external-stylesheet-through-the-document-interface-in-java
       /* HTMLDocument htmlDoc = (HTMLDocument)doc;
        
        final HTMLBodyElement body = (HTMLBodyElement)htmlDoc.getBody();
        //JS [window] access
        DOMWindowImpl wnd = (DOMWindowImpl)((DocumentView)htmlDoc).getDefaultView();
        // Style access
        CSSStyleDeclaration style = wnd.getComputedStyle(body, "");
        System.out.println(style.getPropertyValue("background-color"));*/
    }
    
    
    static protected Document getDocumentFor(String code) {
        
        VBox root = new VBox();
        final WebView browser = new WebView();
        root.getChildren().addAll(browser);
        Scene scene = new Scene(root);
        
        /*HTMLEditor htmlEd=new HTMLEditor();
        String st=htmlEd.getHtmlText();*/
        
        WebEngine webEngine=browser.getEngine();
        
        //PlatformImpl.startup(null);
        //view = new WebView();
        //WebEngine webengine = view.getEngine();
        webEngine.loadContent(code);
        return webEngine.getDocument();
    }
    
    
    
}
