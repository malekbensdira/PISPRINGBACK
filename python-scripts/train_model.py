import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVR
from sklearn.neighbors import KNeighborsRegressor
import joblib

# Charger les données
df = pd.read_csv("immobilier_data.csv")

# 🏠 Sélection des features et de la target (prix)
features = ["superficie", "latitude", "longitude", "nombrePieces", "distanceCentre", "distanceEcoles"]
target = "prix"

X = df[features]
y = df[target]

# 🌟 Normalisation des données
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# 🎯 Séparation des données en train et test
X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, test_size=0.2, random_state=42)

# 🚀 Modèle SVM
svm_model = SVR(kernel="rbf")
svm_model.fit(X_train, y_train)

# 🚀 Modèle KNN
knn_model = KNeighborsRegressor(n_neighbors=5)
knn_model.fit(X_train, y_train)

# 💾 Sauvegarde des modèles et du scaler
joblib.dump(svm_model, "svm_model.pkl")
joblib.dump(knn_model, "knn_model.pkl")
joblib.dump(scaler, "scaler.pkl")
knn_recommender = KNeighborsRegressor(n_neighbors=5)
knn_recommender.fit(X_train, y_train)

# 💾 Sauvegarde du modèle de recommandation
joblib.dump(knn_recommender, "knn_recommender.pkl")

print("✅ Modèle de recommandation KNN entraîné et sauvegardé !")

print("✅ Modèles entraînés et sauvegardés !")
