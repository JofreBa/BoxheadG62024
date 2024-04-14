import pandas as pd
import matplotlib.pyplot as plt
import json

# Read JSON data from file
with open('games.json', 'r') as file:
    data = json.load(file)

# Convert JSON data to pandas DataFrame
df = pd.DataFrame(data)

# Convert 'duration' to seconds
df['duration_seconds'] = df['duration'].apply(lambda x: int(x.split(':')[0]) * 60 + int(x.split(':')[1]))

# Calculate overall average game duration and average score
overall_avg_duration = df['duration_seconds'].mean()
overall_avg_score = df['score'].mean()

# Convert average duration from seconds to minutes and seconds
overall_avg_duration_formatted = f"{int(overall_avg_duration // 60)}:{int(overall_avg_duration % 60):02d}"

# Create table image using matplotlib
fig, ax = plt.subplots(figsize=(8, 4))

ax.axis('tight')
ax.axis('off')

data = [['Overall', overall_avg_duration_formatted, overall_avg_score]]

table = ax.table(
    cellText=data,
    colLabels=['Username', 'Average Duration (MM:SS)', 'Average Score'],
    cellLoc='center',
    loc='center',
    colWidths=[0.2, 0.3, 0.2]
)

table.auto_set_font_size(False)
table.set_fontsize(12)
table.auto_set_column_width(col=list(range(len(data[0]))))

plt.title('Overall Average Game Duration and Score', fontsize=14)
plt.tight_layout()
plt.savefig('overall_average_game_table.png', dpi=200)


