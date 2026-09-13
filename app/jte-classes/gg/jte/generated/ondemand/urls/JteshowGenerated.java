package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlPage;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,4,4,7,7,11,11,11,25,25,25,34,34,34,43,43,43,51,51,51,51,51,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JteapplicationGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"mx-auto max-w-6xl\">\n\n            <h1 class=\"mb-6 text-4xl font-light\">\n                Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("\n            </h1>\n\n            <table\n                data-test=\"url\"\n                class=\"w-full border-collapse border border-gray-200\"\n            >\n                <tbody>\n\n                    <tr>\n                        <td class=\"border border-gray-200 px-4 py-3 font-medium\">\n                            ID\n                        </td>\n                        <td class=\"border border-gray-200 px-4 py-3\">\n                            ");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("\n                        </td>\n                    </tr>\n\n                    <tr>\n                        <td class=\"border border-gray-200 px-4 py-3 font-medium\">\n                            Имя\n                        </td>\n                        <td class=\"border border-gray-200 px-4 py-3\">\n                            ");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("\n                        </td>\n                    </tr>\n\n                    <tr>\n                        <td class=\"border border-gray-200 px-4 py-3 font-medium\">\n                            Дата создания\n                        </td>\n                        <td class=\"border border-gray-200 px-4 py-3\">\n                            ");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().toString());
				jteOutput.writeContent("\n                        </td>\n                    </tr>\n\n                </tbody>\n            </table>\n\n        </div>\n    ");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
