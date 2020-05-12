package jrails;

public class View {

    public static Html empty() {
        return new Html("");
    }

    public static Html br() {
        return new Html("<br/>");
    }

    public static Html t(Object o) {
        // Use o.toString() to get the text for this
        return new Html(o.toString());
    }

    public static Html p(Html child) {
        Html html = new Html("");
        html.p(child);
        return html;
    }

    public static Html div(Html child) {
        Html html = new Html("");
        html.div(child);
        return html;
    }

    public static Html strong(Html child) {
        Html html = new Html("");
        html.strong(child);
        return html;
    }

    public static Html h1(Html child) {
        Html html = new Html("");
        html.h1(child);
        return html;
    }

    public static Html tr(Html child) {
        Html html = new Html("");
        html.tr(child);
        return html;
    }

    public static Html th(Html child) {
        Html html = new Html("");
        html.th(child);
        return html;
    }

    public static Html td(Html child) {
        Html html = new Html("");
        html.td(child);
        return html;
    }

    public static Html table(Html child) {
        Html html = new Html("");
        html.table(child);
        return html;
    }

    public static Html thead(Html child) {
        Html html = new Html("");
        html.thead(child);
        return html;
    }

    public static Html tbody(Html child) {
        Html html = new Html("");
        html.tbody(child);
        return html;
    }

    public static Html textarea(String name, Html child) {
        Html html = new Html("");
        html.textarea(name,child);
        return html;
    }

    public static Html link_to(String text, String url) {
        Html html = new Html("");
        html.link_to(text,url);
        return html;
    }

    public static Html form(String action, Html child) {
        Html html = new Html("");
        html.form(action, child);
        return html;
    }

    public static Html submit(String value) {
        Html html = new Html("");
        html.submit(value);
        return html;
    }
}