@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@param Content main
@param Content header

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Java Project Page analyzer for SEO</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="shortcut icon" href="#">
</head>
<body class="d-flex flex-column">
    <header>
        <nav class="navbar navbar-expand-lg bg-body-tertiary bg-dark border-bottom" data-bs-theme="dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="${NamedRoutes.rootPath()}">Page Analyzer</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    ${header}
                </div>
            </div>
        </nav>
    </header>
    <main class="flex-shrink-0">
        ${main}
    </main>
    <footer class="navbar fixed-bottom mt-auto p-3 border-top bg-light">
        <div class="container d-flex justify-content-center">
            <span class="text-center text-body-secondary">created by <a href="https://github.com/mynameiskatherine/java-project-72">Kate</a></span>
        </div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
