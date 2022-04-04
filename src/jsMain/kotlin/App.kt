import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposableInBody

fun main() {
    val message = "Hello, Kotlin World!"

    console.log(message)

    renderComposableInBody {
        Div {
            Text(message)
        }
    }
}
