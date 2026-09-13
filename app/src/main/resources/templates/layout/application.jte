@import gg.jte.Content
@import hexlet.code.dto.BasePage

@param Content content
@param BasePage page = null

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц</title>
    <link href="/css/main.css" rel="stylesheet">
</head>

<body class="flex min-h-screen flex-col bg-white text-gray-900">

    <nav class="bg-gray-800 text-white">
        <div class="mx-auto flex w-full max-w-6xl items-center gap-6 px-4 py-4">

            <a href="/" class="text-xl font-semibold text-white">
                Анализатор страниц
            </a>

            <a href="/" class="text-gray-300 hover:text-white">
                Главная
            </a>

            <a href="/urls" class="text-gray-300 hover:text-white">
                Сайты
            </a>

        </div>
    </nav>


    @if(page != null && page.getFlash() != null)

        <div
            class="border-b px-4 py-4
            @if("success".equals(page.getFlashType()))
                border-green-200 bg-green-100 text-green-800
            @else
                border-red-200 bg-red-100 text-red-800
            @endif"
            role="alert"
        >
            <div class="mx-auto max-w-6xl">
                ${page.getFlash()}
            </div>
        </div>

    @endif


    <main class="mx-auto w-full max-w-6xl flex-1 px-4 py-10">

        ${content}

    </main>


    <footer class="border-t border-gray-200 py-4">
        <div class="mx-auto max-w-6xl px-4 text-center">

            <a
                href="https://ru.hexlet.io"
                class="text-blue-600 underline"
            >
                Hexlet
            </a>

        </div>
    </footer>

</body>

</html>