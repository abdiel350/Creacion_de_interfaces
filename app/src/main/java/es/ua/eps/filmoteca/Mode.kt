package es.ua.eps.filmoteca

enum class Mode {
    Layouts,
    Compose,
}
// Variable global que será accesible desde cualquier archivo kt para cambiar el modo, sea Layout o Compose
object AppConfig {
    var mode: Mode = Mode.Compose
}