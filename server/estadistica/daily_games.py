import pandas as pd
import matplotlib.pyplot as plt
import json

# Read JSON data from file
with open('daily_games.json', 'r') as file:
    data = json.load(file)

# Convert dictionary to pandas DataFrame
df = pd.DataFrame(list(data.items()), columns=['Date', 'Games Played'])

# Convert 'Date' column to datetime type
df['Date'] = pd.to_datetime(df['Date'])

# Sort DataFrame by 'Date'
df = df.sort_values(by='Date')

# Plotting
plt.figure(figsize=(12, 6))
plt.bar(df['Date'], df['Games Played'], color='blue')
plt.xlabel('Date')
plt.ylabel('Games Played')
plt.title('Number of Games Played Each Day')
plt.xticks(rotation=45)
plt.tight_layout()

# Save the plot as an image
plt.savefig('games_played_bar_chart.png')

