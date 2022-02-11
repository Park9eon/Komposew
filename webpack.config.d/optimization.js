const TerserPlugin = require("terser-webpack-plugin")

config.optimization = {
    splitChunks: {
        chunks: "all",
    },
    minimize: true,
    minimizer: [new TerserPlugin()],
}
