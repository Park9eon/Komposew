(function (config) {
    const HtmlWebpackPlugin = require("html-webpack-plugin")

    config.plugins.push(
        new HtmlWebpackPlugin({
            title: "Komposew",
            filename: "index.html",
            publicPath: "/"
        }),
    )
})(config);
