package com.mycompany.computeCSS;

import com.sun.webkit.dom.DOMWindowImpl;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.views.DocumentView;

public class Renderer {

    private final String css;
    private final String body;
    
    StringProperty computedProperty = new SimpleStringProperty();
    
    public Renderer(String css, String body, StringProperty computedProperty) {
        this.css = css;
        this.body = body;
        this.computedProperty = computedProperty;
    }
    
    public void renderHtml() {
        
        Stage stage = new Stage();
        
        WebView webView = new WebView();
        
        String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>" + css + "</style>\n" +
            "</head>\n" +
            "<body><code><pre>\n" +
            body + "</pre></code>\n" +
            "</body>\n" +
            "</html>";
        
        webView.getEngine().loadContent(html);

        WebEngine webEngine=webView.getEngine();
        
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Document doc = webEngine.getDocument();
                
                HTMLDocument htmlDoc = (HTMLDocument)doc;
                
                //JS [window] access
                DOMWindowImpl wnd = (DOMWindowImpl)((DocumentView)htmlDoc).getDefaultView();
                
                List<Node> nodes = new ArrayList<>();
                NodeList list = doc.getElementsByTagName("span");
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        nodes.add(node);
                    }
                }
                
                nodes.forEach(node -> {
                    CSSStyleDeclaration style = wnd.getComputedStyle((Element)node, "");
                    String color = style.getPropertyValue("color");
                    String background_color = style.getPropertyValue("background-color");
                    String font_weight = style.getPropertyValue("font-weight");
                    String font_style = style.getPropertyValue("font-style");
                    
                    Attr attr = doc.createAttribute("style");
                    String att_style = String.join(";", "color:" + color, "background-color:" + background_color, 
                            "font-weight:" + font_weight, "font-style:" + font_style);
                    attr.setNodeValue(att_style);
                    node.getAttributes().setNamedItem(attr);
                });
                
                // get content body of 'doc'
                Node bodyNode = doc.getElementsByTagName("body").item(0);
                
                String computedBody = getNodeContent(bodyNode);
                System.out.println("renderHtml: computedBody " + computedBody);
                computedProperty.set(computedBody);
                stage.close();

            }
        });
        
        webEngine.loadContent(html);
        
        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);
        stage.setScene(scene);
        //stage.show();
        stage.hide();
    }
    
    private String getNodeContent(Node node) {
        StringWriter sw = new StringWriter();
        try {
          Transformer t = TransformerFactory.newInstance().newTransformer();
          t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
          t.setOutputProperty(OutputKeys.INDENT, "no");
          t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException ex) {
          System.out.println("getNodeContent Transformer Exception:" + ex.toString());
        }
        return sw.toString();
    }
}
