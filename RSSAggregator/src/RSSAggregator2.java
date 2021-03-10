import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Andrews OSeei Kontoh
 *
 */
public final class RSSAggregator2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator2() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title> </
     * head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";
        int titleIndex = getChildElement(channel, "title");
        int descIndex = getChildElement(channel, "description");

        out.print("<html> <head> <title>"
                + channel.child(titleIndex).child(0).label()
                + "</title></head> <body>\r\n" + "      <h1>"
                + channel.child(titleIndex).child(0).label()
                + " <channel> link</h1>\r\n" + "      <p>\r\n"
                + channel.child(descIndex).child(0).label() + "\r\n"
                + "      </p>\r\n" + "      <table border=\"1\">\r\n"
                + "      <tr>\r\n" + "      <th>Date</th>\r\n"
                + "      <th>Source</th>\r\n" + "      <th>News</th>\r\n"
                + "      </tr>");

    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.print("</table>\n" + "</body> </rss>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures
     *
     *          <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     *          </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";
        boolean notFound = true;
        int a = -1, i = 0;
        /*
         * TODO: fill in body
         */
        while (notFound && i < xml.numberOfChildren()) {
            if (xml.child(i).label().equals(tag)) {
                a = i;
            }
            i++;
        }
        return a;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures
     *
     *          <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     *          </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        /*
         * TODO: fill in body
         */
        int index = getChildElement(item, "pubDate");
        if (index >= 0) {
            out.print("<tr>\n<td>" + item.child(index) + "</td>");
        } else {
            out.print("<td> </td>");
        }

        index = getChildElement(item, "source");
        if (index >= 0) {
            out.print("<td>" + item.child(index) + "</td>");
        } else {
            out.print("<td> </td>");
        }

        //display the descriptionn, but if no description, display title
        index = getChildElement(item, "description");
        if (index >= 0) {
            out.print("<td>" + item.child(index) + "</td></tr>");
        } else {
            index = getChildElement(item, "title");
            if (index >= 0) {
                out.print("<td>" + item.child(index) + "</td></tr>");
            }
        }
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        XMLTree xml = new XMLTree1(url);

    }

    /*
     * Creates HTML file for the main page
     * 
     * @param xml xml tree for the url the user provides
     * 
     * @param out output stream to an html file.
     * 
     * @requires out.is_open
     *
     */
    private static void mainPage(XMLTree xml, SimpleWriter out) {
        out.print("<html>\n" + "<head><title>Top Stoories</title></head>\n"
                + "\n<body>" + "\n<h1> Top Stories</h1>" + "\n<ul> \n");
        for (int a = 0; a < xml.numberOfChildren(); a++) {
            out.print("<li><a href=\"" + xml.child(a).attributeValue("url")
                    + "\"> " + xml.child(a).attributeValue("name")
                    + "</a></li>\n");
        }
        out.print("\n </ul> <\n </body>" + "\n</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();

        SimpleWriter cout = new SimpleWriter1L();
        boolean notRSSTag = true;

        //Taking the file name and creating XML tree
        cout.print("Enter the name of the XML file containing list of URLs:  ");
        String fileName = in.nextLine();
        XMLTree XMLFile = new XMLTree1(fileName);

        //Taking the name of file to generate the HTML page into.
        cout.print("Enter the name of file to generate the HTML page with Links"
                + " to other pages: ");
        String xmlFile = in.nextLine();
        SimpleWriter out = new SimpleWriter1L(xmlFile + ".html");

        mainPage(XMLFile, out);
        cout.print("successful");
        in.close();
        out.close();
        cout.close();
    }

}