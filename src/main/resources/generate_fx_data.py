import json
import random
from datetime import datetime, timedelta

def generate_fx_data(num_rows):
    fx_data = []
    currency_pairs = [
        "GBP/USD", "EUR/JPY", "USD/CHF", "AUD/CAD", "NZD/USD",
        "USD/CAD", "EUR/USD", "GBP/JPY", "AUD/USD", "CAD/JPY"
    ]
    exchanges = ["LSEG", "BloombergFX", "Refinitiv", "Xetra", "EBS"]

    base_timestamp = datetime(2025, 5, 29, 15, 45, 0) # Start from a specific time

    for i in range(num_rows):
        symbol = random.choice(currency_pairs)
        # Simulate small fluctuations around typical values
        if symbol == "GBP/USD":
            base_bid = 1.2700
            bid_range = 0.001
        elif symbol == "EUR/JPY":
            base_bid = 169.200
            bid_range = 0.05
        elif symbol == "USD/CHF":
            base_bid = 0.9080
            bid_range = 0.0005
        elif symbol == "AUD/CAD":
            base_bid = 0.9120
            bid_range = 0.001
        elif symbol == "NZD/USD":
            base_bid = 0.6150
            bid_range = 0.0005
        elif symbol == "USD/CAD":
            base_bid = 1.3650
            bid_range = 0.001
        elif symbol == "EUR/USD":
            base_bid = 1.0820
            bid_range = 0.001
        elif symbol == "GBP/JPY":
            base_bid = 205.500
            bid_range = 0.07
        elif symbol == "AUD/USD":
            base_bid = 0.6650
            bid_range = 0.0005
        elif symbol == "CAD/JPY":
            base_bid = 124.000
            bid_range = 0.05
        else:
            base_bid = 1.0000 # Fallback
            bid_range = 0.001

        bid = round(base_bid + (random.random() - 0.5) * bid_range * 2, 4 if 'JPY' not in symbol else 3)
        # Ask is always slightly higher than bid, simulate a realistic spread
        spread = random.uniform(0.0003, 0.0007) if 'JPY' not in symbol else random.uniform(0.02, 0.05)
        ask = round(bid + spread, 4 if 'JPY' not in symbol else 3)

        # Increment timestamp by a small random amount (e.g., 50 to 500 milliseconds)
        current_timestamp = base_timestamp + timedelta(milliseconds=i * random.randint(50, 500))
        timestamp_ms = int(current_timestamp.timestamp() * 1000)

        exchange = random.choice(exchanges)

        fx_data.append({
            "symbol": symbol,
            "bid": bid,
            "ask": ask,
            "timestamp": timestamp_ms,
            "exchange": exchange
        })
    return fx_data

# Generate rows
num_rows_to_generate = 100
data = generate_fx_data(num_rows_to_generate)

# Write to JSON file
file_name = "fx_rates.json"
with open(file_name, 'w') as f:
    json.dump(data, f, indent=2)

print(f"Generated {num_rows_to_generate} rows of FX data and saved to {file_name}")