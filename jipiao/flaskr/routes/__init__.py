from .AuthRoute import auth_bp
from .FlightRoute import flight_bp
from .OrderRoute import order_bp
from .MessageRoute import message_bp


def init_app(app):
    app.register_blueprint(flight_bp)
    app.register_blueprint(auth_bp)
    app.register_blueprint(order_bp)
    app.register_blueprint(message_bp)