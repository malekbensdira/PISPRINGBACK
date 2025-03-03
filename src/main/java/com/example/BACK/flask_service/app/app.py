import joblib
from flask import Flask, request, jsonify

app = Flask(__name__)

# 📌 1. Charger le modèle mis à jour
model = joblib.load('svm_model.pkl')
vectorizer = joblib.load('tfidf_vectorizer.pkl')

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    message = data['message']

    # 📌 2. Transformer le texte en vecteur TF-IDF
    vectorized_message = vectorizer.transform([message])

    # 📌 3. Faire une prédiction
    prediction = model.predict(vectorized_message)
    sentiment = {0: "Negative", 1: "Positive", 2: "Neutral"}

    return jsonify({"sentiment": sentiment[int(prediction[0])]})

if __name__ == '__main__':
    app.run(debug=True)
