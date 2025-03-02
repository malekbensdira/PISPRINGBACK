import pandas as pd
import numpy as np

# Génération de 500 biens immobiliers avec des valeurs aléatoires réalistes
np.random.seed(42)  # Pour des résultats reproductibles

n = 500
data = {
    "superficie": np.random.randint(50, 500, size=n),  # Superficie entre 50 et 500 m²
    "latitude": np.random.uniform(36.7, 36.9, size=n),  # Autour de Tunis
    "longitude": np.random.uniform(10.0, 10.3, size=n),  # Autour de Tunis
    "nombrePieces": np.random.randint(1, 10, size=n),  # De 1 à 10 pièces
    "distanceCentre": np.random.uniform(0.5, 30, size=n),  # De 0.5 km à 30 km
    "distanceEcoles": np.random.uniform(0.2, 15, size=n),  # De 0.2 km à 15 km
}

# Prix généré en fonction de la superficie et de la distance du centre
data["prix"] = data["superficie"] * np.random.uniform(800, 3000, size=n) / (1 + data["distanceCentre"] / 10)

# Création du DataFrame et enregistrement dans un fichier CSV
df = pd.DataFrame(data)
df.to_csv("immobilier_data.csv", index=False, encoding="utf-8")

print("Fichier `immobilier_data.csv` généré avec succès !")
