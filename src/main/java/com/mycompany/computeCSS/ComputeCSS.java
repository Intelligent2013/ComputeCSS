package com.mycompany.computeCSS;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ComputeCSS {

    public static void main(String[] args) throws InterruptedException {
        // start FX toolkit:
        new Thread(() -> Application.launch(FXStarter.class)).start();
        FXStarter.awaitFXToolkit();
        
        String css = ".hljs{color:#24292e;background:#fff}.hljs-doctag,.hljs-keyword,.hljs-meta .hljs-keyword,.hljs-template-tag,.hljs-template-variable,.hljs-type,.hljs-variable.language_{color:#d73a49}.hljs-title,.hljs-title.class_,.hljs-title.class_.inherited__,.hljs-title.function_{color:#6f42c1}.hljs-attr,.hljs-attribute,.hljs-literal,.hljs-meta,.hljs-number,.hljs-operator,.hljs-selector-attr,.hljs-selector-class,.hljs-selector-id,.hljs-variable{color:#005cc5}.hljs-meta .hljs-string,.hljs-regexp,.hljs-string{color:#032f62}.hljs-built_in,.hljs-symbol{color:#e36209}.hljs-code,.hljs-comment,.hljs-formula{color:#6a737d}.hljs-name,.hljs-quote,.hljs-selector-pseudo,.hljs-selector-tag{color:#22863a}.hljs-subst{color:#24292e}.hljs-section{color:#005cc5;font-weight:700}.hljs-bullet{color:#735c0f}.hljs-emphasis{color:#24292e;font-style:italic}.hljs-strong{color:#24292e;font-weight:700}.hljs-addition{color:#22863a;background-color:#f0fff4}.hljs-deletion{color:#b31d28;background-color:#ffeef0}";
        String body = "<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">xi:include</span> <span class=\"hljs-attr\">href</span>=<span class=\"hljs-string\">\"test1.xml\"</span>/&gt;</span>\n" +
            "<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">xi:include</span> <span class=\"hljs-attr\">href</span>=<span class=\"hljs-string\">\"test2.xml\"</span>/&gt;</span>";
        
        System.out.println("source HTML:\n" + body);
        
        String updatedHTML = computeCSS(css, body);
        System.out.println("updated HTML: \n" + updatedHTML);
       
        Platform.exit(); //exit FX toolkit:
    }    
    
    public static String computeCSS(String css, String body) {
        final StringProperty computedProperty = new SimpleStringProperty();
        try {
            final FutureTask query = new FutureTask(new Callable() {
                @Override
                public Object call() throws Exception {
                    Renderer msger = new Renderer(css,body,computedProperty);
                    msger.renderHtml();
                    return "";
                }
            });
            Platform.runLater(query);
            query.get();

            while (computedProperty.get() == null) {
                try {
                    Thread.sleep(10); //wait html rendered 10 milliseconds delay
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("next attempt...");
            }
        } catch (InterruptedException | ExecutionException exc) {
            Thread.currentThread().interrupt();
        } finally {
            Platform.setImplicitExit(true);
        }
        return computedProperty.get();
    }
}
