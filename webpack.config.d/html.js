;(function (config) {
    const HtmlWebpackPlugin = require("html-webpack-plugin")

    config.plugins.push(
        new HtmlWebpackPlugin({
            title: "compose-js",
            filename: "index.html",
            publicPath: "/"
        }),
    )
})(config);
