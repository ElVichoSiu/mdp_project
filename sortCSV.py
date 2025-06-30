import pandas as pd
df = pd.read_csv("Books_rating.csv", sep=r',(?=(?:[^"]*"[^"]*")*[^"]*$)', engine='python')

df["review/time"] = pd.to_numeric(df["review/time"], errors="coerce")

df = df.sort_values(by=["Id", "review/time"])
df.to_csv("sorted2.csv", index=False)