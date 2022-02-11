import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    val message = "Hello, Kotlin World!"

    console.log(message)

    renderComposable(rootElementId = "app") {
        Div {
            Text(message)
        }
    }
}
