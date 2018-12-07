package blog;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class Test {
	public static void main(String[] args) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse("This is *Sparta*");
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		System.out.println(renderer.render(document));
	}
}
