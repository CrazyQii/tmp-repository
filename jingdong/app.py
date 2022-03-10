from flask import Flask, render_template
from flask_cors import CORS
import routes
import setting

app = Flask(__name__)


def create_app():
    app = Flask(__name__, static_folder='static', template_folder='template')  # app核心对象(本地使用)

    # 设置跨域请求
    CORS(app, supports_credentials=True)

    app.config.from_object(setting.Config)  # 加载配置

    routes.init_app(app)

    # 配置首页路由
    @app.route('/', methods=['GET'])
    def index():
        return render_template('index.html')

    # # 只能从前端跳转页面
    # @app.route('/', defaults={'path': ''})
    # @app.route('/<path:path>')
    # def catch_all(path):
    #     return render_template("index.html")

    return app


if __name__ == '__main__':
    app = create_app()
    app.run(host='127.0.0.1', port=5000)
