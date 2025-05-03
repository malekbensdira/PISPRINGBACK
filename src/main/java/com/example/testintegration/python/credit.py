from flask import Flask, request, jsonify, send_file
import joblib
import numpy as np
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
import io
import pandas as pd

app = Flask(__name__)

# Charger les modèles et le scaler (si nécessaire pour prédiction/recommandation)
# Pour ce cas, on suppose que ces fichiers ne sont pas nécessaires pour le contrat
# model = joblib.load("knn_model.pkl")
# scaler = joblib.load("scaler.pkl")
# knn_recommender = joblib.load("knn_recommender.pkl")
# df = pd.read_csv("credit_data.csv")

# 📄 Route pour générer un contrat de crédit en PDF
@app.route('/generate-credit-contract', methods=['POST'])
def generate_credit_contract():
    try:
        data = request.get_json()

        # Champs requis pour le contrat de crédit
        required_fields = [
            "customer_name", "customer_id_card", "bank_name", "bank_representative",
            "credit_amount", "loan_term", "interest_rate"
        ]
        for field in required_fields:
            if field not in data:
                return jsonify({"error": f"Champ '{field}' manquant"}), 400

        # Validation des données
        if not isinstance(data["credit_amount"], (int, float)) or data["credit_amount"] <= 0:
            return jsonify({"error": "Le montant du crédit doit être un nombre positif"}), 400
        if not isinstance(data["loan_term"], int) or data["loan_term"] <= 0:
            return jsonify({"error": "La durée du prêt doit être un entier positif (en mois)"}), 400
        if not isinstance(data["interest_rate"], (int, float)) or data["interest_rate"] < 0:
            return jsonify({"error": "Le taux d'intérêt doit être un nombre positif"}), 400

        pdf_buffer = generate_credit_pdf(data)

        return send_file(
            pdf_buffer,
            as_attachment=True,
            download_name="contrat_credit.pdf",
            mimetype='application/pdf'
        )

    except Exception as e:
        return jsonify({"error": f"Erreur lors de la génération du PDF : {str(e)}"}), 500

# Fonction pour générer un PDF de contrat de crédit bien mis en page
def generate_credit_pdf(content):
    buffer = io.BytesIO()
    c = canvas.Canvas(buffer, pagesize=letter)

    # Titre
    c.setFont("Helvetica-Bold", 16)
    c.drawCentredString(300, 750, "CONTRAT DE CRÉDIT BANCAIRE")

    # Introduction
    c.setFont("Helvetica", 12)
    c.drawString(100, 720, "Entre les soussignés :")

    c.drawString(100, 700, f"{content['bank_name']}, représenté par {content['bank_representative']},")
    c.drawString(100, 680, "ci-après dénommé 'Le Prêteur',")
    c.drawString(100, 660, "d'une part, et")

    c.drawString(100, 640, f"M. {content['customer_name']}, titulaire de la carte d'identité n° {content['customer_id_card']},")
    c.drawString(100, 620, "ci-après dénommé 'L'Emprunteur',")
    c.drawString(100, 600, "d'autre part,")

    c.drawString(100, 580, "Il a été convenu ce qui suit :")

    # Détails du crédit
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 560, "Article 1 : Objet du contrat")
    c.setFont("Helvetica", 12)
    c.drawString(100, 540, f"Le Prêteur met à disposition de l'Emprunteur un crédit d'un montant de {content['credit_amount']} EUR,")
    c.drawString(100, 520, f"pour une durée de {content['loan_term']} mois, avec un taux d'intérêt annuel de {content['interest_rate']} %.")

    # Conditions de remboursement
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 500, "Article 2 : Conditions de remboursement")
    c.setFont("Helvetica", 12)
    c.drawString(100, 480, "L'Emprunteur s'engage à rembourser le montant du crédit selon un échéancier convenu,")
    c.drawString(100, 460, "incluant le capital et les intérêts, payable mensuellement.")

    # Responsabilités
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 440, "Article 3 : Obligations des parties")
    c.setFont("Helvetica", 12)
    c.drawString(100, 420, "Le Prêteur s'engage à fournir les fonds dans les délais convenus.")
    c.drawString(100, 400, "L'Emprunteur s'engage à respecter les termes de paiement et à informer")
    c.drawString(100, 380, "le Prêteur en cas de difficultés financières.")

    # Signatures
    c.setFont("Helvetica-Bold", 14)
    c.drawString(100, 340, "Fait en double exemplaire, le __/__/____")

    c.drawString(100, 300, "Signature de l'Emprunteur :")
    c.drawString(350, 300, "Signature du Prêteur :")

    c.line(100, 280, 250, 280)
    c.line(350, 280, 500, 280)

    c.showPage()
    c.save()

    buffer.seek(0)
    return buffer

if __name__ == '__main__':
    app.run(debug=True)