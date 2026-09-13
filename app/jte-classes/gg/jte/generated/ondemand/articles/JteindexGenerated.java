package gg.jte.generated.ondemand.articles;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteindexGenerated {
	public static final String JTE_NAME = "articles/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,5,5,8,8,48,48,48,48,48,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BasePage page) {
		jteOutput.writeContent("\n\n");
		gg.jte.generated.ondemand.layout.JteapplicationGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"mx-auto max-w-3xl rounded-lg border border-gray-200 bg-gray-50 p-10\">\n\n            <h1 class=\"text-5xl font-light\">\n                Анализатор страниц\n            </h1>\n\n            <p class=\"mt-2 mb-6 text-xl text-gray-600\">\n                Бесплатно проверяйте сайты на SEO пригодность\n            </p>\n\n\n            <form action=\"/urls\" method=\"post\" class=\"flex flex-wrap items-start gap-3\">\n\n                <div class=\"grow\">\n\n                    <label for=\"url-name\" class=\"sr-only\">\n                        Url для проверки\n                    </label>\n\n                    <input\n                        id=\"url-name\"\n                        type=\"text\"\n                        name=\"url\"\n                        class=\"w-full rounded text-lg\"\n                        placeholder=\"https://www.example.com\"\n                    >\n\n                </div>\n\n\n                <input\n                    type=\"submit\"\n                    class=\"cursor-pointer rounded bg-blue-600 px-8 py-2 text-lg font-medium text-white uppercase hover:bg-blue-700\"\n                    value=\"Проверить\"\n                >\n\n            </form>\n\n        </div>\n    ");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BasePage page = (BasePage)params.getOrDefault("page",  null);
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
