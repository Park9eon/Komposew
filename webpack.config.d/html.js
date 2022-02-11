const HtmlWebpackPlugin = require("html-webpack-plugin")

config.plugins.push(
    new HtmlWebpackPlugin({
        title: "Komposew",
        filename: "index.html",
        template: "template.html",
        scriptLoading: "blocking",
    }),
)
