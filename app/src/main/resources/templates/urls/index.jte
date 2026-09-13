@import hexlet.code.dto.UrlPage

@param UrlPage page

@template.layout.application(
    page = page,
    content = @`
        <div class="mx-auto max-w-6xl">

            <h1 class="mb-6 text-4xl font-light">
                Сайты
            </h1>

            <table
                data-test="urls"
                class="w-full border-collapse"
            >
                <thead>
                    <tr class="border-b border-gray-300 text-left">
                        <th class="px-3 py-3">ID</th>
                        <th class="px-3 py-3">Имя</th>
                        <th class="px-3 py-3">Дата создания</th>
                    </tr>
                </thead>

                <tbody>

                    @for (var url : page.getUrls())
                        <tr class="border-b border-gray-200">

                            <td class="px-3 py-3">
                                ${url.getId()}
                            </td>

                            <td class="px-3 py-3">
                                <a
                                    href="/urls/${url.getId()}"
                                    class="text-blue-600 hover:underline"
                                >
                                    ${url.getName()}
                                </a>
                            </td>

                            <td class="px-3 py-3">
                                ${url.getCreatedAt().toString()}
                            </td>

                        </tr>
                    @endfor

                </tbody>
            </table>

        </div>
    `
)