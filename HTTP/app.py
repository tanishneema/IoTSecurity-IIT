import random
from flask import Flask, request, render_template, redirect, url_for

app = Flask(__name__)


@app.route('/process', methods=['GET', 'POST'])
def process_data():
    if request.method == 'POST':
        try:
            username = sanitize_input(request.form.get('username'))
            password = sanitize_input(request.form.get('password'))

            if username == 'admin' and password == 'admin':
                return generate_bill()
            else:
                return 'Invalid credentials'

        except Exception as e:
            return f"An error occurred: {str(e)}"
    elif request.method == 'GET':
        return redirect(url_for('login'))


def sanitize_input(input_data):
    # Implement your input sanitization logic here
    # For example, you can remove special characters or perform input validation
    # Be sure to validate and sanitize all input used in the application
    sanitized_data = input_data.strip()
    return sanitized_data


def generate_bill():
    try:
        units_consumed = random.randint(1, 1000)
        bill_amount = calculate_bill(units_consumed)

        message = f"Units consumed: {units_consumed}<br>"
        message += f"Bill amount: {bill_amount}<br>"
        message += "<br>Thank you for using our service!"

        return render_template('bill.html', message=message)

    except Exception as e:
        return f"An error occurred during bill generation: {str(e)}"


def calculate_bill(units_consumed):
    # Implement your bill calculation logic here
    # For example, you can calculate the bill based on the consumed units
    bill_amount = units_consumed * 3

    if bill_amount < 350:
        bill_amount = 350

    return bill_amount


@app.route('/', methods=['GET', 'POST'])
def logout():
    if request.method == 'POST':
        # Perform the logout action here
        # For example, clear session data or perform any necessary cleanup
        return redirect(url_for('login'))
    else:
        return redirect(url_for('process_data'))


@app.route('/login', methods=['GET', 'POST'])
def login():
    try:
        if request.method == 'POST':
            username = sanitize_input(request.form.get('username'))
            password = sanitize_input(request.form.get('password'))

            if username == 'admin' and password == 'admin':
                return redirect(url_for('process_data'))
            else:
                return 'Invalid credentials'

        return render_template('login.html')

    except Exception as e:
        return f"An error occurred: {str(e)}"


if __name__ == '__main__':
    app.run()
