package biz.jovido.seed.content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class HtmlService {

    public String filterHtml(String html) {
        Document document = Jsoup.parseBodyFragment(html);
        Element p = document.select("p").first();
        if (p != null) {
            html = p.unwrap().outerHtml();
        }

        html = Jsoup.clean(html, Whitelist.basic());

        return html;
    }
}
