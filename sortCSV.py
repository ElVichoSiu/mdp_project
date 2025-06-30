import pandas as pd


df = pd.read_csv("Books_rating.csv")

df["review/time"] = pd.to_numeric(df["review/time"], errors="coerce")

# hacer el sort 
df = df.sort_values(by="review/time")

# guardar
df.to_csv("sorted.csv", index=False)