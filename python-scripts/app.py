from flask import Flask, request, jsonify, send_file
import joblib
import numpy as np
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
import io
import pandas as pd

app = Flask(__name__)

# Charger les modèles et le scaler
model = joblib.load("knn_model.pkl")
scaler = joblib.load("scaler.pkl")
knn_recommender = joblib.load("knn_recommender.pkl")  # Modèle pour recommandations
df = pd.read_csv("immobilier_data.csv")  # Charger les données

VALID_TYPES = ["Appartement", "Maison", "Terrain"]

# 📌 Route pour la prédiction des prix immobiliers
@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()

    if "type_immobilier" not in data:
        return jsonify({"error": "Champ 'type_immobilier' manquant"}), 400

    type_bien = data["type_immobilier"]

    if type_bien not in VALID_TYPES:
        return jsonify({"error": f"Type invalide ! Choisissez parmi {VALID_TYPES}"}), 400

    required_fields = ["superficie", "latitude", "longitude", "nombrePieces", "distanceCentre", "distanceEcoles"]
    for field in required_fields:
        if field not in data:
            return jsonify({"error": f"Champ '{field}' manquant"}), 400

    features = [
        data["superficie"],
        data["latitude"],
        data["longitude"],
        data["nombrePieces"],
        data["distanceCentre"],
        data["distanceEcoles"]
    ]

    features_scaled = scaler.transform([features])
    predicted_price = model.predict(features_scaled)[0]

    return jsonify({"predicted_price": predicted_price}), 200

# 📄 Route pour générer un contrat en PDF
@app.route('/generate-pdf', methods=['POST'])
def generate_contract():
    try:
        data = request.get_json()

        required_fields = [
            "type_immobilier", "superficie", "latitude", "longitude",
            "nombrePieces", "distanceCentre", "distanceEcoles",
            "nom_acheteur", "cin_acheteur", "nom_vendeur", "cin_vendeur"
        ]
        for field in required_fields:
            if field not in data:
                return jsonify({"error": f"Champ '{field}' manquant"}), 400

        pdf_buffer = generate_pdf(data)

        return send_file(pdf_buffer, as_attachment=True, download_name="contrat_achat.pdf", mimetype='application/pdf')

    except Exception as e:
        return jsonify({"error": f"Erreur lors de la génération du PDF : {str(e)}"}), 500

# 🏡 Route pour recommander des biens similaires
@app.route('/recommend', methods=['POST'])
def recommend():
    try:
        data = request.get_json()
        required_fields = ["superficie", "latitude", "longitude", "nombrePieces", "distanceCentre", "distanceEcoles"]

        for field in required_fields:
            if field not in data:
                return jsonify({"error": f"Champ '{field}' manquant"}), 400

        features = np.array([
            data["superficie"],
            data["latitude"],
            data["longitude"],
            data["nombrePieces"],
            data["distanceCentre"],
            data["distanceEcoles"]
        ]).reshape(1, -1)

        features_scaled = scaler.transform(features)
        distances, indices = knn_recommender.kneighbors(features_scaled)
        recommended_properties = df.iloc[indices[0]].to_dict(orient="records")

        return jsonify({"recommended_properties": recommended_properties}), 200

    except Exception as e:
        return jsonify({"error": f"Erreur lors de la recommandation : {str(e)}"}), 500

# Fonction pour générer un PDF bien mis en page
def generate_pdf(content):
    buffer = io.BytesIO()
    c = canvas.Canvas(buffer, pagesize=letter)

    # Titre
    c.setFont("Helvetica-Bold", 16)
    c.drawCentredString(300, 750, "CONTRAT DE VENTE IMMOBILIÈRE")

    # Introduction
    c.setFont("Helvetica", 12)
    c.drawString(100, 720, "Entre les soussignés :")

    c.drawString(100, 700, f"M. {content['nom_vendeur']}, titulaire de la CIN n° {content['cin_vendeur']},")
    c.drawString(100, 680, "ci-après dénommé 'Le Vendeur',")
    c.drawString(100, 660, "d'une part, et")

    c.drawString(100, 640, f"M. {content['nom_acheteur']}, titulaire de la CIN n° {content['cin_acheteur']},")
    c.drawString(100, 620, "ci-après dénommé 'L'Acheteur',")
    c.drawString(100, 600, "d'autre part,")

    c.drawString(100, 580, "Il a été convenu ce qui suit :")

    # Description du bien
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 560, "Article 1 : Désignation du bien immobilier")
    c.setFont("Helvetica", 12)
    c.drawString(100, 540, f"Le bien objet de la présente vente est un(e) {content['type_immobilier']},")
    c.drawString(100, 520, f"d'une superficie de {content['superficie']} m², composé de {content['nombrePieces']} pièces,")
    c.drawString(100, 500, f"situé à la latitude {content['latitude']} et longitude {content['longitude']}.")
    c.drawString(100, 480, f"Ce bien se trouve à {content['distanceCentre']} km du centre-ville et à {content['distanceEcoles']} km des écoles.")

    # Conditions générales
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 460, "Article 2 : Conditions générales")
    c.setFont("Helvetica", 12)
    c.drawString(100, 440, "Le Vendeur s'engage à céder le bien à l'Acheteur, libre de toute")
    c.drawString(100, 420, "hypothèque ou charge, et en parfait état d'usage.")

    c.drawString(100, 400, "L'Acheteur s'engage à respecter toutes les conditions légales")
    c.drawString(100, 380, "afférentes à l'acquisition de ce bien immobilier.")

    # Signatures
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 340, "Fait en double exemplaire, le __/__/____")

    c.drawString(100, 300, "Signature de l'Acheteur :")
    c.drawString(350, 300, "Signature du Vendeur :")

    c.line(100, 280, 250, 280)
    c.line(350, 280, 500, 280)

    c.showPage()
    c.save()

    buffer.seek(0)
    return buffer

if __name__ == '__main__':
    app.run(debug=True)
