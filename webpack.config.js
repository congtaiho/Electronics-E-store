const HtmlWebPackPlugin = require('html-webpack-plugin')
const NodePolyfillPlugin = require('node-polyfill-webpack-plugin')
const path = require('path')

module.exports = {
    entry: {
        app: path.join(__dirname, 'frontend/src', 'index.jsx')
    },
    resolve: {
        extensions: ['.jsx', '.js', '.json'],
        alias: {
            component: path.resolve(__dirname, 'src/component/'),
            container: path.resolve(__dirname, 'src/container/'),
            service: path.resolve(__dirname, 'src/service/')
        }
    },
    devtool: 'inline-source-map',
    devServer: {
        open: true,
        historyApiFallback: true,
        static: {
            directory: path.join(__dirname)
        }
    },
    module: {
        rules: [
            {
                test: /\.jsx$/,
                exclude: /node_modules\/(?!backend)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.html$/,
                use: [{ loader: 'html-loader', options: { minimize: false } }]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.scss$/,
                use: [
                    'style-loader',
                    'css-loader',
                    'sass-loader'
                ]
            }
        ]
    },
    output: {
        publicPath: '/'
    },
    plugins: [
        new HtmlWebPackPlugin({
            template: 'frontend/src/index.html',
            filename: './index.html'
        }),
        new NodePolyfillPlugin()
    ]
}
