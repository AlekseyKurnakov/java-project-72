package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteapplicationGenerated {
	public static final String JTE_NAME = "layout/application.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,3,37,37,37,41,41,43,43,45,45,49,49,49,53,53,58,58,58,78,78,78,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Анализатор страниц</title>\n    <link href=\"/css/main.css\" rel=\"stylesheet\">\n</head>\n\n<body class=\"flex min-h-screen flex-col bg-white text-gray-900\">\n\n    <nav class=\"bg-gray-800 text-white\">\n        <div class=\"mx-auto flex w-full max-w-6xl items-center gap-6 px-4 py-4\">\n\n            <a href=\"/\" class=\"text-xl font-semibold text-white\">\n                Анализатор страниц\n            </a>\n\n            <a href=\"/\" class=\"text-gray-300 hover:text-white\">\n                Главная\n            </a>\n\n            <a href=\"/urls\" class=\"text-gray-300 hover:text-white\">\n                Сайты\n            </a>\n\n        </div>\n    </nav>\n\n\n    ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\n\n        <div\n            class=\"border-b px-4 py-4\n            ");
			if ("success".equals(page.getFlashType())) {
				jteOutput.writeContent("\n                border-green-200 bg-green-100 text-green-800\n            ");
			} else {
				jteOutput.writeContent("\n                border-red-200 bg-red-100 text-red-800\n            ");
			}
			jteOutput.writeContent("\"\n            role=\"alert\"\n        >\n            <div class=\"mx-auto max-w-6xl\">\n                ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\n            </div>\n        </div>\n\n    ");
		}
		jteOutput.writeContent("\n\n\n    <main class=\"mx-auto w-full max-w-6xl flex-1 px-4 py-10\">\n\n        ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n\n    </main>\n\n\n    <footer class=\"border-t border-gray-200 py-4\">\n        <div class=\"mx-auto max-w-6xl px-4 text-center\">\n\n            <a\n                href=\"https://ru.hexlet.io\"\n                class=\"text-blue-600 underline\"\n            >\n                Hexlet\n            </a>\n\n        </div>\n    </footer>\n\n</body>\n\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page",  null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
