package seongmin.ministory.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
public class AllContentsRes {
    private List<GetContentRes> contents;
    private int totalPage;

    public static AllContentsRes from(List<Content> contents, int totalPage) {
        Parser parser = Parser.builder().build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        AllContentsRes response = AllContentsRes.builder()
                .contents(new ArrayList<>())
                .totalPage(totalPage)
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
