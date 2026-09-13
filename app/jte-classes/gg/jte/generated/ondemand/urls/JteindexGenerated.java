package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlPage;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,4,4,7,7,28,28,32,32,32,37,37,37,37,40,40,40,45,45,45,49,49,55,55,55,55,55,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JteapplicationGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"mx-auto max-w-6xl\">\n\n            <h1 class=\"mb-6 text-4xl font-light\">\n                Сайты\n            </h1>\n\n            <table\n                data-test=\"urls\"\n                class=\"w-full border-collapse\"\n            >\n                <thead>\n                    <tr class=\"border-b border-gray-300 text-left\">\n                        <th class=\"px-3 py-3\">ID</th>\n                        <th class=\"px-3 py-3\">Имя</th>\n                        <th class=\"px-3 py-3\">Дата создания</th>\n                    </tr>\n                </thead>\n\n                <tbody>\n\n                    ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n                        <tr class=\"border-b border-gray-200\">\n\n                            <td class=\"px-3 py-3\">\n                                ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("\n                            </td>\n\n                            <td class=\"px-3 py-3\">\n                                <a\n                                    href=\"/urls/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(url.getId());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\"\n                                    class=\"text-blue-600 hover:underline\"\n                                >\n                                    ");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("\n                                </a>\n                            </td>\n\n                            <td class=\"px-3 py-3\">\n                                ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getCreatedAt().toString());
					jteOutput.writeContent("\n                            </td>\n\n                        </tr>\n                    ");
				}
				jteOutput.writeContent("\n\n                </tbody>\n            </table>\n\n        </div>\n    ");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
