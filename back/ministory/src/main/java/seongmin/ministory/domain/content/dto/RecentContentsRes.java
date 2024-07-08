package seongmin.ministory.domain.content.dto;

import lombok.*;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import seongmin.ministory.domain.content.entity.Content;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentContentsRes {
    private List<GetContentRes> contents;

    public static RecentContentsRes from(List<Content> contents) {
        Parser parser = Parser.builder().build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        RecentContentsRes response = RecentContentsRes.builder()
                .contents(new ArrayList<>())
                .build();

        for (Content content : contents) {
            Node document = parser.parse(content.getBody());
            String html = htmlRenderer.render(document);
            Document doc = Jsoup.parse(html); // html 태그 제거
            response.contents.add(GetContentRes.from(content, doc.text()));
        }

        return response;
    }
}
