package com.park9eon.compose.router

import androidx.compose.runtime.*
import com.park9eon.compose.PathPatternResult
import com.park9eon.compose.pathPatternMatch
import history.History
import history.Location
import history.createBrowserHistory

typealias Path = PathPatternResult

internal data class InternalRoute(
    val path: String,
    val render: @Composable (route: Path) -> Unit
)

class RouterBuilder {

    internal val routeMap = mutableListOf<InternalRoute>()

    fun Route(path: String, render: @Composable (route: Path) -> Unit) {
        val route = InternalRoute(path, render)

        this.routeMap.add(route)
    }
}

internal val historyComposition = compositionLocalOf<History?> {
    null
}

@Composable
internal fun getHistory(): History? = historyComposition.current

@Composable
fun Router(
    history: History = remember { createBrowserHistory() },
    routerBuilder: RouterBuilder.() -> Unit,
) {
    val (currentRouteSet, setCurrentRouteSet) = remember {
        mutableStateOf<Pair<InternalRoute, Path>?>(null)
    }
    val routes = remember {
        val builder = RouterBuilder()

        routerBuilder.invoke(builder)

        builder.routeMap
    }

    fun listen(location: Location) {
        val lastRoute = routes.mapNotNull {
            val path = pathPatternMatch(
                it.path,
                "${location.pathname}${location.search}${location.hash}",
            ) ?: return@mapNotNull null

            it to path
        }.last()

        setCurrentRouteSet(lastRoute)
    }

    LaunchedEffect(history) {
        listen(history.location)

        history.listen { update ->
            listen(update.location)
        }
    }

    CompositionLocalProvider(
        historyComposition provides history
    ) {
        currentRouteSet?.run {
            console.log("render")

            val (route, path) = this

            route.render(path)
        }
    }
}
