package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlPage;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,4,4,7,7,11,11,11,25,25,25,34,34,34,43,43,43,54,54,54,54,79,79,81,81,81,82,82,82,83,83,83,84,84,84,85,85,85,86,86,86,88,88,94,94,94,94,94,2,2,2,2};
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
				jteOutput.writeContent("\n                        </td>\n                    </tr>\n\n                </tbody>\n            </table>\n\n            <h2 class=\"mb-4 mt-10 text-2xl font-light\">\n                Проверки\n            </h2>\n\n            <form method=\"post\" action=\"/urls/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("/checks\" class=\"mb-6\">\n                <input\n                    type=\"submit\"\n                    value=\"Запустить проверку\"\n                    class=\"cursor-pointer rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700\"\n                >\n            </form>\n\n            <table\n                data-test=\"checks\"\n                class=\"w-full border-collapse\"\n            >\n                <thead>\n                    <tr class=\"border-b border-gray-300 text-left\">\n                        <th class=\"px-3 py-3\">ID</th>\n                        <th class=\"px-3 py-3\">Код ответа</th>\n                        <th class=\"px-3 py-3\">h1</th>\n                        <th class=\"px-3 py-3\">title</th>\n                        <th class=\"px-3 py-3\">description</th>\n                        <th class=\"px-3 py-3\">Дата проверки</th>\n                    </tr>\n                </thead>\n\n                <tbody>\n\n                    ");
				for (var check : page.getUrlsCheck()) {
					jteOutput.writeContent("\n                        <tr class=\"border-b border-gray-200\">\n                            <td class=\"px-3 py-3\">");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getId());
					jteOutput.writeContent("</td>\n                            <td class=\"px-3 py-3\">");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getStatusCode());
					jteOutput.writeContent("</td>\n                            <td class=\"px-3 py-3\">");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getH1());
					jteOutput.writeContent("</td>\n                            <td class=\"px-3 py-3\">");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getTitle());
					jteOutput.writeContent("</td>\n                            <td class=\"px-3 py-3\">");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getDescription());
					jteOutput.writeContent("</td>\n                            <td class=\"px-3 py-3\">");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getCreatedAt().toString());
					jteOutput.writeContent("</td>\n                        </tr>\n                    ");
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
