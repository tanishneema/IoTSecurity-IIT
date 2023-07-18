class SmartMeter:
    def __init__(self):
        self.__reading = 0
        self.__billing_info = "Unpaid"
        self.__is_authenticated = False

    def get_reading(self):
        return self.__reading

    def set_reading(self, new_reading):
        if self.__is_authenticated:
            if isinstance(new_reading, int) and new_reading >= 0:
                self.__reading = new_reading
                self.__billing_info = "Unpaid"
                print("Reading updated successfully.")
            else:
                print("Invalid input! Reading must be a non-negative integer.")
        else:
            print("Unauthorized access! Reading cannot be changed.")

    def get_billing_info(self):
        if self.__reading == 0:
            self.__billing_info = "Paid"
        else:
            units = max(self.__reading - 25, 0)
            bill = 250 + units * 10
            self.__billing_info = "Unpaid: $" + str(bill)
        return self.__billing_info

    def make_payment(self):
        if self.__is_authenticated:
            if self.__reading == 0:
                print("The meter is already in a paid state.")
            else:
                self.__reading = 0
                self.__billing_info = "Paid"
                print("Payment successful. Meter reading and billing info reset.")
        else:
            print("Unauthorized access! Please authenticate to make a payment.")

    def authenticate(self, password):
        if password == "admin123":
            self.__is_authenticated = True
            print("Authentication successful.")
        else:
            print("Authentication failed. Access denied.")

if __name__ == "__main__":
    meter = SmartMeter()

    while True:
        print("1. Get current reading")
        print("2. Set new reading")
        print("3. Get current billing info")
        print("4. Make a payment")
        print("5. Authenticate")
        print("0. Exit")

        choice = input("Enter your choice: ")

        if choice == "1":
            print("\nCurrent reading:", meter.get_reading())
            print()
        elif choice == "2":
            if meter._SmartMeter__is_authenticated:
                while True:
                    try:
                        new_reading = int(input("Enter the new reading: "))
                        meter.set_reading(new_reading)
                        break
                    except ValueError:
                        print("Invalid input! Reading must be an integer.")
                print()
            else:
                print("Unauthorized access! Please authenticate to modify the reading.")
                print()
        elif choice == "3":
            print("\nCurrent billing info:", meter.get_billing_info())
            print()
        elif choice == "4":
            if meter._SmartMeter__is_authenticated:
                meter.make_payment()
                print()
            else:
                print("Unauthorized access! Please authenticate to make a payment.")
                print()
        elif choice == "5":
            password = input("Enter the password: ")
            meter.authenticate(password)
            print()
        elif choice == "0":
            print("Exiting...")
            break
        else:
            print("Invalid choice. Please try again.")
            print()
