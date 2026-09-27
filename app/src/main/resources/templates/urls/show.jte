@import hexlet.code.dto.UrlPage
@import hexlet.code.util.TextUtils

@param UrlPage page

@template.layout.application(
    page = page,
    content = @`
        <div class="mx-auto max-w-6xl">

            <h1 class="mb-6 text-4xl font-light">
                Сайт: ${page.getUrl().getName()}
            </h1>

            <table
                data-test="url"
                class="w-full border-collapse border border-gray-200"
            >
                <tbody>

                    <tr>
                        <td class="border border-gray-200 px-4 py-3 font-medium">
                            ID
                        </td>
                        <td class="border border-gray-200 px-4 py-3">
                            ${page.getUrl().getId()}
                        </td>
                    </tr>

                    <tr>
                        <td class="border border-gray-200 px-4 py-3 font-medium">
                            Имя
                        </td>
                        <td class="border border-gray-200 px-4 py-3">
                            ${page.getUrl().getName()}
                        </td>
                    </tr>

                    <tr>
                        <td class="border border-gray-200 px-4 py-3 font-medium">
                            Дата создания
                        </td>
                        <td class="border border-gray-200 px-4 py-3">
                            ${page.getUrl().getCreatedAt().toString()}
                        </td>
                    </tr>

                </tbody>
            </table>

            <h2 class="mb-4 mt-10 text-2xl font-light">
                Проверки
            </h2>

            <form method="post" action="/urls/${page.getUrl().getId()}/checks" class="mb-6">
                <input
                    type="submit"
                    value="Запустить проверку"
                    class="cursor-pointer rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700"
                >
            </form>

            <table
                data-test="checks"
                class="w-full border-collapse"
            >
                <thead>
                    <tr class="border-b border-gray-300 text-left">
                        <th class="px-3 py-3">ID</th>
                        <th class="px-3 py-3">Код ответа</th>
                        <th class="px-3 py-3">h1</th>
                        <th class="px-3 py-3">title</th>
                        <th class="px-3 py-3">description</th>
                        <th class="px-3 py-3">Дата проверки</th>
                    </tr>
                </thead>

                <tbody>

                    @for (var check : page.getUrlsCheck())
                        <tr class="border-b border-gray-200">
                            <td class="px-3 py-3">${check.getId()}</td>
                            <td class="px-3 py-3">${check.getStatusCode()}</td>
                            <td class="px-3 py-3">${TextUtils.truncate(check.getH1(), 200)}</td>
                            <td class="px-3 py-3">${TextUtils.truncate(check.getTitle(), 200)}</td>
                            <td class="px-3 py-3">${TextUtils.truncate(check.getDescription(), 200)}</td>
                            <td class="px-3 py-3">${check.getCreatedAt().toString()}</td>
                        </tr>
                    @endfor

                </tbody>
            </table>

        </div>
    `
)