from .AuthRoute import auth_bp
from .FlightRoute import flight_bp


def init_app(app):
    app.register_blueprint(flight_bp)
    app.register_blueprint(auth_bp)