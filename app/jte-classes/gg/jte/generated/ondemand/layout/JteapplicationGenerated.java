package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteapplicationGenerated {
	public static final String JTE_NAME = "layout/application.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,17,17,21,21,21,24,24,24,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n\n    <title>Анализатор страниц</title>\n\n    <link href=\"/css/main.css\" rel=\"stylesheet\">\n</head>\n\n<body>\n    <nav>\n        ");
		jteOutput.writeContent("\n    </nav>\n\n    <main class=\"container mx-auto px-4 py-10\">\n        ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n    </main>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		render(jteOutput, jteHtmlInterceptor, content);
	}
}
