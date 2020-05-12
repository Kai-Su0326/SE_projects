package jrails;

public class Html {
    private String kai = "";

    public Html (String kai) {
        this.kai = kai;
    }

    public String toString() {
        return this.kai;
    }

    public Html empty() {
        this.kai = "";
        return this;
    }

    public Html seq(Html h) {
        this.kai += h.toString();
        return this;
    }

    public Html br() {
        this.kai += "<br/>";
        return this;
    }

    public Html t(Object o) {
        // Use o.toString() to get the text for this
        this.kai = o.toString();
        return this;
    }

    public Html p(Html child) {
        kai += "<p>" + child.kai + "</p>";
        return this;
    }

    public Html div(Html child) {
        kai += "<div>" + child.kai + "</div>";
        return this;
    }

    public Html strong(Html child) {
        kai += "<strong>" + child.kai + "</strong>";
        return this;
    }

    public Html h1(Html child) {
        kai += "<h1>" + child.kai + "</h1>";
        return this;
    }

    public Html tr(Html child) {
        kai += "<tr>" + child.kai + "</tr>";
        return this;
    }

    public Html th(Html child) {
        kai += "<th>" + child.kai + "</th>";
        return this;
    }

    public Html td(Html child) {
        kai += "<td>" + child.kai + "</td>";
        return this;
    }

    public Html table(Html child) {
        kai += "<table>" + child.kai + "</table>";
        return this;
    }

    public Html thead(Html child) {
        kai += "<thead>" + child.kai + "</thead>";
        return this;
    }

    public Html tbody(Html child) {
        kai += "<tbody>" + child.kai + "</tbody>";
        return this;
    }

    public Html textarea(String name, Html child) {
        kai += "<textarea name=\"" + name + "\">" + child.kai + "</textarea>";
        return this;
    }

    public Html link_to(String text, String url) {
        kai += "<a href=\"" + url +"\">" + text + "</a>";
        return this;
    }

    public Html form(String action, Html child) {
        kai += "<form action=\"" + action + "\"" + " accept-charset=\"UTF-8\"" + " method=\"post\">" + child.kai + "</form>";
        return this;
    }

    public Html submit(String value) {
        kai += "<input type=\"submit\" value=\"" + value + "\"/>";
        return this;
    }
}