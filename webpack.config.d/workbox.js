const { GenerateSW } = require("workbox-webpack-plugin")

config.plugins.push(
    new GenerateSW({
        maximumFileSizeToCacheInBytes: 5000000,
    }),
)
