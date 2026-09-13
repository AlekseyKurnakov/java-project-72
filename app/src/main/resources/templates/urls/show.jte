@import hexlet.code.dto.UrlPage

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

        </div>
    `
)