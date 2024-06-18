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

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindTempContentsRes {
    private List<GetContentRes> contents;

    public static FindTempContentsRes from(List<Content> contents) {
        Parser parser = Parser.builder().build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        FindTempContentsRes response = FindTempContentsRes.builder()
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
