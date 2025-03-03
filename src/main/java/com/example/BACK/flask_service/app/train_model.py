from sklearn.svm import SVC
from sklearn.feature_extraction.text import TfidfVectorizer
import joblib

# 📌 1. Nouveau Dataset (Textes + Étiquettes)
X_train = [
    # ✅ Positif (1)
    "I love this product!", "This is absolutely amazing!", "Fantastic service, I will definitely come back.",
    "The best experience I’ve ever had!", "Highly recommended, excellent quality!",
    "This is exactly what I was looking for!", "Very satisfied with my purchase.",
    "Everything was perfect, thank you!", "Outstanding customer support!",
    "The delivery was super fast and the item is great!",

    # ❌ Négatif (0)
    "This is terrible.", "I hate this, what a waste of money.", "Very disappointed with the service.",
    "This is the worst experience ever.", "I will never buy from here again.",
    "Horrible product, it broke after one use.", "Customer support was useless and rude.",
    "The quality is awful, not worth the price.", "I waited too long for delivery, very frustrating!",
    "Completely dissatisfied with my experience.",

    # ⚖️ Neutre (2)
    "It's okay, not great but not bad.", "I don't have much to say about this.",
    "It was an average experience.", "Nothing special, just a normal service.",
    "The product is fine, but I’ve seen better.", "Not the best, not the worst.",
    "It does what it says, no surprises.", "A standard experience, nothing extraordinary.",
    "I guess it depends on personal preference.", "Neutral feeling, neither happy nor unhappy."
]

y_train = [1] * 10 + [0] * 10 + [2] * 10  # 📌 10 Positifs, 10 Négatifs, 10 Neutres

# 📌 2. Vectorisation des textes
vectorizer = TfidfVectorizer()
X_train_vec = vectorizer.fit_transform(X_train)

# 📌 3. Création et entraînement du modèle SVM
model = SVC(kernel='linear')
model.fit(X_train_vec, y_train)

# 📌 4. Sauvegarde du modèle et du vectorizer
joblib.dump(model, 'svm_model.pkl')
joblib.dump(vectorizer, 'tfidf_vectorizer.pkl')

print("✅ Modèle SVM réentraîné avec succès et sauvegardé ! 🚀")
