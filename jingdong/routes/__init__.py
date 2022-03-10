from .good_route import good_bp


def init_app(app):
    app.register_blueprint(good_bp)